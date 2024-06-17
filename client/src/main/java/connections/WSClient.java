package connections;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

public class WSClient extends Endpoint {

    public static void main(String[] args) throws Exception {
        var ws = new WSClient();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a message you want to echo");
        while (true) ws.send(scanner.nextLine());
    }

    public Session session;

    public WSClient() throws Exception {
        URI uri = new URI("ws://localhost:8080/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        // Add onMessage { ClientUI.notify() } (
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {

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