package university.news;

public class PinnedNewsDecorator extends NewsDecorator {
    private static final long serialVersionUID = 1L;

    public PinnedNewsDecorator(NewsItem wrappee) {
        super(wrappee);
    }

    @Override
    public boolean isPinned() {
        return true;
    }

    @Override
    public int getPriority() {
        return getWrappee().getPriority() + 100;
    }

    @Override
    public String toString() {
        return "[PINNED] " + getTitle() + " (" + getTopic() + ")";
    }
}
