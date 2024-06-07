package ui;


import chess.ChessGame;


public class GameUI {

    public static void drawGameBoard() {
        ChessBoardUI.drawBoard( ChessGame.TeamColor.WHITE );
    }

}
