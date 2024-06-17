package websocket;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.NotificationCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, Vector<websocket.Connection>> connections = new ConcurrentHashMap<>();

    public void add(String authtToken, int gameID, Session session) {
        var connection = new websocket.Connection(authtToken, session);
        if (connections.get(gameID) != null) {
            connections.get(gameID).add(connection);
        } else {
            connections.put( gameID, new Vector<websocket.Connection>());
            connections.get(gameID).add( connection );
        }
    }

    public void remove(int gameID) {
        connections.remove(gameID);
    }

    public void broadcast(int gameID, Session sessionOfSender, NotificationCommand notification) throws IOException {
        // Make sure the game exists
        if (connections.get(gameID) != null) {
            Vector<websocket.Connection> gameConnections =  connections.get(gameID);
            for (var connection : gameConnections) {
                if (connection.session.isOpen()) {
                    if (!connection.session.equals(sessionOfSender)) {
                        connection.send(notification.toString());
                    }
                }
            }
        }
    }


    // REmove connectionn



    // Send a mesxage to all



    // Send game data to all players
}