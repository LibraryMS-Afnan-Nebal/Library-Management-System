import java.util.Observable;
import java.util.Observer;
import io.github.cdimascio.dotenv.Dotenv;

public class EmailNotifier implements Observer {
    private EmailService emailService;

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

//        Dotenv dotenv = Dotenv.load();
//        String username = dotenv.get("username");
//        String password = dotenv.get("password");
//        emailService = new EmailService(username,password);
        emailService.sendEmail(user.getEmail(), "Overdue Books Reminder",message);
    }
}
