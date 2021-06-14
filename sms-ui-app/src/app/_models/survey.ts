import { Question } from "./question";

export class Survey {
    surveyId: number;
    title: string;
    version: string;
    status: string;
    userId: number;
    parentSurveyId: number;
    rootSurveyId: number;
    createdDate: string;
    updatedDate: string;
    questions: Question[];

    constructor() {
        this.questions = [];
    }

    fromJSON(json) {
        for (var propName in json) {
            if (propName === 'questions' && json[propName])
                json[propName].forEach(asset => {
                    let question: Question = new Question().fromJSON(asset);
                    this.questions.push(question);
                });
            else
                this[propName] = json[propName];
        }
        return this;
    }
}