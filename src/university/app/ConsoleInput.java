package university.app;

import java.util.Scanner;

public final class ConsoleInput {
    private ConsoleInput() {
    }

    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " ");
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println(I18n.t("enter.number"));
            }
        }
    }

    public static int readIntInRange(Scanner scanner, String prompt, int minInclusive, int maxInclusive) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value >= minInclusive && value <= maxInclusive) {
                return value;
            }
            System.out.println(I18n.t("unknown.choice"));
        }
    }

    public static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " ");
            String line = scanner.nextLine();
            try {
                return Double.parseDouble(line.trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println(I18n.t("enter.decimal"));
            }
        }
    }

    public static String readLine(Scanner scanner, String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine();
    }
}

