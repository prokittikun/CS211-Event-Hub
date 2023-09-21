package cs211.project.models;

import java.util.HashMap;
import java.util.UUID;

public class Answer {
    private UUID id;
    private UUID userId;
    private String answer;
    private UUID questionId;
    private String timestamp;

    public Answer(String userId, String answer, String questionId, String timestamp) {
        this.id = UUID.randomUUID();
        this.userId = UUID.fromString(userId);
        this.answer = answer;
        this.questionId = UUID.fromString(questionId);
        this.timestamp = timestamp;
    }

    public Answer(HashMap<String, String> data) {
        this.id = UUID.fromString(data.get("id"));
        this.userId = UUID.fromString(data.get("userId"));
        this.answer = data.get("answer");
        this.questionId = UUID.fromString(data.get("questionId"));
        this.timestamp = data.get("timestamp");
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> data = new HashMap<>();
        data.put("id",  String.valueOf(this.id));
        data.put("userId",  String.valueOf(this.userId));
        data.put("answer", this.answer);
        data.put("questionId",  String.valueOf(this.questionId));
        data.put("timestamp", this.timestamp);
        return data;
    }
}
