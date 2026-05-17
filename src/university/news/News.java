package university.news;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.enums.NewsTopic;
import university.users.User;

public class News implements NewsItem {
    private static final long serialVersionUID = 1L;

    private String newsId;
    private String title;
    private String content;
    private NewsTopic topic;
    private List<Comment> comments;

    public News(String newsId, String title, String content, NewsTopic topic) {
        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.topic = topic;
        this.comments = new ArrayList<Comment>();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public NewsTopic getTopic() {
        return topic;
    }

    @Override
    public boolean isPinned() {
        return false;
    }

    @Override
    public int getPriority() {
        return topic == NewsTopic.RESEARCH ? 5 : 1;
    }

    @Override
    public void addComment(User author, String text) {
        comments.add(new Comment("C-" + System.nanoTime(), author, text));
    }

    @Override
    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public String getNewsId() {
        return newsId;
    }

    @Override
    public String toString() {
        return (isPinned() ? "[PINNED] " : "") + title + " (" + topic + ")";
    }
}
