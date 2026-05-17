package university.patterns;

import java.io.Serializable;

import university.research.ResearchPaper;

public interface Observer extends Serializable {
    void update(ResearchPaper paper);
}
