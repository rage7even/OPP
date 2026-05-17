package university.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.enums.DegreeType;
import university.exceptions.LowHIndexException;
import university.research.ResearchPaper;
import university.research.Researcher;
import university.research.ResearcherProfile;

public class GraduateStudent extends Student {
    private static final long serialVersionUID = 1L;

    private DegreeType degree;
    private Researcher supervisor;
    private List<ResearchPaper> diplomaProjects;

    public GraduateStudent(String id, String name, String email, String passwordHash, String studentId, DegreeType degree) {
        super(id, name, email, passwordHash, studentId);
        this.degree = degree;
        this.diplomaProjects = new ArrayList<ResearchPaper>();
        setResearcherProfile(new ResearcherProfile("RP-" + id, this, "Graduate School"));
    }

    public void assignSupervisor(Researcher supervisor) {
        int hIndex = supervisor.calculateHIndex();
        if (hIndex < 3) {
            throw new LowHIndexException(hIndex);
        }
        this.supervisor = supervisor;
    }

    public void addDiplomaProject(ResearchPaper paper) {
        if (!diplomaProjects.contains(paper)) {
            diplomaProjects.add(paper);
        }
    }

    public DegreeType getDegree() {
        return degree;
    }

    public Researcher getSupervisor() {
        return supervisor;
    }

    public List<ResearchPaper> getDiplomaProjects() {
        return Collections.unmodifiableList(diplomaProjects);
    }
}
