package university.patterns;

import university.research.ResearchPaper;

public class SortByCitationsStrategy implements PaperSortStrategy {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(ResearchPaper a, ResearchPaper b) {
        return Integer.compare(b.getCitations(), a.getCitations());
    }

    @Override
    public String name() {
        return "citations";
    }
}
