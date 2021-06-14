export class Question {
    questionId: number;
    body: string;
    multiSelect: boolean;
    required: boolean;
    options: [];
    type: string;

    fromJSON(json) {
        for (var propName in json)
            this[propName] = json[propName];
        return this;
    }
}