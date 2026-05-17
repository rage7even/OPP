package university.patterns;

import university.research.ResearchPaper;

public class SortByDateStrategy implements PaperSortStrategy {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(ResearchPaper a, ResearchPaper b) {
        return b.getPublishedAt().compareTo(a.getPublishedAt());
    }

    @Override
    public String name() {
        return "date";
    }
}
