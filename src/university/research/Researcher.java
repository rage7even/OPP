package university.research;

import java.io.Serializable;
import java.util.List;

import university.patterns.PaperSortStrategy;

public interface Researcher extends Serializable {
    int calculateHIndex();
    void printPapers(PaperSortStrategy strategy);
    List<ResearchPaper> getPapers();
    void publishPaper(ResearchPaper paper, Journal journal);
    String getResearcherName();
    String getSchool();
}
