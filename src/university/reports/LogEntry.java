package university.reports;

import java.io.Serializable;
import java.util.Date;

public class LogEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date timestamp;
    private String userId;
    private String action;

    public LogEntry(String userId, String action) {
        this.timestamp = new Date();
        this.userId = userId;
        this.action = action;
    }

    public Date getTimestamp() {
        return new Date(timestamp.getTime());
    }

    public String getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + userId + ": " + action;
    }
}
