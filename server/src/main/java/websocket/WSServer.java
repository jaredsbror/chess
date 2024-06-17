package websocket;


import com.google.gson.Gson;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import spark.Spark;
import websocket.commands.*;
import websocket.messages.LoadGameCommand;
import websocket.messages.NotificationCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


@WebSocket
public class WSServer {
    private final Gson gson = new Gson();
    private final Map<Integer, Vector<Session>> sessions = new HashMap<>();

    @OnWebSocketMessage
    public void onMessage( Session senderSession, String message) throws Exception {
        // PROCESSES THE JSON MESSAGE AND DOES STUFF
        // CONNECT, MAKE MOVE, LEAVE, RESIGN
        UserGameCommand userGameCommand = gson.fromJson( message, UserGameCommand.class );
        switch (userGameCommand.getCommandType()) {
            case CONNECT -> connect( senderSession, gson.fromJson( message, ConnectCommand.class ));
            case MAKE_MOVE -> makeMove( senderSession, gson.fromJson( message, MakeMoveCommand.class ));
            case LEAVE -> leave( senderSession, gson.fromJson( message, LeaveCommand.class ));
            case RESIGN -> resign( senderSession, gson.fromJson( message, ResignCommand.class ));
        }
    }

    // Used for a user to request to connect to a game as a player or observer.
    private void connect( Session messageSender, ConnectCommand connectCommand ) throws IOException {
        // Server sends a LOAD_GAME message back to the root client.
        messageSender.getRemote().sendString( gson.toJson(new LoadGameCommand( ServerMessage.ServerMessageType.LOAD_GAME ) ) );
        // Server sends a Notification message to all other clients in that game informing
        // them the root client connected to the game, either as a player (in which case
        // their color must be specified) or as an observer.
        broadcast
    }

    // Used to request to make a move in a game.
    private void makeMove( Session messageSender , MakeMoveCommand makeMoveCommand) {
        // Server verifies the validity of the move.
        // Game is updated to represent the move. Game is updated in the database.
        // Server sends a LOAD_GAME message to all clients in the game (including the root
        // client) with an updated game.
        // Server sends a Notification message to all other clients in that game informing
        // them what move was made.
        // If the move results in check, checkmate or stalemate the server sends a
        // Notification message to all clients.
    }

    // Tells the server you are leaving the game so it will stop sending you notifications.
    private void leave( Session messageSender , LeaveCommand leaveCommand) {
        // If a player is leaving, then the game is updated to remove the root client.
        // Game is updated in the database.
        // Server sends a Notification message to all other clients in that game informing
        // them that the root client left. This applies to both players and observers.
    }

    // Forfeits the match and ends the game (no more moves can be made).
    private void resign( Session messageSender , ResignCommand resignCommand) {
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
    public void broadcastAll(int gameID, Session senderSession, ServerMessage serverMessage) throws IOException {
        if (sessions.get( gameID ) == null) throw new IOException( "Error: Game does not exist in broadcastAll" );
        for (var s: sessions.get( gameID )) {
            s.getRemote().sendString( gson.toJson( serverMessage) );
        }
    }

    // Send a message to the sender only
    public void broadcastSender(int gameID, Session senderSession, ServerMessage serverMessage) throws IOException {
        if (sessions.get( gameID ) == null) throw new IOException( "Error: Game does not exist in broadcastSender" );
        senderSession.getRemote().sendString( gson.toJson( serverMessage) );
    }

    // Send a message to every session except the sender
    public void broadcastAllExceptSender(int gameID, Session senderSession, ServerMessage serverMessage) throws IOException {
        if (sessions.get( gameID ) == null) throw new IOException( "Error: Game does not exist in broadcastAllExceptSender" );
        for (var s: sessions.get( gameID )) {
            if (!s.equals( senderSession )) {
                s.getRemote().sendString( gson.toJson( serverMessage ) );
            }
        }
    }
}