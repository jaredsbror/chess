package chess;


/*
KING,
QUEEN,
BISHOP,
KNIGHT,
ROOK,
PAWN
 */

public interface Constants {
    // Board dimensions
    int BOARD_NUM_ROWS = 8;
    int BOARD_NUM_COLUMNS = 8;

    // Default black piece locations
    int BLACK_KING_ROW = 7;
    int BLACK_KING_COL = 4;
    int BLACK_QUEEN_ROW = 7;
    int BLACK_QUEEN_COL = 3;
    int BLACK_BISHOP_ROW = 7;
    int BLACK_BISHOP_COL_1 = 2;
    int BLACK_BISHOP_COL_2 = 5;
    int BLACK_KNIGHT_ROW = 7;
    int BLACK_KNIGHT_COL_1 = 1;
    int BLACK_KNIGHT_COL_2 = 6;
    int BLACK_ROOK_ROW = 7;
    int BLACK_ROOK_COL_1 = 0;
    int BLACK_ROOK_COL_2 = 7;
    int BLACK_PAWN_ROW = 6;
    int BLACK_PAWN_START_COL = 0;
    int BLACK_PAWN_END_COL = 7;

    // Default white piece locations
    int WHITE_KING_ROW = 0;
    int WHITE_KING_COL = 4;
    int WHITE_QUEEN_ROW = 0;
    int WHITE_QUEEN_COL = 3;
    int WHITE_BISHOP_ROW = 0;
    int WHITE_BISHOP_COL_1 = 2;
    int WHITE_BISHOP_COL_2 = 5;
    int WHITE_KNIGHT_ROW = 0;
    int WHITE_KNIGHT_COL_1 = 1;
    int WHITE_KNIGHT_COL_2 = 6;
    int WHITE_ROOK_ROW = 0;
    int WHITE_ROOK_COL_1 = 0;
    int WHITE_ROOK_COL_2 = 7;
    int WHITE_PAWN_ROW = 1;
    int WHITE_PAWN_START_COL = 0;
    int WHITE_PAWN_END_COL = 7;
}
