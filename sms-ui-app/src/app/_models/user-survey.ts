import { UserSurveyResponse } from "./user-survey-response";

export class UserSurvey {
    surveyId: number;
    userId: number;
    responseDetails: UserSurveyResponse[];

    constructor() {
        this.responseDetails = [];
    }

    fromJSON(json) {
        for (var propName in json) {
            if (propName === 'responseDetails' && json[propName])
                json[propName].forEach(userSurvey => {
                    let resp: UserSurveyResponse = new UserSurveyResponse().fromJSON(userSurvey);
                    this.responseDetails.push(resp);
                });
            else
                this[propName] = json[propName];
        }
        return this;
    }
}