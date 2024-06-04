package chess;

public interface Constants {
    /// Phase 0
    // Board dimensions
    int BOARD_NUM_ROWS = 8;
    int BOARD_NUM_COLUMNS = 8;
    int BOARD_MIN_ONE_INDEX = 1;
    int BOARD_MAX_ONE_INDEX = 8;
    int BOARD_MIN_ZERO_INDEX = 0;
    int BOARD_MAX_ZERO_INDEX = 7;

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

    // Debugging
    boolean DEBUG_GLOBAL = false;
    boolean DEBUG_CHESS_GAME = false;
    boolean DEBUG_CHESS_BOARD = false;
    boolean DEBUG_CHESS_MOVE = false;
    boolean DEBUG_CHESS_PIECE = false;
    boolean DEBUG_CHESS_POSITION = false;
    boolean DEBUG_KING_MOVES_CALCULATOR = false;
    boolean DEBUG_QUEEN_MOVES_CALCULATOR = false;
    boolean DEBUG_BISHOP_MOVES_CALCULATOR = false;
    boolean DEBUG_KNIGHT_MOVES_CALCULATOR = false;
    boolean DEBUG_ROOK_MOVES_CALCULATOR = false;
    boolean DEBUG_PAWN_MOVES_CALCULATOR = false;
    boolean DEBUG_PARSE_BOARD_IN_CHESSBOARD_JAVA = false;

    String authTable = "authTable";
    String gameTable = "gameTable";
    String userTable = "userTable";
    String username = "username";
    String password = "password";
    String email = "email";
    String gameID = "gameID";
    int gameIDInt = 700;
    String whiteUsername = "whiteUsername";
    String blackUsername = "blackUsername";
    String gameName = "gameName";
    String game = "game";
    String authToken = "authToken";
}
