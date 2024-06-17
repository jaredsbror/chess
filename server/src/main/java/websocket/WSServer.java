package websocket;


import chess.ChessGame;
import chess.ChessPosition;
import com.google.gson.Gson;
import dataaccess.auth.SQLAuthDAO;
import dataaccess.exceptions.DataAccessException;
import dataaccess.game.SQLGameDAO;
import dataaccess.user.SQLUserDAO;
import model.original.GameData;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import spark.Spark;
import websocket.commands.*;
import websocket.messages.ErrorCommand;
import websocket.messages.LoadGameCommand;
import websocket.messages.NotificationCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;


@WebSocket
public class WSServer {
    private final Gson gson = new Gson();
    private final Map<Integer, Vector<Session>> sessions = new HashMap<>();
    private String authToken = null;
    private String username = null;
    private Integer gameID = null;
    private final SQLAuthDAO sqlAuthDAO;
    private final SQLGameDAO sqlGameDAO;
    private final SQLUserDAO sqlUserDAO;

    public WSServer() throws Exception {
        sqlAuthDAO = new SQLAuthDAO();
        sqlGameDAO = new SQLGameDAO();
        sqlUserDAO = new SQLUserDAO();
    }

    @OnWebSocketMessage
    public void onMessage( Session senderSession, String message) throws Exception {
        // PROCESSES THE JSON MESSAGE AND DOES STUFF
        // CONNECT, MAKE MOVE, LEAVE, RESIGN
        UserGameCommand userGameCommand = gson.fromJson( message, UserGameCommand.class );
        authToken = userGameCommand.getAuthString();
        username = sqlAuthDAO.getAuthDataGivenAuthToken( authToken ).username();
        gameID = userGameCommand.getGameID();
        switch (userGameCommand.getCommandType()) {
            case CONNECT -> connect( senderSession, gson.fromJson( message, ConnectCommand.class ));
            case MAKE_MOVE -> makeMove( senderSession, gson.fromJson( message, MakeMoveCommand.class ));
            case LEAVE -> leave( senderSession, gson.fromJson( message, LeaveCommand.class ));
            case RESIGN -> resign( senderSession, gson.fromJson( message, ResignCommand.class ));
        }
    }

    // Used for a user to request to connect to a game as a player or observer.
    private void connect( Session senderSession, ConnectCommand connectCommand ) throws Exception {
        // Server sends a LOAD_GAME message back to the root client.
        senderSession.getRemote().sendString( gson.toJson(new LoadGameCommand( ServerMessage.ServerMessageType.LOAD_GAME, sqlGameDAO.getGameData( gameID ) ) ) );
        // Server sends a Notification message to all other clients in that game informing
        // them the root client connected to the game, either as a player (in which case
        // their color must be specified) or as an observer.
        String notificationString = connectCommand.getPlayStyle() == ConnectCommand.PlayStyle.PLAYER ?
                                    "Player " + username + " has joined the " + connectCommand.getTeamColor() + " team." :
                                    "Observer " + username + " is observing the game.";
        NotificationCommand notificationCommand = new NotificationCommand( ServerMessage.ServerMessageType.NOTIFICATION, notificationString );
        broadcastAllExceptSender( connectCommand.getGameID(), senderSession, notificationCommand );
    }

