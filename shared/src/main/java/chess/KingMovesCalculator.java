package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMovesCalculator implements PieceMovesCalculator {

    // Constructor
    public KingMovesCalculator() {
        // Collection to hold resulting valid moves
        validMoves = new ArrayList<>();
        // Various row and column variables for the current player
        currentRow = 0;
        currentColumn = 0;
        possibleRow = 0;
        possibleColumn = 0;
        // Various row and column variables for the opponent king
        opponentKingRow = 0;
        opponentKingColumn = 0;
        opponentKingRowMin = 0;
        opponentKingRowMax = 0;
        opponentKingColMin = 0;
        opponentKingColMax = 0;

    }

    // Collection to hold resulting valid moves
    private List<ChessMove> validMoves;
    // Various row and column variables for the current player
    private int currentRow;
    private int currentColumn;
    private int possibleRow;
    private int possibleColumn;
    // Various row and column variables for the opponent king
    private int opponentKingRow;
    private int opponentKingColumn;
    private int opponentKingRowMin;
    private int opponentKingRowMax;
    private int opponentKingColMin;
    private int opponentKingColMax;

    // Frequently used check in the method pieceMoves
    private boolean isMoveValid() {
        return (possibleRow >= 0 && possibleRow <= Constants.BOARD_MAX_ROW_INDEX && possibleColumn >= 0 && possibleColumn <= Constants.BOARD_MAX_COLUMN_INDEX && !(possibleRow >= opponentKingRowMin && possibleRow <= opponentKingRowMax && possibleColumn >= opponentKingColMin && possibleColumn <= opponentKingColMax));
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {

        // Various row and column variables for the opponent king
        opponentKingRow = 0;
        opponentKingColumn = 0;
        opponentKingRowMin = 0;
        opponentKingRowMax = 0;
        opponentKingColMin = 0;
        opponentKingColMax = 0;

        /// Iterate over the board and detect where the opponent's king is.
        /// This will be used to make sure the current player's king's move to a spot is valid
        outerLoop:
        // Iterate over board rows
        for (int row = 0; row < Constants.BOARD_NUM_ROWS; row++) {
            // Iterate over board columns
            for (int col = 0; col < Constants.BOARD_NUM_COLUMNS; col++) {
                // If the king is there, save its position
                if (board.getPiece(row, col).getPieceType() == ChessPiece.PieceType.KING) {
                    opponentKingRow = row;
                    opponentKingColumn = col;
                    break outerLoop;
                }
            }
        }

        /// Generate row and column range of forbidden squares for the king to travel
        opponentKingRowMin = opponentKingRow - 1;
        opponentKingRowMax = opponentKingRow + 1;
        opponentKingColMin = opponentKingColumn - 1;
        opponentKingColMax = opponentKingColumn + 1;


        // Collection to hold resulting valid moves
        validMoves = new ArrayList<>();
        // Various row and column variables for the current player
        currentRow = position.getRow();
        currentColumn = position.getColumn();

        /// Check all the possible movement spots (8 around the king)
        possibleRow = currentRow + 1; // Used for spots 1-3
        // Spot #1 (row + 1, column - 1)
        possibleColumn = currentColumn - 1;
        // If the position is valid, add it to the output collection of valid moves
        if (isMoveValid()) {
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
        }
        // Spot #2 (row + 1, column)
        possibleColumn = currentColumn;
        // If the position is valid, add it to the output collection of valid moves
        if (isMoveValid()) {
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
        }
        // Spot #3 (row + 1, column + 1)
        possibleColumn = currentColumn + 1;
        // If the position is valid, add it to the output collection of valid moves
        if (isMoveValid()) {
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
        }


        possibleRow = currentRow; // Used for spots 4-5
        // Spot #4 (row, column - 1)
        possibleColumn = currentColumn - 1;
        // If the position is valid, add it to the output collection of valid moves
        if (isMoveValid()) {
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
        }
        // Spot #5 (row, column + 1)
        possibleColumn = currentColumn + 1;
        // If the position is valid, add it to the output collection of valid moves
        if (isMoveValid()) {
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
        }


        possibleRow = currentRow - 1;  // Used for spots 6-8
        // Spot #6 (row - 1, column - 1)
        possibleColumn = currentColumn - 1;
        // If the position is valid, add it to the output collection of valid moves
        if (isMoveValid()) {
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
        }
        // Spot #7 (row - 1, column)
        possibleColumn = currentColumn;
        // If the position is valid, add it to the output collection of valid moves
        if (isMoveValid()) {
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
        }
        // Spot #8 (row - 1, column + 1)
        possibleColumn = currentColumn + 1;
        // If the position is valid, add it to the output collection of valid moves
        if (isMoveValid()) {
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
        }

        return validMoves;
    }
}
