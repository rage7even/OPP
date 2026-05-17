package university.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import university.enums.Language;
import university.exceptions.AlreadyResearcherException;
import university.patterns.Observer;
import university.research.Journal;
import university.research.ResearchPaper;
import university.research.ResearcherProfile;

public abstract class User implements Observer, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String email;
    private String passwordHash;
    private Language language;
    private ResearcherProfile researcherProfile;
    private List<String> notifications;

    protected User(String id, String name, String email, String passwordHash) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.language = Language.EN;
        this.notifications = new ArrayList<String>();
    }

    public void changeLanguage(Language language) {
        this.language = language;
    }

    public void subscribeToJournal(Journal journal) {
        journal.subscribe(this);
    }

    public void unsubscribeFromJournal(Journal journal) {
        journal.unsubscribe(this);
    }

    @Override
    public void update(ResearchPaper paper) {
        notifications.add("New paper in " + paper.getJournalTitle() + ": " + paper.getTitle());
    }

    public ResearcherProfile becomeResearcher(String school) {
        if (researcherProfile != null) {
            throw new AlreadyResearcherException(id);
        }
        researcherProfile = new ResearcherProfile("RP-" + id, this, school);
        return researcherProfile;
    }

    protected void setResearcherProfile(ResearcherProfile researcherProfile) {
        this.researcherProfile = researcherProfile;
    }

    public boolean isResearcher() {
        return researcherProfile != null;
    }

    public ResearcherProfile getResearcherProfile() {
        return researcherProfile;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Language getLanguage() {
        return language;
    }

    public List<String> getNotifications() {
        return Collections.unmodifiableList(notifications);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + id + ", " + name + ", " + email + "}";
    }
}
