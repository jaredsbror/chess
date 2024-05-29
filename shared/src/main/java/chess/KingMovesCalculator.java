package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class KingMovesCalculator implements PieceMovesCalculator {

    // Constructor
    public KingMovesCalculator(ChessGame.TeamColor currentTeamColor) {
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
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_KING_MOVES_CALCULATOR) System.out.println("Creating " + this);
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
    private boolean isWithinBounds() {
        return (possibleRow >= Constants.BOARD_MIN_ONE_INDEX && possibleRow <= Constants.BOARD_MAX_ONE_INDEX && possibleColumn >= Constants.BOARD_MIN_ONE_INDEX && possibleColumn <= Constants.BOARD_MAX_ONE_INDEX);
    }

    // Frequently used check in the method pieceMoves
    private boolean isMoveValid(ChessBoard board) {
        // Check if it is within bounds
        if (isWithinBounds()) {
            // Check if there is not a friendly piece there
            return !board.doesFriendlyPieceExist(possibleRow, possibleColumn, currentTeamColor);
        }
        return false;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        // Collection to hold resulting valid moves
        validMoves = new ArrayList<>();
        // Various row and column variables for the current player
        currentRow = position.getRow();
        currentColumn = position.getColumn();

        /// Check all the possible movement spots (8 around the king)
       for (possibleRow = currentRow - 1; possibleRow <= currentRow + 1; possibleRow++) {
           for (possibleColumn = currentColumn - 1; possibleColumn <= currentColumn + 1; possibleColumn++) {
               // If the position is valid, add it to the output collection of valid moves
               if (isMoveValid(board)) {
                   validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), null));
               }
           }
       }
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_KING_MOVES_CALCULATOR) System.out.println("(KingCalculator) Found moves from (" + position.getRow() + "," + position.getColumn() + ") for " + board.getPiece(position).toString() + ":-> " + validMoves.toString());
        return validMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KingMovesCalculator that = (KingMovesCalculator) o;
        return currentRow == that.currentRow && currentColumn == that.currentColumn && Objects.equals(validMoves, that.validMoves) && currentTeamColor == that.currentTeamColor && opponentTeamColor == that.opponentTeamColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(validMoves, currentTeamColor, opponentTeamColor, currentRow, currentColumn);
    }

    @Override
    public String toString() {
        return "KingMovesCalculator(" +
                "ValidMoves[" + validMoves +
                "], Team(" + currentTeamColor +
                "), Opponent(" + opponentTeamColor +
                "), Row(" + currentRow +
                "), Column(" + currentColumn +
                "))";
    }
}
