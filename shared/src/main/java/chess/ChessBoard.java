package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    // Variables
    private ChessPiece[][] board = new ChessPiece[Constants.BOARD_NUM_ROWS][Constants.BOARD_NUM_COLUMNS];

    // Constructor
    public ChessBoard() {
        /// Reset the board completely with null pieces
        // Iterate over the chessboard rows...
        for (int row = 0; row < Constants.BOARD_NUM_ROWS; row++) {
            // Iterate over the chessboard columns...
            for (int col = 0; col < Constants.BOARD_NUM_COLUMNS; col++) {
                // Initialize the piece to NULL
                board[row][col] = null;
            }
        }
    }

    /**
     * Adds a chess piece to the chessboard
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = position.getRow() - 1;
        int column = position.getColumn() - 1;
        System.out.println("addPiece(): Adding " + piece.toString() + " to (" + position.getRow() + "," + position.getColumn() + ")");
        board[row][column] = piece;
    }

    // Add piece to the chessboard
    public void addPiece(int row, int column, ChessPiece piece) {
        board[row - 1][column - 1] = piece;
    }

    // Set a position to null
    public void addNull(ChessPosition position) {
        int row = position.getRow() - 1;
        int column = position.getColumn() - 1;
        //        System.out.println("addPiece(): Adding null to (" + row + "," + column + ")");
        board[row][column] = null;
    }

    // Set a position to null
    public void addNull(int row, int column) {
        board[row - 1][column - 1] = null;
    }

    /**
     * Gets a chess piece on the chessboard
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow() - 1;
        int column = position.getColumn() - 1;
        return board[row][column];
    }

    // Get chess piece from the chessboard
    public ChessPiece getPiece(int row, int column) {
        return board[row - 1][column - 1];
    }

    // Return true if there is a piece at a certain position
    public boolean doesNotExistPiece(ChessPosition position) {
        int row = position.getRow() - 1;
        int column = position.getColumn() - 1;
        return board[row][column] == null;
    }

    // Return true if there is a piece at a certain position
    public boolean doesNotExistPiece(int row, int column) {
        return board[row - 1][column - 1] == null;
    }

    // Return true if there is a piece at a certain position
    public boolean doesFriendlyPieceExist(ChessPosition position, ChessGame.TeamColor friendlyTeamColor) {
        if (doesNotExistPiece(position)) return false;
        return getPieceTeamColor(position) == friendlyTeamColor;
    }

    // Return true if there is a piece at a certain position
    public boolean doesFriendlyPieceExist(int row, int column, ChessGame.TeamColor friendlyTeamColor) {
        if (doesNotExistPiece(row, column)) return false;
        return getPieceTeamColor(row, column) == friendlyTeamColor;
    }

    // Return true if there is a piece at a certain position
    public boolean doesOpponentPieceExist(ChessPosition position, ChessGame.TeamColor opponentTeamColor) {
        if (doesNotExistPiece(position)) return false;
        return getPieceTeamColor(position) == opponentTeamColor;
    }

    // Return true if there is a piece at a certain position
    public boolean doesOpponentPieceExist(int row, int column, ChessGame.TeamColor opponentTeamColor) {
        if (doesNotExistPiece(row, column)) return false;
        return getPieceTeamColor(row, column) == opponentTeamColor;
    }

    // Return the color of the piece at a certain location
    public ChessGame.TeamColor getPieceTeamColor(int row, int column) {
        return board[row - 1][column - 1].getTeamColor();
    }

    // Return the color of the piece at a certain location
    public ChessGame.TeamColor getPieceTeamColor(ChessPosition position) {
        int row = position.getRow() - 1;
        int column = position.getColumn() - 1;
        return board[row][column].getTeamColor();
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        System.out.println("Resetting Board");
        /// First reset the board completely with null pieces
        // Iterate over the chessboard rows...
        for (int row = 0; row < Constants.BOARD_NUM_ROWS; row++) {
            // Iterate over the chessboard columns...
            for (int col = 0; col < Constants.BOARD_NUM_COLUMNS; col++) {
                // Initialize the piece to NULL
                board[row][col] = null;
            }
        }

        /// Then add the black and white pieces
        // Add black pieces
        board[Constants.BLACK_NON_PAWN_ROW][Constants.ROOK_COL_1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        board[Constants.BLACK_NON_PAWN_ROW][Constants.KNIGHT_COL_1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        board[Constants.BLACK_NON_PAWN_ROW][Constants.BISHOP_COL_1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        board[Constants.BLACK_NON_PAWN_ROW][Constants.QUEEN_COL] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        board[Constants.BLACK_NON_PAWN_ROW][Constants.KING_COL] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        board[Constants.BLACK_NON_PAWN_ROW][Constants.BISHOP_COL_2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        board[Constants.BLACK_NON_PAWN_ROW][Constants.KNIGHT_COL_2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        board[Constants.BLACK_NON_PAWN_ROW][Constants.ROOK_COL_2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        // Loop to add the pawns...
        for (int col = 0; col < Constants.BOARD_NUM_COLUMNS; col++) {
            board[Constants.BLACK_PAWN_ROW][col] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }

        // Add white pieces
        board[Constants.WHITE_NON_PAWN_ROW][Constants.ROOK_COL_1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        board[Constants.WHITE_NON_PAWN_ROW][Constants.KNIGHT_COL_1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        board[Constants.WHITE_NON_PAWN_ROW][Constants.BISHOP_COL_1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        board[Constants.WHITE_NON_PAWN_ROW][Constants.QUEEN_COL] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        board[Constants.WHITE_NON_PAWN_ROW][Constants.KING_COL] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        board[Constants.WHITE_NON_PAWN_ROW][Constants.BISHOP_COL_2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        board[Constants.WHITE_NON_PAWN_ROW][Constants.KNIGHT_COL_2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        board[Constants.WHITE_NON_PAWN_ROW][Constants.ROOK_COL_2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        // Loop to add the pawns...
        for (int col = 0; col < Constants.BOARD_NUM_COLUMNS; col++) {
            board[Constants.WHITE_PAWN_ROW][col] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard that)) return false;
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "board=" + Arrays.toString(board) +
                '}';
    }
}
