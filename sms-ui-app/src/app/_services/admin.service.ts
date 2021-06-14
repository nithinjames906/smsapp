import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Survey, User } from '../_models';

const API_URL = environment.API_URL;

@Injectable({ providedIn: 'root' })
export class AdminService {
    constructor(private http: HttpClient) { }

    getSurvey(surveyId: number) {
        return this.http.get(`${API_URL}/surveys/${surveyId}`);
    }

    getAllSurveys() {
        return this.http.get(`${API_URL}/surveys`);
    }

    addSurvey(survey: Survey) {
        return this.http.post(`${API_URL}/surveys`, survey);
    }

    updateSurvey(survey: Survey) {
        return this.http.put(`${API_URL}/surveys`, survey);
    }

    publishSurvey(surveyId: number, survey: Survey) {
        return this.http.patch(`${API_URL}/surveys/${surveyId}`, survey);
    }
}