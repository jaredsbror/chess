package service;

import dataaccess.auth.MemoryAuthDAO;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.exceptions.Error500Internal;
import dataaccess.user.MemoryUserDAO;
import model.custom.LoginRequest;
import model.original.UserData;


public class LoginService {
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    private MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    private String username;
    private String password;

    public LoginService(LoginRequest loginRequest) {
        this.username = loginRequest.username();
        this.password = loginRequest.password();
    }

    public String login() throws Error401Unauthorized, Error500Internal {
        // Get corresponding user data from userTable
        UserData tableData = memoryUserDAO.getUser(username);
        // Make sure the passwords match (after making sure tableData is not null)
        if (tableData == null) throw new Error401Unauthorized();
        if (!this.password.equals(tableData.password())) throw new Error401Unauthorized();
        // Eliminate
        // Create a new authData entry in the authTable for the username
        // Return the generated authToken
        return memoryAuthDAO.createAuthToken(username);
    }

}
