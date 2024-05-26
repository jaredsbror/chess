package dataAccess.auth;

import dataAccess.AuthDAO;
import model.AuthData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Contains and modifies User Authorization Data in the Chess application.
public class MemoryAuthDAO implements AuthDAO {
    private Map<String, AuthData> authTable = new HashMap<>();

    /**
     * Get an entry from the authTable.
     * @param username
     * @return AuthData
     */
    public AuthData getAuthDataGivenUsername(String username) {
        return authTable.get(username);
    }

    /**
     * Get an entry from the authTable.
     * @param authToken
     * @return AuthData
     */
    public AuthData getAuthDataGivenAuthToken(String authToken) {
        // Iterate over the hashmap to locate and return the correct AuthData object
        for (var authDataEntry : authTable.entrySet()) {
            if (authDataEntry.getValue().authToken().equals(authToken)) {
                return authDataEntry.getValue();
            }
        }
        return null;
    }

    /**
     * Add a new entry to the authTable.
     * @param username
     * @return AuthData (mainly for debugging)
     */
    public AuthData createAuthToken(String username) {
        String authToken = UUID.randomUUID().toString();
        return authTable.put(username, new AuthData(authToken, username));
    }

    /**
     * Delete a specific entry in the authTable.
     * @param username
     * @return AuthData (mainly for debugging)
     */
    public AuthData deleteAuthDataGivenUsername(String username) {
        return authTable.remove(username);
    }

    /**
     * Delete a specific entry in the authTable.
     * @param authToken
     * @return AuthData (mainly for debugging)
     */
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
