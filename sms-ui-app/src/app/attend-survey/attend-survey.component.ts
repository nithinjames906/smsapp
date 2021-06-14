import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceResponse, Survey, User, UserSurvey, UserSurveyResponse, UserSurveyRequest, Question } from '../_models';
import { AlertService, AuthenticationService, CustomerService } from '../_services';

@Component({
  selector: 'app-attend-survey',
  templateUrl: './attend-survey.component.html',
  styleUrls: ['./attend-survey.component.css']
})
export class AttendSurveyComponent implements OnInit {

  loading = false;
  showThanks: boolean = false;
  survey: Survey = new Survey();
  currentUser: User;
  surveyForm: FormGroup;
  questionIndex: number;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private customerService: CustomerService,
    private alertService: AlertService
  ) {
    this.currentUser = this.authenticationService.currentUserValue;
  }

  ngOnInit() {
    this.loading = true;
    let surveyId = this.route.snapshot.params.surveyId;
    let userId = this.route.snapshot.params.userId;
    this.surveyForm = this.fb.group({
      responseDetails: this.fb.array([], [Validators.required])
    });
    this.customerService.getSurvey(parseInt(surveyId)).subscribe((resp: ServiceResponse) => {
      this.loading = false;
      if (resp.status === 'Success') {
        this.survey = new Survey().fromJSON(resp.data);
        let userId = !this.currentUser ? 0 : this.currentUser.userId;
        this.setUserSurvey(userId);
      }
    });
  }

  responseDetails(): FormArray {
    return this.surveyForm.get("responseDetails") as FormArray
  }

  addSurveyResponseDetail() {
    this.responseDetails().push(this.newSurveyResponseDetail());
  }

  newSurveyResponseDetail(): FormGroup {
    return this.fb.group({
      questionId: [],
      body: [],
      type: [],
      multiSelect: [],
      responseList: this.fb.array([], Validators.required),
      displayed: [false],
      required: [false]
    })
  }

  switchQuestion(i: number) {
    this.questionIndex = i;
    if (this.responseDetails().controls[i])
      this.responseDetails().controls[i].get('displayed').patchValue(true);
  }

  next() {
    this.responseDetails().controls[this.questionIndex].get('displayed').patchValue(false);
    this.switchQuestion(this.questionIndex + 1);
  }

  previous() {
    this.responseDetails().controls[this.questionIndex].get('displayed').patchValue(false);
    this.switchQuestion(this.questionIndex - 1);
  }

  responseList(index: number): FormArray {
    return this.responseDetails().controls[index].get('responseList') as FormArray
  }

  newUserResponse(): FormGroup {
    return this.fb.group({
      option: [],
      response: ['', Validators.required]
    })
  }

  addUserResponse(index: number) {
    this.responseList(index).push(this.newUserResponse());
  }

  setUserSurvey(userId: number) {
    this.loading = true;
    this.customerService.getUserSurveyResponse(this.survey.surveyId, userId).subscribe((resp: ServiceResponse) => {
      this.survey.questions.forEach((qtn, i) => {
        this.setQuestionInfo(i, qtn);
        qtn.options.forEach((option, j) => {
          if (!this.responseList(i).controls[j])
            this.addUserResponse(i);
          this.responseList(i).controls[j].get('option').patchValue(option);
          if (resp.status === 'Success') {
            let userSurvey = new UserSurvey().fromJSON(resp.data);
            if (userSurvey.responseDetails.length > 0) {
              let surveyDetail = userSurvey.responseDetails[i];
              this.setUserResponseInfo(surveyDetail, i, j, qtn, option);
            }
          }
        });
        this.loading = false;
      });
      this.switchQuestion(0);
    });
  }

  private setUserResponseInfo(responseDetail: UserSurveyResponse, surveyIndex: number, responseIndex: number, qtn: Question, option: never) {
    let userResponse = responseDetail.responseList[responseIndex];
    if (qtn.type === 'CHOICE' && qtn.multiSelect && responseDetail.responseList.includes(option))
      this.responseList(surveyIndex).controls[responseIndex].get('response').patchValue(true);
    else if (qtn.type === 'CHOICE' && option === userResponse)
      this.responseList(surveyIndex).controls[responseIndex].get('response').patchValue(userResponse);
    else if (qtn.type === 'FREETEXT')
      this.responseList(surveyIndex).controls[responseIndex].get('response').patchValue(userResponse);
  }

  private setQuestionInfo(i: number, qtn: Question) {
    if (!this.responseDetails().controls[i])
      this.addSurveyResponseDetail();
    this.responseDetails().controls[i].get('questionId').patchValue(qtn.questionId);
    this.responseDetails().controls[i].get('body').patchValue(qtn.body);
    this.responseDetails().controls[i].get('multiSelect').patchValue(qtn.multiSelect);
    this.responseDetails().controls[i].get('type').patchValue(qtn.type);
    this.responseDetails().controls[i].get('required').patchValue(qtn.required);
  }

  getSurveyDetailControl(questionIndex: number, controlName: string) {
    return this.responseDetails().controls[questionIndex].get(controlName);
  }

  getResponseControl(questionIndex: number, responseIndex: number, controlName: string) {
    return this.responseList(questionIndex).controls[responseIndex].get(controlName);
  }

  isRequired(i: number) {
    if (!this.getSurveyDetailControl(i, 'body').value) {
      return false;
    } else {
      return true;
    }
  }

  isValidResponse(respList) {
    if (respList.length == 0 || respList.includes("") || respList.includes(undefined) || respList.includes(null)) {
      return false;
    } else
      return true;
  }

  onSubmit() {
    let qtnsRequired = [];
    this.loading = true;
    let formValue = this.surveyForm.value;
    let body = new UserSurveyRequest();
    formValue.responseDetails.forEach((detail, index) => {
      let respDetail = new UserSurveyResponse();
      if (detail.type === 'CHOICE' && detail.multiSelect) {
        let selectedChoices = detail.responseList.filter(obj => obj.response);
        respDetail.responseList = selectedChoices.map(obj => obj.option);
      } else if (detail.type === 'CHOICE') {
        let selectedChoice = detail.responseList.find(obj => obj.option === obj.response);
        if (selectedChoice)
          respDetail.responseList.push(selectedChoice.response);
      } else if (detail.type === 'FREETEXT' && detail.responseList.length > 0) {
        respDetail.responseList.push(detail.responseList[0].response);
      }
      if (detail.required && !this.isValidResponse(respDetail.responseList)) {
        qtnsRequired.push(index + 1);
      }
      respDetail.questionId = detail.questionId;
      body.surveyId = this.survey.surveyId;
      body.userId = !this.currentUser ? 0 : this.currentUser.userId;
      body.surveyResponses.push(respDetail);
    });
    console.log(body);
    if (qtnsRequired.length == 0)
      this.customerService.updateSurveyResponse(body).subscribe((resp: ServiceResponse) => {
        if (resp.status === 'Success') {
          setTimeout(() => {
            this.alertService.success('User Response Saved Successfully', false);
            this.showThanks = true;
            this.alertService.clear();
            this.loading = false;
          }, 1000);
        } else {
          console.log(resp.message);
          this.alertService.error('Service Failure', true);
          this.loading = false;
        }
      });
    else {
      this.alertService.error('Response required for the Question Q-' + qtnsRequired[0] + '.');
      this.loading = false;
    }
  }
}
