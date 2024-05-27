package dataAccess.user;

import dataAccess.UserDAO;
import model.UserData;
import java.util.*;

// Contains and modifies UserData in Chess application
public class MemoryUserDAO implements UserDAO {
    Map<String, UserData> userTable = new HashMap<>();

    public UserData getUser(String username) {
        return userTable.get(username);
    }

    public void insertUser(String username, String password, String email) {
        userTable.put(username, new UserData(username, password, email));
    }


    public void clear() {
        userTable.clear();
    }
}
