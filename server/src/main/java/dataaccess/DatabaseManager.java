package dataaccess;


import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.Constants;
import model.original.AuthData;
import model.original.GameData;
import model.original.UserData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;


public class DatabaseManager {
    private static final String DATABASE_NAME;
    private static final String USER;
    private static final String PASSWORD;
    private static final String CONNECTION_URL;
    private static final String LOCALHOST_PORT_URL;
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
              gameID INT NOT NULL AUTO_INCREMENT,
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

    /*
     * Load the database information for the db.properties file.
     */
    static {
        try {
            try ( var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream( "db.properties" ) ) {
                if ( propStream == null ) throw new Exception( "Unable to load db.properties" );
                Properties props = new Properties();
                props.load( propStream );
                DATABASE_NAME = props.getProperty( "db.name" );
                USER = props.getProperty( "db.user" );
                PASSWORD = props.getProperty( "db.password" );

                var host = props.getProperty( "db.host" );
                var port = Integer.parseInt( props.getProperty( "db.port" ) );
                CONNECTION_URL = String.format( "jdbc:mysql://%s:%d/%s", host, port, DATABASE_NAME );
                LOCALHOST_PORT_URL = String.format( "jdbc:mysql://%s:%d", host, port );
            }
        } catch ( Exception ex ) {
            throw new RuntimeException( "unable to process db.properties. " + ex.getMessage() );
        }
    }


    public enum TableSource {
        AUTHTABLE,
        GAMETABLE,
        USERTABLE
    };

    public static PreparedStatement createPreparedStatement(Connection connection, String statement, Object... params) throws SQLException {
        // Establish a connection and prepare the statement to be executed.
        var preparedStatement = connection.prepareStatement( statement, RETURN_GENERATED_KEYS);
        // Substitute the given params into the prepared statement to be executed
        for ( var i = 0; i < params.length; i++ ) {
            var param = params[i];
            switch ( param ) {
                case String string -> preparedStatement.setString( i + 1, string );
                case Integer integer -> preparedStatement.setInt( i + 1, integer );
                case null -> preparedStatement.setNull( i + 1, NULL );
                default -> throw new SQLException( "Unexpected parameter type: " + param.getClass().getName() );
            }
        }
        return preparedStatement;
    }

    /**
     * Generically execute an update at the URL in MySQL and return any generated keys
     * @param statement
     */
    public static Object executeUpdate( String statement, Object... params ) throws DataAccessException {
        // Establish a connection and prepare the statement to be executed.
        try (Connection connection = DatabaseManager.getConnection();
            PreparedStatement preparedStatement = createPreparedStatement(connection, statement, params )) {
            // Execute the statement.
            preparedStatement.executeUpdate();
            // Get generated keys
            try (var keys = preparedStatement.getGeneratedKeys()) {
                if ( keys.next() ) {
                    return keys.getObject( 1 );
                }
            }
            return null;
        } catch ( SQLException e ) {
            throw new DataAccessException( e.getMessage() );
        }
    }

