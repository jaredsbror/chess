package ui;


import java.io.PrintStream;

import static ui.ChessUIConstants.*;


public class TerminalUI {
    public static void setForegroundBackground( PrintStream out, String foreground, String background ) {
        out.print( foreground );
        out.print( background );
    }


    private static void setWhitePiece( PrintStream out ) {
        setForegroundBackground( out, SET_TEXT_COLOR_WHITE, SET_BG_COLOR_BLACK );
    }


    private static void setWhite( PrintStream out ) {
        setForegroundBackground( out, SET_TEXT_COLOR_WHITE, SET_BG_COLOR_WHITE );
    }


    private static void setBlackPiece( PrintStream out ) {
        setForegroundBackground( out, SET_TEXT_COLOR_RED, SET_BG_COLOR_WHITE );
    }


    private static void setBlack( PrintStream out ) {
        setForegroundBackground( out, SET_TEXT_COLOR_BLACK, SET_BG_COLOR_BLACK );
    }


    private static void setEdgeLabel( PrintStream out ) {
        setForegroundBackground( out, SET_TEXT_COLOR_BLACK, SET_BG_COLOR_TAN );
    }


    private static void setEdge( PrintStream out ) {
        setForegroundBackground( out, SET_TEXT_COLOR_TAN, SET_BG_COLOR_TAN );
    }

    public static void resetTerminalColors( PrintStream out ) {
        setForegroundBackground( out, SET_TEXT_COLOR_WHITE, SET_BG_COLOR_BLACK );
    }
}
