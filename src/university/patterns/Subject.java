package university.patterns;

import university.research.ResearchPaper;

public interface Subject {
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
    void notifyObservers(ResearchPaper paper);
}
