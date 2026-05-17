package university.exceptions;

public class NotResearcherException extends UniversityException {
    private static final long serialVersionUID = 1L;

    public NotResearcherException(String userId) {
        super("User is not a researcher: " + userId);
    }
}
