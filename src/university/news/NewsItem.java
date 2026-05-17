package university.news;

import java.io.Serializable;
import java.util.List;

import university.enums.NewsTopic;
import university.users.User;

public interface NewsItem extends Serializable {
    String getTitle();
    String getContent();
    NewsTopic getTopic();
    boolean isPinned();
    int getPriority();
    void addComment(User author, String text);
    List<Comment> getComments();
}
