package dataAccess.auth;

import dataAccess.AuthDAO;
import model.AuthData;
import java.util.*;

// Contains and modifies User Authorization Data in the Chess application.
public class MemoryAuthDAO implements AuthDAO {
    private Map<String, AuthData> authTable = new HashMap<>();

    public AuthData getAuthDataGivenUsername(String username) {
        return authTable.get(username);
    }

    public AuthData getAuthDataGivenAuthToken(String authToken) {
        // Iterate over the hashmap to locate and return the correct AuthData object
        for (var authDataEntry : authTable.entrySet()) {
            if (authDataEntry.getValue().authToken().equals(authToken)) {
                return authDataEntry.getValue();
            }
        }
        return null;
    }

    public AuthData createAuthToken(String username) {
        String authToken = UUID.randomUUID().toString();
        return authTable.put(username, new AuthData(authToken, username));
    }

    public AuthData deleteAuthDataGivenUsername(String username) {
        return authTable.remove(username);
    }

    public AuthData deleteAuthDataGivenAuthToken(String authToken) {
        // Create a temporary string to store the key for authTable removal.
        String keyToRemove = null;
        // Iterate over the hashmap to locate the correct authTable entry.
        for (var authDataEntry : authTable.entrySet()) {
            if (authDataEntry.getValue().authToken().equals(authToken)) {
                keyToRemove = authDataEntry.getKey();
                break;
            }
        }
        return authTable.remove(keyToRemove);  // Remove the table entry.
    }

    public void clear() {
        authTable.clear();
    }

    public boolean isEmpty() {
        return authTable.isEmpty();
    }

    public String toString() {
        return authTable.toString();
    }
}
