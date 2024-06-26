package chess;


import java.util.Objects;


/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    // Variables
    private final ChessPosition startPosition;
    private final ChessPosition endPosition;
    private final ChessPiece.PieceType promotionPieceType;


    // Constructor
    public ChessMove( ChessPosition startPosition, ChessPosition endPosition,
                      ChessPiece.PieceType promotionPiece ) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPieceType = promotionPiece;
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_MOVE )
            System.out.println( "Creating Chess" + this.toString() );
    }


    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return startPosition;
    }


    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endPosition;
    }


    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPieceType() {
        return promotionPieceType;
    }


    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        ChessMove chessMove = (ChessMove) o;
        return Objects.equals( startPosition, chessMove.startPosition ) && Objects.equals( endPosition, chessMove.endPosition ) && promotionPieceType == chessMove.promotionPieceType;
    }


    @Override
    public int hashCode() {
        return Objects.hash( startPosition, endPosition, promotionPieceType );
    }


    @Override
    public String toString() {
        return "Move(" +
                "Start[" + startPosition +
                "], End[" + endPosition +
                "], Promotion[" + promotionPieceType +
                "])";
    }
}
