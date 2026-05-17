package university.exceptions;

public class TooManyFailsException extends UniversityException {
    private static final long serialVersionUID = 1L;

    public TooManyFailsException(int failCount) {
        super("Student cannot continue registration after more than 3 fails. Current fails: " + failCount);
    }
}
