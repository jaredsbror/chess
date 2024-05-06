package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class PawnMovesCalculator implements PieceMovesCalculator {

    // Constructor
    public PawnMovesCalculator(ChessGame.TeamColor currentTeamColor) {
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

    // Get current team color
    public ChessGame.TeamColor getCurrentTeamColor() {
        return currentTeamColor;
    }

    // Get opponent team color
    public ChessGame.TeamColor getOpponentTeamColor() {
        return opponentTeamColor;
    }

    // Set current (and opponent) team color
    public void setCurrentTeamColor(ChessGame.TeamColor currentTeamColor) {
        this.currentTeamColor = currentTeamColor;
        opponentTeamColor = (currentTeamColor == ChessGame.TeamColor.WHITE ? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE);
    }

    // Check if a position is on the board
    private boolean isWithinBounds() {
        return (possibleRow >= 0 && possibleRow <= Constants.BOARD_MAX_ROW_INDEX && possibleColumn >= 0 && possibleColumn <= Constants.BOARD_MAX_COLUMN_INDEX);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        // Collection to hold resulting valid moves
        validMoves = new ArrayList<>();
        // Various row and column variables for the current player
        currentRow = position.getRow();
        currentColumn = position.getColumn();

        /// Check three possible moves
        // Move #1: row + 1, column - 1
        possibleRow = currentRow + 1;
        possibleColumn = currentColumn - 1;
        // If the position is valid, add it to the output collection of valid moves
        if (isWithinBounds() && !board.doesFriendlyPieceExist(possibleRow, possibleColumn, currentTeamColor)) {
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
        }

        // Move #1: row + 1, column
        possibleRow = currentRow + 1;
        possibleColumn = currentColumn;
        // If the position is valid, add it to the output collection of valid moves
        if (isWithinBounds() && !board.doesPieceExist(possibleRow, possibleColumn)) {
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
        }

        // Move #1: row + 1, column + 1
        possibleRow = currentRow + 1;
        possibleColumn = currentColumn + 1;
        // If the position is valid, add it to the output collection of valid moves
        if (isWithinBounds() && !board.doesFriendlyPieceExist(possibleRow, possibleColumn, currentTeamColor)) {
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
        }

        return validMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PawnMovesCalculator that = (PawnMovesCalculator) o;
        return currentRow == that.currentRow && currentColumn == that.currentColumn && Objects.equals(validMoves, that.validMoves) && currentTeamColor == that.currentTeamColor && opponentTeamColor == that.opponentTeamColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(validMoves, currentTeamColor, opponentTeamColor, currentRow, currentColumn);
    }

    @Override
    public String toString() {
        return "PawnMovesCalculator{" +
                "validMoves=" + validMoves +
                ", currentTeamColor=" + currentTeamColor +
                ", opponentTeamColor=" + opponentTeamColor +
                ", currentRow=" + currentRow +
                ", currentColumn=" + currentColumn +
                '}';
    }
}
