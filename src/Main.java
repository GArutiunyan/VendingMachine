import Controller.*;
import Facade.Facade;
import Service.UserService;

public class Main {
    public static void main(String[] args) {
        Controller.loadMyMapDB();
        Controller.loginScreen();
        if(!Facade.isLoggedIn()){
            return;
        }

        Controller.vendingMachine();

    }
}