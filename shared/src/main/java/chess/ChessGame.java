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
        teamColor = TeamColor.WHITE;
        chessBoard = new ChessBoard();
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_GAME) System.out.println("Creating Chess" + this.toString());
    }

    // Copy constructor
    public ChessGame(ChessGame original) {
        this.teamColor = original.teamColor;
        this.chessBoard = new ChessBoard(original.chessBoard);
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_GAME) System.out.println("Copy of Chess" + this.toString());
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

    // Check if a position is on the board is valid
    private boolean isNotWithinBounds(ChessPosition position) {
        int row = position.getRow();
        int column = position.getColumn();
        return (row < Constants.BOARD_MIN_ONE_INDEX || row > Constants.BOARD_MAX_ONE_INDEX || column < Constants.BOARD_MIN_ONE_INDEX || column > Constants.BOARD_MAX_ONE_INDEX);
    }

    // Check if a position is on the board is valid
    private boolean isNotWithinBounds(int row, int column) {
        return (row < Constants.BOARD_MIN_ONE_INDEX || row > Constants.BOARD_MAX_ONE_INDEX || column < Constants.BOARD_MIN_ONE_INDEX || column > Constants.BOARD_MAX_ONE_INDEX);
    }

    /**
     * Gets a valid moves for a piece at the given location
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // If the startPosition is invalid, throw error.
        if (isNotWithinBounds(startPosition)) throw new RuntimeException("ERROR: Invalid Start Position in ChessGame.validMoves()");
        // If there is no piece at startPosition, return null.
        if (chessBoard.doesNotExistPiece(startPosition)) return null;
        // Create new ChessPiece object to access the pieceMoves() method.
        ChessPiece chessPiece = new ChessPiece(chessBoard.getPiece(startPosition));
        ArrayList<ChessMove> validMoves = (ArrayList<ChessMove>) chessPiece.pieceMoves(chessBoard, startPosition);
        /// Check for moves that put the friendly King in check and eliminate them.
        // Iterate over the Arraylist<> of valid moves
        for (int index = 0; index < validMoves.size(); index++) {
            // Simulate the move on another chessboard and eliminate move if makeMove() throws InvalidMoveException.
            ChessGame simulation = new ChessGame(this);
            try {
                simulation.makeMove(validMoves.get(index));
            } catch (InvalidMoveException exception) {
                validMoves.remove(index);
                // Optional debug
                if (Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_GAME) System.out.println("Found invalid move " + validMoves.get(index).toString() + " in ChessGame.validMoves() for " + chessPiece.toString());
            }
        }
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_GAME) System.out.println("(ChessGame) Valid moves for " + chessPiece.toString() + "-> " + validMoves.toString());
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     * DOES NOT restore the board to its original state if a move puts the king in check
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // Save starting, ending position, and promotion piece
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        ChessPiece.PieceType promotion = move.getPromotionPieceType();
        // Save a copy of the pieces at the startPosition and endPosition
        // NOTE: These can be references because they are used to create a new object later
        ChessPiece startPiece = chessBoard.getPiece(start);
        TeamColor startColor = startPiece.getTeamColor();
        ChessPiece.PieceType startType = startPiece.getPieceType();

        /// Check once again that the move is valid
        // Check #1: Check that startPosition is in bounds
        if (isNotWithinBounds(start)) throw new InvalidMoveException("InvalidMoveException: Out-of-bounds Start Position");
        // Check #2: Check that endPosition is in bounds
        if (isNotWithinBounds(end)) throw new InvalidMoveException("InvalidMoveException: Out-of-bounds End Position");
        // Check #3: Check that the endPosition does not already have a friendly piece
        if (chessBoard.doesFriendlyPieceExist(end,startColor)) throw new InvalidMoveException("InvalidMoveException: Friendly Piece exists at End Position");
        // Check #4: If the piece is a pawn, and it is due for promotion, check that its promotion piece is valid (e.g. non-null)
        if ((startType == ChessPiece.PieceType.PAWN) &&
            (end.getRow() == (startColor == TeamColor.WHITE ? Constants.BOARD_MAX_ONE_INDEX : Constants.BOARD_MIN_ONE_INDEX)) &&
            (promotion == null)) {
            throw new InvalidMoveException("InvalidMoveException: Promotion Piece for Promoting Pawn is null");
        }

        /// Process the move on the chessboard
        // Step #1: Add null piece to startPosition
        chessBoard.addPiece(start, null);
        // Step #2: Save piece originally at startPosition to endPosition
        // NOTE: Check for a pawn moving because it may need to be promoted
        // If the pawn is about to be promoted, place the promoted piece
        if ((startType == ChessPiece.PieceType.PAWN) &&
            (end.getRow() == (startColor == TeamColor.WHITE ? Constants.BOARD_MAX_ONE_INDEX : Constants.BOARD_MIN_ONE_INDEX))) {
            chessBoard.addPiece(end, new ChessPiece(startColor, promotion));
        } else {    // Simply place the start piece at the end location
            chessBoard.addPiece(end, new ChessPiece(startPiece));
        }
        // Step #3: Make sure the friendly king does not go into check after this move.
        // If the king does, throw an error.
        if (isInCheck(startColor)) throw new InvalidMoveException("InvalidMoveException: Move puts King in Check");
    }

    /**
     * Makes a move in a chess game
     * Restores the board to its original state if a move puts the king in check
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMoveWithBackup(ChessMove move) throws InvalidMoveException {
        // Save starting, ending position, and promotion piece.
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        ChessPiece.PieceType promotion = move.getPromotionPieceType();
        // Save a copy of the pieces at the startPosition and endPosition.
        // NOTE: These can be references because they are used to create a new object later.
        ChessPiece startPiece = chessBoard.getPiece(start);
        TeamColor startColor = startPiece.getTeamColor();
        ChessPiece.PieceType startType = startPiece.getPieceType();

        /// Check once again that the move is valid
        // Check #1: Check that startPosition is in bounds.
        if (isNotWithinBounds(start)) throw new InvalidMoveException("InvalidMoveException: Out-of-bounds Start Position");
        // Check #2: Check that endPosition is in bounds.
        if (isNotWithinBounds(end)) throw new InvalidMoveException("InvalidMoveException: Out-of-bounds End Position");
        // Check #3: Check that the endPosition does not already have a friendly piece.
        if (chessBoard.doesFriendlyPieceExist(end,startColor)) throw new InvalidMoveException("InvalidMoveException: Friendly Piece exists at End Position");
        // Check #4: If the piece is a pawn, and it is due for promotion, check that its promotion piece is valid (e.g. non-null).
        if ((startType == ChessPiece.PieceType.PAWN) &&
                (end.getRow() == (startColor == TeamColor.WHITE ? Constants.BOARD_MAX_ONE_INDEX : Constants.BOARD_MIN_ONE_INDEX)) &&
                (promotion == null)) {
            throw new InvalidMoveException("InvalidMoveException: Promotion Piece for Promoting Pawn is null");
        }

        /// Process the move on the chessboard
        // Step #1: Create a backup of the board to restore if the move becomes invalid.
        ChessBoard backup = new ChessBoard(chessBoard);
        // Step #2: Add null piece to startPosition.
        chessBoard.addPiece(start, null);
        // Step #3: Save piece originally at startPosition to endPosition.
        // NOTE: Check for a pawn moving because it may need to be promoted.
        // If the pawn is about to be promoted, place the promoted piece.
        if ((startType == ChessPiece.PieceType.PAWN) &&
                (end.getRow() == (startColor == TeamColor.WHITE ? Constants.BOARD_MAX_ONE_INDEX : Constants.BOARD_MIN_ONE_INDEX))) {
            chessBoard.addPiece(end, new ChessPiece(startColor, promotion));
        } else {    // Simply place the start piece at the end location
            chessBoard.addPiece(end, new ChessPiece(startPiece));
        }
        // Step #4: Make sure the friendly king does not go into check after this move
        // If the king does, restore the board and throw and error
        if (isInCheck(startColor)) {
            chessBoard = new ChessBoard(backup);
            throw new InvalidMoveException("InvalidMoveException: Move puts King in Check");
        }
    }

    /**
     * Determines if the given team is in check
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        /// Get the location of the current team's King
        ChessPosition currentKingPosition = new ChessPosition(0,0);
        boolean foundKing = false;
        // Iterate over the chessboard rows...
        for (int row = Constants.BOARD_MIN_ONE_INDEX; row <= Constants.BOARD_MAX_ONE_INDEX; row++) {
            // Iterate over the chessboard columns...
            for (int col = Constants.BOARD_MIN_ONE_INDEX; col <= Constants.BOARD_MAX_ONE_INDEX; col++) {
                // If a friendly chess piece does not exist, skip this loop iteration
                if (!chessBoard.doesFriendlyPieceExist(row, col, teamColor)) continue;
                // If the piece is the current king, save its position
                if (chessBoard.getPiece(row, col).getPieceType() == ChessPiece.PieceType.KING) {
                    currentKingPosition = new ChessPosition(row, col);
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
                ArrayList<ChessMove> validMoves = (ArrayList<ChessMove>) validMoves(new ChessPosition(row, col));
                // Iterate over the validMoves
                for (var move : validMoves) {
                    // If any of those valid moves include the current player's king, return true
                    if (move.getEndPosition().equals(currentKingPosition)) return true;
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
