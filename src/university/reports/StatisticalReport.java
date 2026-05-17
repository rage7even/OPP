package university.reports;

import java.io.Serializable;
import java.util.Date;

public class StatisticalReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date createdAt;
    private String content;

    public StatisticalReport(String content) {
        this.createdAt = new Date();
        this.content = content;
    }

    public Date getCreatedAt() {
        return new Date(createdAt.getTime());
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Report created at " + createdAt + "\n" + content;
    }
}
