package ui;


import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;


public class ExtendedChessBoard {
    private ChessSquare[][] extendedChessBoard;
    private String background;
    private String foreground;
    private String squareValue;

    public ExtendedChessBoard() {
        // Initialized variables
        extendedChessBoard = new ChessSquare[ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES][ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES];
        // Properly initialize array
        for (int row = 0; row < ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES; row ++) {
            for (int col = 0; col < ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES; col++) {
                extendedChessBoard[row][col] = null;
            }
        }
        background = foreground = squareValue = null;
    }

    private void setSquare(int row, int col, String value, String foreground, String background) {
        if (row >= 0 && row < extendedChessBoard.length && col >= 0 && col < extendedChessBoard[row].length) {
            extendedChessBoard[row][col] = new ChessSquare(value, foreground, background);
        } else {
            throw new ArrayIndexOutOfBoundsException("Invalid row or column index");
        }
    }

    public ExtendedChessBoard(ChessBoard chessBoard, ChessGame.TeamColor teamColor ) {
        // Initialize variables
        extendedChessBoard = new ChessSquare[ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES][ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES];
        // Properly initialize array
        for (int row = 0; row < ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES; row ++) {
            for (int col = 0; col < ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES; col++) {
                extendedChessBoard[row][col] = null;
            }
        }
        background = foreground = squareValue = null;
        // Convert ChessBoard to ExtendedChessBoard depending on the teamColor
        if (teamColor == ChessGame.TeamColor.WHITE) {
            // Top edge with labels
            for (int col = 0; col < ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES; col++) {
                squareValue = ChessUIConstants.CHESS_EDGE_LABEL_LETTERS_WHITE[col];
                foreground = ChessUIConstants.CHESS_EDGE_FOREGROUND;
                background = ChessUIConstants.CHESS_EDGE_BACKGROUND;
                setSquare( 0, col, squareValue, foreground, background );
            }
            // Game body rows
            for (int row = 1; row <= ChessUIConstants.CHESS_BOARD_SIZE_IN_SQUARES; row++) {
                for (int col = 0; col < ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES; col++) {
                    // Make sure it isn't an edge...
                    if (col == 0 || col == ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES - 1) {
                        squareValue = ChessUIConstants.CHESS_EDGE_LABEL_NUMBERS_WHITE[row];
                        foreground = ChessUIConstants.CHESS_EDGE_FOREGROUND;
                        background = ChessUIConstants.CHESS_EDGE_BACKGROUND;
                        setSquare( row, col, squareValue, foreground, background);
                    } else {
                        ChessPiece chessPiece =  chessBoard.getPiece( row, col);
                        // Determine chess square text value
                        if (chessPiece == null) {
                            squareValue = ChessUIConstants.EMPTY;
                        } else {
                            squareValue = switch ( chessPiece.getPieceType() ) {
                                case KING -> ChessUIConstants.WHITE_KING_LETTER;
                                case QUEEN -> ChessUIConstants.WHITE_QUEEN_LETTER;
                                case BISHOP -> ChessUIConstants.WHITE_BISHOP_LETTER;
                                case KNIGHT -> ChessUIConstants.WHITE_KNIGHT_LETTER;
                                case ROOK -> ChessUIConstants.WHITE_ROOK_LETTER;
                                case PAWN -> ChessUIConstants.WHITE_PAWN_LETTER;
                            };
                        }
                        // Determine foreground color
                        foreground = ChessUIConstants.CHESS_WHITE_PIECE_FOREGROUND;
                        // Determine the background color

                    }
                }
            }
            // Bottom edge with labels
            for (int col = 0; col < ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES; col++) {
                foreground = ChessUIConstants.CHESS_EDGE_FOREGROUND;
                background = ChessUIConstants.CHESS_EDGE_BACKGROUND;
                squareValue = ChessUIConstants.CHESS_EDGE_LABEL_LETTERS_WHITE[col];
                setSquare( ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES - 1, col, squareValue, foreground, background );
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
