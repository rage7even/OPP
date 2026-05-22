package university.app;

import java.util.List;
import java.util.Scanner;

import university.core.University;
import university.employees.Teacher;
import university.enums.DegreeType;
import university.enums.ManagerType;
import university.enums.TeacherPosition;
import university.enums.UserRole;
import university.patterns.DefaultUserFactory;
import university.reports.LogEntry;
import university.users.User;

public final class AdminApp {
    private AdminApp() {
    }

    public static void run(Scanner scanner) {
        University university = University.getInstance();
        while (true) {
            System.out.println(I18n.t("admin.title"));
            System.out.println(I18n.t("admin.show.users"));
            System.out.println(I18n.t("admin.add.user"));
            System.out.println(I18n.t("admin.remove.user"));
            System.out.println(I18n.t("admin.show.logs"));
            System.out.println(I18n.t("admin.save"));
            System.out.println(I18n.t("admin.load"));
            System.out.println(I18n.t("admin.promote.teacher"));
            System.out.println(I18n.t("back"));

            int choice = ConsoleInput.readInt(scanner, I18n.t("choose"));
            switch (choice) {
                case 1:
                    printUsers(university.getUsers());
                    break;
                case 2:
                    addUser(scanner);
                    break;
                case 3:
                    removeUser(scanner);
                    break;
                case 4:
                    printLogs(university.getLogService().getEntries());
                    break;
                case 5:
                    university.save();
                    System.out.println(I18n.t("data.saved"));
                    break;
                case 6:
                    university.load();
                    System.out.println(I18n.t("data.loaded"));
                    break;
                case 7:
                    promoteTeacher(scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println(I18n.t("unknown.choice"));
                    break;
            }
        }
    }

    private static void addUser(Scanner scanner) {
        System.out.println(I18n.t("roles"));
        int roleNumber = ConsoleInput.readIntInRange(scanner, I18n.t("role"), 1, 6);
        UserRole role = roleFromNumber(roleNumber);
        if (role == null) {
            System.out.println(I18n.t("invalid.role"));
            return;
        }
        String id = ConsoleInput.readLine(scanner, I18n.t("id"));
        if (userExists(id)) {
            System.out.println(I18n.t("duplicate.user"));
            return;
        }
        String name = ConsoleInput.readLine(scanner, I18n.t("name"));
        String email = ConsoleInput.readLine(scanner, I18n.t("email"));
        if (emailExists(email)) {
            System.out.println(I18n.t("duplicate.email"));
            return;
        }
        String password = ConsoleInput.readLine(scanner, I18n.t("password"));

        DefaultUserFactory factory = new DefaultUserFactory();
        User user = createUser(scanner, factory, role, id, name, email, password);
        user.changeLanguage(AppLanguage.selected());
        University.getInstance().getUserManagementService().addUser(user);
        System.out.println(I18n.f("added", user));
    }

    private static User createUser(Scanner scanner, DefaultUserFactory factory, UserRole role, String id, String name,
            String email, String password) {
        if (role == UserRole.TEACHER) {
            return factory.createTeacher(id, name, email, password, readTeacherPosition(scanner));
        }
        if (role == UserRole.GRADUATE_STUDENT) {
            return factory.createGraduateStudent(id, name, email, password, readDegreeType(scanner));
        }
        if (role == UserRole.MANAGER) {
            return factory.createManager(id, name, email, password, readManagerType(scanner));
        }
        return factory.create(role, id, name, email, password);
    }

    private static void removeUser(Scanner scanner) {
        String id = ConsoleInput.readLine(scanner, I18n.t("user.id"));
        University.getInstance().getUserManagementService().removeUser(id);
        System.out.println(I18n.f("removed.user", id));
    }

    private static void promoteTeacher(Scanner scanner) {
        if (!printTeachers()) {
            System.out.println(I18n.t("no.teacher"));
            return;
        }
        Teacher teacher = findTeacher(ConsoleInput.readLine(scanner, I18n.t("teacher.id")));
        if (teacher == null) {
            System.out.println(I18n.t("no.teacher"));
            return;
        }
        teacher.promoteToProfessor();
        University.getInstance().getLogService().log(teacher, "Teacher promoted to professor");
        System.out.println(I18n.f("promoted.teacher", teacher));
    }

    private static UserRole roleFromNumber(int roleNumber) {
        switch (roleNumber) {
            case 1:
                return UserRole.STUDENT;
            case 2:
                return UserRole.GRADUATE_STUDENT;
            case 3:
                return UserRole.TEACHER;
            case 4:
                return UserRole.MANAGER;
            case 5:
                return UserRole.ADMIN;
            case 6:
                return UserRole.TECH_SUPPORT;
            default:
                return null;
        }
    }

    private static TeacherPosition teacherPositionFromNumber(int positionNumber) {
        switch (positionNumber) {
            case 1:
                return TeacherPosition.TUTOR;
            case 2:
                return TeacherPosition.LECTOR;
            case 3:
                return TeacherPosition.SENIOR_LECTOR;
            case 4:
                return TeacherPosition.PROFESSOR;
            default:
                throw new IllegalArgumentException("Invalid teacher position: " + positionNumber);
        }
    }

    private static TeacherPosition readTeacherPosition(Scanner scanner) {
        System.out.println(I18n.t("teacher.position"));
        int positionNumber = ConsoleInput.readIntInRange(scanner, I18n.t("choose"), 1, 4);
        return teacherPositionFromNumber(positionNumber);
    }

    private static DegreeType readDegreeType(Scanner scanner) {
        System.out.println("1 - MASTER, 2 - PHD");
        int degreeNumber = ConsoleInput.readIntInRange(scanner, I18n.t("choose"), 1, 2);
        return degreeNumber == 1 ? DegreeType.MASTER : DegreeType.PHD;
    }

    private static ManagerType readManagerType(Scanner scanner) {
        System.out.println("1 - OR, 2 - DEPARTMENT, 3 - DEAN_OFFICE");
        int managerTypeNumber = ConsoleInput.readIntInRange(scanner, I18n.t("choose"), 1, 3);
        switch (managerTypeNumber) {
            case 1:
                return ManagerType.OR;
            case 2:
                return ManagerType.DEPARTMENT;
            case 3:
                return ManagerType.DEAN_OFFICE;
            default:
                throw new IllegalArgumentException("Invalid manager type: " + managerTypeNumber);
        }
    }

    private static boolean userExists(String id) {
        for (User user : University.getInstance().getUsers()) {
            if (user.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private static boolean emailExists(String email) {
        for (User user : University.getInstance().getUsers()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    private static boolean printTeachers() {
        boolean found = false;
        for (User user : University.getInstance().getUsers()) {
            if (user instanceof Teacher) {
                Teacher teacher = (Teacher) user;
                found = true;
                System.out.println(teacher.getId() + " - " + teacher.getName()
                        + ", position=" + teacher.getPosition()
                        + ", researcher=" + teacher.isResearcher());
            }
        }
        return found;
    }

    private static Teacher findTeacher(String id) {
        for (User user : University.getInstance().getUsers()) {
            if (user instanceof Teacher && user.getId().equals(id)) {
                return (Teacher) user;
            }
        }
        return null;
    }

    private static void printUsers(List<User> users) {
        for (User user : users) {
            System.out.println(user);
        }
    }

    private static void printLogs(List<LogEntry> logs) {
        for (LogEntry log : logs) {
            System.out.println(log);
        }
    }
}
