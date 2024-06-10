package ui;


import chess.ChessBoard;
import chess.ChessGame;
import connections.ServerFacade;
import datatypes.ExtendedChessBoard;
import model.custom.LoginRequest;
import model.custom.LoginResult;
import model.custom.RegisterRequest;
import model.custom.RegisterResult;
import model.original.GameData;
import model.original.UserData;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ui.ChessUIConstants.*;


public class GameUI {
    private static ChessBoard chessBoard = new ChessBoard();
    private static PrintStream out = new PrintStream( System.out, true, StandardCharsets.UTF_8 );
    private static Scanner scanner = new Scanner( System.in );
    private static ServerFacade serverFacade = new ServerFacade();

    private static void print(String string) {
        out.print( string );
    }

    private static void printf(String string) {
        out.printf( string );
    }

    private static void println(String string) {
        out.println( string );
    }

    // Helper method to validate string input
    private static String getStringInput() {
        scanner = new Scanner( System.in );
        return scanner.nextLine();
    }

    // Helper method to validate string input
    private static String getValidStringInput(String goal) {
        // If the user types a string, continue. Otherwise, keep scanning.
        while (true) {
            scanner = new Scanner( System.in );
            String inputString = scanner.nextLine();
            if (inputString.equalsIgnoreCase( goal ))  {
                return inputString;
            }
            scanner.next(); // Discard the invalid input token
            println( "Invalid input. A String would be wonderful please" );
        }
    }

    // Helper method to validate integer input
    private static int getValidIntegerInput(int min, int max) {
        int number;
        // If the user types in a valid number, continue. Otherwise, keep scanning.
        while (true) {
            scanner = new Scanner( System.in );
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                if (number >= min && number <= max) {
                    return number; // Exit the loop if input is valid
                } else {
                    println("Invalid input. Let's just enter an integer between " + min + " and " + max + ", shall we?.");
                }
            } else {
                scanner.next(); // Discard the invalid input token
                println( "Invalid input. Please just make my life easy and enter an integer." );
            }
        }
    }

    /*
    Help	Displays text informing the user what actions they can take.
    Quit	Exits the program.
    Login	Prompts the user to input login information.
            Calls the server login API to login the user.
            When successfully logged in, the client should transition to the Postlogin UI.
    Register	Prompts the user to input registration information.
                Calls the server register API to register and login the user.
                If successfully registered, the client should be logged in and transition
                to the Postlogin UI.
     */
    public static void preLoginUI() {
        // Reset the terminal screen
        print( ERASE_SCREEN );
        TerminalUI.resetTerminalColors( out );
        // Print out the menu
        println( "Welcome to CS 240 Chess! Type 'Help' to get started.");

        // If the user types help, continue. Otherwise, keep scanning.
        while (true) {
            scanner = new Scanner( System.in );
            String inputString = scanner.nextLine();
            if (inputString.equalsIgnoreCase( "Help" ))  {
                println( "Good. You can follow instructions!" );
                break;
            }
            println( "Invalid input. Please spare me the misery and just type 'Help'..." );
        }

        // Prelogin Menu loop
        while (true) {
            println( "1. Register" );
            println( "2. Login" );
            println( "3. Quit (and leave me in peace...highly recommended)" );
            println( "4. Help (and I'll be forced to print out this menu again)" );
            print( "Please type a number: " );

            // If the user types in a valid number, continue. Otherwise, keep scanning.
            int integerInput = getValidIntegerInput( 1, 4 );

            // Process the integer input
            switch ( integerInput ) {
                case 1:
                    println("Fine. Let me collect some personal info first for blackmail purposes.");
                    println("Username? ");
                    String username = getStringInput();
                    println("Password? ");
                    String password = getStringInput();
                    println("Email? ");
                    String email = getStringInput();
                    // Connect with the server
                    try {
                        RegisterResult registerResult = serverFacade.register( new RegisterRequest( username, password, email ) );
                        if (registerResult.message() != null) {
                            println("Error: " + registerResult.message());
                            break;
                        }
                        LoginResult loginResult = serverFacade.login( new LoginRequest( username, password ) );
                        if (loginResult.message() != null) {
                            println("Error: " + loginResult.message());
                            break;
                        }
                        postLoginUI();
                        return;
                    } catch ( Exception exception ) {
                        throw new RuntimeException( exception );
                    }
                case 2:
                    println("Remind me again what your username was?");
                    username = getStringInput();
                    println("And your password?");
                    password = getStringInput();
                    // Connect with the server
                    try {
                        LoginResult loginResult = serverFacade.login( new LoginRequest( username, password ) );
                        if (loginResult.message() != null) {
                            println("Error: " + loginResult.message());
                            break;
                        }
                        postLoginUI();
                        return;
                    } catch ( Exception exception ) {
                        throw new RuntimeException( exception );
                    }
                case 3: // Exit the program
                    println( "Good riddance...They don't pay me nearly enough to deal with you guys." );
                    return;
                case 4: // Repeat the while loop
                    break;
                default:
                    throw new RuntimeException( "Error: Invalid integer input in preLoginUI()" );
            }
        }
    }

    // Help	Displays text informing the user what actions they can take.
    // Logout	Logs out the user. Calls the server logout API to logout the user.
    //          After logging out with the server, the client should transition to the Prelogin UI.
    // Create Game	Allows the user to input a name for the new game.
    //              Calls the server create API to create the game.
    //              This does not join the player to the created game;
    //              it only creates the new game in the server.
    // List Games	Lists all the games that currently exist on the server.
    //              Calls the server list API to get all the game data, and displays the
    //              games in a numbered list, including the game name and players (not observers)
    //              in the game. The numbering for the list should be independent of the game IDs.
    // Play Game	Allows the user to specify which game they want to join and what color they
    //              want to play. They should be able to enter the number of the desired game.
    //              Your client will need to keep track of which number corresponds to which game
    //              from the last time it listed the games. Calls the server join API to join the
    //              user to the game.
    // Observe Game	Allows the user to specify which game they want to observe.
    //              They should be able to enter the number of the desired game.
    //              Your client will need to keep track of which number corresponds to which game
    //              from the last time it listed the games. Functionality will be added in Phase 6.
    public static void postLoginUI() {
        println( "postLoginUI" );
    }

    public static void gamePlayUI() {

    }

    public static void drawGameBoard(ChessBoard board) {
        chessBoard = board;
        chessBoard.resetBoard();
        ChessBoardUI.drawBoard( chessBoard, ChessGame.TeamColor.WHITE );
        ChessBoardUI.drawBoard( chessBoard, ChessGame.TeamColor.BLACK );
    }

    public static void drawGameBoard( GameData gameData ) {
        chessBoard = new ChessBoard( ChessBoard.parseBoard( gameData.game().toString() ) );
        chessBoard.resetBoard();
        ChessBoardUI.drawBoard( chessBoard, ChessGame.TeamColor.WHITE );
        ChessBoardUI.drawBoard( chessBoard, ChessGame.TeamColor.BLACK );
    }


}