    // Used to request to make a move in a game.
    private void makeMove( Session senderSession , MakeMoveCommand makeMoveCommand) throws Exception {
        // Server verifies the validity of the move.
        // Game is updated to represent the move. Game is updated in the database.
        GameData gameData = sqlGameDAO.getGameData( gameID );
        ChessGame chessGame = gameData.game();
        try {
            chessGame.makeMove( makeMoveCommand.getChessMove() );
        } catch ( Exception exception ) {
            broadcastSender( gameID, senderSession, new ErrorCommand( ServerMessage.ServerMessageType.ERROR, exception.getMessage()) );
            return;
        }
        // Server sends a LOAD_GAME message to all clients in the game (including the root
        // client) with an updated game.
        broadcastAll(gameID, new LoadGameCommand( ServerMessage.ServerMessageType.LOAD_GAME, sqlGameDAO.getGameData( gameID ) ) );
        // Server sends a Notification message to all other clients in that game informing
        // them what move was made.
        String notificationString = username + " made move " + makeMoveCommand.getChessMove().toString();
        broadcastAllExceptSender( gameID, senderSession,  new NotificationCommand( ServerMessage.ServerMessageType.NOTIFICATION, notificationString ));
        // If the move results in check, checkmate or stalemate the server sends a
        // Notification message to all clients.
        // Send the notification
        if (chessGame.isInCheckmate( ChessGame.TeamColor.WHITE )) {
            notificationString = gameData.whiteUsername() + " is in checkmate. Good game!";
            broadcastAll( gameID, new NotificationCommand( ServerMessage.ServerMessageType.NOTIFICATION, notificationString ) );

        } else if (chessGame.isInCheckmate( ChessGame.TeamColor.BLACK )) {
            notificationString = gameData.blackUsername() + " is in checkmate. Good game!";
            broadcastAll( gameID, new NotificationCommand( ServerMessage.ServerMessageType.NOTIFICATION, notificationString ) );

        } else if (chessGame.isInStalemate( ChessGame.TeamColor.WHITE )) {
            notificationString = gameData.whiteUsername() + " is in stalemate. Good game!";
            broadcastAll( gameID, new NotificationCommand( ServerMessage.ServerMessageType.NOTIFICATION, notificationString ) );

        } else if (chessGame.isInStalemate( ChessGame.TeamColor.BLACK )) {
            notificationString = gameData.blackUsername() + " is in stalemate. Good game!";
            broadcastAll( gameID, new NotificationCommand( ServerMessage.ServerMessageType.NOTIFICATION, notificationString ) );

        } else if (chessGame.isInCheck( ChessGame.TeamColor.WHITE )) {
            notificationString = gameData.whiteUsername() + " is in check.";
            broadcastAll( gameID, new NotificationCommand( ServerMessage.ServerMessageType.NOTIFICATION, notificationString ) );

        } else if (chessGame.isInCheck( ChessGame.TeamColor.BLACK  )) {
            notificationString = gameData.blackUsername() + " is in check.";
            broadcastAll( gameID, new NotificationCommand( ServerMessage.ServerMessageType.NOTIFICATION, notificationString ) );
        }
    }

    // Tells the server you are leaving the game so it will stop sending you notifications.
    private void leave( Session senderSession , LeaveCommand leaveCommand) throws Exception {
        // If a player is leaving, then the game is updated to remove the root client.
        remove( gameID, senderSession );
        // Game is updated in the database.
        GameData gameData = sqlGameDAO.getGameData( gameID );
        if (gameData.whiteUsername().equals( username )) {

        }
//        sqlGameDAO.updateGame(  );
        // Server sends a Notification message to all other clients in that game informing
        // them that the root client left. This applies to both players and observers.

    }

    // Forfeits the match and ends the game (no more moves can be made).
    private void resign( Session senderSession , ResignCommand resignCommand) {
        // Server marks the game as over (no more moves can be made). Game is updated in
        // the database.

        // Server sends a Notification message to all clients in that game informing them
        // that the root client resigned. This applies to both players and observers.

    }



    // Add a session to a game
    public void add(int gameID, Session session) {
        if (sessions.get(gameID) != null) {
            sessions.get(gameID).add(session);
        } else {
            sessions.put( gameID, new Vector<Session>());
            sessions.get(gameID).add( session );
        }
    }

    // Remove game
    public void remove(int gameID) {
        sessions.remove(gameID);
    }

    // Remove session from a game
    public void remove(int gameID, Session session) {
        for (var s : sessions.get( gameID )) {
            sessions.get(gameID).remove( session );
        }
    }

    // Send a message to all sessions in a game
    public void broadcastAll(int gameID, ServerMessage serverMessage) throws Exception {
        if (sessions.get( gameID ) == null) throw new IOException( "Error: Game does not exist in broadcastAll" );
        for (var s: sessions.get( gameID )) {
            s.getRemote().sendString( gson.toJson( serverMessage) );
        }
    }

    // Send a message to the sender only
    public void broadcastSender(int gameID, Session senderSession, ServerMessage serverMessage) throws Exception {
        if (sessions.get( gameID ) == null) throw new IOException( "Error: Game does not exist in broadcastSender" );
        senderSession.getRemote().sendString( gson.toJson( serverMessage) );
    }

    // Send a message to every session except the sender
    public void broadcastAllExceptSender(int gameID, Session senderSession, ServerMessage serverMessage) throws Exception {
        if (sessions.get( gameID ) == null) throw new IOException( "Error: Game does not exist in broadcastAllExceptSender" );
        for (var s: sessions.get( gameID )) {
            if (!s.equals( senderSession )) {
                s.getRemote().sendString( gson.toJson( serverMessage ) );
            }
        }
    }
}