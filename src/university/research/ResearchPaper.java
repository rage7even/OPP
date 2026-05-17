package university.research;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import university.enums.Format;
import university.patterns.CitationFormatterFactory;

public class ResearchPaper implements Serializable {
    private static final long serialVersionUID = 1L;

    private String paperId;
    private String title;
    private List<Researcher> authors;
    private Journal journal;
    private int citations;
    private int pages;
    private Date publishedAt;
    private String doi;

    public ResearchPaper(String paperId, String title, int citations, int pages, Date publishedAt, String doi) {
        this.paperId = paperId;
        this.title = title;
        this.citations = citations;
        this.pages = pages;
        this.publishedAt = new Date(publishedAt.getTime());
        this.doi = doi;
        this.authors = new ArrayList<Researcher>();
    }

    public String getCitation(Format format) {
        return new CitationFormatterFactory().create(format).format(this);
    }

    public void addAuthor(Researcher researcher) {
        if (!authors.contains(researcher)) {
            authors.add(researcher);
        }
    }

    void setJournal(Journal journal) {
        this.journal = journal;
    }

    public String getAuthorsAsText() {
        if (authors.isEmpty()) {
            return "Unknown author";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            if (i > 0) {
                sb.append("; ");
            }
            sb.append(authors.get(i).getResearcherName());
        }
        return sb.toString();
    }

    public String getJournalTitle() {
        return journal == null ? "Unpublished" : journal.getTitle();
    }

    public String getPaperId() {
        return paperId;
    }

    public String getTitle() {
        return title;
    }

    public List<Researcher> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    public Journal getJournal() {
        return journal;
    }

    public int getCitations() {
        return citations;
    }

    public int getPages() {
        return pages;
    }

    public Date getPublishedAt() {
        return new Date(publishedAt.getTime());
    }

    public String getDoi() {
        return doi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResearchPaper)) {
            return false;
        }
        ResearchPaper that = (ResearchPaper) o;
        return Objects.equals(paperId, that.paperId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId);
    }

    @Override
    public String toString() {
        return title + " [" + citations + " citations, " + pages + " pages, " + getJournalTitle() + "]";
    }
}
