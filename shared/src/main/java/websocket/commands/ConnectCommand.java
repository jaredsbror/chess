package websocket.commands;


import chess.ChessGame;


public class ConnectCommand extends UserGameCommand{
    private final int gameID;
    private final String teamColor;

    public ConnectCommand( String authToken, String teamColor, int gameID ) {
        super( authToken );
        this.gameID = gameID;
        this.teamColor = teamColor;
    }

    public int getGameID() {
        return gameID;
    }


    public String getTeamColor() {
        return teamColor;
    }
}
