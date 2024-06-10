package ui;


import chess.ChessBoard;
import chess.ChessGame;
import datatypes.ExtendedChessBoard;


public class GameUI {
    private static ChessBoard chessBoard = new ChessBoard();

    public static void drawGameBoard() {
        chessBoard = new ChessBoard();
        chessBoard.resetBoard();
        ChessBoardUI.drawBoard( new ExtendedChessBoard(chessBoard, ChessGame.TeamColor.WHITE ));
        ChessBoardUI.drawBoard( chessBoard, ChessGame.TeamColor.BLACK );
    }
}