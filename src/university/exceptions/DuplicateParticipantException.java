package university.exceptions;

public class DuplicateParticipantException extends UniversityException {
    private static final long serialVersionUID = 1L;

    public DuplicateParticipantException(String projectId) {
        super("Researcher is already a participant of project: " + projectId);
    }
}
