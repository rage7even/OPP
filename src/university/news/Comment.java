package university.news;

import java.io.Serializable;
import java.util.Date;

import university.users.User;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String commentId;
    private User author;
    private String content;
    private Date date;

    public Comment(String commentId, User author, String content) {
        this.commentId = commentId;
        this.author = author;
        this.content = content;
        this.date = new Date();
    }

    public String getCommentId() {
        return commentId;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    @Override
    public String toString() {
        return author.getName() + ": " + content;
    }
}
