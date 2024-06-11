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
        if (setupLevel == SetupLevel.JOIN_GAME) return;
    }


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    @Order( 1 )
    public void ClearEmptyDatabase() {
        Assertions.assertDoesNotThrow( () -> {
            serverFacade.clearApplication();
        } );
    }

    @Test
    @Order( 2 )
    public void ClearPopulatedDatabase() {
        Assertions.assertDoesNotThrow( () -> {
            serverFacade.clearApplication();
        } );
    }

    @Test
    @Order( 13 )
    void RegisterUser() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.CLEAR_DATABASE );
            serverFacade.register( new RegisterRequest( Constants.USERNAME, Constants.PASSWORD, Constants.EMAIL ) );
        } );
    }

    @Test
    @Order( 14 )
    void RegisterRepeatedUser() {
        Assertions.assertDoesNotThrow( () -> setupForServerFacadeTests( SetupLevel.REGISTER_USER ) );
        Assertions.assertThrows( IOException.class, () -> {
            serverFacade.register( new RegisterRequest( Constants.USERNAME, Constants.PASSWORD, Constants.EMAIL ) );
        } );
    }

    @Test
    @Order( 9 )
    void LoginWithValidCredentials() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.REGISTER_USER );
            authToken = serverFacade.login( new LoginRequest( Constants.USERNAME, Constants.PASSWORD ) ).authToken();
        } );
    }

    @Test
    @Order( 10 )
    void LoginWithInvalidCredentials() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.REGISTER_USER );
        } );
        Assertions.assertThrows( IOException.class, () -> {
            serverFacade.login( new LoginRequest( Constants.USERNAME, "Constants.PASSWORD" ) );
        } );
    }

    @Test
    @Order( 3 )
    void CreateGameWithValidAuthToken() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.LOGIN_USER );
            serverFacade.createGame( new CreateRequest( authToken, Constants.GAME_NAME ) );
        } );
    }

    @Test
    @Order( 4 )
    void CreateGameWithInvalidAuthToken() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.LOGIN_USER );
        } );
        Assertions.assertThrows( IOException.class, () -> {
            serverFacade.createGame( new CreateRequest( "INVALID", Constants.GAME_NAME ) );
        } );
    }

    @Test
    @Order( 5 )
    void JoinGameWithValidTeamColor() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.CREATE_GAME );
            serverFacade.joinGame( new JoinRequest( authToken, "WHITE", gameID ) );
        } );
    }

    @Test
    @Order( 6 )
    void JoinGameWithInvalidTeamColor() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.CREATE_GAME );
        } );
        Assertions.assertThrows( IOException.class, () -> {
            serverFacade.joinGame( new JoinRequest( authToken, "INVALID", gameID ) );
        } );
    }

    @Test
    @Order( 7 )
    void ListEmptyDatabase() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.LOGIN_USER );
            serverFacade.listGames( new ListRequest( authToken ) );
        } );
    }

    @Test
    @Order( 8 )
   void ListPopulatedDatabase() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.CREATE_GAME );
            serverFacade.listGames( new ListRequest( authToken ) );
        } );
    }



    @Test
    @Order( 11 )
    void LogoutWithValidCredentials() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.LOGIN_USER );
            serverFacade.logout( new LogoutRequest( authToken ) );
        } );
    }

    @Test
    @Order( 12 )
    void LogoutWithInvalidCredentials() {
        Assertions.assertDoesNotThrow( () -> {
            setupForServerFacadeTests( SetupLevel.LOGIN_USER );
        } );
        Assertions.assertThrows( IOException.class, () -> {
            serverFacade.logout( new LogoutRequest( "authToken" ) );
        } );
    }



}
