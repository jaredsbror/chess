package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    // Variables
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType pieceType;

    // Constructor
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.pieceType = type;
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_PIECE) System.out.println("Creating Chess" + this.toString());
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    // Set team color
    public void setTeamColor(ChessGame.TeamColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    // Set piece type
    public void setPieceType(ChessPiece.PieceType type) {
        this.pieceType = type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();
        // Check if a piece exists...
        if (board.doesNotExistPiece(myPosition)) {
            return validMoves;
        };

        // Get the piece type that is moving
        switch (pieceType) {
            case KING:
                KingMovesCalculator kingMovesCalculator = new KingMovesCalculator(pieceColor);
                validMoves = kingMovesCalculator.pieceMoves(board, myPosition);
                break;
            case QUEEN:
                QueenMovesCalculator queenMovesCalculator = new QueenMovesCalculator(pieceColor);
                validMoves = queenMovesCalculator.pieceMoves(board, myPosition);
                break;
            case BISHOP:
                BishopMovesCalculator bishopMovesCalculator = new BishopMovesCalculator(pieceColor);
                validMoves = bishopMovesCalculator.pieceMoves(board, myPosition);
                break;
            case KNIGHT:
                KnightMovesCalculator knightMovesCalculator = new KnightMovesCalculator(pieceColor);
                validMoves = knightMovesCalculator.pieceMoves(board, myPosition);
                break;
            case ROOK:
                RookMovesCalculator rookMovesCalculator = new RookMovesCalculator(pieceColor);
                validMoves = rookMovesCalculator.pieceMoves(board, myPosition);
                break;
            case PAWN:
                PawnMovesCalculator pawnMovesCalculator = new PawnMovesCalculator(pieceColor);
                validMoves = pawnMovesCalculator.pieceMoves(board, myPosition);
                break;
            default:
                break;
        }
        // Optional debug
        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_PIECE) System.out.println("(ChessPiece) Evaluated moves for " + board.getPiece(myPosition).toString() + "-> " + validMoves.toString());
        return validMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return pieceColor == that.pieceColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, pieceType);
    }

    @Override
    public String toString() {
        return "Piece[" +
                pieceColor + " " +
                pieceType + ']';
    }
}
