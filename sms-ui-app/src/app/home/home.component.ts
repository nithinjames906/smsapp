import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ServiceResponse, Survey, SurveyAnalyticInfo, User, UserSurvey } from '../_models';
import { AnalyticUserService, AuthenticationService, CustomerService } from '../_services';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  currentUser: User;
  users = [];
  surveyStats: SurveyAnalyticInfo[] = [];
  surveys: UserSurvey[] = [];

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService,
    private analyticUserService: AnalyticUserService,
    private customerUService: CustomerService
  ) {
    this.currentUser = this.authenticationService.currentUserValue;
  }

  ngOnInit() {
    if (this.currentUser && this.currentUser.userRole === 'Analytic')
      this.analyticUserService.getAnalyticData().subscribe((resp: ServiceResponse) => {
        if (resp.status === 'Success') {
          this.surveyStats = (<SurveyAnalyticInfo[]>resp.data);
        }
      });
    else
      this.customerUService.getUserSurveyResponses(!this.currentUser ? 0 : this.currentUser.userId).subscribe((resp: ServiceResponse) => {
        if (resp.status === 'Success') {
          this.surveys = (<UserSurvey[]>resp.data);
        }
      });
  }

  attendSurvey(surveyId: number) {
    this.router.navigate(['/attend-survey/' + surveyId]);
  }

}