import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServiceResponse, Survey } from '../_models';
import { AlertService, AdminService } from '../_services';

@Component({
  selector: 'app-surveys',
  templateUrl: './surveys.component.html',
  styleUrls: ['./surveys.component.css']
})
export class SurveysComponent implements OnInit {

  loading: boolean = false;
  surveys: Survey[];

  constructor(
    private router: Router,
    private adminService: AdminService,
    private alertService: AlertService
  ) { }

  ngOnInit() {
    this.adminService.getAllSurveys().subscribe((resp: ServiceResponse) => {
      if (resp.status === 'Success') {
        this.surveys = <Survey[]>resp.data;
      } else {
        console.log(resp.message);
        this.alertService.error('Service Failure', true);
      }
    });
  }

  modifySurvey(id) {
    this.router.navigate(['/modify-survey/' + id]);
  }

  publishSurvey(id) {
    this.loading = true;
    this.adminService.publishSurvey(id, this.surveys.find(obj => obj.surveyId == id)).subscribe((resp: ServiceResponse) => {
      this.loading = false;
      if (resp.status === 'Success') {
        this.alertService.success('Survey Published Successfully', true);
        this.ngOnInit();
      } else {
        console.log(resp.message);
        this.alertService.error('Service Failure', true);
      }
    });
  }

}
