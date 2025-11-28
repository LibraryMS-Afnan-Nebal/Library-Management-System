public class CD extends Media {
    private static int nextId = 1;
    private final int cdId;

    public CD(String title) {
        super(title);
        this.cdId = nextId++;
    }

    public CD(CD copy) {
        super(copy.getTitle());
        this.cdId = nextId++;
        this.setIsBorrowed(copy.getIsBorrowed());
    }

    @Override
    public int getLoanDurationDays() {
        return 7;
    }

    @Override
    public double getDailyFineRate() {
        return 20;
    }

}
