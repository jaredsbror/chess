package websocket.messages;


public class ErrorCommmand extends ServerMessage {
    private final String errorMessage;

    public ErrorCommmand( ServerMessageType type, String errorMessage ) {
        super( type );
        this.errorMessage = errorMessage;
    }


    public String getErrorMessage() {
        return errorMessage;
    }
}
