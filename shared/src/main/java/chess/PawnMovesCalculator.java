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
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_PAWN_MOVES_CALCULATOR) System.out.println("Creating " + this);
    }

    // Pawn
    // Collection to hold resulting valid moves
    private List<ChessMove> validMoves;
    // Pawn
    // Current and opponent team color
    private ChessGame.TeamColor currentTeamColor;
    private ChessGame.TeamColor opponentTeamColor;
    // Pawn
    // Various row and column variables for the current player
    private int currentRow;
    private int currentColumn;
    private int possibleRow;
    private int possibleColumn;

    // Pawn
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        // Collection to hold resulting valid moves
        validMoves = new ArrayList<>();
        // Various row and column variables for the current player
        currentRow = position.getRow();
        currentColumn = position.getColumn();

        /// Move pawn depending on the current team color
        // If the current team color is white
        if (currentTeamColor == ChessGame.TeamColor.WHITE) {
            /// Check three possible moves
            // Move #1: row + 1, column - 1
            possibleRow = currentRow + 1;
            possibleColumn = currentColumn - 1;
            // If the position is valid, add it to the output collection of valid moves
            if (isWithinBounds() && board.doesOpponentPieceExist(possibleRow, possibleColumn, opponentTeamColor)) {
                processValidMoves(board, position, ChessGame.TeamColor.WHITE);
            }

            // Move #2: row + 1, column
            possibleColumn = currentColumn;
            // If the position is valid, add it to the output collection of valid moves
            if (isWithinBounds() && board.doesNotExistPiece(possibleRow, possibleColumn)) {
                processValidMoves(board, position, ChessGame.TeamColor.WHITE);
            }

            // Move #3: row + 1, column + 1
            possibleColumn = currentColumn + 1;
            // If the position is valid, add it to the output collection of valid moves
            if (isWithinBounds() && board.doesOpponentPieceExist(possibleRow, possibleColumn, opponentTeamColor)) {
                processValidMoves(board, position, ChessGame.TeamColor.WHITE);
            }
        } else {    // Current team color is black
            /// Check three possible moves
            // Move #1: row - 1, column - 1
            possibleRow = currentRow - 1;
            possibleColumn = currentColumn - 1;
            // If the position is valid, add it to the output collection of valid moves
            if (isWithinBounds() && board.doesOpponentPieceExist(possibleRow, possibleColumn, opponentTeamColor)) {
                processValidMoves(board, position, ChessGame.TeamColor.BLACK);
            }

            // Move #2: row - 1, column
            possibleColumn = currentColumn;
            // If the position is valid, add it to the output collection of valid moves
            if (isWithinBounds() && board.doesNotExistPiece(possibleRow, possibleColumn)) {
                processValidMoves(board, position, ChessGame.TeamColor.BLACK);
            }

            // Move #3: row - 1, column + 1
            possibleColumn = currentColumn + 1;
            // If the position is valid, add it to the output collection of valid moves
            if (isWithinBounds() && board.doesOpponentPieceExist(possibleRow, possibleColumn, opponentTeamColor)) {
                processValidMoves(board, position, ChessGame.TeamColor.BLACK);
            }
        }
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_PAWN_MOVES_CALCULATOR) System.out.println("(PawnCalculator) Found moves from (" + position.getRow() + "," + position.getColumn() + ") for " + board.getPiece(position).toString() + ":-> " + validMoves.toString());
        return validMoves;
    }

    // Pawn
    // Check if a position is on the board
    private boolean isWithinBounds() {
        return (possibleRow >= Constants.BOARD_MIN_ONE_INDEX && possibleRow <= Constants.BOARD_MAX_ONE_INDEX && possibleColumn >= Constants.BOARD_MIN_ONE_INDEX && possibleColumn <= Constants.BOARD_MAX_ONE_INDEX);
    }

    // Assuming
    public void processValidMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_PAWN_MOVES_CALCULATOR) System.out.println("Process valid moves");
        // Case #1 - Pawn is up for promotion
        if (possibleRow == (color == ChessGame.TeamColor.WHITE ? Constants.BOARD_MAX_ONE_INDEX : Constants.BOARD_MIN_ONE_INDEX)) {
            // Add all variants of promotions to default move
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.QUEEN));
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.BISHOP));
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.ROOK));
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), ChessPiece.PieceType.KNIGHT));
        }
        // Case #2 - Pawn initial move of 2 spaces
        else if (currentRow == ((color == ChessGame.TeamColor.WHITE ? Constants.WHITE_PAWN_ROW : Constants.BLACK_PAWN_ROW) + 1) && currentColumn == possibleColumn) {
            // Add default chess move
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), null));
            // Test extra move of one space
            possibleRow = (color == ChessGame.TeamColor.WHITE ? possibleRow + 1 : possibleRow - 1);
            // If the position is valid, add it to the output collection of valid moves
            if (isWithinBounds() && board.doesNotExistPiece(possibleRow, possibleColumn)) {
                validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), null));
            }
            // Case #3 - Default move with null promotion
        } else {
            // Add default chess move
            validMoves.add(new ChessMove(position, new ChessPosition(possibleRow, possibleColumn), null));
        }
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
        return "PawnMovesCalculator(" +
                "ValidMoves[" + validMoves +
                "], Team(" + currentTeamColor +
                "), Opponent(" + opponentTeamColor +
                "), Row(" + currentRow +
                "), Column(" + currentColumn +
                "))";
    }
}
