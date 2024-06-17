package websocket;


import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import spark.Spark;
import websocket.commands.*;


@WebSocket
public class WSServer {
    private static final ConnectionManager connectionManager = new ConnectionManager();
    private final Gson gson = new Gson();

    public static void main(String[] args) {
        Spark.port(8080);
        Spark.webSocket("/ws", WSServer.class);
    }

    @OnWebSocketMessage
    public void onMessage( Session session, String message) throws Exception {
        // PROCESSES THE JSON MESSAGE AND DOES STUFF
        // CONNECT, MAKE MOVE, RESIGN
        UserGameCommand userGameCommand = gson.fromJson( message, UserGameCommand.class );
        switch (userGameCommand.getCommandType()) {
            case CONNECT -> connect( session, gson.fromJson( message, ConnectCommand.class ));
            case MAKE_MOVE -> makeMove( session, gson.fromJson( message, MakeMoveCommand.class ));
            case LEAVE -> leave( session, gson.fromJson( message, LeaveCommand.class ));
            case RESIGN -> resign( session, gson.fromJson( message, ResignCommand.class ));
        }

        session.getRemote().sendString("WebSocket response: " + message);
    }

    private void connect( Session session, ConnectCommand connectCommand ) {

    }

    private void makeMove( Session session , MakeMoveCommand makeMoveCommand) {

    }

    private void leave( Session session , LeaveCommand leaveCommand) {

    }

    private void resign( Session session , ResignCommand resignCommand) {

    }



}