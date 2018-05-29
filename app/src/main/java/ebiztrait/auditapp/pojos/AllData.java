package ebiztrait.auditapp.pojos;

import java.util.ArrayList;

public class AllData {
    String question;
    ArrayList<String> answers;
    ArrayList<String> options;
    String imagePath;

    public AllData(String question, ArrayList<String> answers, ArrayList<String> options, String imagePath) {
        this.question = question;
        this.answers = answers;
        this.options = options;
        this.imagePath = imagePath;
    }

    public AllData() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
