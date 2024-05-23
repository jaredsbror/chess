package dataAccess.user;

import model.UserData;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MemoryUserDAOTest {

    @Test
    void clear() {
        Map<String, UserData> user = new HashMap<>();
        user.put("key", new UserData("username", "password", "email"));
        user.clear();
        assertEquals(user, new HashMap<>());
        System.out.println(user);
    }

    @Test
    void notClear() {
        Map<String, UserData> user = new HashMap<>();
        user.clear();
        user.put("key", new UserData("username", "password", "email"));
        assertNotEquals(user, new HashMap<>());
        System.out.println(user);
    }
}