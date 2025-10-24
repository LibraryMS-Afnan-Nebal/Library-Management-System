import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void listUsersTest() {
        UserManager UM = new UserManager();
        User user1 = new User(122, "user1","pass1");
        User user2 = new User(123, "user2","pass2");
        UM.addUser(user1);
        UM.addUser(user2);

        ArrayList<String> usernames = UM.getUsersUsernames();
        assertTrue(usernames.contains("user1"));
        assertTrue(usernames.contains("user2"));
        assertEquals(2, usernames.size());
    }
}