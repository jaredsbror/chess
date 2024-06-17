package connections;


import com.google.gson.Gson;
import datatypes.ServerMessageObserver;
import websocket.messages.ErrorCommand;
import websocket.messages.LoadGameCommand;
import websocket.messages.NotificationCommand;
import websocket.messages.ServerMessage;

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

        // Add onMessage { ClientUI.notify() } (
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                // Detect the server message type and return the appropriate deserialized object
                ServerMessage baseServerMessage = gson.fromJson( message, ServerMessage.class );
                switch (baseServerMessage.getServerMessageType()) {
                    case LOAD_GAME -> {
                        serverMessageObserver.notify(gson.fromJson( message, LoadGameCommand.class ));
                    }
                    case ERROR -> {
                        serverMessageObserver.notify(gson.fromJson( message, ErrorCommand.class ));
                    }
                    case NOTIFICATION -> {
                        serverMessageObserver.notify(gson.fromJson( message, NotificationCommand.class ));
                    }
                    default -> throw new RuntimeException("Error: Invalid ServerMessageType in WSClient.java");
                }
            }
        });
    }

    // SENT A JSON MESSAGE
    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }


    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}