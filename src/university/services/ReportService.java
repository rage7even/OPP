package university.services;

import java.io.Serializable;

import university.core.University;
import university.education.Enrollment;
import university.education.Mark;
import university.employees.Teacher;
import university.reports.StatisticalReport;
import university.users.Student;
import university.users.User;

public class ReportService implements Serializable {
    private static final long serialVersionUID = 1L;

    public StatisticalReport createAcademicReport() {
        int marks = 0;
        double sum = 0;
        for (User user : University.getInstance().getUsers()) {
            if (user instanceof Student) {
                Student student = (Student) user;
                for (Enrollment enrollment : student.getEnrollments()) {
                    Mark mark = enrollment.getMark();
                    if (mark != null) {
                        marks++;
                        sum += mark.getFinalGrade();
                    }
                }
            }
        }
        double average = marks == 0 ? 0 : sum / marks;
        return new StatisticalReport("Marks count: " + marks + "\nAverage final grade: " + String.format("%.2f", average));
    }

    public StatisticalReport createTeacherRatingReport(Teacher teacher) {
        return new StatisticalReport("Teacher: " + teacher.getName() + "\nAverage rating: "
                + String.format("%.2f", teacher.getAverageRating()));
    }
}
