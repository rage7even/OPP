package university.patterns;

import university.employees.Admin;
import university.employees.Manager;
import university.employees.Teacher;
import university.employees.TechSupportSpecialist;
import university.enums.DegreeType;
import university.enums.ManagerType;
import university.enums.TeacherPosition;
import university.enums.UserRole;
import university.users.GraduateStudent;
import university.users.Student;
import university.users.User;

public class DefaultUserFactory implements UserFactory {
    private static final long serialVersionUID = 1L;

    @Override
    public User create(UserRole role, String id, String name, String email, String passwordHash) {
        switch (role) {
            case STUDENT:
                return new Student(id, name, email, passwordHash, id);
            case GRADUATE_STUDENT:
                return new GraduateStudent(id, name, email, passwordHash, id, DegreeType.MASTER);
            case TEACHER:
                return new Teacher(id, name, email, passwordHash, "EMP-" + id, 450000, TeacherPosition.LECTOR);
            case MANAGER:
                return new Manager(id, name, email, passwordHash, "EMP-" + id, 600000, ManagerType.DEPARTMENT);
            case ADMIN:
                return new Admin(id, name, email, passwordHash, "EMP-" + id, 550000);
            case TECH_SUPPORT:
                return new TechSupportSpecialist(id, name, email, passwordHash, "EMP-" + id, 300000);
            default:
                throw new IllegalArgumentException("Unsupported role: " + role);
        }
    }
}
