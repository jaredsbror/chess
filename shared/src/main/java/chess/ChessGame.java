package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_GAME) System.out.println("Creating Chess" + this.toString());
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
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // If the startPosition is invalid, throw error
        // ???
        // If there is no piece at startPosition, return null
        if (chessBoard.doesNotExistPiece(startPosition)) return null;
        // Create new ChessPiece object to access the pieceMoves() method
        // NOTE that the piece type and piece color technically don't matter because the
        // pieceMoves() method automatically determines those values based on the position.
        ChessPiece chessPiece = new ChessPiece(chessBoard.getPieceTeamColor(startPosition), chessBoard.getPiece(startPosition).getPieceType());
        Collection<ChessMove> validMoves = chessPiece.pieceMoves(chessBoard, startPosition);
        // Check for moves that put the friendly King in check and eliminate them
        // ???
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_GAME) System.out.println("(ChessGame) Valid moves for " + chessPiece.toString() + "-> " + validMoves.toString());
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // Save starting, ending position, and promotion piece
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        ChessPiece.PieceType promotion = move.getPromotionPiece();
        // Save a copy of the pieces at the startPosition and endPosition
        // NOTE: These can be references because they are used to create a new object later
        ChessPiece startPiece = chessBoard.getPiece(start);
        ChessPiece endPiece = chessBoard.getPiece(end);

        /// Check once again that the move is valid
        // Check #1: Check that startPosition is in bounds

        // Check #2: Check that endPosition is in bounds

        // Check #3: Check that the endPosition does not already have a friendly piece

        // Check #4: If the piece is a pawn, and it is due for promotion, check that its promotion piece is valid (e.g. non-null)

        // Check #5: Make sure the friendly king does not go into check after this move
        // NOTE: May have to clone the board to test this

        /// Process the move on the chessboard
        // Step #1: Add null piece to startPosition

        // Step #2: Save piece originally at startPosition to endPosition
        // NOTE: Check for a pawn moving because it may need to be promoted
    }

    /**
     * Determines if the given team is in check
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        /// Get the location of the current team's King
        int currentKingRow;
        int currentKingColumn;
        boolean foundKing = false;
        // Iterate over the chessboard rows...
        for (int row = Constants.BOARD_MIN_ONE_INDEX; row <= Constants.BOARD_MAX_ONE_INDEX; row++) {
            // Iterate over the chessboard columns...
            for (int col = Constants.BOARD_MIN_ONE_INDEX; col <= Constants.BOARD_MAX_ONE_INDEX; col++) {
                // If a friendly chess piece does not exist, skip this loop iteration
                if (!chessBoard.doesFriendlyPieceExist(row, col, teamColor)) continue;
                // If the piece is the current king, save its position
                if (chessBoard.getPiece(row, col).getPieceType() == ChessPiece.PieceType.KING) {
                    currentKingRow = row;
                    currentKingColumn = col;
                    foundKing = true;
                }
            }
        }
        // If there is no friendly king, throw an error
        if (!foundKing) {
            System.out.println("Board in ChessGame.isInCheck()");
            chessBoard.printBoard();
            throw new RuntimeException("Unable to find friendly King in ChessGame.isInCheck() on " + chessBoard.toString());
        }

        /// Iterate over each opponent's piece in the board and determine their valid moves.
        // Iterate over the chessboard rows...
        for (int row = Constants.BOARD_MIN_ONE_INDEX; row <= Constants.BOARD_MAX_ONE_INDEX; row++) {
            // Iterate over the chessboard columns...
            for (int col = Constants.BOARD_MIN_ONE_INDEX; col <= Constants.BOARD_MAX_ONE_INDEX; col++) {
                // If a piece does not exist, skip this loop iteration
                if (chessBoard.doesNotExistPiece(row, col)) continue;
                // If the piece is the current's team color, skip this loop iteration
                if (chessBoard.getPieceTeamColor(row, col) == teamColor) continue;
                // Fill a new validMoves array with valid moves
                Collection<ChessMove> validMoves = validMoves(new ChessPosition(row, col));
                // Iterate over the validMoves
                for (var move : validMoves) {
                    // If any of those valid moves include the current player's king, return true
                    if (move.endPosition.getRow() == currentKingRow && move.endPosition.getColumn() == currentKingColumn) return true;
                }
            }
        }
        // Otherwise return false
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // Go through all possible moves and see if the friendly King is still in check
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // Return true if there are no possible valid moves for the friendly team
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_GAME) System.out.println("Setting chessBoard to " + board.toString());
        // Iterate over the chessboard rows...
        for (int row = Constants.BOARD_MIN_ONE_INDEX; row <= Constants.BOARD_MAX_ONE_INDEX; row++) {
            // Iterate over the chessboard columns...
            for (int col = Constants.BOARD_MIN_ONE_INDEX; col <= Constants.BOARD_MAX_ONE_INDEX; col++) {
                // Check if a piece exists...
                if (board.doesNotExistPiece(row, col)) {
                    chessBoard.addNull(row, col);
                    continue;
                };
                // Add a chess piece copy to chessBoard
                TeamColor pieceColor = board.getPiece(row, col).getTeamColor();
                ChessPiece.PieceType pieceType = board.getPiece(row, col).getPieceType();
                chessBoard.addPiece(row, col,new ChessPiece(pieceColor, pieceType) );
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return teamColor == chessGame.teamColor && Objects.equals(chessBoard, chessGame.chessBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, chessBoard);
    }

    @Override
    public String toString() {
        return "Game{" +
                "Color=" + teamColor +
                ",Board=" + chessBoard +
                '}';
    }
}
