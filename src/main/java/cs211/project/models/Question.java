package cs211.project.models;

import java.util.HashMap;
import java.util.UUID;

public class Question {
    private final UUID id;
    private UUID eventId;
    private String question;
    private String type;
    private String answer;

    public Question(String eventId,String question, String type) {
        this.id = UUID.randomUUID();
        this.eventId = UUID.fromString(eventId);
        this.question = question;
        this.type = type;
        this.answer = "";
    }

    public Question(HashMap<String, String> data) {
        this.id = UUID.fromString(data.get("id").trim());
        this.eventId = UUID.fromString(data.get("eventId").trim());
        this.question = data.get("question").trim();
        this.type = data.get("type").trim();
//        this.question = data.get("answer").trim();
    }
    public String getId() { return id.toString(); }

    public String getEventId() { return eventId.toString(); }
    public String getQuestion() { return question; }

    public String getType() { return type; }

    public String getAnswer() { return answer; }

    public void setEventId(String eventId) { this.eventId = UUID.fromString(eventId); }

    public void setQuestion(String question) { this.question = question; }

    public void setType(String type) { this.type = type; }

    public void setAnswer(String answer) { this.answer = answer; }

    //Methods (For WriteFile)
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> data = new HashMap<>();
        data.put("id", id.toString());
        data.put("eventId", eventId.toString());
        data.put("question", question);
        data.put("type", type);
        data.put("answer", answer);
        return data;
    }
}
