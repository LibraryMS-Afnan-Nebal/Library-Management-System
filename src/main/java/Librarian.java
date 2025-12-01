import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Librarian  extends Role {
    public Librarian(int id, String username, String password, String email) {
        super(id, username, password, email);
    }

    /// this should be updated to detect both overdue books & cds
    /*
    public List<Loan> detectOverdueBooks()
    {
        List<Loan> listOfOverdueLoans = loanManager.getOverdueLoans();
        for(Loan loan : listOfOverdueLoans)
        {
            loanManager.accrueFines(10, loan);//To calculate fines
        }
        return listOfOverdueLoans;
    }

*/
}
 