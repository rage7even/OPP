package university.exceptions;

public class InvalidRatingException extends UniversityException {
    private static final long serialVersionUID = 1L;

    public InvalidRatingException(int value) {
        super("Teacher rating must be between 1 and 5. Given: " + value);
    }
}
