package university.exceptions;

public class DuplicateSubscriptionException extends UniversityException {
    private static final long serialVersionUID = 1L;

    public DuplicateSubscriptionException(String journalId) {
        super("Already subscribed to journal: " + journalId);
    }
}
