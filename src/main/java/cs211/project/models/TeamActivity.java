package cs211.project.models;

import java.util.HashMap;
import java.util.UUID;

public class TeamActivity extends Activity{

        private UUID teamId;
        public TeamActivity(String id, String teamId, String name, String detail, String startDate, String startTime, String endDate, String endTime) {
            super(id, name, detail, startDate, startTime, endDate, endTime);
            this.teamId = UUID.fromString(teamId);
        }

        public TeamActivity(HashMap<String, String> activity) {
            super(activity);
            this.teamId = UUID.fromString(activity.get("teamId"));
        }

        public String getTeamId() {
            return teamId.toString();
        }

        public void setTeamId(String eventId) {
            this.teamId = UUID.fromString(eventId);
        }

        public HashMap<String, String> toHashMap() {
            HashMap<String, String> activity = new HashMap<>();
            activity.put("id", this.getId());
            activity.put("teamId", this.getTeamId());
            activity.put("name", this.getName());
            activity.put("detail", this.getDetail());
            activity.put("startDate", this.getStartDate());
            activity.put("startTime", this.getStartTime());
            activity.put("endDate", this.getEndDate());
            activity.put("endTime", this.getEndTime());
            activity.put("status", this.getStatus().toString());
            return activity;
        }
}
