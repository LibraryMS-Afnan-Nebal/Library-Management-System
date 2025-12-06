import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Observer;
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
         book1 = new Book("book1","auther1","1234567890");
         book2 = new Book("book2","auther2","1234567891");
         book3 = new Book("book3","auther3","1234567892");
         user1 = new User(1,"user1","pass1","user1@gmail.com");
         user2 = new User(2,"user2","pass2","user2@gmail.com");

        loan1 = new Loan(book1,user1);  loan2 = new Loan(book2,user2);  loan3 = new Loan(book3,user1);

        mockLoanManager = mock(LoanManager.class);

        reminderService = new ReminderService(mockLoanManager);
    }

    @AfterEach
    void tearDown() {
        reminderService = null;
        mockLoanManager = null;
    }

    @Test
    public void sendRemindersTest()
    {
        Observer mockObserver = mock(Observer.class);
        reminderService.addObserver(mockObserver);

        when(mockLoanManager.getOverdueLoans()).thenReturn(List.of(loan1, loan2, loan3));

        reminderService.sendReminders();

        verify(mockObserver, times(2))
                .update(eq(reminderService), any(UserMessage.class));

    }

    @Test
    public void sendRemindersTest_NoOverdue()
    {
        Observer mockObserver = mock(Observer.class);
        reminderService.addObserver(mockObserver);

        when(mockLoanManager.getOverdueLoans()).thenReturn(List.of());

        reminderService.sendReminders();

        verify(mockObserver, never()).update(any(), any());

    }
}

