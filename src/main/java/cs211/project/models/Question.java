package cs211.project.models;

public class Question {
    private String question;
    private String type;
    private String answer;

    public Question(String question, String type) {
        this.question = question;
        this.type = type;
        this.answer = "";
    }

    public String getQuestion() { return question; }

    public String getType() { return type; }

    public String getAnswer() { return answer; }

    public void setQuestion(String question) { this.question = question; }

    public void setType(String type) { this.type = type; }

    public void setAnswer(String answer) { this.answer = answer; }
}
