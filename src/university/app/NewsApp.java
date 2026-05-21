package university.app;

import java.util.List;
import java.util.Scanner;

import university.core.University;
import university.news.Comment;
import university.news.NewsItem;
import university.users.User;

public final class NewsApp {
    private NewsApp() {
    }

    public static void run(Scanner scanner, User currentUser) {
        while (true) {
            System.out.println(I18n.t("news.app.title"));
            System.out.println(I18n.t("news.show"));
            System.out.println(I18n.t("news.details"));
            System.out.println(I18n.t("news.comment"));
            System.out.println(I18n.t("back"));

            int choice = ConsoleInput.readInt(scanner, I18n.t("choose"));
            switch (choice) {
                case 1:
                    showNews();
                    break;
                case 2:
                    showDetails(scanner);
                    break;
                case 3:
                    addComment(scanner, currentUser);
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

    private static void showDetails(Scanner scanner) {
        NewsItem item = selectNews(scanner);
        if (item == null) {
            return;
        }
        printDetails(item);
    }

    private static void addComment(Scanner scanner, User currentUser) {
        NewsItem item = selectNews(scanner);
        if (item == null) {
            return;
        }
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
}
