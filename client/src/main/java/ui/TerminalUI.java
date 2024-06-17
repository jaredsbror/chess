package ui;


import java.io.PrintStream;

import static ui.ChessUIConstants.*;


public class TerminalUI {
    public void setForegroundBackground( PrintStream out, String foreground, String background ) {
        out.print( foreground );
        out.print( background );
    }

    public void resetTerminalColors( PrintStream out ) {
        setForegroundBackground( out, SET_TEXT_COLOR_WHITE, SET_BG_COLOR_BLACK );
    }

    public void eraseScreen( PrintStream out ) {
        out.print(ERASE_SCREEN);
    }
}
