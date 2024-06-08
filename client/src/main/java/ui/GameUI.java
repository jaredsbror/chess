package ui;


import chess.ChessBoard;
import chess.ChessGame;


public class GameUI {
    private static ChessBoard chessBoard = new ChessBoard();

    public static void drawGameBoard() {
        chessBoard = new ChessBoard();
        chessBoard.resetBoard();
        ChessBoardUI.drawBoard( chessBoard, ChessGame.TeamColor.WHITE );
        ChessBoardUI.drawBoard( chessBoard, ChessGame.TeamColor.BLACK );
    }

}
