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
    private Librarian mockLibrarian;
    @BeforeEach
    void setUp() {
        Book book1 = new Book("book1","auther1","1234567890");
        Book book2 = new Book("book2","auther2","1234567891");
        Book book3 = new Book("book3","auther3","1234567892");
        User user1 = new User(1,"user1","user1@gmail.com","pass1");
        User user2 = new User(2,"user2","user2@gmail.com","pass2");

        loan1 = new Loan(book1,user1);  loan2 = new Loan(book2,user2);  loan3 = new Loan(book3,user1);

        mockLibrarian = mock(Librarian.class);

        reminderService = new ReminderService(mockLibrarian);
    }

    @AfterEach
    void tearDown() {}

    @Test
    public void sendRemindersTest()
    {
        Observer mockObserver = mock(Observer.class);
        reminderService.addObserver(mockObserver);

        when(mockLibrarian.detectOverdueBooks()).thenReturn(List.of(loan1, loan2, loan3));

        reminderService.sendReminders();

        verify(mockObserver, times(2))
                .update(eq(reminderService), any(UserMessage.class));

    }
}