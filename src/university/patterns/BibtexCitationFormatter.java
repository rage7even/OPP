package university.patterns;

import java.text.SimpleDateFormat;

import university.research.ResearchPaper;

public class BibtexCitationFormatter implements CitationFormatter {
    private static final long serialVersionUID = 1L;

    @Override
    public String format(ResearchPaper paper) {
        String year = new SimpleDateFormat("yyyy").format(paper.getPublishedAt());
        return "@article{" + paper.getPaperId() + ",\n"
                + "  title={" + paper.getTitle() + "},\n"
                + "  author={" + paper.getAuthorsAsText() + "},\n"
                + "  journal={" + paper.getJournalTitle() + "},\n"
                + "  year={" + year + "},\n"
                + "  pages={" + paper.getPages() + "},\n"
                + "  doi={" + paper.getDoi() + "}\n"
                + "}";
    }
}
