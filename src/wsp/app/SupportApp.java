package wsp.app;

import java.util.Scanner;

import university.core.University;
import university.employees.TechSupportSpecialist;
import university.support.SupportRequest;
import university.users.Student;

public final class SupportApp {
    private SupportApp() {
    }

    public static void run(Scanner scanner) {
        while (true) {
            System.out.println(I18n.t("support.title"));
            System.out.println(I18n.t("support.create"));
            System.out.println(I18n.t("support.assign"));
            System.out.println(I18n.t("support.accept"));
            System.out.println(I18n.t("support.done"));
            System.out.println(I18n.t("support.show"));
            System.out.println(I18n.t("back"));

            int choice = ConsoleInput.readInt(scanner, I18n.t("choose"));
            switch (choice) {
                case 1:
                    createRequest(scanner);
                    break;
                case 2:
                    assignFirstRequest();
                    break;
                case 3:
                    acceptFirstRequest();
                    break;
                case 4:
                    markFirstDone();
                    break;
                case 5:
                    showRequests();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(I18n.t("unknown.choice"));
                    break;
            }
        }
    }

    private static void createRequest(Scanner scanner) {
        Student student = AppData.firstStudent();
        if (student == null) {
            System.out.println(I18n.t("no.student"));
            return;
        }
        String description = ConsoleInput.readLine(scanner, I18n.t("description"));
        SupportRequest request = University.getInstance().getSupportDesk().createRequest(student, description);
        System.out.println(I18n.f("created", AppFormatter.supportRequest(request)));
    }

    private static void assignFirstRequest() {
        SupportRequest request = firstRequest();
        if (request == null) {
            System.out.println(I18n.t("no.requests"));
            return;
        }
        TechSupportSpecialist support = AppData.firstSupportSpecialist();
        if (support == null) {
            System.out.println(I18n.t("no.support.specialist"));
            return;
        }
        University.getInstance().getSupportDesk().assignToSpecialist(request, support);
        System.out.println(I18n.f("assigned", AppFormatter.supportRequest(request)));
    }

    private static void acceptFirstRequest() {
        SupportRequest request = firstRequest();
        if (request == null) {
            System.out.println(I18n.t("no.requests"));
            return;
        }
        if (request.getAssignedTo() == null) {
            TechSupportSpecialist support = AppData.firstSupportSpecialist();
            if (support == null) {
                System.out.println(I18n.t("no.support.specialist"));
                return;
            }
            request.assignTo(support);
        }
        request.getAssignedTo().acceptRequest(request);
        System.out.println(I18n.f("accepted", AppFormatter.supportRequest(request)));
    }

    private static void markFirstDone() {
        SupportRequest request = firstRequest();
        if (request == null) {
            System.out.println(I18n.t("no.requests"));
            return;
        }
        if (request.getAssignedTo() == null) {
            System.out.println(I18n.t("no.support.specialist"));
            return;
        }
        request.getAssignedTo().markDone(request);
        System.out.println(I18n.f("done", AppFormatter.supportRequest(request)));
    }

    private static void showRequests() {
        for (SupportRequest request : University.getInstance().getSupportRequests()) {
            System.out.println(AppFormatter.supportRequest(request));
        }
    }

    private static SupportRequest firstRequest() {
        if (University.getInstance().getSupportRequests().isEmpty()) {
            return null;
        }
        return University.getInstance().getSupportRequests().get(0);
    }
}
