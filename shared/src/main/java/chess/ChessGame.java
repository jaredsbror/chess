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
    private TeamColor gameTeamColor;
    private ChessBoard chessBoard;

    // Constructor
    public ChessGame() {
        gameTeamColor = TeamColor.WHITE;
        chessBoard = new ChessBoard();
        chessBoard.resetBoard();
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_GAME) System.out.println("Creating Chess" + this);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return gameTeamColor;
    }


    /**
     * Set's which teams turn it is
     * @param teamColor the team whose turn it is
     */
    public void setTeamTurn(TeamColor teamColor) {
        this.gameTeamColor = teamColor;
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
        System.out.println("ValidMoves:");
        chessBoard.printBoard();
        // If the startPosition is invalid, return null
        if (isNotWithinBounds(startPosition)) return null;
        // If there is no piece at startPosition, return null.
        if (chessBoard.doesNotExistPiece(startPosition)) return null;

        // Create new ChessPiece object to access the pieceMoves() method.
        ChessPiece chessPiece = chessBoard.getPiece(startPosition);

        Collection<ChessMove> possibleValidMoves = chessPiece.pieceMoves(chessBoard, startPosition);
        Collection<ChessMove> actualValidMoves = new ArrayList<ChessMove>();    // ???

        /// Check for moves that don't put the friendly King in check and add them.
        // Iterate over the Collection of valid moves
        for (var move: possibleValidMoves) {
            // Save essential piece information for better reading clarity
            ChessPiece startPiece = chessBoard.getPiece(startPosition);
            TeamColor startColor = startPiece.getTeamColor();
            ChessPiece.PieceType startType = startPiece.getPieceType();
            ChessPosition endPosition = move.getEndPosition();
            ChessPiece.PieceType promotionType = move.getPromotionPieceType();

            /// Check that the move is valid (not including if the King is in check)
            // Check #1: Check that startPosition is in bounds.
            if (isNotWithinBounds(move.getStartPosition())) continue;
            // Check #2: Check that endPosition is in bounds.
            if (isNotWithinBounds(move.getEndPosition())) continue;
            // Check #3: Check that the endPosition does not already have a friendly piece.
            if (chessBoard.doesFriendlyPieceExist(endPosition,startColor)) continue;
            // Check #4: If the piece is a pawn, and it is due for promotion, check that its promotion piece is valid (e.g. non-null).
            if ((startType == ChessPiece.PieceType.PAWN) &&
                    (endPosition.getRow() == (startColor == TeamColor.WHITE ? Constants.BOARD_MAX_ONE_INDEX : Constants.BOARD_MIN_ONE_INDEX)) &&
                    (promotionType == null)) {
                continue;
            }

            /// Simulate the move on another chessboard to detect illegal moves causing check
            ChessBoard simulation = new ChessBoard(chessBoard);
            // Step #1: Add null piece to startPosition.
            simulation.addPiece(startPosition, null);
            // Step #2: Save piece originally at startPosition to endPosition.
            // NOTE: Check for a pawn moving because it may need to be promoted.
            // If the pawn is about to be promoted, place the promoted piece.
            if ((startType == ChessPiece.PieceType.PAWN) &&
                    (endPosition.getRow() == (startColor == TeamColor.WHITE ? Constants.BOARD_MAX_ONE_INDEX : Constants.BOARD_MIN_ONE_INDEX))) {
                simulation.addPiece(endPosition, new ChessPiece(startColor, promotionType));
            } else {    // Simply place the start piece at the end location
                simulation.addPiece(endPosition, new ChessPiece(startColor, startType));
            }
            // Step #3: Make sure the friendly king does not go into check after this move
            // If the king does, skip this for-loop iteration
            if (isInCheck(simulation, startColor)) continue;

            // Now that the move has been proven to be completely valid, add it to the list
            actualValidMoves.add(move);
        }

        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_GAME) System.out.println("(ChessGame) Valid moves for " + chessPiece + "-> " + actualValidMoves);
        return actualValidMoves;
    }


    /**
     * Makes a move in a chess game
     * DOES NOT restore the board to its original state if a move puts the king in check
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        System.out.println("makeMove:");
        chessBoard.printBoard();
        // Save starting, ending position, and promotion piece
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece.PieceType promotionType = move.getPromotionPieceType();

        // Get a list of valid moves
        Collection<ChessMove> validMoves = validMoves(startPosition);
        // If the start position or start piece is invalid, get null from validMoves() and throw error
        if (validMoves == null) throw new InvalidMoveException("ERROR: Invalid Starting Position in makeMove() OR not the Piece's Proper Turn");
        // If the move is invalid, throw error
        if (!validMoves.contains(move)) throw new InvalidMoveException("ERROR: Invalid Move in makeMove()");

        // Save essential start position information
        ChessPiece startPiece = chessBoard.getPiece(startPosition);
        TeamColor startColor = startPiece.getTeamColor();
        ChessPiece.PieceType startType = startPiece.getPieceType();

        // If it is not the piece's proper turn, return null
        if (startColor != gameTeamColor) throw new InvalidMoveException("ERROR: Not this piece's turn " + startPiece);

        /// Implement the move on the chessboard
        // Step #1: Add null piece to startPosition.
        chessBoard.addPiece(startPosition, null);
        // Step #2: Save piece originally at startPosition to endPosition.
        // NOTE: Check for a pawn moving because it may need to be promoted.
        // If the pawn is about to be promoted, place the promoted piece.
        if ((startType == ChessPiece.PieceType.PAWN) &&
                (endPosition.getRow() == (startColor == TeamColor.WHITE ? Constants.BOARD_MAX_ONE_INDEX : Constants.BOARD_MIN_ONE_INDEX))) {
            chessBoard.addPiece(endPosition, new ChessPiece(startColor, promotionType));
        } else {    // Simply place the start piece at the end location
            chessBoard.addPiece(endPosition, new ChessPiece(startColor, startType));
        }
        gameTeamColor = (gameTeamColor == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE);
    }

    // Check if a piece is in danger on the current board
    private boolean isInDanger(ChessPosition position, TeamColor teamColor) {
        /// Iterate over each opponent's piece in the board and determine their valid moves.
        // Iterate over the chessboard rows...
        for (int row = Constants.BOARD_MIN_ONE_INDEX; row <= Constants.BOARD_MAX_ONE_INDEX; row++) {
            // Iterate over the chessboard columns...
            for (int col = Constants.BOARD_MIN_ONE_INDEX; col <= Constants.BOARD_MAX_ONE_INDEX; col++) {
                // If a piece does not exist, skip this loop iteration
                if (chessBoard.doesNotExistPiece(row, col)) continue;
                // If the piece is the current's team color, skip this loop iteration
                if (chessBoard.doesFriendlyPieceExist(row, col, teamColor)) continue;
                // Fill a new validMoves array with valid moves
                Collection<ChessMove> validMoves = chessBoard.getPiece(row ,col).pieceMoves(chessBoard, new ChessPosition(row, col));
                // Iterate over the validMoves
                for (var move : validMoves) {
                    // If any of those valid moves include the current player's king, return true
                    if (move.getEndPosition().equals(position)) return true;
                }
            }
        }
        // Otherwise return false
        return false;
    }

    // Check if a piece is in danger on another board
    private boolean isInDanger(ChessBoard board, ChessPosition position, TeamColor teamColor) {
        /// Iterate over each opponent's piece in the board and determine their valid moves.
        // Iterate over the chessboard rows...
        for (int row = Constants.BOARD_MIN_ONE_INDEX; row <= Constants.BOARD_MAX_ONE_INDEX; row++) {
            // Iterate over the chessboard columns...
            for (int col = Constants.BOARD_MIN_ONE_INDEX; col <= Constants.BOARD_MAX_ONE_INDEX; col++) {
                // If a piece does not exist, skip this loop iteration
                if (board.doesNotExistPiece(row, col)) continue;
                // If the piece is the current's team color, skip this loop iteration
                if (board.doesFriendlyPieceExist(row, col, teamColor)) continue;
                // Fill a new validMoves array with valid moves
                Collection<ChessMove> validMoves = board.getPiece(row ,col).pieceMoves(board, new ChessPosition(row, col));
                // Iterate over the validMoves
                for (var move : validMoves) {
                    // If any of those valid moves include the current player's king, return true
                    if (move.getEndPosition().equals(position)) return true;
                }
            }
        }
        // Otherwise return false
        return false;
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
            System.out.println("Unable to find friendly King in ChessGame.isInCheck() on " + chessBoard.toString());
            return false;
        }
        // Return whether any opponent moves include the King as their last position
        return isInDanger(currentKingPosition, teamColor);
    }

    /**
     * Determines if the given team on a custom board is in check
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(ChessBoard board ,TeamColor teamColor) {
        /// Get the location of the current team's King
        ChessPosition currentKingPosition = new ChessPosition(0,0);
        boolean foundKing = false;
        // Iterate over the chessboard rows...
        for (int row = Constants.BOARD_MIN_ONE_INDEX; row <= Constants.BOARD_MAX_ONE_INDEX; row++) {
            // Iterate over the chessboard columns...
            for (int col = Constants.BOARD_MIN_ONE_INDEX; col <= Constants.BOARD_MAX_ONE_INDEX; col++) {
                // If a friendly chess piece does not exist, skip this loop iteration
                if (!board.doesFriendlyPieceExist(row, col, teamColor)) continue;
                // If the piece is the current king, save its position
                if (board.getPiece(row, col).getPieceType() == ChessPiece.PieceType.KING) {
                    currentKingPosition = new ChessPosition(row, col);
                    foundKing = true;
                }
            }
        }
        // If there is no friendly king, return false
        if (!foundKing) {
            System.out.println("Unable to find friendly King in ChessGame.isInCheck():");
            board.printBoard();
            return false;
        }
        // Return whether or not any opponent moves include the King as their last position
        return isInDanger(board, currentKingPosition, teamColor);
    }

    /**
     * Determines if the given team is in checkmate
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // If the team is not in check, return false
        if (!isInCheck(teamColor)) return false;

        /// Return true if there are no possible valid moves for the friendly team
        // Iterate over the chessboard rows...
        for (int row = Constants.BOARD_MIN_ONE_INDEX; row <= Constants.BOARD_MAX_ONE_INDEX; row++) {
            // Iterate over the chessboard columns...
            for (int col = Constants.BOARD_MIN_ONE_INDEX; col <= Constants.BOARD_MAX_ONE_INDEX; col++) {
                // If a friendly chess piece exists,
                if (chessBoard.doesFriendlyPieceExist(row, col, teamColor)) {
                    Collection<ChessMove> validMoves = validMoves(new ChessPosition(row, col));
                    // If validMoves is null, continue
                    if (validMoves == null) continue;
                    // If validMoves is NOT empty, return false
                    if (!validMoves.isEmpty()) return false;
                };
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // If the team is in check, return false
        if (isInCheck(teamColor)) return false;
        /// Return true if there are no possible valid moves for the friendly team
        // Iterate over the chessboard rows...
        for (int row = Constants.BOARD_MIN_ONE_INDEX; row <= Constants.BOARD_MAX_ONE_INDEX; row++) {
            // Iterate over the chessboard columns...
            for (int col = Constants.BOARD_MIN_ONE_INDEX; col <= Constants.BOARD_MAX_ONE_INDEX; col++) {
                // If a friendly chess piece exists,
                if (chessBoard.doesFriendlyPieceExist(row, col, teamColor)) {
                    Collection<ChessMove> validMoves = validMoves(new ChessPosition(row, col));
                    // If validMoves is null, continue
                    if (validMoves == null) continue;
                    // If validMoves is NOT empty, return false
                    if (!validMoves.isEmpty()) return false;
                };
            }
        }
        return true;
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
                }
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
        return gameTeamColor == chessGame.gameTeamColor && Objects.equals(chessBoard, chessGame.chessBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameTeamColor, chessBoard);
    }

    @Override
    public String toString() {
        return "Game{" +
                "Color=" + gameTeamColor +
                ",Board=" + chessBoard +
                '}';
    }
}
