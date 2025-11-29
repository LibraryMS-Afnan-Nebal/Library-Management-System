import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Librarian {
    private final int id;
    private boolean loggedIn;
    private String username;
    private  String password;
    private LoanManager loanManager;

    public Librarian(int id, String username,String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.loggedIn = false;
        this.loanManager = LoanManager.getInstance();
    }

    /// this should be updated to detect both overdue books & cds
    public List<Loan> detectOverdueBooks()
    {
        List<Loan> listOfOverdueLoans = loanManager.getOverdueLoans();
        for(Loan loan : listOfOverdueLoans)
        {
            loanManager.accrueFines(10, loan);//To calculate fines
        }
        return listOfOverdueLoans;
    }
    // --- Getters ---
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isLoggedIn() { return loggedIn; }
    public LoanManager getLoanManager() { return loanManager; }

    // --- Setters ---
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }
    public void setLoanManager(LoanManager loanManager) { this.loanManager = loanManager; }
}
 