package university.app;

import java.util.ArrayList;
import java.util.List;

import university.core.University;
import university.education.CourseOffering;
import university.education.Enrollment;
import university.employees.Manager;
import university.employees.Teacher;
import university.employees.TechSupportSpecialist;
import university.research.Journal;
import university.research.ResearcherProfile;
import university.users.GraduateStudent;
import university.users.Student;
import university.users.User;

public final class AppData {
    private AppData() {
    }

    static Student firstStudent() {
        for (User user : University.getInstance().getUsers()) {
            if (user instanceof Student) {
                return (Student) user;
            }
        }
        return null;
    }

    static GraduateStudent firstGraduateStudent() {
        for (User user : University.getInstance().getUsers()) {
            if (user instanceof GraduateStudent) {
                return (GraduateStudent) user;
            }
        }
        return null;
    }

    static Teacher firstTeacher() {
        for (User user : University.getInstance().getUsers()) {
            if (user instanceof Teacher) {
                return (Teacher) user;
            }
        }
        return null;
    }

    static Manager firstManager() {
        for (User user : University.getInstance().getUsers()) {
            if (user instanceof Manager) {
                return (Manager) user;
            }
        }
        return null;
    }

    static TechSupportSpecialist firstSupportSpecialist() {
        for (User user : University.getInstance().getUsers()) {
            if (user instanceof TechSupportSpecialist) {
                return (TechSupportSpecialist) user;
            }
        }
        return null;
    }

    static CourseOffering firstOffering() {
        if (University.getInstance().getCourseOfferings().isEmpty()) {
            return null;
        }
        return University.getInstance().getCourseOfferings().get(0);
    }

    static Journal firstJournal() {
        if (University.getInstance().getJournals().isEmpty()) {
            return null;
        }
        return University.getInstance().getJournals().get(0);
    }

    static ResearcherProfile firstResearcherProfile() {
        for (User user : University.getInstance().getUsers()) {
            if (user.getResearcherProfile() != null) {
                return user.getResearcherProfile();
            }
        }
        return null;
    }

    static Enrollment firstEnrollment() {
        for (Student student : students()) {
            if (!student.getEnrollments().isEmpty()) {
                return student.getEnrollments().get(0);
            }
        }
        return null;
    }

    static List<Student> students() {
        List<Student> students = new ArrayList<Student>();
        for (User user : University.getInstance().getUsers()) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }
        return students;
    }
}

