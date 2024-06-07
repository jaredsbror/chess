package ui;


import chess.ChessBoard;
import chess.ChessGame;


public class ExtendedChessBoard {
    private ChessSquare[][] extendedChessBoard;
    private String background;
    private String foreground;
    private String squareValue;

    public ExtendedChessBoard() {
        extendedChessBoard = new ChessSquare[ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES][ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES];
        background = foreground = squareValue = null;
    }

    public ExtendedChessBoard(ChessBoard chessBoard, ChessGame.TeamColor teamColor ) {
        extendedChessBoard = new ChessSquare[ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES][ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES];
        background = foreground = squareValue = null;
        // Convert depending on the teamColor
        if (teamColor == ChessGame.TeamColor.WHITE) {
            // Top edge with labels
            for (int col = 0; col < ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES; col++) {
                background = ChessUIConstants.CHESS_EDGE_BACKGROUND;
                foreground = ChessUIConstants.CHESS_EDGE_FOREGROUND;
                squareValue = ChessUIConstants.CHESS_SQUARE_BUFFER + ChessUIConstants.CHESS_EDGE_LABEL_LETTERS_WHITE[col] + ChessUIConstants.CHESS_SQUARE_BUFFER;
            }
            // Game body
            for (int row = 0; row < ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES; row++) {

            }
            // Bottom edge with labels
            for (int col = 0; col < ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES; col++) {
                background = ChessUIConstants.CHESS_EDGE_BACKGROUND;
                foreground = ChessUIConstants.CHESS_EDGE_FOREGROUND;
                squareValue = ChessUIConstants.CHESS_SQUARE_BUFFER + ChessUIConstants.CHESS_EDGE_LABEL_LETTERS_WHITE[col] + ChessUIConstants.CHESS_SQUARE_BUFFER;
            }
        } else {
            // Iterate over the rows and columns of extendedChessBoard
            for (int col = 0; col < ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES; col++) {
                extendedChessBoard[0
            }
            for (int row = 0; row < ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES; row++) {

            }
        }
    }

    public ChessSquare[] getRow(int row) {
        return extendedChessBoard[row];
    }

    public ChessSquare getChessSquare(int row, int column) {
        return extendedChessBoard[row][column];
    }
}
