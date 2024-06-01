package dataaccess;

import chess.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseManager {
    private static final String DATABASE_NAME;
    private static final String USER;
    private static final String PASSWORD;
    private static final String CONNECTION_URL;
    private static final String LOCALHOST_PORT_URL;

    enum TableChoice {
        AUTHTABLE,
        GAMETABLE,
        USERTABLE
    }

    /*
     * Load the database information for the db.properties file.
     */
    static {
        try {
            try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
                if (propStream == null) throw new Exception("Unable to load db.properties");
                Properties props = new Properties();
                props.load(propStream);
                DATABASE_NAME = props.getProperty("db.name");
                USER = props.getProperty("db.user");
                PASSWORD = props.getProperty("db.password");

                var host = props.getProperty("db.host");
                var port = Integer.parseInt(props.getProperty("db.port"));
                CONNECTION_URL = String.format("jdbc:mysql://%s:%d/%s", host, port, DATABASE_NAME);
                LOCALHOST_PORT_URL = String.format("jdbc:mysql://%s:%d", host, port);
            }
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties. " + ex.getMessage());
        }
    }

    /**
     * Initial parameters to set up the authTable in mySQL
     */
    private static final String[] createAuthTableStatement = {
            """
            CREATE TABLE IF NOT EXISTS authTable (
              authToken VARCHAR(255) NOT NULL,
              username VARCHAR(255) NOT NULL,
              PRIMARY KEY (`username`)
            )
            """
    };

    /**
     * Initial parameters to set up the gameTable in mySQL
     */
    private static final String[] createGameTableStatement = {
            """
            CREATE TABLE IF NOT EXISTS  gameTable (
              gameID INT NOT NULL,
              whiteUsername VARCHAR(255),
              blackUsername VARCHAR(255),
              gameName VARCHAR(255) NOT NULL,
              game TEXT NOT NULL,
              PRIMARY KEY (`gameID`)
            )
            """
    };

    /**
     * Initial parameters to set up the userTable in mySQL
     */
    private static final String[] createUserTableStatement = {
            """
            CREATE TABLE IF NOT EXISTS  userTable (
              username VARCHAR(255) NOT NULL,
              password VARCHAR(255) NOT NULL,
              email VARCHAR(255) NOT NULL,
              PRIMARY KEY (`username`)
            )
            """
    };

    // Check if a sql statement string contains certain word(s)
    public static boolean containsKeywords(String sql, String... keywords) {
        // Convert SQL statement to lowercase for case-insensitive search
        String lowercaseSQL = sql.toLowerCase();
        // Check if the statement contains any of the specified keywords
        for (String keyword : keywords)
            if (lowercaseSQL.contains(keyword.toLowerCase()))
                return true;
        return false;
    }

    /**
     * Generically execute a statement at the URL in MySQL
     * @param statement
     */
    public static void executeStatement(String statement) throws DataAccessException {
        // Establish a connection and prepare the statement to be executed.
        try (var connection = DriverManager.getConnection(LOCALHOST_PORT_URL, USER, PASSWORD)) {
            try (var preparedStatement = connection.prepareStatement(statement)) {
                // Execute the statement. Check if it returned an empty returnSet.
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    };

    /**
     * Generically execute a statement at the URL in MySQL
     * @param statement
     */
    public static void executeStatementInChess(String statement) throws DataAccessException {
        // Establish a connection and prepare the statement to be executed.
        try (var connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD)) {
            try (var preparedStatement = connection.prepareStatement(statement)) {
                // Execute the statement. Check if it returned an empty returnSet.
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    };

    /**
     * Generically execute a statement in the database in MySQL and returns whether the result is empty
     * @param statement
     */
    public static Boolean executeStatementAndReturnEmpty(String statement) throws DataAccessException {
        // Establish a connection and prepare the statement to be executed.
        try (var connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD)) {
            try (var preparedStatement = connection.prepareStatement(statement)) {
                // Execute the statement. Check if it returned an empty returnSet.
                if (preparedStatement.execute()) {
                    return (!preparedStatement.getResultSet().next());
                } else {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    };


    /**
     * Executes a statement in the database in MySQL and returns a list of objects (strings, ints, etc)
     * @param statement
     */
    public static List<Object> executeStatementAndMaybeReturnSingleRow(String statement) throws DataAccessException {
        List<Object> resultList = new ArrayList<>();
        // Establish a connection and prepare the statement to be executed.
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                // Execute the statement. Check if it returned a result set.
                if (preparedStatement.execute()) {
                    ResultSet resultSet = preparedStatement.getResultSet();
                    // If the result set is empty, return an empty list
                    if (!resultSet.next()) return resultList;
                    // Process resultData returned depending on the statement executed.
                    if (containsKeywords(statement, Constants.authTable))
                        // Check for what individual values are needed and add them.
                        if (containsKeywords(statement, Constants.authToken)) resultList.add(resultSet.getObject(Constants.authToken));
                        if (containsKeywords(statement, Constants.username)) resultList.add(resultSet.getObject(Constants.username));
                    else if (containsKeywords(statement, Constants.gameTable))
                        // Check for what individual values are needed and add them.
                        if (containsKeywords(statement, Constants.gameID)) resultList.add(resultSet.getObject(Constants.gameID));
                        if (containsKeywords(statement, Constants.whiteUsername)) resultList.add(resultSet.getObject(Constants.whiteUsername));
                        if (containsKeywords(statement, Constants.blackUsername)) resultList.add(resultSet.getObject(Constants.blackUsername));
                        if (containsKeywords(statement, Constants.gameName)) resultList.add(resultSet.getObject(Constants.gameName));
                        if (containsKeywords(statement, Constants.game)) resultList.add(resultSet.getObject(Constants.game));
                    else if (containsKeywords(statement, Constants.userTable))
                        // Check for what individual values are needed and add them.
                        if (containsKeywords(statement, Constants.username)) resultList.add(resultSet.getObject(Constants.username));
                        if (containsKeywords(statement, Constants.password)) resultList.add(resultSet.getObject(Constants.password));
                        if (containsKeywords(statement, Constants.email)) resultList.add(resultSet.getObject(Constants.email));
                }
            }
            return resultList;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Creates the database if it does not already exist.
     */
    public static void createDatabase() throws DataAccessException {
        executeStatement("CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME);
        createTable(TableChoice.AUTHTABLE);
        createTable(TableChoice.GAMETABLE);
        createTable(TableChoice.USERTABLE);
    }

    /**
     * Creates a table if it does not already exist.
     * @param tableChoice (which of the tables to create)
     */
    private static void createTable(TableChoice tableChoice) throws DataAccessException {
        String statement;
        // Depending on tableChoice, get the statement
        switch (tableChoice) {
            case AUTHTABLE:
                statement = String.join(" ", createAuthTableStatement);
                executeStatementAndMaybeReturnSingleRow(statement);
                break;
            case GAMETABLE:
                statement = String.join(" ", createGameTableStatement);
                executeStatementAndMaybeReturnSingleRow(statement);
                break;
            case USERTABLE:
                statement = String.join(" ", createUserTableStatement);
                executeStatementAndMaybeReturnSingleRow(statement);
                break;
            default:
                break;
        }
    }

    public static void pingDatabase() throws DataAccessException {
        // Attempt to execute a harmless statement in order to test the SQL connection to the database
        try {
            DatabaseManager.executeStatementAndMaybeReturnSingleRow("SELECT 1");
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public static void pingTables() throws DataAccessException {
        // Attempt to execute harmless statements in order to test the SQL connection to the tables
        try {
            DatabaseManager.executeStatementAndMaybeReturnSingleRow("USE chess");
            DatabaseManager.executeStatementAndMaybeReturnSingleRow("SELECT * FROM authTable");
            DatabaseManager.executeStatementAndMaybeReturnSingleRow("SELECT * FROM gameTable");
            DatabaseManager.executeStatementAndMaybeReturnSingleRow("SELECT * FROM userTable");
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DbInfo.getConnection(databaseName)) {
     * // execute SQL statements.
     * }
     * </code>
     */
    public static Connection getConnection() throws DataAccessException {
        try {
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            conn.setCatalog(DATABASE_NAME);
            return conn;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static String getUSER() {
        return USER;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static String getConnectionUrl() {
        return CONNECTION_URL;
    }
}
