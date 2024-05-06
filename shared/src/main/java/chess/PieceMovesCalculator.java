package chess;

import java.util.Collection;

public interface PieceMovesCalculator {
    // Calculate the possible moves for a specific chess piece
    Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position);
}
