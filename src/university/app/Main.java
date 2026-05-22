package university.app;

import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import university.core.University;
import university.employees.Admin;
import university.employees.Employee;
import university.employees.Manager;
import university.employees.Teacher;
import university.enums.Language;
import university.enums.UserRole;
import university.exceptions.UnauthorizedActionException;
import university.exceptions.UniversityException;
import university.patterns.DefaultUserFactory;
import university.services.Session;
import university.users.GraduateStudent;
import university.users.Student;
import university.users.StudentOrganization;
import university.users.User;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        configureUtf8Output();

        Scanner scanner = new Scanner(System.in);
        Language language = chooseLanguage(scanner);
        AppLanguage.apply(language);
        loadSavedDataIfPresent();
        AppLanguage.apply(language);
        System.out.println(I18n.f("selected.language", I18n.languageName(language)));
        System.out.println();
        ensureBootstrapAdmin();
        ensureDefaultStudentOrganizations();

        while (true) {
            Session session = login(scanner);
            if (session == null) {
                continue;
            }
            runLauncher(scanner, session);
        }
    }

    private static void runLauncher(Scanner scanner, Session session) {
        User user = session.getCurrentUser();
        while (true) {
            printLauncher(user);
            int choice = ConsoleInput.readInt(scanner, I18n.t("choose.app"));

            switch (choice) {
                case 1:
                    if (user instanceof Admin) {
                        AdminApp.run(scanner);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 2:
                    if (canUseEducationApp(user)) {
                        EducationApp.run(scanner, user);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 3:
                    if (user instanceof Manager) {
                        ManagerApp.run(scanner, (Manager) user);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 4:
                    if (user.isResearcher()) {
                        ResearchApp.run(scanner, user);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 5:
                    SupportApp.run(scanner, user);
                    break;
                case 6:
                    NewsApp.run(scanner, user);
                    break;
                case 7:
                    changeLanguage(scanner, user);
                    break;
                case 8:
                    changePassword(scanner, user);
                    break;
                case 9:
                    if (user instanceof Employee) {
                        CommunicationApp.run(scanner, (Employee) user);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 0:
                    University.getInstance().getAuthService().logout(session);
                    System.out.println(I18n.t("logged.out"));
                    return;
                default:
                    System.out.println(I18n.t("unknown.choice"));
                    break;
            }

            System.out.println();
        }
    }

    private static void printLauncher(User user) {
        System.out.println(I18n.t("launcher.title"));
        System.out.println(I18n.f("current.user", user.getName()));
        if (user instanceof Admin) {
            System.out.println(I18n.t("launcher.admin"));
        }
        if (canUseEducationApp(user)) {
            System.out.println(I18n.t("launcher.education"));
        }
        if (user instanceof Manager) {
            System.out.println(I18n.t("launcher.manager"));
        }
        if (user.isResearcher()) {
            System.out.println(I18n.t("launcher.research"));
        }
        System.out.println(I18n.t("launcher.support"));
        System.out.println(I18n.t("launcher.news"));
        System.out.println(I18n.t("launcher.change.language"));
        System.out.println(I18n.t("launcher.change.password"));
        if (user instanceof Employee) {
            System.out.println(I18n.t("launcher.messages"));
        }
        System.out.println(I18n.t("launcher.logout"));
    }

    private static Session login(Scanner scanner) {
        System.out.println(I18n.t("login.title"));
        System.out.println(I18n.t("launcher.exit"));
        String email = ConsoleInput.readLine(scanner, I18n.t("email"));
        if ("0".equals(email)) {
            System.out.println(I18n.t("bye"));
            System.exit(0);
            return null;
        }
        String password = ConsoleInput.readLine(scanner, I18n.t("password"));
        try {
            Session session = University.getInstance().getAuthService().login(email, password);
            System.out.println(I18n.f("logged.in", session.getUser().getName()));
            return session;
        } catch (UnauthorizedActionException e) {
            System.out.println(I18n.t("login.failed"));
            return null;
        }
    }

    private static boolean canUseEducationApp(User user) {
        return isRegularStudent(user) || user instanceof Teacher || user instanceof Manager;
    }

    private static boolean isRegularStudent(User user) {
        return user instanceof Student && !(user instanceof GraduateStudent);
    }

    private static void ensureBootstrapAdmin() {
        if (!University.getInstance().getUsers().isEmpty()) {
            return;
        }
        User admin = new DefaultUserFactory().create(UserRole.ADMIN, "A1", "Admin", "admin@uni.kz", "admin");
        admin.changeLanguage(AppLanguage.selected());
        University.getInstance().addUser(admin);
        System.out.println(I18n.t("bootstrap.admin.created"));
    }

    private static void ensureDefaultStudentOrganizations() {
        if (!University.getInstance().getStudentOrganizations().isEmpty()) {
            return;
        }
        University.getInstance().addStudentOrganization(new StudentOrganization(
                "ORG-DEV",
                "Developers Club",
                "Student community for programming and software projects."));
        University.getInstance().addStudentOrganization(new StudentOrganization(
                "ORG-DEBATE",
                "Debate League",
                "Public speaking, debate practice, and student events."));
    }

    private static void loadSavedDataIfPresent() {
        File dataFile = new File("data/university.ser");
        if (!dataFile.exists() || dataFile.length() == 0) {
            return;
        }
        try {
            University.getInstance().load();
            System.out.println(I18n.t("data.loaded"));
        } catch (UniversityException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Users:
     * 1) Admin: id=A1, name=Aigerim Admin, email=admin@uni.kz, password=admin
     * 2) Manager: id=M1, name=Dana Dean, email=dean@uni.kz, password=dean
     * 3) Teacher: id=T1, name=Prof. Assylzhan, email=prof@uni.kz, password=prof
     * 4) Student: id=S1, name=Nursultan Student, email=student@uni.kz, password=student
     * 5) Graduate student: id=G1, name=Aruzhan Master, email=grad@uni.kz, password=grad
     * 6) Tech support: id=TS1, name=Aliya Support, email=support@uni.kz, password=support
     *
     * Courses:
     * CS101 - Object-Oriented Programming, 5 credits
     * RES501 - Research Methods, 4 credits
     *
     * Journal:
     * J-UR - University Research Journal
     */

    private static Language chooseLanguage(Scanner scanner) {
        System.out.println(I18n.t("choose.language.title"));
        System.out.println("1 - Қазақша");
        System.out.println("2 - English (default)");
        System.out.println("3 - Русский");
        while (true) {
            System.out.print(I18n.t("choose.language.prompt") + " ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return Language.EN;
            }
            try {
                switch (Integer.parseInt(input)) {
                    case 1:
                        return Language.KZ;
                    case 2:
                        return Language.EN;
                    case 3:
                        return Language.RU;
                    default:
                        System.out.println(I18n.t("unknown.choice"));
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println(I18n.t("enter.number"));
            }
        }
    }

    private static void changeLanguage(Scanner scanner, User user) {
        Language language = chooseLanguage(scanner);
        AppLanguage.changeFor(user, language);
        System.out.println(I18n.f("selected.language", I18n.languageName(language)));
    }

    private static void changePassword(Scanner scanner, User user) {
        String currentPassword = ConsoleInput.readLine(scanner, I18n.t("password.current"));
        String newPassword = ConsoleInput.readLine(scanner, I18n.t("password.new"));
        String confirmPassword = ConsoleInput.readLine(scanner, I18n.t("password.confirm"));
        if (!newPassword.equals(confirmPassword)) {
            System.out.println(I18n.t("password.mismatch"));
            return;
        }
        try {
            user.changePassword(currentPassword, newPassword);
            University.getInstance().getLogService().log(user, "Changed password");
            System.out.println(I18n.t("password.changed"));
        } catch (UnauthorizedActionException e) {
            System.out.println(I18n.t("password.incorrect"));
        } catch (IllegalArgumentException e) {
            System.out.println(I18n.t("required.field"));
        }
    }

    private static void configureUtf8Output() {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setErr(new PrintStream(System.err, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // Default console encoding will be used if UTF-8 is not available.
        }
    }
}
