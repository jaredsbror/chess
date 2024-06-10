package ui;


import chess.ChessBoard;
import chess.ChessGame;
import datatypes.ChessSquare;
import datatypes.ExtendedChessBoard;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.ChessUIConstants.*;
import static ui.TerminalUI.resetTerminalColors;
import static ui.TerminalUI.setForegroundBackground;


public class ChessBoardUI {
    private static ExtendedChessBoard extendedChessBoard = new ExtendedChessBoard();

    // Draw entire board from either the white or black player's perspective
    public static void drawBoard( ChessBoard chessBoard, ChessGame.TeamColor teamColor) {
        extendedChessBoard = new ExtendedChessBoard(chessBoard, teamColor);
        drawBoard( extendedChessBoard );
    }

    // Draw entire board from either the white or black player's perspective
    public static void drawBoard( ExtendedChessBoard extendedChessBoardObject) {
        // Reset the terminal screen
        var out = new PrintStream( System.out, true, StandardCharsets.UTF_8 );
        out.print( ERASE_SCREEN );
        // Iterate over the rows and print them out
        for (int row = 0; row < CHESS_EDGE_SIZE_IN_SQUARES; row++) {
            drawRow( out, extendedChessBoardObject.getRow( row ) );
        }
        // Reset terminal colors
        resetTerminalColors( out );
    }

    private static void drawRow(PrintStream out, ChessSquare[] chessSquareRow) {
        // Iterate over the rows and columns to draw the board
        for (var chessSquare: chessSquareRow) {
            setForegroundBackground(  out, chessSquare.foreground(), chessSquare.background() );
            out.print( chessSquare.text() );
        }
        // Reset terminal colors
        resetTerminalColors( out );
        out.println();
    }


}
