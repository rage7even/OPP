package university.research;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.exceptions.DuplicateParticipantException;

public class ResearchProject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String projectId;
    private String topic;
    private List<ResearchPaper> papers;
    private List<Researcher> participants;

    public ResearchProject(String projectId, String topic) {
        this.projectId = projectId;
        this.topic = topic;
        this.papers = new ArrayList<ResearchPaper>();
        this.participants = new ArrayList<Researcher>();
    }

    public void addParticipant(Researcher researcher) {
        if (participants.contains(researcher)) {
            throw new DuplicateParticipantException(projectId);
        }
        participants.add(researcher);
    }

    public void addPaper(ResearchPaper paper) {
        if (!papers.contains(paper)) {
            papers.add(paper);
        }
    }

    public String getProjectId() {
        return projectId;
    }

    public String getTopic() {
        return topic;
    }

    public List<ResearchPaper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    public List<Researcher> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    @Override
    public String toString() {
        return projectId + " " + topic + " participants=" + participants.size();
    }
}
