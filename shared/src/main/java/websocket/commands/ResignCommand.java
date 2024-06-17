package websocket.commands;


public class ResignCommand extends UserGameCommand{
    private final int gameID;
    private final String playerName;

    public ResignCommand( String authToken, int gameID, String playerName ) {
        super( authToken );
        this.gameID = gameID;
        this.playerName = playerName;
    }

    public int getGameID() {
        return gameID;
    }


    public String getPlayerName() {
        return playerName;
    }
}
