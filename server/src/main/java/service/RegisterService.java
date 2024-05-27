package service;

import dataAccess.auth.MemoryAuthDAO;
import dataAccess.exceptions.*;
import dataAccess.user.MemoryUserDAO;
import model.RegisterRequest;

public class RegisterService {
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    private MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    private final String username;
    private final String password;
    private final String email;

    public RegisterService(RegisterRequest registerRequest) {
        this.username = registerRequest.username();
        this.password = registerRequest.password();
        this.email = registerRequest.email();
    }

    public String register() throws FailureResponse400, FailureResponse403, FailureResponse500 {
        // Verify that the user does not exist
        if (memoryUserDAO.getUser(username) != null) {
            throw new FailureResponse403();
        }
        // Add UserData into userTable
        memoryUserDAO.insertUser(username, password, email);
        // Add AuthData into authTable
        memoryAuthDAO.createAuthToken(username);
        // Return the generated authToken
        return memoryAuthDAO.getAuthDataGivenUsername(username).authToken();
    }


}
