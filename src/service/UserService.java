package service;

import repository.*;

import dtos.LoginAttemptResult;
import dtos.UserDTO;

public class UserService {
    public static boolean loginStatus = false;
    public static UserDTO currentUser = null;



    public static boolean checkLogInStatus() {
        return loginStatus;
    }

    public static LoginAttemptResult logIn(String username, String password) {
        UserDTO userDTO = Repository.userSelectByUserName(username);
        if (userDTO == null) {
            return LoginAttemptResult.USERNAME_DOES_NOT_EXIST;
        }
        if (userDTO.getPassword().equals(password)) {
            currentUser = userDTO;
            loginStatus = true;
            return LoginAttemptResult.SUCCESS;
        }
        return LoginAttemptResult.WRONG_PASSWORD;
    }

    public static void registerNewUser(String username, String password, int money) {
        UserDTO userDTO = new UserDTO(Repository.numberOfUsers()+1,money,username,password, UserDTO.UserType.CUSTOMER);
        Repository.userInsert(userDTO);
    }

    public static void logOut(){
        currentUser = null;
        loginStatus = false;
    }
    public static UserDTO getCurrentUser() {
        return currentUser;
    }
}
