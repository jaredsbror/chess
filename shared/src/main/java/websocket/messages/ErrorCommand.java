package websocket.messages;


public class ErrorCommand extends ServerMessage {
    private final String errorMessage;

    public ErrorCommand( ServerMessageType type, String errorMessage ) {
        super( type );
        this.errorMessage = errorMessage;
    }


    public String getErrorMessage() {
        return errorMessage;
    }
}
