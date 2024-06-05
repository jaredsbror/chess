package dataaccess.user;


import dataaccess.UserDAO;
import model.original.UserData;

import java.util.HashMap;
import java.util.Map;


// Contains and modifies UserData in Chess application
public class MemoryUserDAO implements UserDAO {
    private static final Map<String, UserData> userTable = new HashMap<>();


    public UserData getUser( String username ) {
        return userTable.get( username );
    }


    public void insertUser( String username, String password, String email ) {
        userTable.put( username, new UserData( username, password, email ) );
    }


    public void clear() {
        userTable.clear();
    }


    public boolean isEmpty() {
        return userTable.isEmpty();
    }
}
