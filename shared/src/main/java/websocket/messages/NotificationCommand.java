package websocket.messages;


public class NotificationCommand extends ServerMessage {
    private final String message;

    public NotificationCommand( ServerMessageType type, String message ) {
        super( type );
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
