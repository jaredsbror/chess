package chess;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class BishopMovesCalculator implements PieceMovesCalculator {

    // Bishop
    // Collection to hold resulting valid moves
    private List<ChessMove> validMoves;
    // Bishop
    // Current and opponent team color
    private final ChessGame.TeamColor currentTeamColor;
    private final ChessGame.TeamColor opponentTeamColor;
    // Bishop
    // Various row and column variables for the current player
    private int currentRow;
    private int currentColumn;
    private int possibleRow;
    private int possibleColumn;
    // Constructor
    public BishopMovesCalculator( ChessGame.TeamColor currentTeamColor ) {
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
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_BISHOP_MOVES_CALCULATOR )
            System.out.println( "Creating " + this );
    }


    // Check if a position is on the board
    private boolean isWithinBounds() {
        return ( possibleRow >= Constants.BOARD_MIN_ONE_INDEX && possibleRow <= Constants.BOARD_MAX_ONE_INDEX && possibleColumn >= Constants.BOARD_MIN_ONE_INDEX && possibleColumn <= Constants.BOARD_MAX_ONE_INDEX );
    }


    // Method to process bishop possibleRow and possibleColumn and to avoid code duplication
    private boolean processOrBreakOutOfBishopLoop( ChessBoard board, ChessPosition position ) {
        // If there is a friendly piece there, break out of the loop before adding a valid move
        if ( board.doesFriendlyPieceExist( possibleRow, possibleColumn, currentTeamColor ) ) return true;
        // The position is valid, add it to the output collection of valid moves
        validMoves.add( new ChessMove( position, new ChessPosition( possibleRow, possibleColumn ), null ) );
        // If there is an opponent's piece there, break out of the loop after adding a valid move
        return board.doesOpponentPieceExist( possibleRow, possibleColumn, opponentTeamColor );
    }


    @Override
    public Collection<ChessMove> pieceMoves( ChessBoard board, ChessPosition position ) {
        // Collection to hold resulting valid moves
        validMoves = new ArrayList<>();
        // Various row and column variables for the current player
        currentRow = position.getRow();
        currentColumn = position.getColumn();

        /// Check four possible move directions
        // Move #1: row++, column--
        possibleRow = currentRow + 1;
        possibleColumn = currentColumn - 1;
        // Loop to check movements
        while ( isWithinBounds() ) {
            if ( processOrBreakOutOfBishopLoop( board, position ) ) break;
            possibleRow++;
            possibleColumn--;
        }

        // Move #2: row++, column++
        possibleRow = currentRow + 1;
        possibleColumn = currentColumn + 1;
        // Loop to check movements
        while ( isWithinBounds() ) {
            if ( processOrBreakOutOfBishopLoop( board, position ) ) break;
            possibleRow++;
            possibleColumn++;
        }

        // Move #3: row--, column++
        possibleRow = currentRow - 1;
        possibleColumn = currentColumn + 1;
        // Loop to check movements
        while ( isWithinBounds() ) {
            if ( processOrBreakOutOfBishopLoop( board, position ) ) break;
            possibleRow--;
            possibleColumn++;
        }

        // Move #4: row--, column--
        possibleRow = currentRow - 1;
        possibleColumn = currentColumn - 1;
        // Loop to check movements
        while ( isWithinBounds() ) {
            if ( processOrBreakOutOfBishopLoop( board, position ) ) break;
            possibleRow--;
            possibleColumn--;
        }
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_BISHOP_MOVES_CALCULATOR )
            System.out.println( "(BishopCalculator) Found moves from (" + position.getRow() + "," + position.getColumn() + ") for " + board.getPiece( position ).toString() + ":-> " + validMoves.toString() );
        return validMoves;
    }


    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        BishopMovesCalculator that = (BishopMovesCalculator) o;
        return currentRow == that.currentRow && currentColumn == that.currentColumn && Objects.equals( validMoves, that.validMoves ) && currentTeamColor == that.currentTeamColor && opponentTeamColor == that.opponentTeamColor;
    }


    @Override
    public int hashCode() {
        return Objects.hash( validMoves, currentTeamColor, opponentTeamColor, currentRow, currentColumn );
    }


    @Override
    public String toString() {
        return "BishopMovesCalculator(" +
                "ValidMoves[" + validMoves +
                "], Team(" + currentTeamColor +
                "), Opponent(" + opponentTeamColor +
                "), Row(" + currentRow +
                "), Column(" + currentColumn +
                "))";
    }
}
