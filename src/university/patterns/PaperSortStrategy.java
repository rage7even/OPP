package university.patterns;

import java.io.Serializable;
import java.util.Comparator;

import university.research.ResearchPaper;

public interface PaperSortStrategy extends Comparator<ResearchPaper>, Serializable {
    String name();
}
