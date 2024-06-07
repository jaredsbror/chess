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
    public static final String SET_BG_COLOR_TAN = SET_BG_COLOR + "180m";
    public static final String SET_BG_COLOR_RED = SET_BG_COLOR + "160m";
    public static final String SET_BG_COLOR_GREEN = SET_BG_COLOR + "46m";
    public static final String SET_BG_COLOR_DARK_GREEN = SET_BG_COLOR + "22m";
    public static final String SET_BG_COLOR_YELLOW = SET_BG_COLOR + "226m";
    public static final String SET_BG_COLOR_BLUE = SET_BG_COLOR + "12m";
    public static final String SET_BG_COLOR_MAGENTA = SET_BG_COLOR + "5m";
    public static final String SET_BG_COLOR_WHITE = SET_BG_COLOR + "15m";
    public static final String RESET_BG_COLOR = UNICODE_ESCAPE + "[49m";

    public static final String CHESS_EDGE_BACKGROUND = SET_BG_COLOR_TAN;
    public static final String CHESS_EDGE_FOREGROUND = SET_TEXT_COLOR_BLACK;
    public static final String CHESS_WHITE_BACKGROUND = SET_BG_COLOR_WHITE;
    public static final String CHESS_BLACK_BACKGROUND = SET_BG_COLOR_BLACK;
    public static final String CHESS_WHITE_PIECE_FOREGROUND = SET_TEXT_COLOR_YELLOW;
    public static final String CHESS_BLACK_PIECE_FOREGROUND = SET_TEXT_COLOR_BLUE;
    public static final String CHESS_NO_PIECE_FOREGROUND = SET_TEXT_COLOR_WHITE;

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
    public static final String[] CHESS_EDGE_LABEL_NUMBERS_WHITE = {
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
    public static final String[] CHESS_EDGE_LABEL_NUMBERS_BLACK = {
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
    public static final String EMPTY = CHESS_SQUARE_BUFFER + "\u2003" + CHESS_SQUARE_BUFFER;

    public static String moveCursorToLocation(int x, int y) { return UNICODE_ESCAPE + "[" + y + ";" + x + "H"; }
}
