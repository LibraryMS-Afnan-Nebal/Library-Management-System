import java.util.*;

public class ReminderService extends Observable  {
    private final Librarian librarian;
    public ReminderService(Librarian librarian)
    {
        this.librarian = librarian;
    }

    public void sendReminders()
    {
        List <Loan> overdueLoans = librarian.detectOverdueBooks();

        Map<User, List<Loan>> loansByUser = new HashMap<>();
        for (Loan loan : overdueLoans)
        {
            loansByUser.computeIfAbsent(loan.getUser(), k -> new ArrayList<>()).add(loan);
        }

        for (Map.Entry<User, List<Loan>> entry : loansByUser.entrySet())
        {
            User user = entry.getKey();
            double fine = user.getFineBalance();
            int overdueCount = entry.getValue().size();
            String message = "You have " + overdueCount + " overdue book(s) , The total Fine = " + fine;
            setChanged();
            notifyObservers(new UserMessage(user, message));
        }
    }
}




