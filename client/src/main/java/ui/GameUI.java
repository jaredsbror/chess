package ui;


import chess.ChessBoard;
import chess.ChessGame;
import datatypes.ExtendedChessBoard;
import model.original.GameData;


public class GameUI {
    private static ChessBoard chessBoard = new ChessBoard();

    public static void drawGameBoard(ChessBoard board) {
        chessBoard = board;
        chessBoard.resetBoard();
        ChessBoardUI.drawBoard( chessBoard, ChessGame.TeamColor.WHITE );
        ChessBoardUI.drawBoard( chessBoard, ChessGame.TeamColor.BLACK );
    }

    public static void drawGameBoard( GameData gameData ) {
        chessBoard = new ChessBoard( ChessBoard.parseBoard( gameData.game().toString() ) );
        chessBoard.resetBoard();
        ChessBoardUI.drawBoard( chessBoard, ChessGame.TeamColor.WHITE );
        ChessBoardUI.drawBoard( chessBoard, ChessGame.TeamColor.BLACK );
    }
}
