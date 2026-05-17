package university.research;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.exceptions.DuplicateParticipantException;
import university.patterns.PaperSortStrategy;
import university.users.User;

public class ResearcherProfile implements Researcher {
    private static final long serialVersionUID = 1L;

    private String profileId;
    private User owner;
    private String school;
    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;

    public ResearcherProfile(String profileId, User owner, String school) {
        this.profileId = profileId;
        this.owner = owner;
        this.school = school;
        this.papers = new ArrayList<ResearchPaper>();
        this.projects = new ArrayList<ResearchProject>();
    }

    @Override
    public int calculateHIndex() {
        List<Integer> citations = new ArrayList<Integer>();
        for (ResearchPaper paper : papers) {
            citations.add(paper.getCitations());
        }
        Collections.sort(citations, Collections.reverseOrder());
        int h = 0;
        for (int i = 0; i < citations.size(); i++) {
            if (citations.get(i) >= i + 1) {
                h = i + 1;
            }
        }
        return h;
    }

    @Override
    public void printPapers(PaperSortStrategy strategy) {
        List<ResearchPaper> sorted = new ArrayList<ResearchPaper>(papers);
        Collections.sort(sorted, strategy);
        for (ResearchPaper paper : sorted) {
            System.out.println(paper);
        }
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    @Override
    public void publishPaper(ResearchPaper paper, Journal journal) {
        if (!papers.contains(paper)) {
            papers.add(paper);
        }
        paper.addAuthor(this);
        journal.publishPaper(paper);
    }

    public void joinProject(ResearchProject project) throws DuplicateParticipantException {
        project.addParticipant(this);
        if (!projects.contains(project)) {
            projects.add(project);
        }
    }

    public String getProfileId() {
        return profileId;
    }

    public User getOwner() {
        return owner;
    }

    @Override
    public String getResearcherName() {
        return owner.getName();
    }

    @Override
    public String getSchool() {
        return school;
    }

    public List<ResearchProject> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    @Override
    public String toString() {
        return getResearcherName() + " (" + school + "), h-index=" + calculateHIndex();
    }
}
