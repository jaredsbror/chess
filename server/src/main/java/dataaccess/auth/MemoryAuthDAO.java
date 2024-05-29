package dataaccess.auth;

import dataaccess.AuthDAO;
import model.AuthData;
import java.util.*;

// Contains and modifies User Authorization Data in the Chess application.
public class MemoryAuthDAO implements AuthDAO {
    private static Map<String, AuthData> authTable = new HashMap<>();

    public AuthData getAuthDataGivenAuthToken(String authToken) {
        return authTable.get(authToken);
    }

    public String createAuthToken(String username) {
        String authToken = UUID.randomUUID().toString();
        authTable.put(authToken, new AuthData(authToken, username));
        return authToken;
    }

    // Verify that an authToken exists in the authTable
    public Boolean verifyAuthToken(String authToken) {
        return authTable.get(authToken) != null;
    }

    public AuthData deleteAuthDataGivenUsername(String username) {
        // Create a temporary string to store the key for authTable removal.
        String keyToRemove = null;
        // Iterate over the hashmap to locate the correct authTable entry.
        for (var authDataEntry : authTable.entrySet()) {
            String tableUsername = authDataEntry.getValue().username();
            if (username.equals(tableUsername)) {
                keyToRemove = authDataEntry.getKey();
                break;
            }
        }
        // Remove the table entry.
        return authTable.remove(keyToRemove);
    }

    public AuthData deleteAuthDataGivenAuthToken(String authToken) {
        return authTable.remove(authToken);
    }

    public void clear() {
        authTable.clear();
    }

    public Boolean isEmpty() {
        return authTable.isEmpty();
    }

    public String toString() {
        return authTable.toString();
    }
}
