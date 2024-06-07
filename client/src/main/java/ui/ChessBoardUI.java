package ui;


import chess.ChessBoard;
import chess.ChessGame;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.ChessUIConstants.*;


public class ChessBoardUI {
    private static ExtendedChessBoard extendedChessBoard = new ExtendedChessBoard();

    // Draw entire board from either the white or black player's perspective
    public static void drawBoard( ChessGame.TeamColor teamColor, ChessBoard chessBoard) {
        // Reset the terminal screen
        var out = new PrintStream( System.out, true, StandardCharsets.UTF_8 );
        out.print( ERASE_SCREEN );

        // Determine which perspective to draw from
        if (teamColor == ChessGame.TeamColor.WHITE) {
            extendedChessBoard
        } else {

        }

        resetTerminalColors( out );
    }

    private static void drawRow(PrintStream out, ChessSquare[] chessSquareRow) {
        // Iterate over the rows and columns to draw the board
        for (var chessSquare: chessSquareRow) {
            setBackgroundForeground(  out, chessSquare.background(), chessSquare.foreground() );
            out.print( chessSquare.text() );
        }
        out.println();
    }

    private static void setBackgroundForeground( PrintStream out, String background, String foreground ) {
        out.print( background );
        out.print( foreground );
    }


    private static void setWhitePiece( PrintStream out ) {
        setBackgroundForeground( out, SET_BG_COLOR_BLACK, SET_TEXT_COLOR_WHITE );
    }


    private static void setWhite( PrintStream out ) {
        setBackgroundForeground( out, SET_BG_COLOR_WHITE, SET_TEXT_COLOR_WHITE );
    }


    private static void setBlackPiece( PrintStream out ) {
        setBackgroundForeground( out, SET_BG_COLOR_WHITE, SET_TEXT_COLOR_RED );
    }


    private static void setBlack( PrintStream out ) {
        setBackgroundForeground( out, SET_BG_COLOR_BLACK, SET_TEXT_COLOR_BLACK );
    }


    private static void setEdgeLabel( PrintStream out ) {
        setBackgroundForeground( out, SET_BG_COLOR_TAN, SET_TEXT_COLOR_BLACK );
    }


    private static void setEdge( PrintStream out ) {
        setBackgroundForeground( out, SET_BG_COLOR_TAN, SET_TEXT_COLOR_TAN );
    }

    private static void resetTerminalColors( PrintStream out ) {
        setBackgroundForeground( out, SET_BG_COLOR_BLACK, SET_TEXT_COLOR_WHITE );
    }
}
