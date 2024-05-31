package service;

import dataaccess.auth.MemoryAuthDAO;
import dataaccess.auth.SQLAuthDao;
import dataaccess.exceptions.*;
import dataaccess.user.MemoryUserDAO;
import model.custom.RegisterRequest;

public class RegisterService {
    private SQLAuthDao sqlAuthDao = new SQLAuthDao();
    private MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    private String username;
    private String password;
    private String email;

    public RegisterService(RegisterRequest registerRequest) {
        this.username = registerRequest.username();
        this.password = registerRequest.password();
        this.email = registerRequest.email();
    }

    public String register() throws Error400BadRequest, Error403AlreadyTaken, Error500Internal {
        // Verify that the user does not exist
        if (memoryUserDAO.getUser(username) != null) throw new Error403AlreadyTaken();
        // Add UserData into userTable
        memoryUserDAO.insertUser(username, password, email);
        // Add AuthData into authTable
        // Return the generated authToken
        return sqlAuthDao.createAuthToken(username);
    }


}
