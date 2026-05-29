package software_capstone.backend.app.store.document.category;

public enum ItemGrade {
    A(20), B(10), C(5);

    private final int xpOnFeed;

    ItemGrade(int xpOnFeed) {
        this.xpOnFeed = xpOnFeed;
    }

    public int getXpOnFeed() {
        return xpOnFeed;
    }
}
