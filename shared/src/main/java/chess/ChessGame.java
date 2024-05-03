package chess;

import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    // Variables
    private TeamColor teamColor;
    private ChessBoard chessBoard;

    // Constructor
    public ChessGame() {
        teamColor = TeamColor.WHITE;
        chessBoard = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamColor;
    }

    /**
     * Set's which teams turn it is
     * @param teamColor the team whose turn it is
     */
    public void setTeamTurn(TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // ???
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        // Iterate over the chessboard rows...
        for (int row = 0; row < Constants.BOARD_NUM_ROWS; row++) {
            // Iterate over the chessboard columns...
            for (int col = 0; col < Constants.BOARD_NUM_COLUMNS; col++) {
                // Check the color...
                if (board.getPiece(row, col).getTeamColor() == TeamColor.WHITE) {
                    // Set the board location to a certain piece
                    switch (board.getPiece(row, col).getPieceType()) {
                        case KING:
                            chessBoard.addPiece(row, col,new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KING) );
                            break;
                        case QUEEN:
                            chessBoard.addPiece(row, col,new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.QUEEN) );
                            break;
                        case BISHOP:
                            chessBoard.addPiece(row, col,new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.BISHOP) );
                            break;
                        case KNIGHT:
                            chessBoard.addPiece(row, col,new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.KNIGHT) );
                            break;
                        case ROOK:
                            chessBoard.addPiece(row, col,new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.ROOK) );
                            break;
                        case PAWN:
                            chessBoard.addPiece(row, col,new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.PAWN) );
                            break;
                        default:
                            chessBoard.addPiece(row, col, null);
                            break;
                    }
                } else {    // The piece is black
                    // Set the board location to a certain piece
                    switch (board.getPiece(row, col).getPieceType()) {
                        case KING:
                            chessBoard.addPiece(row, col,new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KING) );
                            break;
                        case QUEEN:
                            chessBoard.addPiece(row, col,new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.QUEEN) );
                            break;
                        case BISHOP:
                            chessBoard.addPiece(row, col,new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.BISHOP) );
                            break;
                        case KNIGHT:
                            chessBoard.addPiece(row, col,new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.KNIGHT) );
                            break;
                        case ROOK:
                            chessBoard.addPiece(row, col,new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.ROOK) );
                            break;
                        case PAWN:
                            chessBoard.addPiece(row, col,new ChessPiece(TeamColor.BLACK, ChessPiece.PieceType.PAWN) );
                            break;
                        default:
                            chessBoard.addPiece(row, col, null);
                            break;
                    }
                }

            }
        }
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return chessBoard;
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
