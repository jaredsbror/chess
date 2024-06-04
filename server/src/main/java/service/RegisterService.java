package service;


import dataaccess.DataAccessException;
import dataaccess.auth.SQLAuthDAO;
import dataaccess.exceptions.Error400BadRequest;
import dataaccess.exceptions.Error403AlreadyTaken;
import dataaccess.user.SQLUserDAO;
import model.custom.RegisterRequest;
import model.original.UserData;
import org.mindrot.jbcrypt.BCrypt;


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
        UserData userData = sqlUserDAO.getUser( username );
        if ( userData != null ) throw new Error403AlreadyTaken();
        // Add UserData into userTable
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        sqlUserDAO.insertUser( username, hashedPassword, email );
        // Add AuthData into authTable
        String authToken = sqlAuthDao.createAuthToken( username );
        // Return the generated authToken
        return authToken;
    }


}
