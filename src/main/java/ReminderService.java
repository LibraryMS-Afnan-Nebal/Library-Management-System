import java.util.*;

public class ReminderService extends Observable  {
    private final LoanManager loanManager;
    public ReminderService(LoanManager loanManager)
    {
        this.loanManager = loanManager;
    }

    public void sendReminders()
    {
        List <Loan> overdueLoans = loanManager.getOverdueLoans();

        Map<User, List<Loan>> loansByUser = new HashMap<>();
        for (Loan loan : overdueLoans)
        {
            loansByUser.computeIfAbsent(loan.getUser(), k -> new ArrayList<>()).add(loan);
        }

        for (Map.Entry<User, List<Loan>> entry : loansByUser.entrySet())
        {
            User user = entry.getKey();
            double fines = user.getFineCalculator().estimateOngoingFine(entry.getValue(),10);
            int overdueCount = entry.getValue().size();
            String message = "You have " + overdueCount + " overdue book(s) , Estimated Fine: " + fines+" NIS.";
            setChanged();
            notifyObservers(new UserMessage(user, message));
        }
    }
}




