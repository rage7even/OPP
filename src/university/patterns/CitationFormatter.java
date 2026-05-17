package university.patterns;

import java.io.Serializable;

import university.research.ResearchPaper;

public interface CitationFormatter extends Serializable {
    String format(ResearchPaper paper);
}
