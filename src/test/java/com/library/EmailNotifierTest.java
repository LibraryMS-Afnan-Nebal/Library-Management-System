package com.library;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Observable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
class EmailNotifierTest {

    private EmailService mockEmailService;
    private EmailNotifier emailNotifier;
    @BeforeEach
    void setUp()
    {
        mockEmailService = mock(EmailService.class);
        emailNotifier = new EmailNotifier(mockEmailService);
    }

    @AfterEach
    void tearDown() {
        // No cleanup needed for this test class.
        //
    }

    @Test
    void updateSendsEmailTest()
    {
        User user = new User(1, "user1","pass1","user1@gmail.com");
        String message = "You have 1 overdue book";
        UserMessage userMessage = new UserMessage(user, message);

        Observable mockObservable = mock(Observable.class);

        emailNotifier.update(mockObservable, userMessage);

        verify(mockEmailService, times(1))
                .sendEmail(eq(user.getEmail()), eq("Overdue Books Reminder"), eq(message));
    }

    @Test
    void updateDoesNothing_WhenArgIsNotUserMessage()
    {
        Observable mockObservable = mock(Observable.class);
        String invalidArg = "Not a UserMessage";

        emailNotifier.update(mockObservable, invalidArg);

        verify(mockEmailService, never()).sendEmail(anyString(), anyString(), anyString());
    }

}