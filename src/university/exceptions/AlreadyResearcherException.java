package university.exceptions;

public class AlreadyResearcherException extends UniversityException {
    private static final long serialVersionUID = 1L;

    public AlreadyResearcherException(String userId) {
        super("User already has a researcher profile: " + userId);
    }
}
