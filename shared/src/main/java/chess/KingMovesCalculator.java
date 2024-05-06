package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMovesCalculator implements PieceMovesCalculator {

    // Frequently used check in the method pieceMoves
    private boolean validityCheck() {

    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {

        // Various row and column variables for the opponent king
        int opponentKingRow = 0;
        int opponentKingColumn = 0;
        int opponentKingRowMin = 0;
        int opponentKingRowMax = 0;
        int opponentKingColMin = 0;
        int opponentKingColMax = 0;

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
        List<ChessMove> validMoves = new ArrayList<>();
        // Various row and column variables for the current player
        int currentRow = position.getRow();
        int currentColumn = position.getColumn();
        int possibleRow = 0;
        int possibleColumn = 0;

        /// Check all the possible movement spots (8 around the king)
        possibleRow = currentRow + 1; // Used for spots 1-3
        // Check for a valid row index
        if (possibleRow >= 0 && possibleRow <= 7) {
            // Spot #1 (row + 1, column - 1)
            possibleColumn = currentColumn - 1;
            // If the position is valid, add it to the output collection of valid moves
            if (possibleColumn >= 0 && possibleColumn <= 7 && !(possibleRow >= opponentKingRowMin && possibleRow <= opponentKingRowMax && possibleColumn >= opponentKingColMin && possibleColumn <= opponentKingColMax)) {
                validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
            }
            // Spot #2 (row + 1, column)
            possibleColumn = currentColumn;
            // If the position is valid, add it to the output collection of valid moves
            if (possibleColumn >= 0 && possibleColumn <= 7 && !(possibleRow >= opponentKingRowMin && possibleRow <= opponentKingRowMax && possibleColumn >= opponentKingColMin && possibleColumn <= opponentKingColMax)) {
                validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
            }
            // Spot #3 (row + 1, column + 1)
            possibleColumn = currentColumn + 1;
            // If the position is valid, add it to the output collection of valid moves
            if (possibleColumn >= 0 && possibleColumn <= 7 && !(possibleRow >= opponentKingRowMin && possibleRow <= opponentKingRowMax && possibleColumn >= opponentKingColMin && possibleColumn <= opponentKingColMax)) {
                validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
            }
        }


        possibleRow = currentRow; // Used for spots 4-5
        // Check for a valid row index
        if (possibleRow >= 0 && possibleRow <= 7) {
            // Spot #4 (row, column - 1)
            possibleColumn = currentColumn - 1;
            // If the position is valid, add it to the output collection of valid moves
            if (possibleColumn >= 0 && possibleColumn <= 7 && !(possibleRow >= opponentKingRowMin && possibleRow <= opponentKingRowMax && possibleColumn >= opponentKingColMin && possibleColumn <= opponentKingColMax)) {
                validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
            }
            // Spot #5 (row, column + 1)
            possibleColumn = currentColumn + 1;
            // If the position is valid, add it to the output collection of valid moves
            if (possibleColumn >= 0 && possibleColumn <= 7 && !(possibleRow >= opponentKingRowMin && possibleRow <= opponentKingRowMax && possibleColumn >= opponentKingColMin && possibleColumn <= opponentKingColMax)) {
                validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
            }
        }


        possibleRow = currentRow - 1;  // Used for spots 6-8
        // Check for a valid row index
        if (possibleRow >= 0 && possibleRow <= 7) {
            // Spot #6 (row - 1, column - 1)
            possibleColumn = currentColumn - 1;
            // If the position is valid, add it to the output collection of valid moves
            if (possibleColumn >= 0 && possibleColumn <= 7 && !(possibleRow >= opponentKingRowMin && possibleRow <= opponentKingRowMax && possibleColumn >= opponentKingColMin && possibleColumn <= opponentKingColMax)) {
                validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
            }
            // Spot #7 (row - 1, column)
            possibleColumn = currentColumn;
            // If the position is valid, add it to the output collection of valid moves
            if (possibleColumn >= 0 && possibleColumn <= 7 && !(possibleRow >= opponentKingRowMin && possibleRow <= opponentKingRowMax && possibleColumn >= opponentKingColMin && possibleColumn <= opponentKingColMax)) {
                validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
            }
            // Spot #8 (row - 1, column + 1)
            possibleColumn = currentColumn + 1;
            // If the position is valid, add it to the output collection of valid moves
            if (possibleColumn >= 0 && possibleColumn <= 7 && !(possibleRow >= opponentKingRowMin && possibleRow <= opponentKingRowMax && possibleColumn >= opponentKingColMin && possibleColumn <= opponentKingColMax)) {
                validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
            }
        }

        return validMoves;
    }
}
