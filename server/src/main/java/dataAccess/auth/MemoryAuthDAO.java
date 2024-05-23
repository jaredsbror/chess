package dataAccess.auth;

import dataAccess.AuthDAO;
import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {
    Map<String, AuthData> auth = new HashMap<>();

    public void clear() {
        auth.clear();
    }
}
