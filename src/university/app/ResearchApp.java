package university.app;

import java.util.Calendar;
import java.util.Scanner;

import university.core.University;
import university.enums.Format;
import university.patterns.SortByCitationsStrategy;
import university.patterns.SortByDateStrategy;
import university.patterns.SortByPagesStrategy;
import university.research.Journal;
import university.research.JournalRequest;
import university.research.ResearchPaper;
import university.research.ResearchProject;
import university.research.ResearcherProfile;
import university.users.GraduateStudent;
import university.users.User;

public final class ResearchApp {
    private ResearchApp() {
    }

    public static void run(Scanner scanner, User currentUser) {
        while (true) {
            System.out.println(I18n.t("research.title"));
            System.out.println(I18n.t("research.create.journal"));
            System.out.println(I18n.t("research.publish"));
            System.out.println(I18n.t("research.hindex"));
            System.out.println(I18n.t("research.sorted"));
            if (currentUser instanceof GraduateStudent) {
                System.out.println(I18n.t("research.supervisor"));
            }
            System.out.println(I18n.t("research.join"));
            System.out.println(I18n.t("research.news"));
            System.out.println(I18n.t("research.journal.requests"));
            if (currentUser instanceof GraduateStudent) {
                System.out.println(I18n.t("research.diploma.add"));
                System.out.println(I18n.t("research.diploma.show"));
            }
            System.out.println(I18n.t("back"));

            int choice = ConsoleInput.readInt(scanner, I18n.t("choose"));
            switch (choice) {
                case 1:
                    requestJournalCreation(scanner, currentUser);
                    break;
                case 2:
                    publishPaper(scanner, currentUser);
                    break;
                case 3:
                    showHIndex(currentUser);
                    break;
                case 4:
                    printSorted(scanner);
                    break;
                case 5:
                    if (currentUser instanceof GraduateStudent) {
                        setSupervisor(scanner, (GraduateStudent) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 6:
                    joinProject(scanner, currentUser);
                    break;
                case 7:
                    showNewsAndNotifications(currentUser);
                    break;
                case 8:
                    showMyJournalRequests(currentUser);
                    break;
                case 9:
                    if (currentUser instanceof GraduateStudent) {
                        addDiplomaProject(scanner, (GraduateStudent) currentUser);
                    } else {
                        System.out.println(I18n.t("access.denied"));
                    }
                    break;
                case 10:
                    if (currentUser instanceof GraduateStudent) {
                        showDiplomaProjects((GraduateStudent) currentUser);
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

    private static void requestJournalCreation(Scanner scanner, User currentUser) {
        String id = readRequiredLine(scanner, I18n.t("journal.id"));
        if (id == null) {
            return;
        }
        if (findJournal(id, false) != null) {
            System.out.println(I18n.t("duplicate.journal"));
            return;
        }
        if (pendingJournalRequestExists(id)) {
            System.out.println(I18n.t("duplicate.journal.request"));
            return;
        }
        String title = readRequiredLine(scanner, I18n.t("journal.title"));
        if (title == null) {
            return;
        }
        JournalRequest request = new JournalRequest(
                "JREQ-" + System.nanoTime(),
                currentUser,
                id,
                title);
        University.getInstance().addJournalRequest(request);
        currentUser.addNotification("Journal request submitted: " + title + " (" + id + ")");
        University.getInstance().getLogService().log(currentUser, "Submitted journal request " + request.getRequestId());
        System.out.println(I18n.f("created.journal.request", AppFormatter.journalRequest(request)));
    }

    private static void publishPaper(Scanner scanner, User currentUser) {
        ResearcherProfile researcher = currentUser.getResearcherProfile();
        if (researcher == null) {
            System.out.println(I18n.t("no.researcher"));
            return;
        }
        Journal journal = selectJournal(scanner);
        if (journal == null) {
            return;
        }
        String id = readRequiredLine(scanner, I18n.t("paper.id"));
        if (id == null) {
            return;
        }
        String title = readRequiredLine(scanner, I18n.t("paper.title"));
        if (title == null) {
            return;
        }
        int citations = ConsoleInput.readInt(scanner, I18n.t("paper.citations"));
        if (citations < 0) {
            System.out.println(I18n.t("paper.citations.nonnegative"));
            return;
        }
        int pages = ConsoleInput.readInt(scanner, I18n.t("paper.pages"));
        if (pages <= 0) {
            System.out.println(I18n.t("paper.pages.positive"));
            return;
        }
        String doi = readRequiredLine(scanner, I18n.t("paper.doi"));
        if (doi == null) {
            return;
        }
        ResearchPaper p1 = paper(id, title, citations, pages, Calendar.getInstance().get(Calendar.YEAR), doi);
        try {
            University.getInstance().getJournalService().publishPaper(researcher, p1, journal);
            System.out.println(I18n.t("published.papers"));
            System.out.println(p1.getCitation(Format.PLAIN_TEXT));
            System.out.println(p1.getCitation(Format.BIBTEX));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showHIndex(User currentUser) {
        ResearcherProfile researcher = currentUser.getResearcherProfile();
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

    private static void setSupervisor(Scanner scanner, GraduateStudent graduateStudent) {
        ResearcherProfile researcher = selectResearcherProfile(scanner);
        if (researcher == null) {
            return;
        }
        if (researcher.getOwner().equals(graduateStudent)) {
            System.out.println(I18n.t("access.denied"));
            return;
        }
        try {
            University.getInstance().getResearchService().setSupervisor(graduateStudent, researcher);
            System.out.println(I18n.f("supervisor.assigned", researcher.getResearcherName()));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void joinProject(Scanner scanner, User currentUser) {
        ResearcherProfile researcher = currentUser.getResearcherProfile();
        if (researcher == null) {
            System.out.println(I18n.t("no.researcher"));
            return;
        }
        String id = readRequiredLine(scanner, I18n.t("project.id"));
        if (id == null) {
            return;
        }
        String topic = readRequiredLine(scanner, I18n.t("project.topic"));
        if (topic == null) {
            return;
        }
        ResearchProject project = new ResearchProject(id, topic);
        User owner = researcher.getOwner();
        University.getInstance().getResearchService().joinProject(owner, project);
        System.out.println(project);
    }

    private static void showNewsAndNotifications(User currentUser) {
        System.out.println(I18n.t("news"));
        if (University.getInstance().getNewsService().getFeed().isEmpty()) {
            System.out.println(I18n.t("no.news"));
        }
        for (university.news.NewsItem item : University.getInstance().getNewsService().getFeed()) {
            System.out.println(item);
        }
        System.out.println(I18n.f("student.notifications", currentUser.getNotifications()));
    }

    private static void showMyJournalRequests(User currentUser) {
        boolean found = false;
        for (JournalRequest request : University.getInstance().getJournalRequests()) {
            if (currentUser.equals(request.getRequester())) {
                found = true;
                System.out.println(AppFormatter.journalRequest(request));
            }
        }
        if (!found) {
            System.out.println(I18n.t("no.my.journal.requests"));
        }
    }

    private static void addDiplomaProject(Scanner scanner, GraduateStudent graduateStudent) {
        String id = readRequiredLine(scanner, I18n.t("paper.id"));
        if (id == null) {
            return;
        }
        String title = readRequiredLine(scanner, I18n.t("paper.title"));
        if (title == null) {
            return;
        }
        int citations = ConsoleInput.readInt(scanner, I18n.t("paper.citations"));
        if (citations < 0) {
            System.out.println(I18n.t("paper.citations.nonnegative"));
            return;
        }
        int pages = ConsoleInput.readInt(scanner, I18n.t("paper.pages"));
        if (pages <= 0) {
            System.out.println(I18n.t("paper.pages.positive"));
            return;
        }
        int year = ConsoleInput.readInt(scanner, I18n.t("year"));
        if (year <= 1900) {
            System.out.println(I18n.t("year.invalid"));
            return;
        }
        String doi = readRequiredLine(scanner, I18n.t("paper.doi"));
        if (doi == null) {
            return;
        }
        ResearchPaper diplomaProject = paper(id, title, citations, pages, year, doi);
        if (graduateStudent.getResearcherProfile() != null) {
            diplomaProject.addAuthor(graduateStudent.getResearcherProfile());
        }
        graduateStudent.addDiplomaProject(diplomaProject);
        University.getInstance().getLogService().log(graduateStudent, "Added diploma project " + id);
        System.out.println(I18n.f("diploma.project.saved", diplomaProject));
    }

    private static void showDiplomaProjects(GraduateStudent graduateStudent) {
        if (graduateStudent.getDiplomaProjects().isEmpty()) {
            System.out.println(I18n.t("no.diploma.projects"));
            return;
        }
        for (ResearchPaper diplomaProject : graduateStudent.getDiplomaProjects()) {
            System.out.println(diplomaProject);
        }
    }

    private static ResearchPaper paper(String id, String title, int citations, int pages, int year, String doi) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.MARCH, 10);
        return new ResearchPaper(id, title, citations, pages, calendar.getTime(), doi);
    }

    private static Journal selectJournal(Scanner scanner) {
        if (University.getInstance().getJournals().isEmpty()) {
            System.out.println(I18n.t("no.journal"));
            return null;
        }
        for (Journal journal : University.getInstance().getJournals()) {
            System.out.println(journal);
        }
        String journalId = ConsoleInput.readLine(scanner, I18n.t("journal.id"));
        return findJournal(journalId, true);
    }

    private static Journal findJournal(String journalId, boolean printIfMissing) {
        for (Journal journal : University.getInstance().getJournals()) {
            if (journal.getJournalId().equals(journalId.trim())) {
                return journal;
            }
        }
        if (printIfMissing) {
            System.out.println(I18n.t("no.journal"));
        }
        return null;
    }

    private static ResearcherProfile selectResearcherProfile(Scanner scanner) {
        boolean hasResearcher = false;
        for (User user : University.getInstance().getUsers()) {
            if (user.getResearcherProfile() != null) {
                hasResearcher = true;
                System.out.println(user.getId() + " - " + user.getResearcherProfile());
            }
        }
        if (!hasResearcher) {
            System.out.println(I18n.t("no.researcher"));
            return null;
        }
        String researcherId = ConsoleInput.readLine(scanner, I18n.t("researcher.id"));
        for (User user : University.getInstance().getUsers()) {
            if (user.getResearcherProfile() != null && user.getId().equals(researcherId)) {
                return user.getResearcherProfile();
            }
        }
        System.out.println(I18n.t("no.researcher"));
        return null;
    }

    private static String readRequiredLine(Scanner scanner, String prompt) {
        String value = ConsoleInput.readLine(scanner, prompt).trim();
        if (value.isEmpty()) {
            System.out.println(I18n.t("required.field"));
            return null;
        }
        return value;
    }

    private static boolean pendingJournalRequestExists(String journalId) {
        for (JournalRequest request : University.getInstance().getJournalRequests()) {
            if (request.getStatus() == university.enums.RegistrationStatus.PENDING
                    && request.getJournalId().equalsIgnoreCase(journalId)) {
                return true;
            }
        }
        return false;
    }
}

