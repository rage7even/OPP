package university.patterns;

import java.text.SimpleDateFormat;

import university.research.ResearchPaper;

public class PlainTextCitationFormatter implements CitationFormatter {
    private static final long serialVersionUID = 1L;

    @Override
    public String format(ResearchPaper paper) {
        String year = new SimpleDateFormat("yyyy").format(paper.getPublishedAt());
        return paper.getAuthorsAsText() + " (" + year + "). " + paper.getTitle()
                + ". " + paper.getJournalTitle() + ". doi:" + paper.getDoi();
    }
}
