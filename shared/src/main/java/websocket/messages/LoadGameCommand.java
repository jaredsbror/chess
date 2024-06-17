package websocket.messages;


import model.original.GameData;


public class LoadGameCommand extends ServerMessage {
    private GameData game = null;

    public LoadGameCommand( ServerMessageType type, GameData gameData ) {
        super( type );
        this.game = gameData;
    }
}
