import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserSurvey, UserSurveyRequest } from '../_models';

const API_URL = environment.API_URL;

@Injectable({ providedIn: 'root' })
export class CustomerService {
    constructor(private http: HttpClient) { }

    getAllSurveys() {
        return this.http.get(`${API_URL}/customer/surveys`);
    }

    getSurvey(surveyId: number) {
        return this.http.get(`${API_URL}/customer/surveys/${surveyId}`);
    }

    getUserSurveyResponse(surveyId: number, userId: number) {
        return this.http.get(`${API_URL}/customer/surveys/response/${surveyId}/${userId}`);
    }

    getUserSurveyResponses(userId: number) {
        return this.http.get(`${API_URL}/customer/surveys/responses/${userId}`);
    }


    updateSurveyResponse(userSurvey: UserSurveyRequest) {
        return this.http.post(`${API_URL}/customer/surveys/response`, userSurvey);
    }
}