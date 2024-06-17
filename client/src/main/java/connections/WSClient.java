package connections;


import com.google.gson.Gson;
import datatypes.ServerMessageObserver;
import websocket.messages.*;

import javax.websocket.*;
import java.net.URI;

public class WSClient extends Endpoint {
    private final Gson gson = new Gson();
    private final ServerMessageObserver serverMessageObserver;
    public Session session;

    public WSClient(ServerMessageObserver serverMessageObserver) throws Exception {
        // Add ServerMessageObserver
        this.serverMessageObserver = serverMessageObserver;
        // Connect to Websocket
        URI uri = new URI("ws://localhost:8080/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        // Add onMessage { ChessClient.notify() } (
        this.session.addMessageHandler( (MessageHandler.Whole<String>) message -> {
            // Detect the server message type and return the appropriate deserialized object
            ServerMessage baseServerMessage = gson.fromJson( message, ServerMessage.class );
            switch (baseServerMessage.getServerMessageType()) {
                case LOAD_GAME -> {
                    try {
                        serverMessageObserver.notify(gson.fromJson( message, LoadGameCommand.class ));
                    } catch ( Exception e ) {
                        throw new RuntimeException( e );
                    }
                }
                case ERROR -> {
                    try {
                        serverMessageObserver.notify(gson.fromJson( message, ErrorCommand.class ));
                    } catch ( Exception e ) {
                        throw new RuntimeException( e );
                    }
                }
                case NOTIFICATION -> {
                    try {
                        serverMessageObserver.notify(gson.fromJson( message, NotificationCommand.class ));
                    } catch ( Exception e ) {
                        throw new RuntimeException( e );
                    }
                }
                default -> throw new RuntimeException("Error: Invalid ServerMessageType in WSClient.java");
            }
        } );
    }


    @Override
    public void onOpen( javax.websocket.Session session, EndpointConfig endpointConfig ) {

    }
}