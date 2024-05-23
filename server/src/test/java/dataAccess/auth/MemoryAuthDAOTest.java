package dataAccess.auth;

import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MemoryAuthDAOTest {

    @Test
    void clear() {
        Map<String, UserData> auth = new HashMap<>();
        auth.put("key", new UserData("username", "password", "email"));
        auth.clear();
        assertEquals(auth, new HashMap<>());
        System.out.println(auth);
    }

    @Test
    void notClear() {
        Map<String, UserData> auth = new HashMap<>();
        auth.put("key", new UserData("username", "password", "email"));
        assertNotEquals(auth, new HashMap<>());
        System.out.println(auth);
    }
}