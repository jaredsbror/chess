package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    // Variables
    private ChessPiece[][] board = new ChessPiece[Constants.BOARD_NUM_ROWS][Constants.BOARD_NUM_COLUMNS];

    // Constructor
    public ChessBoard() {
        resetBoard();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = position.getRow();
        int column = position.getColumn();
        board[row][column] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow();
        int column = position.getColumn();
        return board[row][column];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // Iterate over the chessboard rows...
        for (int row = 0; row < Constants.BOARD_NUM_ROWS; row++) {
            // Iterate over the chessboard columns...
            for (int col = 0; col < Constants.BOARD_NUM_COLUMNS; col++) {
                // Initialize the piece to NULL
                board[row][col] = null;
            }
        }


    }

    //
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    //
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    //
    @Override
    public String toString() {
        return super.toString();
    }
}
