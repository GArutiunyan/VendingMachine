import controller.*;

public class Main {
    public static void main(String[] args) {
        Controller.loadMyMapDB();
        while (true){
            Controller.loginScreen();
            if(!Controller.isLoggedIn()){
                Controller.saveMyMapDBToFile();
                return;
            }
            Controller.mainMenu();
        }
    }
}