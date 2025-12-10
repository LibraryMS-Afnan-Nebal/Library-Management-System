package com.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class AccountManagerTest {
    private FakeAccountManager manager;
    static class FakeRole extends Role {
        public FakeRole(int id, String username, String password, String email) {
            super(id, username, password, email);
        }
    }

    static class FakeAccountManager extends AccountManager<FakeRole> {
        @Override
        protected FakeRole buildAccount(int id, String username, String password, String email) {
            return new FakeRole(id, username, password, email);
        }
    }

    @BeforeEach
    void setUp() {
        manager = new FakeAccountManager();
        manager.accounts.clear();
        manager.usernameToId.clear();
        manager.nextId = 1;
    }
    @Test
    void testSignUp_Success() {
        assertTrue(manager.signUp("John", "1234", "j@test.com"));
        assertEquals(1, manager.accounts.size());
        assertEquals(1, manager.usernameToId.get("john"));
    }

    @Test
    void testSignUp_EmptyFields_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.signUp("", "pass", "email@test.com"));
        assertThrows(IllegalArgumentException.class,
                () -> manager.signUp("U", "", "email@test.com"));
        assertThrows(IllegalArgumentException.class,
                () -> manager.signUp("U", "pass", ""));
        assertEquals(0, manager.accounts.size());
    }

    @Test
    void testSignUp_UsernameAlreadyExists_ThrowsException() {
        manager.signUp("John", "1234", "a@test.com");
        assertThrows(IllegalArgumentException.class,
                () -> manager.signUp("John", "abcd", "b@test.com"));
    }
    @Test
    void testLogin_Success() {
        manager.signUp("John", "1234", "a@test.com");

        assertTrue(manager.login("John", "1234"));
        FakeRole account = manager.accounts.get(1);
        assertTrue(account.isLoggedIn());
    }

    @Test
    void testLogin_Fail_UserNotFound() {
        assertFalse(manager.login("ghost", "1234"));
    }

    @Test
    void testLogin_Fail_WrongPassword() {
        manager.signUp("John", "1234", "a@test.com");
        assertFalse(manager.login("John", "wrong"));
    }

    @Test
    void testLogin_EmptyFields_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.login("", "123"));
        assertThrows(IllegalArgumentException.class,
                () -> manager.login("abc", ""));
    }
    @Test
    void testLogout_Success() {
        manager.signUp("John", "123", "a@test.com");
        manager.login("John", "123");

        assertTrue(manager.logout("John"));
        assertFalse(manager.accounts.get(1).isLoggedIn());
    }

    @Test
    void testLogout_Fail_UserNotFound() {
        assertFalse(manager.logout("ghost"));
    }
    @Test
    void testChangeUsername_Success() {
        manager.signUp("John", "123", "a@test.com");
        manager.login("John", "123");

        assertTrue(manager.changeUsername("John", "Mike"));
        assertNull(manager.usernameToId.get("john"));
        assertEquals(1, manager.usernameToId.get("mike"));
    }

    @Test
    void testChangeUsername_Fail_OldNameNotFound() {
        assertFalse(manager.changeUsername("ghost", "newname"));
    }

    @Test
    void testChangeUsername_Fail_NewNameExists() {
        manager.signUp("John", "123", "a@test.com");
        manager.signUp("Mike", "555", "b@test.com");

        manager.login("John", "123");

        assertFalse(manager.changeUsername("John", "Mike"));
    }

    @Test
    void testChangeUsername_Fail_NotLoggedIn() {
        manager.signUp("John", "123", "a@test.com");
        // Not logging in

        assertFalse(manager.changeUsername("John", "NewJohn"));
    }
    @Test
    void testChangePassword_Success() {
        manager.signUp("John", "123", "a@test.com");
        manager.login("John", "123");

        assertTrue(manager.changePassword("John", "newPass"));
        assertEquals("newPass", manager.accounts.get(1).getPassword());
    }

    @Test
    void testChangePassword_Fail_NotLoggedIn() {
        manager.signUp("John", "123", "a@test.com");

        assertFalse(manager.changePassword("John", "newPass"));
    }

    @Test
    void testChangePassword_Fail_UserNotFound() {
        assertFalse(manager.changePassword("ghost", "pass"));
    }
}