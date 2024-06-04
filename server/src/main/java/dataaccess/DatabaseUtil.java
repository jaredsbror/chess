package dataaccess;


import chess.Constants;
import dataaccess.auth.SQLAuthDAO;
import dataaccess.game.SQLGameDAO;
import dataaccess.user.SQLUserDAO;


public class DatabaseUtil {
    // Refresh the database to a clear state (used for testing)
    public static void refreshDatabase() {
        try {
            SQLAuthDAO sqlAuthDAO = new SQLAuthDAO();
            sqlAuthDAO.clear();

            SQLGameDAO sqlGameDAO = new SQLGameDAO();
            sqlGameDAO.clear();

            SQLUserDAO sqlUserDAO = new SQLUserDAO();
            sqlUserDAO.clear();

            // If there are additional setup steps, you can add them here
        } catch (Exception exception) {
            throw new RuntimeException("Error: Failed to refresh database", exception);
        }
    }

    // Populate the database with a user (used for testing)
    public static String populateDatabaseWithUser() {
        try {
            // Set up the database
            SQLAuthDAO sqlAuthDAO = new SQLAuthDAO();
            SQLUserDAO sqlUserDAO = new SQLUserDAO();
            // Register a new user
            sqlUserDAO.insertUser( Constants.username, Constants.password, Constants.email );
            // Login as the new user
            // Return the authToken
            return sqlAuthDAO.createAuthToken( Constants.username );
        } catch (Exception exception) {
            throw new RuntimeException("Error: Failed to populate database", exception);
        }
    }

    // Populate the database with a game (used for testing)
    public static int populateDatabaseWithGame(String authToken) {
        try {
            // Set up the database
            SQLGameDAO sqlGameDAO = new SQLGameDAO();
            // Create a new game
            int gameID = sqlGameDAO.insertGame( Constants.gameName );
            // Add the user as white player in that game
            sqlGameDAO.updateGame( gameID, Constants.username, "WHITE" );
            // If there are additional setup steps, add them here
            // Return the gameID
            return gameID;
        } catch (Exception exception) {
            throw new RuntimeException("Error: Failed to populate database", exception);
        }
    }
}
