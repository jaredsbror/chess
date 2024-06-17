package ui;


import chess.ChessBoard;
import chess.ChessGame;
import client.ServerFacade;
import datatypes.ServerMessageObserver;
import model.custom.*;
import model.original.GameData;
import websocket.messages.ErrorCommand;
import websocket.messages.LoadGameCommand;
import websocket.messages.NotificationCommand;
import websocket.messages.ServerMessage;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static ui.ChessUIConstants.ERASE_SCREEN;

// Have Handler use switch-case to deserialize/serialize messages and use notify method to process them in ChessClient.
public class ChessClient implements ServerMessageObserver {
    private final PrintStream out = new PrintStream( System.out, true, StandardCharsets.UTF_8 );
    private final TerminalUI terminalUI = new TerminalUI();
    private final ChessBoardUI chessBoardUI = new ChessBoardUI();
    private final ServerFacade serverFacade;
    private final Map<Integer, Integer> gameNumbersToGameIDs = new HashMap<>();
    private Scanner scanner = new Scanner( System.in );
    private String authToken = null;
    private Integer gameID = null;
    private Integer gameIndex = null;
    private GameData gameData = null;
    private String playerName = null;
    private ChessGame.TeamColor teamColor;

    public ChessClient( int port) throws Exception {
        serverFacade = new ServerFacade(port);
        teamColor = ChessGame.TeamColor.WHITE;
    }

    /*
    Help	Displays text informing the user what actions they can take.
    Quit	Exits the program.
    Login	Prompts the user to input login information.
            Calls the server login API to log in the user.
            When successfully logged in, the client should transition to the Postlogin UI.
    Register	Prompts the user to input registration information.
                Calls the server register API to register and login the user.
                If successfully registered, the client should be logged in and transition
                to the Postlogin UI.
     */
    public void preLoginUI() throws Exception {
        // Reset the terminal screen
        terminalUI.resetTerminalColors( out );
        // Print out the menu
        println( "\nWelcome to CS 240 Chess! Type 'Help' to get started." );
        print(">>> ");
        // Prompt for 'Help' input
        waitForHelpInput();
        // Start the pre-login menu
        preLoginMenuLoop();
    }


    private void preLoginMenuLoop() throws Exception {
        boolean exitPreLogin = false;
        // Prelogin Menu loop
        while ( !exitPreLogin ) {
            printPreLoginMenu();
            int integerInput = getValidIntegerInput( 1, 5 );
            exitPreLogin = handlePreLoginMenuSelection( integerInput );
        }
    }


    // Helper method to validate string input
    private String getStringInput( String prompt ) throws Exception {
        println( prompt );
        print(">>> ");
        scanner = new Scanner( System.in );
        return scanner.nextLine();
    }


    // Helper method to validate integer input
    private int getValidIntegerInput( int min, int max ) throws Exception {
        int number;
        // If the user types in a valid number, continue. Otherwise, keep scanning.
        while ( true ) {
            print(">>> ");
            scanner = new Scanner( System.in );
            if ( scanner.hasNextInt() ) {
                number = scanner.nextInt();
                if ( number >= min && number <= max ) {
                    return number; // Exit the loop if input is valid
                } else {
                    println( "Invalid input. Let's just enter an integer between " + min + " and " + max + ", shall we?." );
                }
            } else {
                scanner.next(); // Discard the invalid input token
                println( "Invalid input. Please just make my life easy and enter an integer." );
            }
        }
    }


    private void waitForHelpInput() throws Exception {
        while ( true ) {
            String inputString = scanner.nextLine().trim();
            if ( inputString.equalsIgnoreCase( "Help" ) ) {
                println( "Good. You can follow instructions!" );
                break;
            }
            println( "Invalid input. Please spare me the misery and just type 'Help'..." );
        }
    }


    private void printPreLoginMenu() throws Exception {
        println( "\n\n1. Register" );
        println( "2. Login" );
        println( "3. Quit (and leave me in peace...highly recommended)" );
        println( "4. Help (and I'll be forced to print out this menu again)" );
        println( "5. Clear Database (proceed at your own risk!" );
    }


    private boolean handlePreLoginMenuSelection( int selection ) throws Exception {
        try {
            switch ( selection ) {
                case 1:
                    handleRegister();
                    return true; // Exit pre-login UI after successful registration
                case 2:
                    handleLogin();
                    return true; // Exit pre-login UI after successful login
                case 3:
                    println( "Good riddance...They don't pay me nearly enough to deal with you guys." );
                    System.exit( 0 );
                case 4:
                    break; // Will cause the menu to be printed again
                case 5:
                    handleClearDatabase();
                    break;
                default:
                    throw new RuntimeException( "Error: Invalid integer input in preLoginUI()" );
            }
        } catch ( Exception exception ) {
            println( "Error: " + exception.getMessage() );
        }
        return false; // Continue the pre-login UI loop
    }


