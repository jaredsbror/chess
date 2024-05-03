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

    // Add piece to the chessboard
    public void addPiece(int row, int column, ChessPiece piece) {
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

    // Get chess piece from the chessboard
    public ChessPiece getPiece(int row, int column) {
        return board[row][column];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
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
