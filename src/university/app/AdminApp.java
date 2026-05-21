package university.app;

import java.util.List;
import java.util.Scanner;

import university.core.University;
import university.enums.UserRole;
import university.patterns.DefaultUserFactory;
import university.patterns.UserFactory;
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
        int roleNumber = ConsoleInput.readInt(scanner, I18n.t("role"));
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
        String password = ConsoleInput.readLine(scanner, I18n.t("password"));

        UserFactory factory = new DefaultUserFactory();
        User user = factory.create(role, id, name, email, password);
        user.changeLanguage(AppLanguage.selected());
        University.getInstance().getUserManagementService().addUser(user);
        System.out.println(I18n.f("added", user));
    }

    private static void removeUser(Scanner scanner) {
        String id = ConsoleInput.readLine(scanner, I18n.t("user.id"));
        University.getInstance().getUserManagementService().removeUser(id);
        System.out.println(I18n.f("removed.user", id));
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

    private static boolean userExists(String id) {
        for (User user : University.getInstance().getUsers()) {
            if (user.getId().equals(id)) {
                return true;
            }
        }
        return false;
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

