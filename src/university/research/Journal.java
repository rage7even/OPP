package university.research;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.exceptions.DuplicateSubscriptionException;
import university.patterns.Observer;
import university.patterns.Subject;

public class Journal implements Subject, Serializable {
    private static final long serialVersionUID = 1L;

    private String journalId;
    private String title;
    private List<Observer> subscribers;
    private List<ResearchPaper> papers;

    public Journal(String journalId, String title) {
        this.journalId = journalId;
        this.title = title;
        this.subscribers = new ArrayList<Observer>();
        this.papers = new ArrayList<ResearchPaper>();
    }

    @Override
    public void subscribe(Observer observer) {
        if (subscribers.contains(observer)) {
            throw new DuplicateSubscriptionException(journalId);
        }
        subscribers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        subscribers.remove(observer);
    }

    @Override
    public void notifyObservers(ResearchPaper paper) {
        for (Observer observer : subscribers) {
            observer.update(paper);
        }
    }

    public void publishPaper(ResearchPaper paper) {
        if (!papers.contains(paper)) {
            papers.add(paper);
            paper.setJournal(this);
            notifyObservers(paper);
        }
    }

    public String getJournalId() {
        return journalId;
    }

    public String getTitle() {
        return title;
    }

    public List<Observer> getSubscribers() {
        return Collections.unmodifiableList(subscribers);
    }

    public List<ResearchPaper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    @Override
    public String toString() {
        return journalId + " - " + title + " papers=" + papers.size();
    }
}
