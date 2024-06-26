package chess;


import java.util.Arrays;

import static chess.ChessPiece.PieceType.*;
import static chess.Constants.DEBUG_PARSE_BOARD_IN_CHESSBOARD_JAVA;


/**
 * A chessboard that can hold and rearrange chess pieces.
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    // Variables
    private ChessPiece[][] board;


    private void clearBoard() {
        board = new ChessPiece[Constants.BOARD_NUM_ROWS][Constants.BOARD_NUM_COLUMNS];
        /// Reset the board completely with null pieces
        // Iterate over the chessboard rows and columns...
        for ( int row = 0; row < Constants.BOARD_NUM_ROWS; row++ ) {
            for ( int col = 0; col < Constants.BOARD_NUM_COLUMNS; col++ ) {
                // Initialize the piece to NULL
                board[row][col] = null;
            }
        }
    }

    // Default Constructor
    public ChessBoard() {
        clearBoard();
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_BOARD )
            System.out.println( "Creating Chess" + this.toString() );
    }


    // Constructor to set the board to a custom board
    public ChessBoard( ChessPiece[][] chessBoard ) {
        clearBoard();
        // Iterate over the chessboard rows and columns...
        for ( int row = 0; row < Constants.BOARD_NUM_ROWS; row++ ) {
            // Initialize the piece to NULL
            System.arraycopy( chessBoard[row], 0, board[row], 0, Constants.BOARD_NUM_COLUMNS );
        }
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_BOARD )
            System.out.println( "Creating Chess" + this.toString() );
    }

    // Copy constructor (deep)
    public ChessBoard( ChessBoard original ) {
        board = new ChessPiece[Constants.BOARD_NUM_ROWS][Constants.BOARD_NUM_COLUMNS];
        /// Create a deep copy of the original board in the new ChessBoard object
        // Iterate over the chessboard rows...
        for ( int row = Constants.BOARD_MIN_ONE_INDEX; row <= Constants.BOARD_MAX_ONE_INDEX; row++ ) {
            // Iterate over the chessboard columns...
            for ( int col = Constants.BOARD_MIN_ONE_INDEX; col <= Constants.BOARD_MAX_ONE_INDEX; col++ ) {
                // If the location is null, add a null piece
                if ( original.getPiece( row, col ) == null ) {
                    this.addPiece( row, col, null );
                    continue;
                }
                // Initialize the piece to the value on the original board.
                this.addPiece( row, col, new ChessPiece( original.getPiece( row, col ).getTeamColor(), original.getPiece( row, col ).getPieceType() ) );
            }
        }
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_BOARD )
            System.out.println( "Copy of Chess" + this.toString() );
    }

    // Tranpose an array of chess pieces
    public void switchPerspective() {
        int numRows = board.length;
        int numCols = board[0].length;

        // Reverse the array horizontally
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols / 2; col++) {
                // Swap elements between left and right sides of the array
                var temp = board[row][col];
                board[row][col] = board[row][numCols - 1 - col];
                board[row][numCols - 1 - col] = temp;
            }
        }

        // Reverse the array vertically
        for (int row = 0; row < numRows / 2; row++) {
            for (int col = 0; col < numCols; col++) {
                // Swap elements between top and bottom sides of the array
                var temp = board[row][col];
                board[row][col] = board[numRows - 1 - row][col];
                board[numRows - 1 - row][col] = temp;
            }
        }
    }

    // Parse a string to determine
    public static ChessGame.TeamColor parseColor( String gameString ) {
        String[] parts = gameString.split( "Color=" );
        return ( parts[1].split( "," )[0].equalsIgnoreCase( "white" ) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK );
    }


    // Parse a chessboard array from a ChessGame string
    public static ChessPiece[][] parseBoard( String gameString ) {
        int boardStartIndex = gameString.indexOf( "Board{[[" ) + 7;
        int boardEndIndex = gameString.lastIndexOf( "]]" ) + 2;

        String boardString = gameString.substring( boardStartIndex, boardEndIndex );
        if ( DEBUG_PARSE_BOARD_IN_CHESSBOARD_JAVA ) System.out.println( "boardString: " + boardString );

        // Remove the outer brackets
        final int bracketWidth = 2;
        boardString = boardString.substring( bracketWidth, boardString.length() - bracketWidth );
        // Split the rows
        String[] rows = boardString.split( "], \\[" );

        ChessPiece[][] board = new ChessPiece[rows.length][];
        // Iterate over the rows...
        for ( int i = 0; i < rows.length; i++ ) {
            // Remove the inner brackets and split by comma
            String[] pieces = rows[i].replace( "Piece[", "" ).replace( "]", "" ).split( ", " );
            ChessPiece[] boardRow = new ChessPiece[pieces.length];
            // Iterate over the pieces and parse the individual pieces for color and type...
            for ( int j = 0; j < pieces.length; j++ ) {
                String piece = pieces[j].trim();
                if ( piece.equals( "null" ) ) {
                    boardRow[j] = null;
                } else {
                    // Parse the piece
                    // Adjust the 'piece' string to isolate the variables
                    if ( piece.contains( "WHITE" ) )
                        piece = piece.substring( piece.indexOf( "WHITE" ) );
                    else if ( piece.contains( "BLACK" ) )
                        piece = piece.substring( piece.indexOf( "BLACK" ) );
                    else
                        throw new RuntimeException( "Error: Did not receive string 'piece' containing either 'WHITE' or 'BLACK' (piece = " + piece );
                    if ( DEBUG_PARSE_BOARD_IN_CHESSBOARD_JAVA ) System.out.println( "Piece: " + piece );
                    String[] pieceParts = piece.replace( "Piece[", "" ).replace( "]", "" ).split( "\\s+" );
                    // Convert the piecePart strings to objects and/or enums
                    if ( DEBUG_PARSE_BOARD_IN_CHESSBOARD_JAVA )
                        System.out.println( "Pieceparts" + Arrays.toString( pieceParts ) );
                    ChessGame.TeamColor pieceColor = pieceParts[0].equalsIgnoreCase( "white" ) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
                    if ( DEBUG_PARSE_BOARD_IN_CHESSBOARD_JAVA ) System.out.println( pieceColor );
                    ChessPiece.PieceType pieceType = switch ( pieceParts[1] ) {
                        case "QUEEN" -> QUEEN;
                        case "KING" -> KING;
                        case "BISHOP" -> BISHOP;
                        case "KNIGHT" -> KNIGHT;
                        case "ROOK" -> ROOK;
                        case "PAWN" -> PAWN;
                        default -> null;
                    };
                    boardRow[j] = new ChessPiece( pieceColor, pieceType );
                }
            }
            board[i] = boardRow;
        }
        return board;
    }


    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece( ChessPosition position, ChessPiece piece ) {
        int row = position.getRow() - 1;
        int column = position.getColumn() - 1;
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_BOARD )
            System.out.println( "Adding " + piece.toString() + " to (" + position.getRow() + "," + position.getColumn() + ")" );
        board[row][column] = piece;
    }


    // Add piece to the chessboard
    public void addPiece( int row, int column, ChessPiece piece ) {
        board[row - 1][column - 1] = piece;
        // Optional debug        if (Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_BOARD) System.out.println("Adding " + piece.toString() + " to (" + row + "," + column + ")");
    }


    // Set a position to null
    public void addNull( int row, int column ) {
        board[row - 1][column - 1] = null;
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_BOARD )
            System.out.println( "Adding null to (" + row + "," + column + ")" );
    }


    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece( ChessPosition position ) {
        int row = position.getRow() - 1;
        int column = position.getColumn() - 1;
        return board[row][column];
    }


    // Get chess piece from the chessboard
    public ChessPiece getPiece( int row, int column ) {
        return board[row - 1][column - 1];
    }


    // Return true if there is a piece at a certain position
    public boolean doesNotExistPiece( ChessPosition position ) {
        int row = position.getRow() - 1;
        int column = position.getColumn() - 1;
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_BOARD )
            System.out.println( "Piece does" + ( board[row][column] == null ? " not " : " " ) + "exist at " + position.toString() );
        return board[row][column] == null;
    }


    // Return true if there is a piece at a certain position
    public boolean doesNotExistPiece( int row, int column ) {
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_BOARD )
            System.out.println( "Piece does" + ( board[row - 1][column - 1] == null ? " not " : " " ) + "exist at (" + row + "," + column + ")" );
        return board[row - 1][column - 1] == null;
    }


    // Return true if there is a piece at a certain position
    public boolean doesFriendlyPieceExist( ChessPosition position, ChessGame.TeamColor friendlyTeamColor ) {
        if ( doesNotExistPiece( position ) ) return false;
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_BOARD )
            System.out.println( "Ally does" + ( getPieceTeamColor( position ) == friendlyTeamColor ? " not " : " " ) + "exist at " + position.toString() );
        return getPieceTeamColor( position ) == friendlyTeamColor;
    }


    // Return true if there is a piece at a certain position
    public boolean doesFriendlyPieceExist( int row, int column, ChessGame.TeamColor friendlyTeamColor ) {
        if ( doesNotExistPiece( row, column ) ) return false;
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_BOARD )
            System.out.println( "Ally does" + ( getPieceTeamColor( row, column ) == friendlyTeamColor ? " not " : " " ) + "exist at (" + row + "," + column + ")" );
        return getPieceTeamColor( row, column ) == friendlyTeamColor;
    }


    // Return true if there is a piece at a certain position
    public boolean doesOpponentPieceExist( int row, int column, ChessGame.TeamColor opponentTeamColor ) {
        if ( doesNotExistPiece( row, column ) ) return false;
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_BOARD )
            System.out.println( "Opponent does" + ( getPieceTeamColor( row, column ) == opponentTeamColor ? " not " : " " ) + "exist at (" + row + "," + column + ")" );
        return getPieceTeamColor( row, column ) == opponentTeamColor;
    }


    // Return the color of the piece at a certain location
    public ChessGame.TeamColor getPieceTeamColor( int row, int column ) {
        return board[row - 1][column - 1].getTeamColor();
    }


    // Return the color of the piece at a certain location
    public ChessGame.TeamColor getPieceTeamColor( ChessPosition position ) {
        int row = position.getRow() - 1;
        int column = position.getColumn() - 1;
        return board[row][column].getTeamColor();
    }


    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_BOARD )
            System.out.println( "Resetting board to default setup" );
        /// First reset the board completely with null pieces
        // Iterate over the chessboard rows...
        for ( int row = 0; row < Constants.BOARD_NUM_ROWS; row++ ) {
            // Iterate over the chessboard columns...
            for ( int col = 0; col < Constants.BOARD_NUM_COLUMNS; col++ ) {
                // Initialize the piece to NULL
                board[row][col] = null;
            }
        }

        /// Then add the black and white pieces
        // Add black pieces
        board[Constants.BLACK_NON_PAWN_ROW][Constants.ROOK_COL_1] = new ChessPiece( ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK );
        board[Constants.BLACK_NON_PAWN_ROW][Constants.KNIGHT_COL_1] = new ChessPiece( ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT );
        board[Constants.BLACK_NON_PAWN_ROW][Constants.BISHOP_COL_1] = new ChessPiece( ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP );
        board[Constants.BLACK_NON_PAWN_ROW][Constants.QUEEN_COL] = new ChessPiece( ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN );
        board[Constants.BLACK_NON_PAWN_ROW][Constants.KING_COL] = new ChessPiece( ChessGame.TeamColor.BLACK, KING );
        board[Constants.BLACK_NON_PAWN_ROW][Constants.BISHOP_COL_2] = new ChessPiece( ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP );
        board[Constants.BLACK_NON_PAWN_ROW][Constants.KNIGHT_COL_2] = new ChessPiece( ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT );
        board[Constants.BLACK_NON_PAWN_ROW][Constants.ROOK_COL_2] = new ChessPiece( ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK );
        // Loop to add the pawns...
        for ( int col = 0; col < Constants.BOARD_NUM_COLUMNS; col++ ) {
            board[Constants.BLACK_PAWN_ROW][col] = new ChessPiece( ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN );
        }

        // Add white pieces
        board[Constants.WHITE_NON_PAWN_ROW][Constants.ROOK_COL_1] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK );
        board[Constants.WHITE_NON_PAWN_ROW][Constants.KNIGHT_COL_1] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT );
        board[Constants.WHITE_NON_PAWN_ROW][Constants.BISHOP_COL_1] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP );
        board[Constants.WHITE_NON_PAWN_ROW][Constants.QUEEN_COL] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN );
        board[Constants.WHITE_NON_PAWN_ROW][Constants.KING_COL] = new ChessPiece( ChessGame.TeamColor.WHITE, KING );
        board[Constants.WHITE_NON_PAWN_ROW][Constants.BISHOP_COL_2] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP );
        board[Constants.WHITE_NON_PAWN_ROW][Constants.KNIGHT_COL_2] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT );
        board[Constants.WHITE_NON_PAWN_ROW][Constants.ROOK_COL_2] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK );
        // Loop to add the pawns...
        for ( int col = 0; col < Constants.BOARD_NUM_COLUMNS; col++ ) {
            board[Constants.WHITE_PAWN_ROW][col] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN );
        }
        // Optional debug
        if ( Constants.DEBUG_GLOBAL || Constants.DEBUG_CHESS_BOARD ) printBoard();
    }


    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( !( o instanceof ChessBoard that ) ) return false;
        return Arrays.deepEquals( board, that.board );
    }


    @Override
    public int hashCode() {
        return Arrays.deepHashCode( board );
    }


    @Override
    public String toString() {
        return "Board{" + Arrays.deepToString( board ) + '}';
    }


    // Print out a visually correct chessboard (i.e. the top left corresponds to position (8,1) and the bottom left (1,1)
    public void printBoard() {
        // Print out the chessboard in a clear, visual way.
        for ( int row = Constants.BOARD_MAX_ZERO_INDEX; row >= Constants.BOARD_MIN_ZERO_INDEX; row-- ) {
            // Iterate over the columns
            System.out.print( '|' );
            for ( int col = Constants.BOARD_MIN_ZERO_INDEX; col <= Constants.BOARD_MAX_ZERO_INDEX; col++ ) {
                // Continue on to the next loop if it is null
                if ( board[row][col] == null ) {
                    System.out.print( " |" );
                    continue;
                }
                // Save piece type and color
                ChessGame.TeamColor pieceColor = board[row][col].getTeamColor();
                ChessPiece.PieceType pieceType = board[row][col].getPieceType();
                // Check the piece type
                switch ( pieceType ) {
                    case KING:
                        System.out.print( ( pieceColor == ChessGame.TeamColor.WHITE ? "K" : "k" ) + "|" );
                        break;
                    case QUEEN:
                        System.out.print( ( pieceColor == ChessGame.TeamColor.WHITE ? "Q" : "q" ) + "|" );
                        break;
                    case BISHOP:
                        System.out.print( ( pieceColor == ChessGame.TeamColor.WHITE ? "B" : "b" ) + "|" );
                        break;
                    case KNIGHT:
                        System.out.print( ( pieceColor == ChessGame.TeamColor.WHITE ? "N" : "n" ) + "|" );
                        break;
                    case ROOK:
                        System.out.print( ( pieceColor == ChessGame.TeamColor.WHITE ? "R" : "r" ) + "|" );
                        break;
                    case PAWN:
                        System.out.print( ( pieceColor == ChessGame.TeamColor.WHITE ? "P" : "p" ) + "|" );
                        break;
                    default:
                        System.out.print( " |" );
                        break;
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
