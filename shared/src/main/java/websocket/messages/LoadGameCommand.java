package websocket.messages;


import model.original.GameData;


public class LoadGameCommand extends ServerMessage {
    private final GameData game = null;

    public LoadGameCommand( ServerMessageType type ) {
        super( type );
    }
}
