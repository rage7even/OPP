package university.app;

import java.util.Scanner;

import university.core.University;
import university.employees.TechSupportSpecialist;
import university.support.SupportRequest;
import university.users.User;

public final class SupportApp {
    private SupportApp() {
    }

    public static void run(Scanner scanner, User currentUser) {
        while (true) {
            System.out.println(I18n.t("support.title"));
            System.out.println(I18n.t("support.create"));
            if (currentUser instanceof TechSupportSpecialist) {
                System.out.println(I18n.t("support.assign"));
                System.out.println(I18n.t("support.accept"));
                System.out.println(I18n.t("support.done"));
                System.out.println(I18n.t("support.show"));
            }
            System.out.println(I18n.t("back"));

            int choice = ConsoleInput.readInt(scanner, I18n.t("choose"));
            switch (choice) {
                case 1:
                    createRequest(scanner, currentUser);
                    break;
                case 2:
                    if (currentUser instanceof TechSupportSpecialist) {
                        assignFirstRequest((TechSupportSpecialist) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 3:
                    if (currentUser instanceof TechSupportSpecialist) {
                        acceptFirstRequest((TechSupportSpecialist) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 4:
                    if (currentUser instanceof TechSupportSpecialist) {
                        markFirstDone((TechSupportSpecialist) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 5:
                    if (currentUser instanceof TechSupportSpecialist) {
                        showRequests();
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println(I18n.t("unknown.choice"));
                    break;
            }
        }
    }

    private static void createRequest(Scanner scanner, User currentUser) {
        String description = ConsoleInput.readLine(scanner, I18n.t("description"));
        SupportRequest request = University.getInstance().getSupportDesk().createRequest(currentUser, description);
        System.out.println(I18n.f("created", AppFormatter.supportRequest(request)));
    }

    private static void assignFirstRequest(TechSupportSpecialist support) {
        SupportRequest request = firstRequest();
        if (request == null) {
            System.out.println(I18n.t("no.requests"));
            return;
        }
        University.getInstance().getSupportDesk().assignToSpecialist(request, support);
        System.out.println(I18n.f("assigned", AppFormatter.supportRequest(request)));
    }

    private static void acceptFirstRequest(TechSupportSpecialist support) {
        SupportRequest request = firstRequest();
        if (request == null) {
            System.out.println(I18n.t("no.requests"));
            return;
        }
        if (request.getAssignedTo() == null) {
            request.assignTo(support);
        }
        request.getAssignedTo().acceptRequest(request);
        System.out.println(I18n.f("accepted", AppFormatter.supportRequest(request)));
    }

    private static void markFirstDone(TechSupportSpecialist support) {
        SupportRequest request = firstRequest();
        if (request == null) {
            System.out.println(I18n.t("no.requests"));
            return;
        }
        if (request.getAssignedTo() == null) {
            request.assignTo(support);
        }
        if (request.getStatus() != university.enums.RequestStatus.ACCEPTED) {
            request.getAssignedTo().acceptRequest(request);
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