    private void handleRegister() throws Exception {
        println( "Fine. Let me collect some personal info first for blackmail purposes." );
        String username = getStringInput( "Username? " );
        String password = getStringInput( "Password? " );
        String email = getStringInput( "Email? " );

        RegisterResult registerResult = serverFacade.register( new RegisterRequest( username, password, email ) );
        if ( registerResult.message() != null ) {
            println( "Error: " + registerResult.message() );
            return;
        }

        LoginResult loginResult = serverFacade.login( new LoginRequest( username, password ) );
        if ( loginResult.message() != null ) {
            println( "Error: " + loginResult.message() );
            return;
        }
        authToken = loginResult.authToken();
        postLoginUI();
    }


    private void handleLogin() throws Exception {
        String username = getStringInput( "Remind me again what your username was?" );
        String password = getStringInput( "And your password?" );

        LoginResult loginResult = serverFacade.login( new LoginRequest( username, password ) );
        if ( loginResult.message() != null ) {
            println( "Error: " + loginResult.message() );
            return;
        }
        authToken = loginResult.authToken();
        postLoginUI();
    }


    private void handleClearDatabase() throws Exception {
        println( "Confirm Database Clearing (y/n):" );
        print(">>> ");

        // Confirm database deletion
        scanner = new Scanner( System.in );
        String confirmation = scanner.nextLine();
        if ( confirmation.equalsIgnoreCase( "y" ) ) {
            println( "Confirming Database Clearing" );
            // Connect to the server
            ClearResult clearResult = serverFacade.clearApplication();
            if ( clearResult.message() != null ) {
                println( "Error: " + clearResult.message() );
            }
        } else {
            println( "Aborting Database Clearing...Did not receive a positive response" );
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
    public void postLoginUI() throws Exception {
        // Reset the terminal screen
        print( ERASE_SCREEN );
        terminalUI.resetTerminalColors( out );

        boolean exitPostLogin = false;
        // Prelogin Menu loop
        while ( !exitPostLogin ) {
            printPostLoginMenu();
            int integerInput = getValidIntegerInput( 1, 5 );
            exitPostLogin = handlePostLoginMenuSelection( integerInput );
        }
    }


    private void printPostLoginMenu() throws Exception {
        println( "\n\nThe Ultimate Chess 240 Game Menu 2.0" );
        println( "1. Logout (Maybe someone like you should consider this?)" );
        println( "2. Create Game" );
        println( "3. List Games (Avoid this option at all costs! I'm allergic to work!)" );
        println( "4. Play Game" );
        println( "5. Observe Game" );
    }


    private boolean handlePostLoginMenuSelection( int selection ) throws Exception {
        try {
            switch ( selection ) {
                case 1: // Logout
                    handleLogout();
                    return true;
                case 2: // Create Game
                    handleCreateGame();
                    break;
                case 3: // List Games
                    handleListGames();
                    break;
                case 4: // Play Game
                    handleJoinGame();
                    break;
                case 5: // Observe Game
                    handleObserveGame();
                    break;
                default:
                    throw new RuntimeException( "Error: Invalid integer input in postLoginUI()" );
            }
        } catch ( Exception exception ) {
            println( "Error: " + exception.getMessage() );
        }
        return false; // Continue the post-login UI loop
    }


    private void handleLogout() throws Exception {
        println( "Phew...One step closer to leaving me in peace!" );
        // Connect to the server
        LogoutResult logoutResult = serverFacade.logout( new LogoutRequest( authToken ) );
        if ( logoutResult.message() != null ) {
            println( "Error: " + logoutResult.message() );
            return;
        }
        preLoginMenuLoop(); //???
    }


    private void handleCreateGame() throws Exception {
        String gameName = getStringInput( "What will you name this farce of a 'game'? " );

        // Connect to the server
        CreateResult createResult = serverFacade.createGame( new CreateRequest( authToken, gameName ) );
        if ( createResult.message() != null ) {
            println( "Error: " + createResult.message() );
            return;
        }
        println( "Wonderful name! I'm sure your mother would be proud of you." );
    }


    private void handleListGames() throws Exception {
        // Connect to the server
        ListResult listResult = serverFacade.listGames( new ListRequest( authToken ) );
        if ( listResult.message() != null ) {
            println( "Error: " + listResult.message() );
            return;
        }
        // Print out the games and assign the matching index numbers to their ids
        gameNumbersToGameIDs.clear();
        for ( int index = 0; index < listResult.games().size(); index++ ) {
            println( "Game #" + index + " NAME[" + listResult.games().get( index ).gameName() + "] WHITE[" +  listResult.games().get( index ).whiteUsername() + "] BLACK[" +  listResult.games().get( index ).blackUsername() + "]");
            gameNumbersToGameIDs.put( index, listResult.games().get( index ).gameID() );
        }
    }


    private void handleJoinGame() throws Exception {
        String playerColor = getStringInput( "Player Color?" );
        if (playerColor.equalsIgnoreCase( "WHITE" ) ) teamColor = ChessGame.TeamColor.WHITE;
        else if ( playerColor.equalsIgnoreCase( "BLACK" ) ) teamColor = ChessGame.TeamColor.BLACK;
        println( "Game Number? (i.e. Game #1, #2, etc... as shown after listing games))\n[Note that unless you list the games first, joining a game will return an error, even with a valid game number!]" );
        gameIndex = getValidIntegerInput( 0, 1000000 );
        gameID = gameNumbersToGameIDs.get( gameIndex );
        // Display the game
        playGameUI();
    }


    private void handleObserveGame() throws Exception {
        println( "Game Number? (displayed before each listed game, e.g. 1) GameData..." );
        gameIndex = getValidIntegerInput( 0, 1000000 );
        gameID = gameNumbersToGameIDs.get( gameIndex );
        // Display the game
        observeGameUI();
    }


    public void playGameUI() throws Exception {
        // Draw menu and chessboard
        drawPlayGameUIMenu();
        // Loop
        playGameUILoop();
    }

    public void drawPlayGameUIMenu() throws Exception {
        println("\n\n1. Help");
        println("2. Redraw Chess Board");
        println("3. Leave");
        println("4. Make Move");
        println("5. Resign");
        println("6. Highlight Legal Moves");
        // Connect to the server to get the gameData
        gameData = getGameData( gameID, true );
        drawGameBoard(  gameData, teamColor );
    }

    public void playGameUILoop() throws Exception  {
        // Game UI loop
        while (true) {
            int result = getValidIntegerInput( 1, 6 );
            switch ( result ) {
                case 1: // Help
                    drawPlayGameUIMenu();
                    break;
                case 2: // Redraw Chessboard
                    drawPlayGameUIMenu();
                    break;
                case 3: // Leave Game
                    handleLeaveGame();
                    return;
                case 4: // Make move
                    handleMakeMove();
                    break;
                case 5: // Resign
                    handleResign();
                    return;
                case 6: // Highlight Legal Moves
                    handleHighlightLegalMoves();
                    break;
                default:
                    println("Error: Invalid numerical input in playGameUILoop()");
                    break;
            }
        }
    }

    public void observeGameUI() throws Exception  {
        // Draw menu and chessboard
        drawObserveGameUIMenu();
        // Loop
        observeGameUILoop();
    }

    public void drawObserveGameUIMenu() throws Exception {
        println("\n\n1. Help");
        println("2. Redraw Chess Board");
        println("3. Leave");
        println("4. Highlight Legal Moves");
        // Connect to the server to get the gameData
        gameData = getGameData( gameID, true );
        teamColor = ChessGame.TeamColor.WHITE;
        drawGameBoard(  gameData, teamColor );
    }

    public void observeGameUILoop() throws Exception  {
        // Observe Game UI Loop
        while (true) {
            int result = getValidIntegerInput( 1, 4 );
            switch ( result ) {
                case 1: // Help
                    drawObserveGameUIMenu();
                    break;
                case 2: // Redraw Chessboard
                    drawObserveGameUIMenu();
                    break;
                case 3: // Leave Game
                    handleLeaveGame();
                    return;
                case 4: // Highlight Legal Moves
                    handleHighlightLegalMoves();
                    break;
                default:
                    println("Error: Invalid numerical input in observeGameUILoop()");
                    break;
            }
        }
    }

    public void handleLeaveGame() throws Exception  {

    }

    public void handleMakeMove() throws Exception  {

    }

    public void handleResign() throws Exception  {

    }

    public void handleHighlightLegalMoves() throws Exception  {

    }



    public GameData getGameData(int index, boolean isGameID) throws Exception {
        // Depending on isGameID, get the gameID (either it's the gameID or the game index)
        if (isGameID) gameID = index;
        else gameID = gameNumbersToGameIDs.get( index );
        // Connect to the server to get the gameData
        ListResult listResult = serverFacade.listGames( new ListRequest( authToken ) );
        if ( listResult.message() != null ) {
            println( "Error: " + listResult.message() );
            return null;
        }
        // Locate the game with the correct gameID
        GameData gameData = null;
        for ( var game : listResult.games() ) {
            if ( gameID.equals( game.gameID() ) ) gameData = game;
        }
        return gameData;
    }


    public void drawGameBoard( GameData gameData, ChessGame.TeamColor teamColor ) throws Exception  {
        ChessBoard chessBoard = new ChessBoard( ChessBoard.parseBoard( gameData.game().toString() ) );
        chessBoard.resetBoard();
        if (teamColor == ChessGame.TeamColor.WHITE) chessBoardUI.drawBoard( chessBoard, ChessGame.TeamColor.WHITE );
        else chessBoardUI.drawBoard( chessBoard, ChessGame.TeamColor.BLACK );
    }


    private void print( String string ) {
        out.print( string );
    }


    private void println( String string ) {
        out.println( string );
    }


    // WS
    @Override
    public void notify( ServerMessage serverMessage ) throws Exception {

    }

}
