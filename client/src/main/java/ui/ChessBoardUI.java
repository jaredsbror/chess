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
            setForegroundBackground(  out, chessSquare.foreground(), chessSquare.background() );
            out.print( chessSquare.text() );
        }
        out.println();
    }

    private static void setForegroundBackground( PrintStream out, String foreground, String background ) {
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

    private static void resetTerminalColors( PrintStream out ) {
        setForegroundBackground( out, SET_TEXT_COLOR_WHITE, SET_BG_COLOR_BLACK );
    }
}
