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
                System.out.println(I18n.t("support.reject"));
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
                        assignRequest(scanner, (TechSupportSpecialist) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 3:
                    if (currentUser instanceof TechSupportSpecialist) {
                        acceptRequest(scanner, (TechSupportSpecialist) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 4:
                    if (currentUser instanceof TechSupportSpecialist) {
                        rejectRequest(scanner, (TechSupportSpecialist) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 5:
                    if (currentUser instanceof TechSupportSpecialist) {
                        markDone(scanner, (TechSupportSpecialist) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 6:
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

    private static void assignRequest(Scanner scanner, TechSupportSpecialist support) {
        SupportRequest request = selectRequest(scanner);
        if (request == null) {
            return;
        }
        if (!canHandle(request, support)) {
            System.out.println(I18n.t("access.denied"));
            return;
        }
        University.getInstance().getSupportDesk().assignToSpecialist(request, support);
        System.out.println(I18n.f("assigned", AppFormatter.supportRequest(request)));
    }

    private static void acceptRequest(Scanner scanner, TechSupportSpecialist support) {
        SupportRequest request = selectRequest(scanner);
        if (request == null) {
            return;
        }
        if (request.getAssignedTo() == null) {
            request.assignTo(support);
        } else if (!request.getAssignedTo().equals(support)) {
            System.out.println(I18n.t("access.denied"));
            return;
        }
        try {
            request.getAssignedTo().acceptRequest(request);
            System.out.println(I18n.f("accepted", AppFormatter.supportRequest(request)));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void rejectRequest(Scanner scanner, TechSupportSpecialist support) {
        SupportRequest request = selectRequest(scanner);
        if (request == null) {
            return;
        }
        if (request.getAssignedTo() == null) {
            request.assignTo(support);
        } else if (!request.getAssignedTo().equals(support)) {
            System.out.println(I18n.t("access.denied"));
            return;
        }
        request.getAssignedTo().rejectRequest(request);
        System.out.println(I18n.f("rejected", AppFormatter.supportRequest(request)));
    }

    private static void markDone(Scanner scanner, TechSupportSpecialist support) {
        SupportRequest request = selectRequest(scanner);
        if (request == null) {
            return;
        }
        if (request.getAssignedTo() == null || !request.getAssignedTo().equals(support)) {
            System.out.println(I18n.t("access.denied"));
            return;
        }
        try {
            request.getAssignedTo().markDone(request);
            System.out.println(I18n.f("done", AppFormatter.supportRequest(request)));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showRequests() {
        for (SupportRequest request : University.getInstance().getSupportRequests()) {
            System.out.println(AppFormatter.supportRequest(request));
        }
    }

    private static SupportRequest selectRequest(Scanner scanner) {
        if (University.getInstance().getSupportRequests().isEmpty()) {
            System.out.println(I18n.t("no.requests"));
            return null;
        }
        showRequests();
        String requestId = ConsoleInput.readLine(scanner, I18n.t("request.id"));
        for (SupportRequest request : University.getInstance().getSupportRequests()) {
            if (request.getRequestId().equals(requestId)) {
                return request;
            }
        }
        System.out.println(I18n.t("no.requests"));
        return null;
    }

    private static boolean canHandle(SupportRequest request, TechSupportSpecialist support) {
        return request.getAssignedTo() == null || request.getAssignedTo().equals(support);
    }
}

