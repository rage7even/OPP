package university.services;

import java.io.Serializable;
import java.util.Date;

import university.users.User;

public class Session implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sessionId;
    private User user;
    private Date issuedAt;

    public Session(String sessionId, User user) {
        this.sessionId = sessionId;
        this.user = user;
        this.issuedAt = new Date();
    }

    public String getSessionId() {
        return sessionId;
    }

    public User getUser() {
        return user;
    }

    public User getCurrentUser() {
        return user;
    }

    public void setCurrentUser(User user) {
        this.user = user;
    }

    public Date getIssuedAt() {
        return new Date(issuedAt.getTime());
    }

    public boolean isAuthenticated() {
        return user != null;
    }

    public void clear() {
        user = null;
    }
}
