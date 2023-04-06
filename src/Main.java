import controller.*;
import facade.Facade;

public class Main {
    public static void main(String[] args) {
        Controller.loadMyMapDB();
        while (true){
            Controller.loginScreen();
            if(!Facade.isLoggedIn()){
                Controller.saveMyMapDBToFile();
                return;
            }
            Controller.mainMenu();
        }
    }
}