    /**
     * Executes a statement in the database in MySQL and returns a list of objects (strings, ints, etc)
     *
     * @param statement
     */
    public static List<Object> executeSingleRowQuery( String statement, TableSource tableSource, Object... params ) throws DataAccessException {
        // Establish a connection and prepare the statement to be executed.
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = createPreparedStatement(connection, statement, params )) {
            List<Object> resultList = new ArrayList<>();
            // Execute the statement. Check if it returned a result set.
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // If the result set is empty, return an empty list
                if ( !resultSet.next() ) return resultList;
                // Process resultData returned depending on the statement executed.
                switch (tableSource) {
                    case AUTHTABLE:
                        resultList.add( resultSet.getObject( Constants.authToken ) );
                        resultList.add( resultSet.getObject( Constants.username ) );
                        break;
                    case GAMETABLE:
                        resultList.add( resultSet.getObject( Constants.gameID ) );
                        resultList.add( resultSet.getObject( Constants.whiteUsername ) );
                        resultList.add( resultSet.getObject( Constants.blackUsername ) );
                        resultList.add( resultSet.getObject( Constants.gameName ) );
                        resultList.add( resultSet.getObject( Constants.game ) );
                        break;
                    case USERTABLE:
                        resultList.add( resultSet.getObject( Constants.username ) );
                        resultList.add( resultSet.getObject( Constants.password ) );
                        resultList.add( resultSet.getObject( Constants.email ) );
                        break;
                    default:
                        break;
                }
            }
            return resultList;
        } catch ( SQLException e ) {
            throw new DataAccessException( e.getMessage() );
        }
    }


    /**
     * Executes a statement in the database in MySQL and returns a list of objects (strings, ints,)
     * @param statement
     */
    public static List<Object> executeMultipleRowQuery( String statement, TableSource tableSource, Object... params ) throws DataAccessException {
        // Establish a connection and prepare the statement to be executed.
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = createPreparedStatement(connection, statement, params )) {
            List<Object> resultList = new ArrayList<>();
            // Execute the statement. Check if it returned a result set.
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Process the data depending on the table queried
                switch (tableSource) {
                    case AUTHTABLE:
                        // Process the table data
                        while ( resultSet.next() ) {
                            // Process authData
                            String authToken = (String) resultSet.getObject( Constants.authToken );
                            String username = (String) resultSet.getObject( Constants.username );
                            resultList.add( new AuthData( authToken, username ) );
                        }
                        break;
                    case GAMETABLE:
                        // Process the table data
                        while ( resultSet.next() ) {
                            // Process gameData
                            int gameID = (int) resultSet.getObject( Constants.gameID );
                            String whiteUsername = (String) resultSet.getObject( Constants.whiteUsername );
                            String blackUsername = (String) resultSet.getObject( Constants.blackUsername );
                            String gameName = (String) resultSet.getObject( Constants.gameName );
                            String gameString = (String) resultSet.getObject( Constants.game );
                            // Process the gameString into a ChessGame object
                            ChessGame.TeamColor teamColor = ChessBoard.parseColor( gameString );
                            ChessPiece[][] board = ChessBoard.parseBoard( gameString );
                            ChessGame chessGame = new ChessGame( teamColor, board );
                            // Add the GameData to the resultList
                            resultList.add( new GameData( gameID, whiteUsername, blackUsername, gameName, chessGame ) );
                        }
                        break;
                    case USERTABLE:
                        // Process the table data
                        while ( resultSet.next() ) {
                            // Process userData
                            String username = (String) resultSet.getObject( Constants.username );
                            String password = (String) resultSet.getObject( Constants.password );
                            String email = (String) resultSet.getObject( Constants.email );
                            resultList.add( new UserData( username, password, email ) );
                        }
                        break;
                    default:
                        break;
                }

            }
            return resultList;
        } catch ( SQLException e ) {
            throw new DataAccessException( e.getMessage() );
        }
    }

    /**
     * Creates the database if it does not already exist.
     */
    public static void createDatabase() throws DataAccessException {
        executeUpdate( "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME );
        createTable( TableSource.AUTHTABLE );
        createTable( TableSource.GAMETABLE );
        createTable( TableSource.USERTABLE );
    }


    /**
     * Creates a table if it does not already exist.
     * @param dataType (which of the tables to create)
     */
    private static void createTable( TableSource dataType ) throws DataAccessException {
        String statement;
        // Depending on tableChoice, get the statement
        switch ( dataType ) {
            case AUTHTABLE:
                statement = String.join( " ", createAuthTableStatement );
                executeUpdate( statement );
                break;
            case GAMETABLE:
                statement = String.join( " ", createGameTableStatement );
                executeUpdate( statement );
                break;
            case USERTABLE:
                statement = String.join( " ", createUserTableStatement );
                executeUpdate( statement );
                break;
            default:
                break;
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
            var conn = DriverManager.getConnection( CONNECTION_URL, USER, PASSWORD );
            conn.setCatalog( DATABASE_NAME );
            return conn;
        } catch ( SQLException e ) {
            throw new DataAccessException( e.getMessage() );
        }
    }
}
