package ui;


import chess.ChessBoard;
import chess.ChessGame;
import connections.ServerFacade;
import model.custom.*;
import model.original.GameData;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ui.ChessUIConstants.*;


public class GameUI {
    private static ChessBoard chessBoard = new ChessBoard();
    private static final PrintStream out = new PrintStream( System.out, true, StandardCharsets.UTF_8 );
    private static Scanner scanner = new Scanner( System.in );
    private static final ServerFacade serverFacade = new ServerFacade();
    private static String authToken = null;

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
        println( "\nWelcome to CS 240 Chess! Type 'Help' to get started.");

        // Prompt for 'Help' input
        waitForHelpInput();

        preLoginMenuLoop();
    }

    private static void preLoginMenuLoop() {
        boolean exitPreLogin = false;
        // Prelogin Menu loop
        while (!exitPreLogin) {
            printPreLoginMenu();
            int integerInput = getValidIntegerInput(1, 4);
            exitPreLogin = handlePreLoginMenuSelection(integerInput);
        }
    }

    // Helper method to validate string input
    private static String getStringInput(String prompt) {
        println(prompt);
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

    private static void waitForHelpInput() {
        while (true) {
            String inputString = scanner.nextLine().trim();
            if (inputString.equalsIgnoreCase("Help")) {
                println("Good. You can follow instructions!");
                break;
            }
            println("Invalid input. Please spare me the misery and just type 'Help'...");
        }
    }

    private static void printPreLoginMenu() {
        println( "\n1. Register" );
        println( "2. Login" );
        println( "3. Quit (and leave me in peace...highly recommended)" );
        println( "4. Help (and I'll be forced to print out this menu again)" );
        print( "Please type a number: " );
    }

    private static boolean handlePreLoginMenuSelection( int selection) {
        try {
            switch (selection) {
                case 1:
                    handleRegister();
                    return true; // Exit pre-login UI after successful registration
                case 2:
                    handleLogin();
                    return true; // Exit pre-login UI after successful login
                case 3:
                    println("Good riddance...They don't pay me nearly enough to deal with you guys.");
                    System.exit(0);
                case 4:
                    break; // Will cause the menu to be printed again
                default:
                    throw new RuntimeException("Error: Invalid integer input in preLoginUI()");
            }
        } catch (Exception exception) {
            println("Error: " + exception.getMessage());
        }
        return false; // Continue the pre-login UI loop
    }

    private static void handleRegister() throws Exception {
        println("Fine. Let me collect some personal info first for blackmail purposes.");
        String username = getStringInput("Username? ");
        String password = getStringInput("Password? ");
        String email = getStringInput("Email? ");

        RegisterResult registerResult = serverFacade.register(new RegisterRequest(username, password, email));
        if (registerResult.message() != null) {
            println("Error: " + registerResult.message());
            return;
        }

        LoginResult loginResult = serverFacade.login(new LoginRequest(username, password));
        if (loginResult.message() != null) {
            println("Error: " + loginResult.message());
            return;
        }

        postLoginUI();
    }

    private static void handleLogin() throws Exception {
        String username = getStringInput("Remind me again what your username was?");
        String password = getStringInput("And your password?");

        LoginResult loginResult = serverFacade.login(new LoginRequest(username, password));
        if (loginResult.message() != null) {
            println("Error: " + loginResult.message());
            return;
        }
        authToken = loginResult.authToken();
        postLoginUI();
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
        // Reset the terminal screen
        print( ERASE_SCREEN );
        TerminalUI.resetTerminalColors( out );

        boolean exitPostLogin = false;
        // Prelogin Menu loop
        while (!exitPostLogin) {
            printPostLoginMenu();
            int integerInput = getValidIntegerInput(1, 5);
            exitPostLogin = handlePostLoginMenuSelection(integerInput);
        }
    }

    private static void printPostLoginMenu() {
        println("\n\nThe Ultimate Chess 240 Game Menu 2.0");
        println("AuthToken: " + authToken);
        println("1. Logout (Maybe someone like you should consider this?)");
        println("2. Create Game");
        println("3. List Games (Avoid this option at all costs! I don't like work.)");
        println("4. Play Game");
        println("5. Observe Game ");
        println("Please type a number: ");
    }

    private static boolean handlePostLoginMenuSelection( int selection) {
        try {
            switch (selection) {
                case 1: // Logout
                    handleLogout();
                    return true;
                case 2: // Create Game
                    handleCreateGame();
                    break;
                case 3: // List Games
                case 4: // Play Game
                case 5: // Observe Game
                default:
                    throw new RuntimeException("Error: Invalid integer input in postLoginUI()");
            }
        } catch (Exception exception) {
            println("Error: " + exception.getMessage());
        }
        return false; // Continue the pre-login UI loop
    }

    private static void handleLogout() throws Exception {
        println("Phew...One step closer to leaving me in peace!");

        LogoutResult logoutResult = serverFacade.logout( new LogoutRequest( authToken ) );
        if (logoutResult.message() != null) {
            println("Error: " + logoutResult.message());
            return;
        }
        preLoginMenuLoop(); //???
    }

    private static void handleCreateGame() throws Exception {
        String gameName = getStringInput("What will you name this so-called 'game'? ");

        CreateResult createResult = serverFacade.createGame(new CreateRequest(authToken, gameName));
        if (createResult.message() != null) {
            println("Error: " + createResult.message());
            return;
        }
        println("Wonderful name! I'm sure your mother would be proud of you.");
    }

    private static void handleListGames() throws Exception {
        println("Fine. Let me collect some personal info first for blackmail purposes.");
        String username = getStringInput("Username? ");
        String password = getStringInput("Password? ");
        String email = getStringInput("Email? ");

        RegisterResult registerResult = serverFacade.register(new RegisterRequest(username, password, email));
        if (registerResult.message() != null) {
            println("Error: " + registerResult.message());
            return;
        }

        LoginResult loginResult = serverFacade.login(new LoginRequest(username, password));
        if (loginResult.message() != null) {
            println("Error: " + loginResult.message());
            return;
        }

        postLoginUI();
    }

    private static void handleJoinGame() throws Exception {
        println("Fine. Let me collect some personal info first for blackmail purposes.");
        String username = getStringInput("Username? ");
        String password = getStringInput("Password? ");
        String email = getStringInput("Email? ");

        RegisterResult registerResult = serverFacade.register(new RegisterRequest(username, password, email));
        if (registerResult.message() != null) {
            println("Error: " + registerResult.message());
            return;
        }

        LoginResult loginResult = serverFacade.login(new LoginRequest(username, password));
        if (loginResult.message() != null) {
            println("Error: " + loginResult.message());
            return;
        }

        postLoginUI();
    }

    private static void handleObserveGame() throws Exception {
        println("Fine. Let me collect some personal info first for blackmail purposes.");
        String username = getStringInput("Username? ");
        String password = getStringInput("Password? ");
        String email = getStringInput("Email? ");

        RegisterResult registerResult = serverFacade.register(new RegisterRequest(username, password, email));
        if (registerResult.message() != null) {
            println("Error: " + registerResult.message());
            return;
        }

        LoginResult loginResult = serverFacade.login(new LoginRequest(username, password));
        if (loginResult.message() != null) {
            println("Error: " + loginResult.message());
            return;
        }

        postLoginUI();
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

    private static void print(String string) {
        out.print( string );
    }

    private static void printf(String string) {
        out.printf( string );
    }

    private static void println(String string) {
        out.println( string );
    }

}
