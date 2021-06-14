import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceResponse, Survey, User } from '../_models';
import { AlertService, AdminService, AuthenticationService } from '../_services';

@Component({
  selector: 'app-modify-survey',
  templateUrl: './modify-survey.component.html',
  styleUrls: ['./modify-survey.component.css']
})
export class ModifySurveyComponent implements OnInit {

  loading = false;
  currentUser: User;
  surveyForm: FormGroup;
  responseTypes: String[] = ['CHOICE', 'FREETEXT'];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private adminService: AdminService,
    private alertService: AlertService
  ) {
    this.currentUser = this.authenticationService.currentUserValue;
  }

  ngOnInit() {
    this.initSurvey();
    let surveyId = this.route.snapshot.params.id;
    if (parseInt(surveyId) !== NaN && parseInt(surveyId) > 0)
      this.adminService.getSurvey(surveyId).subscribe((resp: ServiceResponse) => {
        if (resp.status === 'Success') {
          let survey = new Survey().fromJSON(resp.data);
          this.surveyForm.controls['surveyId'].patchValue(survey.surveyId);
          this.surveyForm.controls['title'].patchValue(survey.title);
          survey.questions.forEach((qtn, i) => {
            if (!this.questions().controls[i])
              this.addQuestion();
            this.questions().controls[i].get('questionId').patchValue(qtn.questionId);
            this.questions().controls[i].get('body').patchValue(qtn.body);
            this.questions().controls[i].get('multiSelect').patchValue(qtn.multiSelect);
            this.questions().controls[i].get('required').patchValue(qtn.required);
            this.questions().controls[i].get('type').patchValue(qtn.type);
            if (qtn.type == 'CHOICE')
              qtn.options.forEach((opt, j) => {
                if (!this.options(i).controls[j])
                  this.addOption(i);
                this.options(i).controls[j].get('option').patchValue(opt);
              });
          });
          this.loading = false;
        } else {
          this.loading = false;
          this.alertService.error('Service Failure', false);
        }
      });
  }

  initSurvey() {
    this.surveyForm = this.fb.group({
      surveyId: [],
      title: ['', Validators.required],
      questions: this.fb.array([], [Validators.required]),
    });
    this.addQuestion();
  }

  questions(): FormArray {
    return this.surveyForm.get("questions") as FormArray
  }

  newQuestion(): FormGroup {
    return this.fb.group({
      questionId: [],
      body: ['', Validators.required],
      type: ['FREETEXT', Validators.required],
      multiSelect: [false],
      required: [false],
      options: this.fb.array([])
    })
  }

  newOption(): FormGroup {
    return this.fb.group({
      option: ['']
    })
  }

  addQuestion() {
    this.questions().push(this.newQuestion());
  }

  removeQuestion(i: number) {
    this.questions().removeAt(i);
  }

  options(questionIndex: number): FormArray {
    return this.questions().controls[questionIndex].get("options") as FormArray
  }

  addOption(questionIndex: number) {
    this.options(questionIndex).push(this.newOption());
  }

  removeOption(questionIndex: number, index: number) {
    this.options(questionIndex).removeAt(index);
  }

  onSubmit() {
    this.loading = true;
    console.log(this.surveyForm.value);
    let body = { ...this.surveyForm.value };
    body.questions.forEach(qtn => {
      qtn.options = qtn.options.map(opt => opt.option);
    });
    console.log(body);
    let survey = new Survey().fromJSON(body);
    survey.userId = this.currentUser.userId;
    if (survey.surveyId)
      this.adminService.updateSurvey(survey).subscribe((resp: ServiceResponse) => {
        this.loading = false;
        if (resp.status === 'Success') {
          this.alertService.success('Survey Updated Successfully', true);
          this.router.navigate(['/surveys']);
        } else {
          console.log(resp.message);
          this.alertService.error('Service Failure', true);
        }
      });
    else
      this.adminService.addSurvey(survey).subscribe((resp: ServiceResponse) => {
        this.loading = false;
        if (resp.status === 'Success') {
          this.alertService.success('Survey Added Successfully', true);
          this.router.navigate(['/surveys']);
        } else {
          console.log(resp.message);
          this.alertService.error('Service Failure', true);
        }
      });
  }

}
