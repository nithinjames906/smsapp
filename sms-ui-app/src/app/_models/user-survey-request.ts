import { UserSurveyResponse } from "./user-survey-response";

export class UserSurveyRequest {
    surveyId: number;
    userId: number;
    surveyResponses: UserSurveyResponse[];

    constructor() {
        this.surveyResponses = [];
    }

    fromJSON(json) {
        for (var propName in json) {
            if (propName === 'surveyResponses' && json[propName])
                json[propName].forEach(userSurvey => {
                    let resp: UserSurveyResponse = new UserSurveyResponse().fromJSON(userSurvey);
                    this.surveyResponses.push(resp);
                });
            else
                this[propName] = json[propName];
        }
        return this;
    }
}