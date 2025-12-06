import java.time.LocalDate;

public class Loan {
    private static int nextId = 1;
    private int loanId;
    private User user;
    private Media media;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean returned;
    private LocalDate lastAccruedDate;


    public Loan(Media media,User user)
    {
        this.loanId = nextId++;
        this.media = media;
        this.user = user;
        this.borrowDate = LocalDate.now();
        this.dueDate = LocalDate.now().plusDays(media.getLoanDurationDays());
        this.returnDate = null;
        this.returned = false;
        this.lastAccruedDate = this.dueDate;
    }


    public User getUser() {return this.user;}
    public Media getMedia() { return media; }
    public LocalDate getDueDate() {return this.dueDate;}
    public LocalDate getReturnDate() {return this.returnDate;}
    public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}
    public void setReturned(boolean returned) {this.returned = returned;}
    public void setReturnDate(LocalDate now) {this.returnDate = now; }
    public boolean getReturned() {return this.returned;}
    public LocalDate getLastAccruedDate() { return lastAccruedDate; }
    public void setLastAccruedDate(LocalDate lastAccruedDate) { this.lastAccruedDate = lastAccruedDate; }

}
