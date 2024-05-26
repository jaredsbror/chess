package dataAccess.user;

import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import java.util.*;

// Contains and modifies UserData in Chess application
public class MemoryUserDAO implements UserDAO {
    Map<String, UserData> user = new HashMap<>();

//    public boolean verifyUser(AuthData authToken) {
//        return user.containsValue(authToken);
//    }
    public UserData getUser(String username) {
        return user.get(username);
    }

    public void clear() {
        user.clear();
    }
}
