package university.app;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import university.enums.Language;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        configureUtf8Output();

        Scanner scanner = new Scanner(System.in);
        Language language = chooseLanguage(scanner);
        AppLanguage.apply(language);
        System.out.println(I18n.f("selected.language", language));
        System.out.println();

        while (true) {
            System.out.println(I18n.t("launcher.title"));
            System.out.println(I18n.t("launcher.admin"));
            System.out.println(I18n.t("launcher.education"));
            System.out.println(I18n.t("launcher.manager"));
            System.out.println(I18n.t("launcher.research"));
            System.out.println(I18n.t("launcher.support"));
            System.out.println(I18n.t("launcher.exit"));

            int choice = ConsoleInput.readInt(scanner, I18n.t("choose.app"));

            switch (choice) {
                case 1:
                    AdminApp.run(scanner);
                    break;
                case 2:
                    EducationApp.run(scanner);
                    break;
                case 3:
                    ManagerApp.run(scanner);
                    break;
                case 4:
                    ResearchApp.run(scanner);
                    break;
                case 5:
                    SupportApp.run(scanner);
                    break;
                case 0:
                    System.out.println(I18n.t("bye"));
                    scanner.close();
                    return;
                default:
                    System.out.println(I18n.t("unknown.choice"));
                    break;
            }

            System.out.println();
        }
    }

    /*
     * Sample data for manual input. These are comments only; the program starts empty.
     *
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
        System.out.println("1 - KZ");
        System.out.println("2 - EN (default)");
        System.out.println("3 - RU");
        System.out.print(I18n.t("choose.language.prompt") + " ");

        String input = scanner.nextLine().trim();
        if ("1".equals(input)) {
            return Language.KZ;
        }
        if ("3".equals(input)) {
            return Language.RU;
        }
        return Language.EN;
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

