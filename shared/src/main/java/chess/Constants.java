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
    int BOARD_MIN_ONE_INDEX = 1;
    int BOARD_MAX_ONE_INDEX = 8;
    int BOARD_MIN_ZERO_INDEX = 1;
    int BOARD_MAX_ZERO_INDEX = 8;


    // Default column positions
    int ROOK_COL_1 = 0;
    int KNIGHT_COL_1 = 1;
    int BISHOP_COL_1 = 2;
    int QUEEN_COL = 3;
    int KING_COL = 4;
    int BISHOP_COL_2 = 5;
    int KNIGHT_COL_2 = 6;
    int ROOK_COL_2 = 7;

    // Default black piece rows
    int BLACK_NON_PAWN_ROW = 7;
    int BLACK_PAWN_ROW = 6;

    // Default white piece rows
    int WHITE_PAWN_ROW = 1;
    int WHITE_NON_PAWN_ROW = 0;
}
