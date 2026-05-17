package university.exceptions;

public class CreditLimitExceededException extends UniversityException {
    private static final long serialVersionUID = 1L;

    public CreditLimitExceededException(int currentCredits, int attempted) {
        super("Credit limit exceeded. Current: " + currentCredits + ", attempted total: " + attempted + ", limit: 21");
    }
}
