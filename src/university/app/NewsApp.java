package university.app;

import java.util.List;
import java.util.Scanner;

import university.core.University;
import university.news.Comment;
import university.news.NewsItem;
import university.research.Journal;
import university.research.ResearchPaper;
import university.users.User;

public final class NewsApp {
    private NewsApp() {
    }

    public static void run(Scanner scanner, User currentUser) {
        NewsItem selectedNews = null;
        while (true) {
            System.out.println(I18n.t("news.app.title"));
            System.out.println(I18n.t("news.show"));
            System.out.println(I18n.t("news.details"));
            if (selectedNews != null) {
                System.out.println(I18n.f("selected.news", selectedNews.getTitle()));
                System.out.println(I18n.t("news.comment"));
            }
            System.out.println(I18n.t("journals.show"));
            System.out.println(I18n.t("journal.subscribe.menu"));
            System.out.println(I18n.t("journal.unsubscribe.menu"));
            System.out.println(I18n.t("journal.papers.menu"));
            System.out.println(I18n.t("notifications.show"));
            System.out.println(I18n.t("back"));

            int choice = ConsoleInput.readInt(scanner, I18n.t("choose"));
            switch (choice) {
                case 1:
                    showNews();
                    break;
                case 2:
                    selectedNews = showDetails(scanner);
                    break;
                case 3:
                    if (selectedNews == null) {
                        System.out.println(I18n.t("select.news.first"));
                    } else {
                        addComment(scanner, currentUser, selectedNews);
                    }
                    break;
                case 4:
                    showJournals();
                    break;
                case 5:
                    subscribeJournal(scanner, currentUser);
                    break;
                case 6:
                    unsubscribeJournal(scanner, currentUser);
                    break;
                case 7:
                    showJournalPapers(scanner);
                    break;
                case 8:
                    showNotifications(currentUser);
                    break;
                case 0:
                    return;
                default:
                    System.out.println(I18n.t("unknown.choice"));
                    break;
            }
        }
    }

    private static void showNews() {
        List<NewsItem> feed = University.getInstance().getNewsService().getFeed();
        if (feed.isEmpty()) {
            System.out.println(I18n.t("no.news"));
            return;
        }
        for (int i = 0; i < feed.size(); i++) {
            NewsItem item = feed.get(i);
            System.out.println((i + 1) + " - " + headline(item));
        }
    }

    private static NewsItem showDetails(Scanner scanner) {
        NewsItem item = selectNews(scanner);
        if (item == null) {
            return null;
        }
        printDetails(item);
        return item;
    }

    private static void addComment(Scanner scanner, User currentUser, NewsItem item) {
        String text = ConsoleInput.readLine(scanner, I18n.t("comment.text"));
        item.addComment(currentUser, text);
        System.out.println(I18n.t("comment.added"));
    }

    private static NewsItem selectNews(Scanner scanner) {
        List<NewsItem> feed = University.getInstance().getNewsService().getFeed();
        if (feed.isEmpty()) {
            System.out.println(I18n.t("no.news"));
            return null;
        }
        showNews();
        int number = ConsoleInput.readInt(scanner, I18n.t("news.number"));
        if (number < 1 || number > feed.size()) {
            System.out.println(I18n.t("unknown.choice"));
            return null;
        }
        return feed.get(number - 1);
    }

    private static void printDetails(NewsItem item) {
        System.out.println(headline(item));
        System.out.println(item.getContent());
        System.out.println(I18n.t("comments"));
        if (item.getComments().isEmpty()) {
            System.out.println(I18n.t("no.comments"));
            return;
        }
        for (Comment comment : item.getComments()) {
            System.out.println("- " + comment);
        }
    }

    private static String headline(NewsItem item) {
        return (item.isPinned() ? "[PINNED] " : "")
                + item.getTitle()
                + " (" + item.getTopic() + ", "
                + item.getComments().size() + " " + I18n.t("comments.word") + ")";
    }

    private static void showJournals() {
        if (University.getInstance().getJournals().isEmpty()) {
            System.out.println(I18n.t("no.journal"));
            return;
        }
        for (Journal journal : University.getInstance().getJournals()) {
            System.out.println(journal + ", subscribers=" + journal.getSubscribers().size());
        }
    }

    private static void subscribeJournal(Scanner scanner, User currentUser) {
        Journal journal = selectJournal(scanner);
        if (journal == null) {
            return;
        }
        try {
            University.getInstance().getJournalService().subscribe(currentUser, journal);
            System.out.println(I18n.f("journal.subscribed", journal.getTitle()));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void unsubscribeJournal(Scanner scanner, User currentUser) {
        Journal journal = selectJournal(scanner);
        if (journal == null) {
            return;
        }
        University.getInstance().getJournalService().unsubscribe(currentUser, journal);
        System.out.println(I18n.f("journal.unsubscribed", journal.getTitle()));
    }

    private static void showJournalPapers(Scanner scanner) {
        Journal journal = selectJournal(scanner);
        if (journal == null) {
            return;
        }
        if (journal.getPapers().isEmpty()) {
            System.out.println(I18n.t("no.papers"));
            return;
        }
        for (ResearchPaper paper : journal.getPapers()) {
            System.out.println(paper);
        }
    }

    private static void showNotifications(User currentUser) {
        System.out.println(I18n.t("notifications"));
        if (currentUser.getNotifications().isEmpty()) {
            System.out.println(I18n.t("no.notifications"));
            return;
        }
        for (String notification : currentUser.getNotifications()) {
            System.out.println("- " + notification);
        }
    }

    private static Journal selectJournal(Scanner scanner) {
        if (University.getInstance().getJournals().isEmpty()) {
            System.out.println(I18n.t("no.journal"));
            return null;
        }
        showJournals();
        String journalId = ConsoleInput.readLine(scanner, I18n.t("journal.id"));
        for (Journal journal : University.getInstance().getJournals()) {
            if (journal.getJournalId().equals(journalId)) {
                return journal;
            }
        }
        System.out.println(I18n.t("no.journal"));
        return null;
    }
}
