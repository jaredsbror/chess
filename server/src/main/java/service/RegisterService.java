package service;

import dataAccess.auth.MemoryAuthDAO;
import dataAccess.user.MemoryUserDAO;
import model.RegisterRequest;

public class RegisterService {
    private MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    private MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    RegisterRequest registerRequest;

    public RegisterService(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
    }

    public void register() {
        if (memoryUserDAO.getUser(registerRequest.username()) != null) {

        }
    }

//    private MemoryAuthDAO
}
