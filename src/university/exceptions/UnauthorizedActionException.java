package university.exceptions;

public class UnauthorizedActionException extends UniversityException {
    private static final long serialVersionUID = 1L;

    public UnauthorizedActionException(String action) {
        super("Unauthorized action: " + action);
    }
}
