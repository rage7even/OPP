package university.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import university.communication.Message;
import university.core.University;
import university.employees.Employee;
import university.users.User;

public final class CommunicationApp {
    private CommunicationApp() {
    }

    public static void run(Scanner scanner, Employee currentEmployee) {
        while (true) {
            System.out.println(I18n.t("messages.title"));
            System.out.println(I18n.t("messages.send"));
            System.out.println(I18n.t("messages.show"));
            System.out.println(I18n.t("back"));

            int choice = ConsoleInput.readInt(scanner, I18n.t("choose"));
            switch (choice) {
                case 1:
                    sendMessage(scanner, currentEmployee);
                    break;
                case 2:
                    showMessages(currentEmployee);
                    break;
                case 0:
                    return;
                default:
                    System.out.println(I18n.t("unknown.choice"));
                    break;
            }
        }
    }

    private static void sendMessage(Scanner scanner, Employee currentEmployee) {
        Employee receiver = selectEmployee(scanner, currentEmployee);
        if (receiver == null) {
            return;
        }
        String text = ConsoleInput.readLine(scanner, I18n.t("message.text"));
        if (text.trim().isEmpty()) {
            System.out.println(I18n.t("required.field"));
            return;
        }
        Message message = currentEmployee.sendMessage(receiver, text);
        University.getInstance().addMessage(message);
        receiver.addNotification("New message from " + currentEmployee.getName() + ": " + text);
        University.getInstance().getLogService().log(currentEmployee, "Sent message to " + receiver.getId());
        System.out.println(I18n.f("sent.message", message));
    }

    private static void showMessages(Employee currentEmployee) {
        List<Message> related = new ArrayList<Message>();
        for (Message message : University.getInstance().getMessages()) {
            if (currentEmployee.equals(message.getSender()) || currentEmployee.equals(message.getReceiver())) {
                related.add(message);
            }
        }
        if (related.isEmpty()) {
            System.out.println(I18n.t("no.messages"));
            return;
        }
        for (Message message : related) {
            System.out.println(message);
        }
    }

    private static Employee selectEmployee(Scanner scanner, Employee currentEmployee) {
        List<Employee> employees = new ArrayList<Employee>();
        for (User user : University.getInstance().getUsers()) {
            if (user instanceof Employee && !user.equals(currentEmployee)) {
                employees.add((Employee) user);
            }
        }
        if (employees.isEmpty()) {
            System.out.println(I18n.t("no.employee"));
            return null;
        }
        for (Employee employee : employees) {
            System.out.println(employee.getId() + " - " + employee.getName());
        }
        String employeeId = ConsoleInput.readLine(scanner, I18n.t("employee.id"));
        for (Employee employee : employees) {
            if (employee.getId().equals(employeeId)) {
                return employee;
            }
        }
        System.out.println(I18n.t("no.employee"));
        return null;
    }
}
