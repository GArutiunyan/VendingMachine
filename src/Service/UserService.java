package Service;

import MyMapDB.User;
import Repository.Repository;

public class UserService {
    public static boolean loginStatus = false;
    public static User currentUser;

    public enum LoginAttemptResult{
        SUCCESS, USERNAME_DOES_NOT_EXIST,WRONG_PASSWORD
    }
    public static boolean checkLogInStatus(){
        return loginStatus;
    }
    public static LoginAttemptResult logIn(String username, String password){
        User user = Repository.userSelectByUserName(username);
        if(user==null){
            return LoginAttemptResult.USERNAME_DOES_NOT_EXIST;
        }
        if(user.getPassword().equals(password)){
            currentUser = user;
            return LoginAttemptResult.SUCCESS;
        }
        return LoginAttemptResult.WRONG_PASSWORD;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
}
