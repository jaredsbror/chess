package websocket.messages;


import model.original.GameData;


public class LoadGameCommand extends ServerMessage {
    private final GameData gameData;

    public LoadGameCommand( ServerMessageType type, GameData gameData ) {
        super( type );
        this.gameData = gameData;
    }

    public GameData getGameData() {
        return gameData;
    }
}
