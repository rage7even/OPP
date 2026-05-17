package university.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.core.University;
import university.reports.LogEntry;
import university.users.User;

public class LogService implements Serializable {
    private static final long serialVersionUID = 1L;

    public void log(User user, String action) {
        String userId = user == null ? "SYSTEM" : user.getId();
        University.getInstance().addLogEntry(new LogEntry(userId, action));
    }

    public List<LogEntry> getEntries() {
        return Collections.unmodifiableList(new ArrayList<LogEntry>(University.getInstance().getLogEntries()));
    }
}
