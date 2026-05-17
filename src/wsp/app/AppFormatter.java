package wsp.app;

import university.education.Course;
import university.education.Enrollment;
import university.education.Mark;
import university.support.SupportRequest;

public final class AppFormatter {
    private AppFormatter() {
    }

    public static String course(Course course) {
        return course.getCourseId() + " - " + course.getName()
                + " (" + course.getCredits() + " " + I18n.t("credits.word")
                + ", " + I18n.courseType(course.getType()) + ")";
    }

    public static String enrollment(Enrollment enrollment) {
        StringBuilder sb = new StringBuilder();
        sb.append(enrollment.getEnrollmentId())
                .append(" - ")
                .append(enrollment.getOffering().getCourse().getName())
                .append(", ")
                .append(I18n.t("status"))
                .append(": ")
                .append(I18n.registrationStatus(enrollment.getStatus()));
        Mark mark = enrollment.getMark();
        if (mark != null) {
            sb.append(", ").append(I18n.t("mark.saved")).append(" ").append(mark);
        }
        return sb.toString();
    }

    public static String supportRequest(SupportRequest request) {
        return I18n.t("request") + " " + request.getRequestId()
                + ", " + I18n.t("status") + ": " + I18n.requestStatus(request.getStatus())
                + ", " + request.getDescription();
    }
}
