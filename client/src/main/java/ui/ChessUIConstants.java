package ui;

/**
 * This class contains constants and functions relating to ANSI Escape Sequences that are useful in the Client display
 */
public class ChessUIConstants {

    private static final String UNICODE_ESCAPE = "\u001b";
    private static final String ANSI_ESCAPE = "\033";

    public static final String ERASE_SCREEN = UNICODE_ESCAPE + "[H" + UNICODE_ESCAPE + "[2J";
    public static final String ERASE_LINE = UNICODE_ESCAPE + "[2K";

    public static final String SET_TEXT_BOLD = UNICODE_ESCAPE + "[1m";
    public static final String SET_TEXT_FAINT = UNICODE_ESCAPE + "[2m";
    public static final String RESET_TEXT_BOLD_FAINT = UNICODE_ESCAPE + "[22m";
    public static final String SET_TEXT_ITALIC = UNICODE_ESCAPE + "[3m";
    public static final String RESET_TEXT_ITALIC = UNICODE_ESCAPE + "[23m";
    public static final String SET_TEXT_UNDERLINE = UNICODE_ESCAPE + "[4m";
    public static final String RESET_TEXT_UNDERLINE = UNICODE_ESCAPE + "[24m";
    public static final String SET_TEXT_BLINKING = UNICODE_ESCAPE + "[5m";
    public static final String RESET_TEXT_BLINKING = UNICODE_ESCAPE + "[25m";

    private static final String SET_TEXT_COLOR = UNICODE_ESCAPE + "[38;5;";
    private static final String SET_BG_COLOR = UNICODE_ESCAPE + "[48;5;";

    public static final String SET_TEXT_COLOR_BLACK = SET_TEXT_COLOR + "0m";
    public static final String SET_TEXT_COLOR_LIGHT_GREY = SET_TEXT_COLOR + "242m";
    public static final String SET_TEXT_COLOR_DARK_GREY = SET_TEXT_COLOR + "235m";
    public static final String SET_TEXT_COLOR_TAN = SET_BG_COLOR + "180m";
    public static final String SET_TEXT_COLOR_RED = SET_TEXT_COLOR + "160m";
    public static final String SET_TEXT_COLOR_GREEN = SET_TEXT_COLOR + "46m";
    public static final String SET_TEXT_COLOR_YELLOW = SET_TEXT_COLOR + "226m";
    public static final String SET_TEXT_COLOR_BLUE = SET_TEXT_COLOR + "12m";
    public static final String SET_TEXT_COLOR_MAGENTA = SET_TEXT_COLOR + "5m";
    public static final String SET_TEXT_COLOR_WHITE = SET_TEXT_COLOR + "15m";
    public static final String RESET_TEXT_COLOR = UNICODE_ESCAPE + "[39m";

    public static final String SET_BG_COLOR_BLACK = SET_BG_COLOR + "0m";
    public static final String SET_BG_COLOR_LIGHT_GREY = SET_BG_COLOR + "242m";
    public static final String SET_BG_COLOR_DARK_GREY = SET_BG_COLOR + "235m";
    public static final String SET_BG_COLOR_RED = SET_BG_COLOR + "160m";
    public static final String SET_BG_COLOR_GREEN = SET_BG_COLOR + "46m";
    public static final String SET_BG_COLOR_DARK_GREEN = SET_BG_COLOR + "22m";
    public static final String SET_BG_COLOR_YELLOW = SET_BG_COLOR + "226m";
    public static final String SET_BG_COLOR_BLUE = SET_BG_COLOR + "12m";
    public static final String SET_BG_COLOR_MAGENTA = SET_BG_COLOR + "5m";
    public static final String SET_BG_COLOR_WHITE = SET_BG_COLOR + "15m";
    public static final String RESET_BG_COLOR = UNICODE_ESCAPE + "[49m";

    // Existing colors
    public static final String SET_BG_COLOR_DARK_MAHOGANY = "\u001B[48;2;139;69;19m"; // Dark reddish mahogany
    public static final String SET_BG_COLOR_MAHOGANY = "\u001B[48;2;192;64;0m";
    public static final String SET_BG_COLOR_TAN = "\u001B[48;2;210;180;140m";
    public static final String SET_BG_COLOR_DARK_BROWN = "\u001B[48;2;101;67;33m";
    public static final String SET_FG_COLOR_WHITE = "\u001B[38;5;15m";

    // New colors for black pieces and edge labels
    public static final String SET_FG_COLOR_BRIGHT_YELLOW = "\u001B[38;5;226m";
    public static final String SET_FG_COLOR_BRIGHT_CYAN = "\u001B[38;5;51m";
    public static final String SET_FG_COLOR_BRIGHT_GREEN = "\u001B[38;5;46m";
    public static final String SET_FG_COLOR_GOLD = "\u001B[38;5;220m";
    public static final String SET_FG_COLOR_LIGHT_GRAY = "\u001B[38;5;250m";
    public static final String SET_FG_COLOR_OFF_WHITE = "\u001B[38;5;253m"; // Soft off-white
    public static final String SET_FG_COLOR_BRIGHT_WHITE = "\u001B[38;2;255;255;255m";
    public static final String SET_FG_COLOR_DARK_BLUE = "\u001B[38;2;0;0;139m";
    public static final String SET_FG_COLOR_LIGHT_PURPLE = "\u001B[38;5;189m"; // Light purple (lavender)

    // Choose appropriate colors
    public static final String SET_FG_COLOR_BLACK_PIECE = SET_FG_COLOR_BRIGHT_YELLOW; // Or choose another
    public static final String SET_FG_COLOR_EDGE_LABEL = SET_FG_COLOR_GOLD; // Or choose another



