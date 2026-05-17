package university.services;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import university.core.University;
import university.enums.NewsTopic;
import university.news.News;
import university.news.NewsItem;
import university.news.PinnedNewsDecorator;
import university.patterns.Observer;
import university.research.ResearchPaper;
import university.research.ResearcherProfile;

public class NewsService implements Observer, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void update(ResearchPaper paper) {
        generateResearchAnnouncement(paper);
    }

    public NewsItem addGeneralNews(String title, String content) {
        NewsItem item = new News("NEWS-" + System.nanoTime(), title, content, NewsTopic.GENERAL);
        University.getInstance().addNews(item);
        return item;
    }

    public NewsItem generateResearchAnnouncement(ResearchPaper paper) {
        NewsItem item = new PinnedNewsDecorator(new News("NEWS-" + System.nanoTime(),
                "New research paper: " + paper.getTitle(),
                paper.getAuthorsAsText() + " published in " + paper.getJournalTitle(),
                NewsTopic.RESEARCH));
        University.getInstance().addNews(item);
        return item;
    }

    public NewsItem generateTopCitedResearcherNews(String school, int year) {
        ResearcherProfile top = University.getInstance().getResearchService().getTopCitedResearcher(school, year);
        String text = top == null ? "No researcher data for " + school : top.toString();
        NewsItem item = new PinnedNewsDecorator(new News("NEWS-" + System.nanoTime(),
                "Top cited researcher " + year, text, NewsTopic.RESEARCH));
        University.getInstance().addNews(item);
        return item;
    }

    public List<NewsItem> getFeed() {
        List<NewsItem> feed = University.getInstance().getNewsFeed();
        Collections.sort(feed, new Comparator<NewsItem>() {
            @Override
            public int compare(NewsItem a, NewsItem b) {
                return Integer.compare(b.getPriority(), a.getPriority());
            }
        });
        return feed;
    }
}
