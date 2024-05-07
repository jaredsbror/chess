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

    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
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
        return type;
    }

    // Set piece type
    public void setPieceType(ChessPiece.PieceType type) {
        this.type = type;
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
        PieceType movingPieceType;
        ChessGame.TeamColor currentTeamColor;
        // Check if a piece exists...
        if (board.doesNotExistPiece(myPosition)) {
            return validMoves;
        };
        // Check the color...
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            currentTeamColor = ChessGame.TeamColor.WHITE;
            // Get the piece type that is moving
            switch (board.getPiece(myPosition).getPieceType()) {
                case KING:
                    KingMovesCalculator kingMovesCalculator = new KingMovesCalculator(currentTeamColor);
                    validMoves = kingMovesCalculator.pieceMoves(board, myPosition);
                    break;
                case QUEEN:
                    QueenMovesCalculator queenMovesCalculator = new QueenMovesCalculator(currentTeamColor);
                    validMoves = queenMovesCalculator.pieceMoves(board, myPosition);
                    break;
                case BISHOP:
                    BishopMovesCalculator bishopMovesCalculator = new BishopMovesCalculator(currentTeamColor);
                    validMoves = bishopMovesCalculator.pieceMoves(board, myPosition);
                    break;
                case KNIGHT:
                    KnightMovesCalculator knightMovesCalculator = new KnightMovesCalculator(currentTeamColor);
                    validMoves = knightMovesCalculator.pieceMoves(board, myPosition);
                    break;
                case ROOK:
                    RookMovesCalculator rookMovesCalculator = new RookMovesCalculator(currentTeamColor);
                    validMoves = rookMovesCalculator.pieceMoves(board, myPosition);
                    break;
                case PAWN:
                    PawnMovesCalculator pawnMovesCalculator = new PawnMovesCalculator(currentTeamColor);
                    validMoves = pawnMovesCalculator.pieceMoves(board, myPosition);
                    break;
                default:
                    break;
            }
        } else {    // The piece is black
            currentTeamColor = ChessGame.TeamColor.BLACK;
            // Get the piece type that is moving
            switch (board.getPiece(myPosition).getPieceType()) {
                case KING:
                    KingMovesCalculator kingMovesCalculator = new KingMovesCalculator(currentTeamColor);
                    validMoves = kingMovesCalculator.pieceMoves(board, myPosition);
                    break;
                case QUEEN:
                    QueenMovesCalculator queenMovesCalculator = new QueenMovesCalculator(currentTeamColor);
                    validMoves = queenMovesCalculator.pieceMoves(board, myPosition);
                    break;
                case BISHOP:
                    BishopMovesCalculator bishopMovesCalculator = new BishopMovesCalculator(currentTeamColor);
                    validMoves = bishopMovesCalculator.pieceMoves(board, myPosition);
                    break;
                case KNIGHT:
                    KnightMovesCalculator knightMovesCalculator = new KnightMovesCalculator(currentTeamColor);
                    validMoves = knightMovesCalculator.pieceMoves(board, myPosition);
                    break;
                case ROOK:
                    RookMovesCalculator rookMovesCalculator = new RookMovesCalculator(currentTeamColor);
                    validMoves = rookMovesCalculator.pieceMoves(board, myPosition);
                    break;
                case PAWN:
                    PawnMovesCalculator pawnMovesCalculator = new PawnMovesCalculator(currentTeamColor);
                    validMoves = pawnMovesCalculator.pieceMoves(board, myPosition);
                    break;
                default:
                    break;
            }
        }
        return validMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}
