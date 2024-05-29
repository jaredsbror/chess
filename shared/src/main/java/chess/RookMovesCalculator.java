package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class RookMovesCalculator implements PieceMovesCalculator {

    // Constructor
    public RookMovesCalculator(ChessGame.TeamColor currentTeamColor) {
        // Collection to hold resulting valid moves
        validMoves = new ArrayList<>();
        // Current and opponent team color
        this.currentTeamColor = currentTeamColor;
        opponentTeamColor = (currentTeamColor == ChessGame.TeamColor.WHITE ? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE);
        // Various row and column variables for the current player
        currentRow = 0;
        currentColumn = 0;
        possibleRow = 0;
        possibleColumn = 0;
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_ROOK_MOVES_CALCULATOR) System.out.println("Creating " + this);
    }

    // Collection to hold resulting valid moves
    private List<ChessMove> validMoves;
    // Current and opponent team color
    private ChessGame.TeamColor currentTeamColor;
    private ChessGame.TeamColor opponentTeamColor;
    // Various row and column variables for the current player
    private int currentRow;
    private int currentColumn;
    private int possibleRow;
    private int possibleColumn;

    // Check if a position is on the board
    private boolean isNotWithinBounds() {
        return (possibleRow < Constants.BOARD_MIN_ONE_INDEX || possibleRow > Constants.BOARD_MAX_ONE_INDEX || possibleColumn < Constants.BOARD_MIN_ONE_INDEX || possibleColumn > Constants.BOARD_MAX_ONE_INDEX);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        // Collection to hold resulting valid moves
        validMoves = new ArrayList<>();
        // Various row and column variables for the current player
        currentRow = position.getRow();
        currentColumn = position.getColumn();

        /// Check four possible move directions
        // Move #1: row++, column
        possibleColumn = currentColumn;
        // Iterate through the possible rows
        for (possibleRow = currentRow + 1; possibleRow <= Constants.BOARD_MAX_ONE_INDEX; possibleRow++) {
            // If the position is out of bounds, break without adding a valid move
            if (isNotWithinBounds()) {
                break;
            }
            // If there is a friendly piece there, break out of the loop before adding a valid move
            else if (board.doesFriendlyPieceExist(possibleRow, possibleColumn, currentTeamColor)) {
                break;
            }
            // The position is valid, add it to the output collection of valid moves
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), null));
            // If there is an opponent's piece there, break out of the loop after adding a valid move
            if (board.doesOpponentPieceExist(possibleRow, possibleColumn, opponentTeamColor)) {
                break;
            }
        }

        // Move #2: row, column++
        possibleRow = currentRow;
        // Iterate through the possible columns
        for (possibleColumn = currentColumn + 1; possibleColumn <= Constants.BOARD_MAX_ONE_INDEX; possibleColumn++) {
            // If the position is out of bounds, break without adding a valid move
            if (isNotWithinBounds()) {
                break;
            }
            // If there is a friendly piece there, break out of the loop before adding a valid move
            else if (board.doesFriendlyPieceExist(possibleRow, possibleColumn, currentTeamColor)) {
                break;
            }
            // The position is valid, add it to the output collection of valid moves
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), null));
            // If there is an opponent's piece there, break out of the loop after adding a valid move
            if (board.doesOpponentPieceExist(possibleRow, possibleColumn, opponentTeamColor)) {
                break;
            }
        }

        // Move #3: row--, column
        possibleColumn = currentColumn;
        // Iterate through the possible rows
        for (possibleRow = currentRow - 1; possibleRow >= 0; possibleRow--) {
            // If the position is out of bounds, break without adding a valid move
            if (isNotWithinBounds()) {
                break;
            }
            // If there is a friendly piece there, break out of the loop before adding a valid move
            else if (board.doesFriendlyPieceExist(possibleRow, possibleColumn, currentTeamColor)) {
                break;
            }
            // The position is valid, add it to the output collection of valid moves
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), null));
            // If there is an opponent's piece there, break out of the loop after adding a valid move
            if (board.doesOpponentPieceExist(possibleRow, possibleColumn, opponentTeamColor)) {
                break;
            }
        }

        // Move #4: row, column--
        possibleRow = currentRow;
        // Iterate through the possible columns
        for (possibleColumn = currentColumn - 1; possibleColumn >= 0; possibleColumn--) {
            // If the position is out of bounds, break without adding a valid move
            if (isNotWithinBounds()) {
                break;
            }
            // If there is a friendly piece there, break out of the loop before adding a valid move
            else if (board.doesFriendlyPieceExist(possibleRow, possibleColumn, currentTeamColor)) {
                break;
            }
            // The position is valid, add it to the output collection of valid moves
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), null));
            // If there is an opponent's piece there, break out of the loop after adding a valid move
            if (board.doesOpponentPieceExist(possibleRow, possibleColumn, opponentTeamColor)) {
                break;
            }
        }
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_ROOK_MOVES_CALCULATOR) System.out.println("(RookCalculator) Found moves from (" + position.getRow() + "," + position.getColumn() + ") for " + board.getPiece(position).toString() + ":-> " + validMoves.toString());
        return validMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RookMovesCalculator that = (RookMovesCalculator) o;
        return currentRow == that.currentRow && currentColumn == that.currentColumn && Objects.equals(validMoves, that.validMoves) && currentTeamColor == that.currentTeamColor && opponentTeamColor == that.opponentTeamColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(validMoves, currentTeamColor, opponentTeamColor, currentRow, currentColumn);
    }

    @Override
    public String toString() {
        return "RookMovesCalculator(" +
                "ValidMoves[" + validMoves +
                "], Team(" + currentTeamColor +
                "), Opponent(" + opponentTeamColor +
                "), Row(" + currentRow +
                "), Column(" + currentColumn +
                "))";
    }
}
