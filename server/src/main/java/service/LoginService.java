package service;

import dataAccess.auth.MemoryAuthDAO;
import dataAccess.exceptions.FailureResponse401;
import dataAccess.exceptions.FailureResponse500;
import dataAccess.user.MemoryUserDAO;
import model.LoginRequest;
import model.UserData;


public class LoginService {
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    private MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    private String username;
    private String password;

    public LoginService(LoginRequest loginRequest) {
        this.username = loginRequest.username();
        this.password = loginRequest.password();
    }

    public String login() throws FailureResponse401, FailureResponse500 {
        // Get corresponding user data from userTable
        UserData tableData = memoryUserDAO.getUser(username);
        // Make sure the passwords match (after making sure tableData is not null)
        if (tableData == null) throw new FailureResponse401();
        if (!this.password.equals(tableData.password())) throw new FailureResponse401();
        // Eliminate
        // Create a new authData entry in the authTable for the username
        // Return the generated authToken
        return memoryAuthDAO.createAuthToken(username);
    }

}
