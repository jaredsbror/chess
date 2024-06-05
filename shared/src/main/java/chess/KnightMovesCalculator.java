package chess;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class KnightMovesCalculator implements PieceMovesCalculator {

    // Knight
    // Collection to hold resulting valid moves
    private List<ChessMove> validMoves;
    // Knight
    // Current and opponent team color
    private final ChessGame.TeamColor currentTeamColor;
    private final ChessGame.TeamColor opponentTeamColor;
    // Knight
    // Various row and column variables for the current player
    private int currentRow;
    private int currentColumn;
    private int possibleRow;
    private int possibleColumn;
    // Constructor
    public KnightMovesCalculator( ChessGame.TeamColor currentTeamColor ) {
        // Collection to hold resulting valid moves
        validMoves = new ArrayList<>();
        // Current and opponent team color
        this.currentTeamColor = currentTeamColor;
        opponentTeamColor = ( currentTeamColor == ChessGame.TeamColor.WHITE ? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE );
        // Various row and column variables for the current player
        currentRow = 0;
        currentColumn = 0;
        possibleRow = 0;
        possibleColumn = 0;
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_KNIGHT_MOVES_CALCULATOR )
            System.out.println( "Creating " + this );
    }


    // Knight
    // Check if a position is on the board
    private boolean isWithinBounds() {
        return ( possibleRow >= Constants.BOARD_MIN_ONE_INDEX && possibleRow <= Constants.BOARD_MAX_ONE_INDEX && possibleColumn >= Constants.BOARD_MIN_ONE_INDEX && possibleColumn <= Constants.BOARD_MAX_ONE_INDEX );
    }


    // Knight
    @Override
    public Collection<ChessMove> pieceMoves( ChessBoard board, ChessPosition position ) {
        // Collection to hold resulting valid moves
        validMoves = new ArrayList<>();
        // Various row and column variables for the current player
        currentRow = position.getRow();
        currentColumn = position.getColumn();

        /// Check eight possible L-shaped moves
        // Move #1 Row + 2, Column - 1
        possibleRow = currentRow + 2;
        possibleColumn = currentColumn - 1;
        // If the position is valid, add it to the output collection of valid moves
        if ( isWithinBounds() && !board.doesFriendlyPieceExist( possibleRow, possibleColumn, currentTeamColor ) ) {
            validMoves.add( new ChessMove( position, new ChessPosition( possibleRow, possibleColumn ), null ) );
        }

        // Move #2 Row + 2, Column + 1
        possibleRow = currentRow + 2;
        possibleColumn = currentColumn + 1;
        // If the position is valid, add it to the output collection of valid moves
        if ( isWithinBounds() && !board.doesFriendlyPieceExist( possibleRow, possibleColumn, currentTeamColor ) ) {
            validMoves.add( new ChessMove( position, new ChessPosition( possibleRow, possibleColumn ), null ) );
        }

        // Move #3 Row + 1, Column - 2
        possibleRow = currentRow + 1;
        possibleColumn = currentColumn - 2;
        // If the position is valid, add it to the output collection of valid moves
        if ( isWithinBounds() && !board.doesFriendlyPieceExist( possibleRow, possibleColumn, currentTeamColor ) ) {
            validMoves.add( new ChessMove( position, new ChessPosition( possibleRow, possibleColumn ), null ) );
        }

        // Move #4 Row + 1, Column + 2
        possibleRow = currentRow + 1;
        possibleColumn = currentColumn + 2;
        // If the position is valid, add it to the output collection of valid moves
        if ( isWithinBounds() && !board.doesFriendlyPieceExist( possibleRow, possibleColumn, currentTeamColor ) ) {
            validMoves.add( new ChessMove( position, new ChessPosition( possibleRow, possibleColumn ), null ) );
        }

        // Move #5 Row - 1, Column - 2
        possibleRow = currentRow - 1;
        possibleColumn = currentColumn - 2;
        // If the position is valid, add it to the output collection of valid moves
        if ( isWithinBounds() && !board.doesFriendlyPieceExist( possibleRow, possibleColumn, currentTeamColor ) ) {
            validMoves.add( new ChessMove( position, new ChessPosition( possibleRow, possibleColumn ), null ) );
        }

        // Move #6 Row - 1, Column + 2
        possibleRow = currentRow - 1;
        possibleColumn = currentColumn + 2;
        // If the position is valid, add it to the output collection of valid moves
        if ( isWithinBounds() && !board.doesFriendlyPieceExist( possibleRow, possibleColumn, currentTeamColor ) ) {
            validMoves.add( new ChessMove( position, new ChessPosition( possibleRow, possibleColumn ), null ) );
        }

        // Move #7 Row - 2, Column - 1
        possibleRow = currentRow - 2;
        possibleColumn = currentColumn - 1;
        // If the position is valid, add it to the output collection of valid moves
        if ( isWithinBounds() && !board.doesFriendlyPieceExist( possibleRow, possibleColumn, currentTeamColor ) ) {
            validMoves.add( new ChessMove( position, new ChessPosition( possibleRow, possibleColumn ), null ) );
        }

        // Move #8 Row - 2, Column + 1
        possibleRow = currentRow - 2;
        possibleColumn = currentColumn + 1;
        // If the position is valid, add it to the output collection of valid moves
        if ( isWithinBounds() && !board.doesFriendlyPieceExist( possibleRow, possibleColumn, currentTeamColor ) ) {
            validMoves.add( new ChessMove( position, new ChessPosition( possibleRow, possibleColumn ), null ) );
        }
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_KNIGHT_MOVES_CALCULATOR )
            System.out.println( "(KnightCalculator) Found moves from (" + position.getRow() + "," + position.getColumn() + ") for " + board.getPiece( position ).toString() + ":-> " + validMoves.toString() );
        return validMoves;
    }


    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        KnightMovesCalculator that = (KnightMovesCalculator) o;
        return currentRow == that.currentRow && currentColumn == that.currentColumn && Objects.equals( validMoves, that.validMoves ) && currentTeamColor == that.currentTeamColor && opponentTeamColor == that.opponentTeamColor;
    }


    @Override
    public int hashCode() {
        return Objects.hash( validMoves, currentTeamColor, opponentTeamColor, currentRow, currentColumn );
    }


    @Override
    public String toString() {
        return "KnightMovesCalculator(" +
                "ValidMoves[" + validMoves +
                "], Team(" + currentTeamColor +
                "), Opponent(" + opponentTeamColor +
                "), Row(" + currentRow +
                "), Column(" + currentColumn +
                "))";
    }
}
