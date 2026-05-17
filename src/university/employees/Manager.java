package university.employees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.communication.OfficialRequest;
import university.core.University;
import university.education.Course;
import university.education.CourseOffering;
import university.education.Enrollment;
import university.education.Lesson;
import university.enums.LessonType;
import university.enums.ManagerType;
import university.patterns.StudentSortStrategy;
import university.patterns.TeacherSortStrategy;
import university.reports.StatisticalReport;
import university.users.Student;
import university.users.User;

public class Manager extends Employee {
    private static final long serialVersionUID = 1L;

    private ManagerType managerType;
    private List<OfficialRequest> officialRequests;

    public Manager(String id, String name, String email, String passwordHash, String employeeId, double salary,
            ManagerType managerType) {
        super(id, name, email, passwordHash, employeeId, salary);
        this.managerType = managerType;
        this.officialRequests = new ArrayList<OfficialRequest>();
    }

    public void assignCourseToTeacher(Course course, LessonType lessonType, Teacher teacher) {
        teacher.assignCourse(course);
        course.addLesson(new Lesson(lessonType, new java.util.Date(), teacher));
    }

    public void approveStudentRegistration(Enrollment enrollment) {
        University.getInstance().getRegistrationService().approve(this, enrollment);
    }

    public CourseOffering addCourseForRegistration(Course course, String major, int year) {
        CourseOffering offering = new CourseOffering("OFF-" + course.getCourseId() + "-" + year, course, major, year);
        University.getInstance().addCourseOffering(offering);
        return offering;
    }

    public StatisticalReport createStatisticalReport() {
        return University.getInstance().getReportService().createAcademicReport();
    }

    public void manageNews() {
        University.getInstance().getNewsService().addGeneralNews("Manager update", "Academic office posted a new update.");
    }

    public List<Student> viewStudents(StudentSortStrategy strategy) {
        List<Student> students = new ArrayList<Student>();
        for (User user : University.getInstance().getUsers()) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }
        Collections.sort(students, strategy);
        return students;
    }

    public List<Teacher> viewTeachers(TeacherSortStrategy strategy) {
        List<Teacher> teachers = new ArrayList<Teacher>();
        for (User user : University.getInstance().getUsers()) {
            if (user instanceof Teacher) {
                teachers.add((Teacher) user);
            }
        }
        Collections.sort(teachers, strategy);
        return teachers;
    }

    public List<OfficialRequest> viewOfficialRequests() {
        return Collections.unmodifiableList(officialRequests);
    }

    public void receiveOfficialRequest(OfficialRequest request) {
        officialRequests.add(request);
    }

    public ManagerType getManagerType() {
        return managerType;
    }
}
