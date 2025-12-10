package com.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ReminderService extends Observable {
    private final LoanManager loanManager;

    public ReminderService(LoanManager loanManager) {
        this.loanManager = loanManager;
    }

    public boolean sendReminders() {
        List<Loan> overdueLoans = loanManager.getOverdueLoans();
        if (overdueLoans.isEmpty())
        {
            System.out.println("No reminders to send. There are no overdue loans at the moment.");
            return false;
        }

        Map<User, List<Loan>> loansByUser = new HashMap<>();
        for (Loan loan : overdueLoans)
        {
            loansByUser.computeIfAbsent(loan.getUser(), k -> new ArrayList<>()).add(loan);
        }

        LocalDate today = LocalDate.now();
        for (Map.Entry<User, List<Loan>> entry : loansByUser.entrySet())
        {
            User user = entry.getKey();
            List<Loan> userLoans = entry.getValue();

            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("📚 Overdue Items Reminder\n\n");
            messageBuilder.append("You have ")
                    .append(userLoans.size())
                    .append(" overdue item(s):\n\n");

            int index = 1;
            double totalFine=0.0;
            for (Loan loan : userLoans)
            {
                Media media = loan.getMedia();
                long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), today);

                double fine = user.getFineCalculator().applyStrategy(overdueDays * media.getDailyFineRate());
                totalFine +=fine;

                messageBuilder.append(index++)
                        .append(") ")
                        .append(media.getTitle())
                        .append(" (")
                        .append(media.getClass().getSimpleName())
                        .append(")\n")
                        .append("   • Overdue: ").append(overdueDays).append(" days\n")
                        .append("   • Fine: ").append(String.format("%.2f", fine)).append(" NIS\n\n");

            }

            messageBuilder.append("-------------------------------------\n");
            messageBuilder.append(String.format("Estimated Total Fine: %.2f NIS", totalFine));

            setChanged();
            notifyObservers(new UserMessage(user, messageBuilder.toString()));
        }
        return true;
    }


}




