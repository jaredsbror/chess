package dataAccess.auth;

import dataAccess.AuthDAO;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO {
    Map<String, UserData> auth = new HashMap<>();

    public void clear() {
        auth.clear();
    }
}
