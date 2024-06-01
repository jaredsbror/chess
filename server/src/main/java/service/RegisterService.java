package service;


import dataaccess.DataAccessException;
import dataaccess.auth.SQLAuthDAO;
import dataaccess.exceptions.Error400BadRequest;
import dataaccess.exceptions.Error403AlreadyTaken;
import dataaccess.user.SQLUserDAO;
import model.custom.RegisterRequest;

public class RegisterService {
    private final SQLAuthDAO sqlAuthDao;
    private final SQLUserDAO sqlUserDAO;
    private final String username;
    private final String password;
    private final String email;

    public RegisterService(RegisterRequest registerRequest) throws DataAccessException {
        // Process registerRequest variables
        this.username = registerRequest.username();
        this.password = registerRequest.password();
        this.email = registerRequest.email();
        // Try to access the database
        sqlAuthDao = new SQLAuthDAO();
        sqlUserDAO = new SQLUserDAO();
    }

    public String register() throws Error400BadRequest, Error403AlreadyTaken, DataAccessException {
        // Verify that the user does not exist
        if ( sqlUserDAO.getUser( username ) != null ) throw new Error403AlreadyTaken();
        // Add UserData into userTable
        sqlUserDAO.insertUser( username, password, email );
        // Add AuthData into authTable
        // Return the generated authToken
        return sqlAuthDao.createAuthToken( username );
    }


}
