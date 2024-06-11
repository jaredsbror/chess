package client;


import chess.Constants;
import model.custom.*;
import org.junit.jupiter.api.*;
import server.Server;

import java.io.IOException;
import java.net.URISyntaxException;


@TestMethodOrder( MethodOrderer.OrderAnnotation.class )
public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;
    private String authToken = null;
    private int gameID = -1;
    private enum SetupLevel {
        CLEAR_DATABASE,
        REGISTER_USER,
        LOGIN_USER,
        CREATE_GAME,
        JOIN_GAME
    }

    private void setupForServerFacadeTests(SetupLevel setupLevel) throws IOException, URISyntaxException {
        serverFacade.clearApplication();
        if (setupLevel == SetupLevel.CLEAR_DATABASE) return;
        serverFacade.register( new RegisterRequest( Constants.USERNAME, Constants.PASSWORD, Constants.EMAIL  ) );
        if (setupLevel == SetupLevel.REGISTER_USER) return;
        authToken = serverFacade.login( new LoginRequest( Constants.USERNAME, Constants.PASSWORD ) ).authToken();
        if (setupLevel == SetupLevel.LOGIN_USER) return;
        gameID = serverFacade.createGame( new CreateRequest( authToken, Constants.GAME_NAME ) ).gameID();
        if (setupLevel == SetupLevel.CREATE_GAME) return;
        serverFacade.joinGame( new JoinRequest( authToken, "WHITE", gameID ) );
    }


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    @Order( 1 )
    public void clearEmptyDatabase() {
        Assertions.assertDoesNotThrow( () -> {
            serverFacade.clearApplication();
        } );
    }

    @Test
    @Order( 2 )
    public void clearPopulatedDatabase() {
        Assertions.assertDoesNotThrow( () -> {
            serverFacade.clearApplication();
        } );
    }

    @Test
    @Order( 13 )
    void registerUser() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.CLEAR_DATABASE );
            serverFacade.register( new RegisterRequest( Constants.USERNAME, Constants.PASSWORD, Constants.EMAIL ) );
        } );
    }

    @Test
    @Order( 14 )
    void registerRepeatedUser() {
        Assertions.assertDoesNotThrow( () -> setupForServerFacadeTests( SetupLevel.REGISTER_USER ) );
        Assertions.assertThrows( IOException.class, () -> serverFacade.register( new RegisterRequest( Constants.USERNAME, Constants.PASSWORD, Constants.EMAIL ) ) );
    }

    @Test
    @Order( 9 )
    void loginWithValidCredentials() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.REGISTER_USER );
            authToken = serverFacade.login( new LoginRequest( Constants.USERNAME, Constants.PASSWORD ) ).authToken();
        } );
    }

    @Test
    @Order( 10 )
    void loginWithInvalidCredentials() {
        Assertions.assertDoesNotThrow( () -> setupForServerFacadeTests( SetupLevel.REGISTER_USER ) );
        Assertions.assertThrows( IOException.class, () -> serverFacade.login( new LoginRequest( Constants.USERNAME, "Constants.PASSWORD" ) ) );
    }

    @Test
    @Order( 3 )
    void createGameWithValidAuthToken() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.LOGIN_USER );
            serverFacade.createGame( new CreateRequest( authToken, Constants.GAME_NAME ) );
        } );
    }

    @Test
    @Order( 4 )
    void createGameWithInvalidAuthToken() {
        Assertions.assertDoesNotThrow( () -> setupForServerFacadeTests( SetupLevel.LOGIN_USER ) );
        Assertions.assertThrows( IOException.class, () -> serverFacade.createGame( new CreateRequest( "INVALID", Constants.GAME_NAME ) ) );
    }

    @Test
    @Order( 5 )
    void joinGameWithValidTeamColor() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.CREATE_GAME );
            serverFacade.joinGame( new JoinRequest( authToken, "WHITE", gameID ) );
        } );
    }

    @Test
    @Order( 6 )
    void joinGameWithInvalidTeamColor() {
        Assertions.assertDoesNotThrow( () -> setupForServerFacadeTests( SetupLevel.CREATE_GAME ) );
        Assertions.assertThrows( IOException.class, () -> serverFacade.joinGame( new JoinRequest( authToken, "INVALID", gameID ) ) );
    }

    @Test
    @Order( 7 )
    void listEmptyDatabase() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.LOGIN_USER );
            serverFacade.listGames( new ListRequest( authToken ) );
        } );
    }

    @Test
    @Order( 8 )
   void listPopulatedDatabase() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.CREATE_GAME );
            serverFacade.listGames( new ListRequest( authToken ) );
        } );
    }



    @Test
    @Order( 11 )
    void logoutWithValidCredentials() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.LOGIN_USER );
            serverFacade.logout( new LogoutRequest( authToken ) );
        } );
    }

    @Test
    @Order( 12 )
    void logoutWithInvalidCredentials() {
        Assertions.assertDoesNotThrow( () -> setupForServerFacadeTests( SetupLevel.LOGIN_USER ) );
        Assertions.assertThrows( IOException.class, () -> serverFacade.logout( new LogoutRequest( "authToken" ) ) );
    }



}
