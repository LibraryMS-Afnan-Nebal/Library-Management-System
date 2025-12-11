import java.util.Observable;
import java.util.Observer;
public class EmailNotifier implements Observer {
    private final EmailService emailService;

    public EmailNotifier(EmailService emailService)
    {
        this.emailService = emailService;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (!(arg instanceof UserMessage))
            return;

        UserMessage um = (UserMessage) arg;
        User user = um.getUser();
        String message = um.getMessage();
        emailService.sendEmail(user.getEmail(), "Overdue Books Reminder",message);
    }
}