    public static final String CHESS_EDGE_BACKGROUND = SET_BG_COLOR_DARK_MAHOGANY;
    public static final String CHESS_EDGE_FOREGROUND = SET_FG_COLOR_GOLD;
    public static final String CHESS_WHITE_BACKGROUND = SET_BG_COLOR_TAN;
    public static final String CHESS_BLACK_BACKGROUND = SET_BG_COLOR_DARK_BROWN;
    public static final String CHESS_WHITE_PIECE_FOREGROUND = SET_FG_COLOR_LIGHT_PURPLE;
    public static final String CHESS_BLACK_PIECE_FOREGROUND = SET_FG_COLOR_DARK_BLUE;

    public static final int CHESS_EDGE_SIZE_IN_SQUARES = 10;
    public static final int CHESS_BOARD_SIZE_IN_SQUARES = 8;
    public static final String CHESS_SQUARE_BUFFER = " ";
    public static final String[] CHESS_EDGE_LABEL_LETTERS_WHITE = {
            CHESS_SQUARE_BUFFER + " " + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "A" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "B" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "C" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "D" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "E" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "F" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "G" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "H" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + " " + CHESS_SQUARE_BUFFER };
    public static final String[] CHESS_EDGE_LABEL_LETTERS_BLACK = {
            CHESS_SQUARE_BUFFER + " " + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "H" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "G" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "F" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "E" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "D" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "C" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "B" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "A" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + " " + CHESS_SQUARE_BUFFER };
    public static final String[] CHESS_EDGE_LABEL_NUMBERS_BLACK = {
            CHESS_SQUARE_BUFFER + " " + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "1" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "2" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "3" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "4" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "5" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "6" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "7" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "8" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + " " + CHESS_SQUARE_BUFFER };
    public static final String[] CHESS_EDGE_LABEL_NUMBERS_WHITE = {
            CHESS_SQUARE_BUFFER + " " + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "8" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "7" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "6" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "5" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "4" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "3" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "2" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + "1" + CHESS_SQUARE_BUFFER,
            CHESS_SQUARE_BUFFER + " " + CHESS_SQUARE_BUFFER };

    public static final String WHITE_KING = CHESS_SQUARE_BUFFER + "♔" + CHESS_SQUARE_BUFFER;
    public static final String WHITE_QUEEN = CHESS_SQUARE_BUFFER + "♕" + CHESS_SQUARE_BUFFER;
    public static final String WHITE_BISHOP = CHESS_SQUARE_BUFFER + "♗" + CHESS_SQUARE_BUFFER;
    public static final String WHITE_KNIGHT = CHESS_SQUARE_BUFFER + "♘" + CHESS_SQUARE_BUFFER;
    public static final String WHITE_ROOK = CHESS_SQUARE_BUFFER + "♖" + CHESS_SQUARE_BUFFER;
    public static final String WHITE_PAWN = CHESS_SQUARE_BUFFER + "♙" + CHESS_SQUARE_BUFFER;
    public static final String BLACK_KING = CHESS_SQUARE_BUFFER + "♚" + CHESS_SQUARE_BUFFER;
    public static final String BLACK_QUEEN = CHESS_SQUARE_BUFFER + "♛" + CHESS_SQUARE_BUFFER;
    public static final String BLACK_BISHOP = CHESS_SQUARE_BUFFER + "♝" + CHESS_SQUARE_BUFFER;
    public static final String BLACK_KNIGHT = CHESS_SQUARE_BUFFER + "♞" + CHESS_SQUARE_BUFFER;
    public static final String BLACK_ROOK = CHESS_SQUARE_BUFFER + "♜" + CHESS_SQUARE_BUFFER;
    public static final String BLACK_PAWN = CHESS_SQUARE_BUFFER + "♟" + CHESS_SQUARE_BUFFER;
    public static final String WHITE_KING_LETTER = CHESS_SQUARE_BUFFER + "K" + CHESS_SQUARE_BUFFER;
    public static final String WHITE_QUEEN_LETTER = CHESS_SQUARE_BUFFER + "Q" + CHESS_SQUARE_BUFFER;
    public static final String WHITE_BISHOP_LETTER = CHESS_SQUARE_BUFFER + "B" + CHESS_SQUARE_BUFFER;
    public static final String WHITE_KNIGHT_LETTER = CHESS_SQUARE_BUFFER + "N" + CHESS_SQUARE_BUFFER;
    public static final String WHITE_ROOK_LETTER = CHESS_SQUARE_BUFFER + "R" + CHESS_SQUARE_BUFFER;
    public static final String WHITE_PAWN_LETTER = CHESS_SQUARE_BUFFER + "P" + CHESS_SQUARE_BUFFER;
    public static final String BLACK_KING_LETTER = CHESS_SQUARE_BUFFER + "K" + CHESS_SQUARE_BUFFER;
    public static final String BLACK_QUEEN_LETTER = CHESS_SQUARE_BUFFER + "Q" + CHESS_SQUARE_BUFFER;
    public static final String BLACK_BISHOP_LETTER = CHESS_SQUARE_BUFFER + "B" + CHESS_SQUARE_BUFFER;
    public static final String BLACK_KNIGHT_LETTER = CHESS_SQUARE_BUFFER + "N" + CHESS_SQUARE_BUFFER;
    public static final String BLACK_ROOK_LETTER = CHESS_SQUARE_BUFFER + "R" + CHESS_SQUARE_BUFFER;
    public static final String BLACK_PAWN_LETTER = CHESS_SQUARE_BUFFER + "P" + CHESS_SQUARE_BUFFER;
    public static final String EMPTY = CHESS_SQUARE_BUFFER + " " + CHESS_SQUARE_BUFFER;

    public static String moveCursorToLocation(int x, int y) { return UNICODE_ESCAPE + "[" + y + ";" + x + "H"; }
}
