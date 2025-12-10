import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Test
    void testEmailSendExceptionWithMock() {
        EmailService emailServiceMock = mock(EmailService.class);

        // نحدد السلوك: عند استدعاء sendEmail، نرمي EmailSendException مباشرة
        doThrow(new EmailSendException("Failed to send email", new RuntimeException("Mock cause")))
                .when(emailServiceMock)
                .sendEmail(anyString(), anyString(), anyString());

        EmailSendException exception = assertThrows(
                EmailSendException.class,
                () -> emailServiceMock.sendEmail("test@example.com", "Subject", "Body")
        );

        assertTrue(exception.getMessage().contains("Failed to send email"));
    }
}
