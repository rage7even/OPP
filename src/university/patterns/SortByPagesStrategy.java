package university.patterns;

import university.research.ResearchPaper;

public class SortByPagesStrategy implements PaperSortStrategy {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(ResearchPaper a, ResearchPaper b) {
        return Integer.compare(b.getPages(), a.getPages());
    }

    @Override
    public String name() {
        return "pages";
    }
}
