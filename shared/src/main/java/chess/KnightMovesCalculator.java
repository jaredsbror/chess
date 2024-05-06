package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMovesCalculator implements PieceMovesCalculator {

    // Constructor
    public KnightMovesCalculator() {
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
        return List.of();
    }
}
