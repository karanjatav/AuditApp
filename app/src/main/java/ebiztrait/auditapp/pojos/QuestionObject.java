package ebiztrait.auditapp.pojos;

import java.util.ArrayList;

public class QuestionObject {
    String questionId;
    String question;
    ArrayList<String> options;

    public QuestionObject() {
    }

    public QuestionObject(String questionId, String question, ArrayList<String> options) {
        this.questionId = questionId;
        this.question = question;
        this.options = options;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
