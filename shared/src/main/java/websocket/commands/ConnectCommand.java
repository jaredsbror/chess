package websocket.commands;


import chess.ChessGame;


public class ConnectCommand extends UserGameCommand{
    private final String teamColor;
    private final PlayStyle playStyle;

    public enum PlayStyle {
        OBSERVER,
        PLAYER
    }

    public ConnectCommand( String authToken, String teamColor, int gameID, PlayStyle playStyle) {
        super( authToken, gameID );
        this.teamColor = teamColor;
        this.playStyle = playStyle;
    }

    public String getTeamColor() {
        return teamColor;
    }


    public PlayStyle getPlayStyle() {
        return playStyle;
    }
}
