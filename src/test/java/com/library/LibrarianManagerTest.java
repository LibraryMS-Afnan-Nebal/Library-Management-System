package com.library;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibrarianManagerTest {

    @Test
    void testSingletonInstance() {
        LibrarianManager m1 = LibrarianManager.getInstance();
        LibrarianManager m2 = LibrarianManager.getInstance();

        assertSame(m1, m2, "LibrarianManager should return the same singleton instance.");
    }

    @Test
    void testBuildAccountCreatesLibrarian() {
        LibrarianManager manager = LibrarianManager.getInstance();

        Librarian librarian = manager.buildAccount(1, "LibX", "pass123", "libx@test.com");

        assertNotNull(librarian);
        assertEquals(1, librarian.getId());
        assertEquals("LibX", librarian.getUsername());
        assertEquals("pass123", librarian.getPassword());
        assertEquals("libx@test.com", librarian.getEmail());
    }
}