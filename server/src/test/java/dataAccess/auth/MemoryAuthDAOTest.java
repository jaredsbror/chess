package dataAccess.auth;

import dataAccess.user.MemoryUserDAO;
import model.AuthData;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemoryAuthDAOTest {

    private static final String username = "username";
    private static final String authToken = "authToken";

    @Test
    @Order(1)
    void isAuthTableEmpty() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        boolean isEmpty = (Objects.equals(memoryAuthDAO.toString(), "{}" ));
        assertEquals(memoryAuthDAO.isEmpty(), isEmpty, "authTable should be empty.");
        System.out.println(memoryAuthDAO);
    }

    @Test
    @Order(2)
    void isAuthTableNotEmpty() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        memoryAuthDAO.createAuthToken(username);
        boolean isEmpty = (Objects.equals(memoryAuthDAO.toString(), "{}" ));
        assertEquals(memoryAuthDAO.isEmpty(), isEmpty, "authTable should not be empty");
        System.out.println(memoryAuthDAO);
    }

    @Test
    @Order(3)
    void createNewAuthToken() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        assertNull(memoryAuthDAO.createAuthToken(username), "createAuthToken() should return null.");
        assertFalse(memoryAuthDAO.isEmpty(), "authTable should not be empty.");
        System.out.println(memoryAuthDAO);
    }

    @Test
    @Order(4)
    void createDuplicateAuthToken() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        memoryAuthDAO.createAuthToken(username);
        assertNotNull(memoryAuthDAO.createAuthToken(username), "createAuthToken() should not return null.");
        assertFalse(memoryAuthDAO.isEmpty(), "authTable should not be empty.");
        System.out.println(memoryAuthDAO);
    }

    @Test
    @Order(5)
    void clearEmptyTable() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        memoryAuthDAO.clear();
        assertTrue(memoryAuthDAO.isEmpty(), "authTable should be empty.");
        System.out.println(memoryAuthDAO);
    }

    @Test
    @Order(6)
    void clearPopulatedTable() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        memoryAuthDAO.createAuthToken("user1");
        memoryAuthDAO.createAuthToken("user2");
        memoryAuthDAO.createAuthToken("user3");
        memoryAuthDAO.clear();
        assertTrue(memoryAuthDAO.isEmpty(), "authTable should be empty.");
        System.out.println(memoryAuthDAO);
    }

    @Test
    @Order(11)
    void getAuthDataFromEmptyTableGivenUsername() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        System.out.println(memoryAuthDAO);
    }

    @Test
    @Order(12)
    void getAuthDataFromPopulatedTableGivenUsername() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        System.out.println(memoryAuthDAO);
    }

    @Test
    @Order(13)
    void getAuthDataFromEmptyTableGivenAuthToken() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        System.out.println(memoryAuthDAO);
    }

    @Test
    @Order(14)
    void notGetAuthDataFromPopulatedTableGivenAuthToken() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        System.out.println(memoryAuthDAO);
    }

    @Test
    @Order(7)
    void deleteAuthDataFromEmptyTableGivenUsername() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        assertNull(memoryAuthDAO.deleteAuthDataGivenUsername(username), "deleteAuthDataGivenUsername() should return null.");
        System.out.println(memoryAuthDAO);
    }

    @Test
    @Order(8)
    void deleteAuthDataFromPopulatedTableGivenUsername() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        memoryAuthDAO.createAuthToken(username);
        assertNotNull(memoryAuthDAO.deleteAuthDataGivenUsername(username), "deleteAuthDataGivenUsername() should not return null.");
        System.out.println(memoryAuthDAO);
    }

    @Test
    @Order(9)
    void deleteAuthDataFromEmptyTableGivenAuthToken() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        assertNull(memoryAuthDAO.deleteAuthDataGivenAuthToken(authToken), "deleteAuthDataGivenAuthToken() should return null.");
        System.out.println(memoryAuthDAO);
    }

    @Test
    @Order(10)
    void deleteAuthDataFromPopulatedTableGivenAuthToken() {
        MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
        memoryAuthDAO.createAuthToken(username);
        assertNotNull(memoryAuthDAO.deleteAuthDataGivenAuthToken(authToken), "deleteAuthDataGivenAuthToken() should not return null.");
        System.out.println(memoryAuthDAO);
    }


}