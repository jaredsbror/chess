package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

        /// Check four possible move directions
        // Move #1: row++, column
        possibleColumn = currentColumn;
        // Iterate through the possible rows
        for (possibleRow = currentRow + 1; possibleRow <= Constants.BOARD_MAX_COLUMN_INDEX; possibleRow++) {
            // If the position is out of bounds, break without adding a valid move
            if (!isWithinBounds()) {
                break;
            }
            // The position is valid, add it to the output collection of valid moves
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
            // If there is a piece there, break out of the loop
            if (board.doesPieceExist(possibleRow, possibleColumn)) {
                break;
            }
        }

        // Move #2: row, column++
        possibleRow = currentRow;
        // Iterate through the possible columns
        for (possibleColumn = currentColumn + 1; possibleColumn <= Constants.BOARD_MAX_COLUMN_INDEX; possibleColumn++) {
            // If the position is out of bounds, break without adding a valid move
            if (!isWithinBounds()) {
                break;
            }
            // The position is valid, add it to the output collection of valid moves
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
            // If there is a piece there, break out of the loop
            if (board.doesPieceExist(possibleRow, possibleColumn)) {
                break;
            }
        }

        // Move #3: row--, column
        possibleColumn = currentColumn;
        // Iterate through the possible rows
        for (possibleRow = currentRow - 1; possibleRow >= 0; possibleRow--) {
            // If the position is out of bounds, break without adding a valid move
            if (!isWithinBounds()) {
                break;
            }
            // The position is valid, add it to the output collection of valid moves
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
            // If there is a piece there, break out of the loop
            if (board.doesPieceExist(possibleRow, possibleColumn)) {
                break;
            }
        }

        // Move #4: row, column--
        possibleRow = currentRow;
        // Iterate through the possible columns
        for (possibleColumn = currentColumn - 1; possibleColumn >= 0; possibleColumn--) {
            // If the position is out of bounds, break without adding a valid move
            if (!isWithinBounds()) {
                break;
            }
            // The position is valid, add it to the output collection of valid moves
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
            // If there is a piece there, break out of the loop
            if (board.doesPieceExist(possibleRow, possibleColumn)) {
                break;
            }
        }

        return validMoves;
    }
}