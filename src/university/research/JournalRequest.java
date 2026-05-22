package university.research;

import java.io.Serializable;

import university.enums.RegistrationStatus;
import university.users.User;

public class JournalRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String requestId;
    private User requester;
    private String journalId;
    private String journalTitle;
    private RegistrationStatus status;

    public JournalRequest(String requestId, User requester, String journalId, String journalTitle) {
        this.requestId = requestId;
        this.requester = requester;
        this.journalId = journalId;
        this.journalTitle = journalTitle;
        this.status = RegistrationStatus.PENDING;
    }

    public void approve() {
        if (status == RegistrationStatus.REJECTED) {
            throw new IllegalStateException("rejected journal request cannot be approved");
        }
        status = RegistrationStatus.APPROVED;
    }

    public void reject() {
        if (status == RegistrationStatus.APPROVED) {
            throw new IllegalStateException("approved journal request cannot be rejected");
        }
        status = RegistrationStatus.REJECTED;
    }

    public String getRequestId() {
        return requestId;
    }

    public User getRequester() {
        return requester;
    }

    public String getJournalId() {
        return journalId;
    }

    public String getJournalTitle() {
        return journalTitle;
    }

    public RegistrationStatus getStatus() {
        return status;
    }
}
