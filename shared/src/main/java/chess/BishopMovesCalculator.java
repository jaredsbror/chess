package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalculator implements PieceMovesCalculator {

    // Constructor
    public BishopMovesCalculator() {
        // Collection to hold resulting valid moves
        validMoves = new ArrayList<>();
        // Various row and column variables for the current player
        currentRow = 0;
        currentColumn = 0;
        possibleRow = 0;
        possibleColumn = 0;
    }

    // Collection to hold resulting valid moves
    private List<ChessMove> validMoves;
    // Various row and column variables for the current player
    private int currentRow;
    private int currentColumn;
    private int possibleRow;
    private int possibleColumn;

    // Check if a position is on the board
    private boolean isWithinBounds() {
        return (possibleRow >= 0 && possibleRow <= Constants.BOARD_MAX_ROW_INDEX && possibleColumn >= 0 && possibleColumn <= Constants.BOARD_MAX_COLUMN_INDEX);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        // Collection to hold resulting valid moves
        validMoves = new ArrayList<>();

        /// Check four possible move directions
        // Move #1: row++, column--
        possibleColumn = currentColumn - 1;
        possibleRow = currentRow + 1;
        //
        while (isWithinBounds()) {

        }

        // Move #2: row++, column++
        possibleRow = currentRow;
        // Iterate through the possible columns

        // Move #3: row--, column++
        possibleColumn = currentColumn;

        // Move #4: row--, column--
        possibleRow = currentRow;

        return validMoves;
    }
}
