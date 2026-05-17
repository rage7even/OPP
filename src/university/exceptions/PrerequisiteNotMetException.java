package university.exceptions;

public class PrerequisiteNotMetException extends UniversityException {
    private static final long serialVersionUID = 1L;

    public PrerequisiteNotMetException(String courseId) {
        super("Prerequisite is not met for course: " + courseId);
    }
}
