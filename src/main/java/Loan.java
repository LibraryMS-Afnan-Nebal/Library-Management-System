import java.time.LocalDate;

public class Loan {
    private static int nextId = 1;
    private int loanId;
    private User user;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean returned;
    private boolean isOverdue;

    public Loan(Book book ,User user)
    {
        this.loanId = nextId++;
        this.book = book;
        this.user = user;
        this.borrowDate = LocalDate.now();
        this.dueDate = LocalDate.now().plusDays(28);
        this.returnDate = null;
        this.returned = false;
        this.isOverdue = false;
    }

    public User getUser() {return this.user;}
    public Book getBook() {return this.book;}
    public LocalDate getDueDate() {return this.dueDate;}
    public boolean getReturned() {return this.returned;}
    public LocalDate getReturnDate() {return this.returnDate;}
    public boolean getIsOverdue() {return isOverdue;}
    public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}
    public void setReturned(boolean returned) {this.returned = returned;}
    public void setReturnDate(LocalDate now) {this.returnDate = now; }
    public void setIsOverdue(boolean overdue) {this.isOverdue = overdue;}
}
