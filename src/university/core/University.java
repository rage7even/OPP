package university.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import university.education.Course;
import university.education.CourseOffering;
import university.news.NewsItem;
import university.reports.LogEntry;
import university.research.Journal;
import university.services.AuthService;
import university.services.CourseRegistrationService;
import university.services.FileUniversityDataStore;
import university.services.InMemoryUserRepository;
import university.services.JournalService;
import university.services.LogService;
import university.services.NewsService;
import university.services.NotificationService;
import university.services.ReportService;
import university.services.ResearchService;
import university.services.SupportDeskService;
import university.services.UniversityDataStore;
import university.services.UserManagementService;
import university.support.SupportRequest;
import university.users.User;

public class University implements Serializable {
    private static final long serialVersionUID = 1L;
    private static University instance = new University();

    private List<User> users;
    private List<Course> courses;
    private List<CourseOffering> courseOfferings;
    private List<Journal> journals;
    private List<NewsItem> newsFeed;
    private List<SupportRequest> supportRequests;
    private List<LogEntry> logEntries;

    private transient AuthService authService;
    private transient CourseRegistrationService registrationService;
    private transient NewsService newsService;
    private transient SupportDeskService supportDesk;
    private transient UniversityDataStore dataStore;
    private transient LogService logService;
    private transient ResearchService researchService;
    private transient JournalService journalService;
    private transient NotificationService notificationService;
    private transient ReportService reportService;
    private transient UserManagementService userManagementService;

    private University() {
        users = new ArrayList<User>();
        courses = new ArrayList<Course>();
        courseOfferings = new ArrayList<CourseOffering>();
        journals = new ArrayList<Journal>();
        newsFeed = new ArrayList<NewsItem>();
        supportRequests = new ArrayList<SupportRequest>();
        logEntries = new ArrayList<LogEntry>();
        rebuildServices();
    }

    public static University getInstance() {
        if (instance.authService == null) {
            instance.rebuildServices();
        }
        return instance;
    }

    public void save() {
        dataStore.save(this);
    }

    public void load() {
        University loaded = dataStore.load();
        users = loaded.users;
        courses = loaded.courses;
        courseOfferings = loaded.courseOfferings;
        journals = loaded.journals;
        newsFeed = loaded.newsFeed;
        supportRequests = loaded.supportRequests;
        logEntries = loaded.logEntries;
        rebuildServices();
    }

    public void clear() {
        users.clear();
        courses.clear();
        courseOfferings.clear();
        journals.clear();
        newsFeed.clear();
        supportRequests.clear();
        logEntries.clear();
    }

    private void rebuildServices() {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        authService = new AuthService(userRepository);
        registrationService = new CourseRegistrationService();
        newsService = new NewsService();
        supportDesk = new SupportDeskService();
        dataStore = new FileUniversityDataStore("data/university.ser");
        logService = new LogService();
        researchService = new ResearchService();
        journalService = new JournalService();
        notificationService = new NotificationService();
        reportService = new ReportService();
        userManagementService = new UserManagementService();
    }

    public void addUser(User user) {
        removeUser(user.getId());
        users.add(user);
    }

    public void removeUser(String userId) {
        for (int i = users.size() - 1; i >= 0; i--) {
            if (users.get(i).getId().equals(userId)) {
                users.remove(i);
            }
        }
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public void addCourseOffering(CourseOffering offering) {
        if (!courseOfferings.contains(offering)) {
            courseOfferings.add(offering);
        }
    }

    public void addJournal(Journal journal) {
        if (!journals.contains(journal)) {
            journals.add(journal);
            journal.subscribe(newsService);
            journal.subscribe(notificationService);
        }
    }

    public void addNews(NewsItem item) {
        newsFeed.add(item);
    }

    public void addSupportRequest(SupportRequest request) {
        supportRequests.add(request);
    }

    public void addLogEntry(LogEntry entry) {
        logEntries.add(entry);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<CourseOffering> getCourseOfferings() {
        return courseOfferings;
    }

    public List<Journal> getJournals() {
        return journals;
    }

    public List<NewsItem> getNewsFeed() {
        return new ArrayList<NewsItem>(newsFeed);
    }

    public List<SupportRequest> getSupportRequests() {
        return Collections.unmodifiableList(supportRequests);
    }

    public List<LogEntry> getLogEntries() {
        return Collections.unmodifiableList(logEntries);
    }

    public AuthService getAuthService() {
        return authService;
    }

    public CourseRegistrationService getRegistrationService() {
        return registrationService;
    }

    public NewsService getNewsService() {
        return newsService;
    }

    public SupportDeskService getSupportDesk() {
        return supportDesk;
    }

    public LogService getLogService() {
        return logService;
    }

    public ResearchService getResearchService() {
        return researchService;
    }

    public JournalService getJournalService() {
        return journalService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public ReportService getReportService() {
        return reportService;
    }

    public UserManagementService getUserManagementService() {
        return userManagementService;
    }
}
