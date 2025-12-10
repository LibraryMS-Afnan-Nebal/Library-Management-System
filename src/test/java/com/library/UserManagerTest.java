package com.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {
    private UserManager userManager;
    private LoanManager loanManager;

    @BeforeEach
    void setUp() {
        userManager = UserManager.getInstance();
        userManager.accounts.clear();
        userManager.usernameToId.clear();

        loanManager = LoanManager.getInstance();
        loanManager.getLoanList().clear();

    }


    @Test
    void testSingleton() {
        UserManager m1 = UserManager.getInstance();
        UserManager m2 = UserManager.getInstance();

        assertSame(m1, m2); }

    @Test
    void testBuildAccountCreatesUser() {
        User u = userManager.buildAccount(100, "userX", "passX", "x@test.com");

        assertNotNull(u);
        assertEquals(100, u.getId());
        assertEquals("userX", u.getUsername());
        assertEquals("passX", u.getPassword());
        assertEquals("x@test.com", u.getEmail());
    }
    @Test
    void testCanUnregister_NullUser() {
        assertFalse(userManager.canUnregister(null));
    }
    @Test
    void testCanUnregister_UserHasFines() {
        User user = new User(1, "Ali", "123", "a@test.com");
        user.setFineBalance(20);
        userManager.accounts.put(1, user);

        assertFalse(userManager.canUnregister(user));
    }
    @Test
    void testCanUnregister_UserHasActiveLoan() {
        User user = new User(2, "Sara", "123", "s@test.com");
        userManager.accounts.put(2, user);

        Book book = new Book("Book A", "Author", "1234567890");
        Loan loan = new Loan(book, user);

        loan.setReturned(false); // unreturned
        loanManager.getLoanList().add(loan);

        assertFalse(userManager.canUnregister(user));
    }
    @Test
    void testCanUnregister_ReturnedLoanOnly() {
        User user = new User(3, "Lina", "pass", "l@test.com");
        userManager.accounts.put(3, user);

        Book book = new Book("Book B", "Auth", "1234567892");
        Loan loan = new Loan(book, user);

        loan.setReturned(true);
        loanManager.getLoanList().add(loan);

        assertTrue(userManager.canUnregister(user));
    }
    @Test
    void testCanUnregister_NoFines_NoLoans() {
        User user = new User(4, "Nour", "pass", "n@test.com");
        userManager.accounts.put(4, user);

        assertTrue(userManager.canUnregister(user));
    }
    @Test
    void testCanUnregister_LoanBelongsToAnotherUser() {

        userManager.signUp("u1", "p1", "e1@mail.com");
        userManager.signUp("u2", "p2", "e2@mail.com");

        User targetUser = userManager.accounts.get(1);
        User otherUser  = userManager.accounts.get(2);

        Book b = new Book("T", "A", "1234567890");
        Loan loan = new Loan(b, otherUser);
        loanManager.getLoanList().add(loan);

        boolean result = userManager.canUnregister(targetUser);
        assertTrue(result, "User should still be unregisterable because the loan belongs to someone else");
    }

    //unregistr user
    @Test
    void testUnregisterUser_UserNotFound() {
        assertFalse(userManager.unregisterUser(999));
    }

    @Test
    void testUnregisterUser_FailsDueToEligibility() {
        User user = new User(4, "U4", "p4", "u4@test.com");
        user.setFineBalance(10);

        userManager.accounts.put(4, user);
        userManager.usernameToId.put("u4", 4);

        assertFalse(userManager.unregisterUser(4));
        assertTrue(userManager.accounts.containsKey(4));
    }
    @Test
    void testUnregisterUser_SuccessfulRemoval() {
        User user = new User(5, "U5", "p5", "u5@test.com");

        userManager.accounts.put(5, user);
        userManager.usernameToId.put("u5", 5);

        assertTrue(userManager.unregisterUser(5));
        assertFalse(userManager.accounts.containsKey(5));
        assertFalse(userManager.usernameToId.containsKey("u5"));
    }

    @Test
    void testEvaluateAndPromoteUser_Regular() {
        User u = new User(10, "A", "x", "a@test.com");
        u.setFineBalance(0);
        u.incrementTotalBorrowedCount();

        userManager.evaluateAndPromoteUser(u);

        assertTrue(u.getFineCalculator().getFineStrategy()  instanceof RegularFineStrategy);
    }

    @Test
    void testEvaluateAndPromoteUser_Silver() {
        User u = new User(11, "B", "y", "b@test.com");
        for (int i = 0; i < 5; i++) u.incrementTotalBorrowedCount(); // count = 5

        userManager.evaluateAndPromoteUser(u);

        assertTrue(u.getFineCalculator().getFineStrategy()  instanceof SilverFineStrategy);
    }

    @Test
    void testEvaluateAndPromoteUser_Gold() {
        User u = new User(12, "C", "z", "c@test.com");
        for (int i = 0; i < 15; i++) u.incrementTotalBorrowedCount(); // count = 15

        userManager.evaluateAndPromoteUser(u);

        assertTrue(u.getFineCalculator().getFineStrategy() instanceof GoldFineStrategy);
    }
    @Test
    void testPrintAllUsers() {
        userManager.accounts.clear();
        userManager.usernameToId.clear();

        User u1 = new User(1, "Alice", "p1", "a@test.com");
        User u2 = new User(2, "Bob", "p2", "b@test.com");

        userManager.accounts.put(1, u1);
        userManager.accounts.put(2, u2);

        // Capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Act
        userManager.printAllUsers();

        // Restore System.out
        System.setOut(originalOut);

        // Convert captured output to string
        String output = outputStream.toString().trim();

        // Assert output contents
        assertTrue(output.contains("ID"), "Header should include ID.");
        assertTrue(output.contains("Username"), "Header should include Username.");

        assertTrue(output.contains("1") && output.contains("Alice"), "Should list user1.");
        assertTrue(output.contains("2") && output.contains("Bob"), "Should list user2.");
    }

    @Test
    void printAllUserTest_NoUsersFound()
    {
        assertFalse(userManager.printAllUsers());

    }




}