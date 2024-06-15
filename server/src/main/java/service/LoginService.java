package service;


import dataaccess.exceptions.DataAccessException;
import dataaccess.auth.SQLAuthDAO;
import dataaccess.exceptions.Error401Unauthorized;
import dataaccess.user.SQLUserDAO;
import model.custom.LoginRequest;
import model.original.UserData;
import org.mindrot.jbcrypt.BCrypt;


public class LoginService {
    private final SQLAuthDAO sqlAuthDao;
    private final SQLUserDAO sqlUserDAO;
    private final String username;
    private final String password;


    public LoginService( LoginRequest loginRequest ) throws DataAccessException {
        this.username = loginRequest.username();
        this.password = loginRequest.password();
        sqlAuthDao = new SQLAuthDAO();
        sqlUserDAO = new SQLUserDAO();
    }


    public String login() throws Error401Unauthorized, DataAccessException {
        // Get corresponding user data from userTable
        UserData tableData = sqlUserDAO.getUser( username );
        // Make sure the passwords match (after making sure tableData is not null)
        if ( tableData == null ) {
            throw new Error401Unauthorized();
        }
        if ( !BCrypt.checkpw( password, tableData.password() ) ) {
            throw new Error401Unauthorized();
        }
        // Create a new authData entry in the authTable for the username
        String authToken = sqlAuthDao.createAuthToken( username );
        // Return the generated authToken
        return authToken;
    }

}
