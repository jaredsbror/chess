package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMovesCalculator implements PieceMovesCalculator {

    public PawnMovesCalculator() {
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
        return (possibleRow >= 0 && possibleRow <= 7 && possibleColumn >= 0 && possibleColumn <= 7);
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
        if (isWithinBounds()) {
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
        if (isWithinBounds()) {
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
        }

        return validMoves;
    }
}
