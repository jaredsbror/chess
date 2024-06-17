package websocket.commands;


import chess.ChessGame;


public class ConnectCommand extends UserGameCommand{
    private final int gameID;
    private final String playerName;
    private final String teamColor;
    private final PlayStyle playStyle;

    public enum PlayStyle {
        OBSERVER,
        PLAYER
    }

    public ConnectCommand( String authToken, String teamColor, int gameID, PlayStyle playStyle, String playerName ) {
        super( authToken );
        this.gameID = gameID;
        this.teamColor = teamColor;
        this.playStyle = playStyle;
        this.playerName = playerName;
    }

    public int getGameID() {
        return gameID;
    }


    public String getTeamColor() {
        return teamColor;
    }


    public String getPlayerName() {
        return playerName;
    }


    public PlayStyle getPlayStyle() {
        return playStyle;
    }
}
