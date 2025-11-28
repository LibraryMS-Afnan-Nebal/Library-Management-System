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
        if (loanManager.getLoanList().isEmpty())
        {
            System.out.println("There are no loans");
            return null;
        }

        List<Loan> overdueLoans = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for(Loan loan : loanManager.getLoanList() )
        {
            if (today.isAfter(loan.getDueDate()))
            {
                overdueLoans.add(loan);
                loanManager.accrueFines(10,loan);//To calculate fines
            }
        }
        return overdueLoans;
    }
}
 