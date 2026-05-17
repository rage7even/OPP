package university.services;

import java.io.Serializable;

import university.patterns.Observer;
import university.research.ResearchPaper;

public class NotificationService implements Observer, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void update(ResearchPaper paper) {
        notifyNewPaper(paper);
    }

    public void notifyNewPaper(ResearchPaper paper) {
        System.out.println("Notification: new paper published - " + paper.getTitle());
    }

    public void notifyTopResearcher(String school, int year) {
        System.out.println("Notification: top researcher news generated for " + school + " " + year);
    }
}
