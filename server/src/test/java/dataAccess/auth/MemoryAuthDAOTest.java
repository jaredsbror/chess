package dataAccess.auth;

import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MemoryAuthDAOTest {

    @Test
    void clear() {
        Map<String, AuthData> auth = new HashMap<>();
        auth.put("key", new AuthData("authToken", "username"));
        auth.clear();
        assertEquals(auth, new HashMap<>());
        System.out.println(auth);
    }

    @Test
    void notClear() {
        Map<String, AuthData> auth = new HashMap<>();
        auth.clear();
        auth.put("key", new AuthData("authToken", "username"));
        assertNotEquals(auth, new HashMap<>());
        System.out.println(auth);
    }
}