package university.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.core.University;
import university.exceptions.NotResearcherException;
import university.patterns.PaperSortStrategy;
import university.research.ResearchPaper;
import university.research.ResearchProject;
import university.research.Researcher;
import university.research.ResearcherProfile;
import university.users.GraduateStudent;
import university.users.User;

public class ResearchService implements Serializable {
    private static final long serialVersionUID = 1L;

    public void setSupervisor(GraduateStudent student, Researcher supervisor) {
        student.setSupervisor(supervisor);
    }

    public void joinProject(User user, ResearchProject project) {
        if (!user.isResearcher()) {
            throw new NotResearcherException(user.getId());
        }
        user.getResearcherProfile().joinProject(project);
    }

    public ResearcherProfile getTopCitedResearcher(String school, int year) {
        ResearcherProfile top = null;
        int bestCitations = -1;
        for (User user : University.getInstance().getUsers()) {
            ResearcherProfile profile = user.getResearcherProfile();
            if (profile != null && profile.getSchool().equalsIgnoreCase(school)) {
                int sum = 0;
                for (ResearchPaper paper : profile.getPapers()) {
                    @SuppressWarnings("deprecation")
                    int paperYear = paper.getPublishedAt().getYear() + 1900;
                    if (paperYear == year) {
                        sum += paper.getCitations();
                    }
                }
                if (sum > bestCitations) {
                    bestCitations = sum;
                    top = profile;
                }
            }
        }
        return top;
    }

    public void printAllResearchPapers(PaperSortStrategy strategy) {
        List<ResearchPaper> papers = new ArrayList<ResearchPaper>();
        for (User user : University.getInstance().getUsers()) {
            if (user.isResearcher()) {
                for (ResearchPaper paper : user.getResearcherProfile().getPapers()) {
                    if (!papers.contains(paper)) {
                        papers.add(paper);
                    }
                }
            }
        }
        Collections.sort(papers, strategy);
        for (ResearchPaper paper : papers) {
            System.out.println(paper);
        }
    }
}
