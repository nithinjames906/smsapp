import { Question } from "./question";

export class UserSurveyResponse {

    questionDetail: Question;
    questionId: number;
    responseList: String[];

    constructor() {
        this.responseList = [];
    }

    fromJSON(json) {
        for (var propName in json) {
            if (propName === 'responseList' && json[propName])
                json[propName].forEach(resp => {
                    this.responseList.push(resp);
                });
            else
                this[propName] = json[propName];
        }
        return this;
    }
}