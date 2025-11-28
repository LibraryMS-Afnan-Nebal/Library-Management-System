import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Librarian {
    private String username;
    private  String paassword;
    private LoanManager loanManager;

    public Librarian(String username,String paassword)
    {
        this.username = username;
        this.paassword = paassword;
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
}
 