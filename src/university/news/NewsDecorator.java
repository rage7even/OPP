package university.news;

import java.util.List;

import university.enums.NewsTopic;
import university.users.User;

public abstract class NewsDecorator implements NewsItem {
    private static final long serialVersionUID = 1L;

    private NewsItem wrappee;

    protected NewsDecorator(NewsItem wrappee) {
        this.wrappee = wrappee;
    }

    protected NewsItem getWrappee() {
        return wrappee;
    }

    @Override
    public String getTitle() {
        return wrappee.getTitle();
    }

    @Override
    public String getContent() {
        return wrappee.getContent();
    }

    @Override
    public NewsTopic getTopic() {
        return wrappee.getTopic();
    }

    @Override
    public boolean isPinned() {
        return wrappee.isPinned();
    }

    @Override
    public int getPriority() {
        return wrappee.getPriority();
    }

    @Override
    public void addComment(User author, String text) {
        wrappee.addComment(author, text);
    }

    @Override
    public List<Comment> getComments() {
        return wrappee.getComments();
    }

    @Override
    public String toString() {
        return wrappee.toString();
    }
}
