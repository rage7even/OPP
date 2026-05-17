package university.exceptions;

public class CourseNotAvailableException extends UniversityException {
    private static final long serialVersionUID = 1L;

    public CourseNotAvailableException(String courseId) {
        super("Course is not available for registration: " + courseId);
    }
}
