import { environment } from '../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ServiceResponse } from '../_models';

const API_URL = environment.API_URL;

@Injectable({ providedIn: 'root' })
export class AnalyticUserService {
    constructor(private http: HttpClient) { }

    getAnalyticData() {
        return this.http.get(`${API_URL}/analytic/surveys/stats`);
    }
}