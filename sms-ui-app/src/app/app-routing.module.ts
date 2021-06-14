import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AttendSurveyComponent } from './attend-survey/attend-survey.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { ModifySurveyComponent } from './modify-survey/modify-survey.component';
import { RegisterComponent } from './register/register.component';
import { SurveysComponent } from './surveys/surveys.component';
import { AuthGuard } from './_helpers';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'surveys', component: SurveysComponent, canActivate: [AuthGuard] },
  { path: 'modify-survey/:id', component: ModifySurveyComponent, canActivate: [AuthGuard] },
  { path: 'attend-survey/:surveyId', component: AttendSurveyComponent },

  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
