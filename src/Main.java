import controller.*;
public class Main {
    private static class SaveMyMapDBToFile extends Thread {
        @Override
        public void run() {
            Controller.saveMyMapDBToFile();
        }
    }
    public static void main(String[] args) {
        SaveMyMapDBToFile saveMyMapDBToFile = new SaveMyMapDBToFile();
        Runtime.getRuntime().addShutdownHook(saveMyMapDBToFile);
        Controller.loadMyMapDB();
        while (true){
            Controller.loginScreen();
            if(!Controller.isLoggedIn()){
                System.exit(0);
            }
            Controller.mainMenu();
        }
    }
}