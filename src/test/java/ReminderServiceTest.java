import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ReminderServiceTest {

    private ReminderService reminderService;
    private Loan loan1,loan2,loan3;
    private Book book1, book2,book3;
    private User user1 , user2;
    private LoanManager mockLoanManager;
    @BeforeEach
    void setUp() {
        Book book1 = new Book("book1","auther1","1234567890");
        Book book2 = new Book("book2","auther2","1234567891");
        Book book3 = new Book("book3","auther3","1234567892");
        User user1 = new User(1,"user1","user1@gmail.com","pass1");
        User user2 = new User(2,"user2","user2@gmail.com","pass2");

        loan1 = new Loan(book1,user1);  loan2 = new Loan(book2,user2);  loan3 = new Loan(book3,user1);
        loan1.setIsOverdue(true); loan2.setIsOverdue(true); loan3.setIsOverdue(true);

        mockLoanManager = mock(LoanManager.class);

        reminderService = new ReminderService(mockLoanManager);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void sendRemindersTest()
    {
        //نضع Mock Observer حتى نراقب هل نادى update() ولا لا؟
        Observer mockObserver = mock(Observer.class);
        reminderService.addObserver(mockObserver);

        when(mockLoanManager.detectOverdueBooks()).thenReturn(List.of(loan1, loan2, loan3));

        reminderService.sendReminders();

        verify(mockObserver, times(2))
                .update(eq(reminderService), any(UserMessage.class));

    }
}