package ebiztrait.auditapp.pojos;

import java.util.ArrayList;

public class AnswersObject {
    String answerId;
    String questionId;
    ArrayList<String> answers;
    String imagePath;

    public AnswersObject() {
    }


    public AnswersObject(String questionId, ArrayList<String> answers, String imagePath) {
        this.questionId = questionId;
        this.answers = answers;
        this.imagePath = imagePath;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
