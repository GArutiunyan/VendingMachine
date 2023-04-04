import Controller.*;
import Facade.Facade;
import Service.UserService;

public class Main {
    public static void main(String[] args) {
        Controller.loadMyMapDB();
        while (true){
            Controller.loginScreen();
            if(!Facade.isLoggedIn()){
                return;
            }
            Controller.mainMenu();
        }
    }
}