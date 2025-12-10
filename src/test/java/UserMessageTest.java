import org.junit.jupiter.api.Test;

class UserMessageTest {

    @Test
    void testUserMessageStoresDataCorrectly() {
        User user = new User(1, "Afnan", "pass", "afnan@gmail.com");
        String msg = "This is a test message.";

        UserMessage um = new UserMessage(user, msg);

        assertEquals(user, um.getUser());
        assertEquals(msg, um.getMessage());
    }
}
