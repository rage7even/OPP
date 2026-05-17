package wsp.app;

import java.util.Calendar;
import java.util.Scanner;

import university.core.University;
import university.enums.Format;
import university.patterns.SortByCitationsStrategy;
import university.patterns.SortByDateStrategy;
import university.patterns.SortByPagesStrategy;
import university.research.Journal;
import university.research.ResearchPaper;
import university.research.ResearchProject;
import university.research.ResearcherProfile;
import university.users.GraduateStudent;
import university.users.User;

public final class ResearchApp {
    private ResearchApp() {
    }

    public static void run(Scanner scanner) {
        while (true) {
            System.out.println(I18n.t("research.title"));
            System.out.println(I18n.t("research.create.journal"));
            System.out.println(I18n.t("research.publish"));
            System.out.println(I18n.t("research.hindex"));
            System.out.println(I18n.t("research.sorted"));
            System.out.println(I18n.t("research.supervisor"));
            System.out.println(I18n.t("research.join"));
            System.out.println(I18n.t("research.news"));
            System.out.println(I18n.t("back"));

            int choice = ConsoleInput.readInt(scanner, I18n.t("choose"));
            switch (choice) {
                case 1:
                    createJournal(scanner);
                    break;
                case 2:
                    publishPaper(scanner);
                    break;
                case 3:
                    showHIndex();
                    break;
                case 4:
                    printSorted(scanner);
                    break;
                case 5:
                    assignSupervisor();
                    break;
                case 6:
                    joinProject(scanner);
                    break;
                case 7:
                    showNewsAndNotifications();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(I18n.t("unknown.choice"));
                    break;
            }
        }
    }

    private static void createJournal(Scanner scanner) {
        String id = ConsoleInput.readLine(scanner, I18n.t("journal.id"));
        String title = ConsoleInput.readLine(scanner, I18n.t("journal.title"));
        Journal journal = new Journal(id, title);
        University.getInstance().addJournal(journal);
        System.out.println(I18n.f("created.journal", journal));
    }

    private static void publishPaper(Scanner scanner) {
        ResearcherProfile researcher = AppData.firstResearcherProfile();
        Journal journal = AppData.firstJournal();
        if (researcher == null) {
            System.out.println(I18n.t("no.researcher"));
            return;
        }
        if (journal == null) {
            System.out.println(I18n.t("no.journal"));
            return;
        }
        String id = ConsoleInput.readLine(scanner, I18n.t("paper.id"));
        String title = ConsoleInput.readLine(scanner, I18n.t("paper.title"));
        int citations = ConsoleInput.readInt(scanner, I18n.t("paper.citations"));
        int pages = ConsoleInput.readInt(scanner, I18n.t("paper.pages"));
        String doi = ConsoleInput.readLine(scanner, I18n.t("paper.doi"));
        ResearchPaper p1 = paper(id, title, citations, pages, Calendar.getInstance().get(Calendar.YEAR), doi);
        University.getInstance().getJournalService().publishPaper(researcher, p1, journal);
        System.out.println(I18n.t("published.papers"));
        System.out.println(p1.getCitation(Format.PLAIN_TEXT));
        System.out.println(p1.getCitation(Format.BIBTEX));
    }

    private static void showHIndex() {
        ResearcherProfile researcher = AppData.firstResearcherProfile();
        if (researcher == null) {
            System.out.println(I18n.t("no.researcher"));
            return;
        }
        System.out.println(I18n.f("hindex", researcher.calculateHIndex()));
    }

    private static void printSorted(Scanner scanner) {
        System.out.println(I18n.t("sort.papers"));
        int sort = ConsoleInput.readInt(scanner, I18n.t("sort"));
        if (sort == 2) {
            University.getInstance().getResearchService().printAllResearchPapers(new SortByDateStrategy());
        } else if (sort == 3) {
            University.getInstance().getResearchService().printAllResearchPapers(new SortByPagesStrategy());
        } else {
            University.getInstance().getResearchService().printAllResearchPapers(new SortByCitationsStrategy());
        }
    }

    private static void assignSupervisor() {
        GraduateStudent graduateStudent = AppData.firstGraduateStudent();
        ResearcherProfile researcher = AppData.firstResearcherProfile();
        if (graduateStudent == null) {
            System.out.println(I18n.t("no.graduate.student"));
            return;
        }
        if (researcher == null) {
            System.out.println(I18n.t("no.researcher"));
            return;
        }
        University.getInstance().getResearchService().assignSupervisor(graduateStudent, researcher);
        System.out.println(I18n.f("supervisor.assigned", researcher.getResearcherName()));
    }

    private static void joinProject(Scanner scanner) {
        ResearcherProfile researcher = AppData.firstResearcherProfile();
        if (researcher == null) {
            System.out.println(I18n.t("no.researcher"));
            return;
        }
        String id = ConsoleInput.readLine(scanner, I18n.t("project.id"));
        String topic = ConsoleInput.readLine(scanner, I18n.t("project.topic"));
        ResearchProject project = new ResearchProject(id, topic);
        User owner = researcher.getOwner();
        University.getInstance().getResearchService().joinProject(owner, project);
        System.out.println(project);
    }

    private static void showNewsAndNotifications() {
        System.out.println(I18n.t("news"));
        if (University.getInstance().getNewsService().getFeed().isEmpty()) {
            System.out.println(I18n.t("no.news"));
        }
        for (university.news.NewsItem item : University.getInstance().getNewsService().getFeed()) {
            System.out.println(item);
        }
        university.users.Student student = AppData.firstStudent();
        if (student != null) {
            System.out.println(I18n.f("student.notifications", student.getNotifications()));
        }
    }

    private static ResearchPaper paper(String id, String title, int citations, int pages, int year, String doi) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.MARCH, 10);
        return new ResearchPaper(id, title, citations, pages, calendar.getTime(), doi);
    }
}
