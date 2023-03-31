import Controller.Controller;
import Facade.Facade;
import Service.UserService;

public class Main {
    public static void main(String[] args) {
        Controller.start();
        if(!Facade.isLoggedIn()){
            return;
        }

    }
}