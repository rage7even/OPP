package university.app;

import java.util.Calendar;

import university.education.Course;
import university.education.CourseOffering;
import university.education.Enrollment;
import university.education.Mark;
import university.enums.RegistrationStatus;
import university.research.JournalRequest;
import university.research.ResearchPaper;
import university.support.SupportRequest;
import university.users.Student;
import university.users.StudentOrganization;
import university.users.StudentOrganizationRequest;

public final class AppFormatter {
    private AppFormatter() {
    }

    public static String course(Course course) {
        return course.getCourseId() + " - " + course.getName()
                + " (" + course.getCredits() + " " + I18n.t("credits.word")
                + ", " + I18n.courseType(course.getType()) + ")";
    }

    public static String offering(CourseOffering offering) {
        int freeSeats = offering.getCourse().getCapacity();
        int occupiedSeats = approvedCount(offering);
        int totalSeats = freeSeats + occupiedSeats;
        return offering.getOfferingId() + " - " + course(offering.getCourse())
                + ", " + I18n.t("major") + ": " + offering.getMajor()
                + ", " + I18n.t("year") + ": " + offering.getYear()
                + ", seats: " + totalSeats + " total, " + occupiedSeats + " occupied, " + freeSeats + " free";
    }

    public static String enrollment(Enrollment enrollment) {
        StringBuilder sb = new StringBuilder();
        sb.append(enrollment.getEnrollmentId())
                .append(" - ")
                .append(enrollment.getStudent().getName())
                .append(", ")
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
        String result = I18n.t("request") + " " + request.getRequestId()
                + ", " + I18n.t("status") + ": " + I18n.requestStatus(request.getStatus())
                + ", " + request.getDescription();
        if (request.getAssignedTo() != null) {
            result += ", assignedTo=" + request.getAssignedTo().getName();
        }
        return result;
    }

    public static String studentOrganization(StudentOrganization organization) {
        String head = organization.getHead() == null ? "-" : organization.getHead().getName();
        return organization.getOrgId()
                + " - "
                + organization.getName()
                + ", members=" + organization.getMembers().size()
                + ", head=" + head
                + ", " + organization.getDescription();
    }

    public static String studentOrganizationRequest(StudentOrganizationRequest request) {
        return request.getRequestId()
                + " - "
                + request.getStudent().getName()
                + ", "
                + request.getOrganizationId()
                + ", "
                + request.getOrganizationName()
                + ", "
                + I18n.t("status")
                + ": "
                + I18n.registrationStatus(request.getStatus())
                + ", "
                + request.getDescription();
    }

    public static String journalRequest(JournalRequest request) {
        return request.getRequestId()
                + " - "
                + request.getRequester().getName()
                + ", "
                + request.getJournalId()
                + ", "
                + request.getJournalTitle()
                + ", "
                + I18n.t("status")
                + ": "
                + I18n.registrationStatus(request.getStatus());
    }

    public static String diplomaProject(ResearchPaper paper) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paper.getPublishedAt());
        String status = paper.getJournal() == null
                ? I18n.t("diploma.project.draft")
                : paper.getJournalTitle();
        return paper.getPaperId()
                + " - "
                + paper.getTitle()
                + " ["
                + paper.getCitations()
                + " citations, "
                + paper.getPages()
                + " pages, "
                + I18n.t("year")
                + ": "
                + calendar.get(Calendar.YEAR)
                + ", "
                + I18n.t("status")
                + ": "
                + status
                + "]";
    }

    private static int approvedCount(CourseOffering offering) {
        int count = 0;
        for (Student student : AppData.students()) {
            for (Enrollment enrollment : student.getEnrollments()) {
                if (offering.equals(enrollment.getOffering())
                        && enrollment.getStatus() == RegistrationStatus.APPROVED) {
                    count++;
                }
            }
        }
        return count;
    }
}

