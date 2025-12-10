package com.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibrarianTest {

    private Librarian librarian;
    private LoanManager mockLoanManager;

    @BeforeEach
    void setUp() {
        mockLoanManager = mock(LoanManager.class);
        librarian = new Librarian(1, "lib", "pw", "lib@example.com", mockLoanManager);
    }

    @Test
    void detectOverdueMedia_callsAccrueForEachOverdueLoan()
    {
        Book b1 = new Book("Book1", "Author1", "1234567890");
        User u1 = new User(1, "user1","pass1" ,"u1@example.com");
        Loan loan1 = new Loan(b1, u1);
        loan1.setDueDate(LocalDate.now().minusDays(3));
        loan1.setReturned(false);

        Book b2 = new Book("Book2", "Author2", "1234567891");
        User u2 = new User(2, "user2", "pass2" ,"u2@example.com");
        Loan loan2 = new Loan(b2, u2);
        loan2.setDueDate(LocalDate.now().minusDays(1));
        loan2.setReturned(false);

        when(mockLoanManager.getOverdueLoans()).thenReturn(List.of(loan1, loan2));
        List<Loan> overdueLoans = librarian.detectOverdueMedia();

        assertEquals(2, overdueLoans.size());
        assertTrue(overdueLoans.contains(loan1));
        assertTrue(overdueLoans.contains(loan2));

        verify(mockLoanManager, times(1))
                .accrueFines(b1.getDailyFineRate(), loan1);
        verify(mockLoanManager, times(1))
                .accrueFines(b2.getDailyFineRate(), loan2);
    }

    @Test
    void realConstructor_coverage() {
        Librarian librarian = new Librarian(1, "lib", "pw", "lib@example.com");
        assertNotNull(librarian);
    }

}
