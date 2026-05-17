package university.exceptions;

public class LowHIndexException extends UniversityException {
    private static final long serialVersionUID = 1L;

    public LowHIndexException(int hIndex) {
        super("Research supervisor must have h-index at least 3. Current h-index: " + hIndex);
    }
}
