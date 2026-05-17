package university.services;

import java.io.Serializable;

import university.core.University;
import university.research.Journal;
import university.research.ResearchPaper;
import university.research.Researcher;
import university.users.User;

public class JournalService implements Serializable {
    private static final long serialVersionUID = 1L;

    public void subscribe(User user, Journal journal) {
        journal.subscribe(user);
    }

    public void unsubscribe(User user, Journal journal) {
        journal.unsubscribe(user);
    }

    public void publishPaper(Researcher author, ResearchPaper paper, Journal journal) {
        author.publishPaper(paper, journal);
        University.getInstance().getLogService().log(null, "Published paper " + paper.getTitle());
    }
}
