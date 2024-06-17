package ui;


import chess.ChessBoard;
import chess.ChessGame;
import datatypes.ChessSquare;
import datatypes.ExtendedChessBoard;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.ChessUIConstants.CHESS_EDGE_SIZE_IN_SQUARES;


public class ChessBoardUI {
    private final TerminalUI terminalUI = new TerminalUI();


    // Draw entire board from either the white or black player's perspective
    public void drawBoard( ChessBoard chessBoard, ChessGame.TeamColor teamColor ) {
        ExtendedChessBoard extendedChessBoard = new ExtendedChessBoard( chessBoard, teamColor );
        drawBoard( extendedChessBoard );
    }


    // Draw entire board from either the white or black player's perspective
    public void drawBoard( ExtendedChessBoard extendedChessBoardObject ) {
        // Reset the terminal screen
        var out = new PrintStream( System.out, true, StandardCharsets.UTF_8 );
        // Iterate over the rows and print them out
        for ( int row = 0; row < CHESS_EDGE_SIZE_IN_SQUARES; row++ ) {
            drawRow( out, extendedChessBoardObject.getRow( row ) );
        }
        // Reset terminal colors
        terminalUI.resetTerminalColors( out );
    }


    private void drawRow( PrintStream out, ChessSquare[] chessSquareRow ) {
        // Iterate over the rows and columns to draw the board
        for ( var chessSquare : chessSquareRow ) {
            terminalUI.setForegroundBackground( out, chessSquare.foreground(), chessSquare.background() );
            out.print( chessSquare.text() );
        }
        // Reset terminal colors
        terminalUI.resetTerminalColors( out );
        out.println();
    }

}
