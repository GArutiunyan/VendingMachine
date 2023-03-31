package Controller;

import Facade.Facade;
import Service.Service;
import Service.UserService;

import java.io.IOException;
import java.util.Scanner;

public class Controller {

    public static void clearScreen(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
    static Scanner scanner = new Scanner(System.in);


    public static void loadMyMapDB(){
        Facade.loadMyMapDB();
    }

    public static void loginScreen(){
        int input;
        while(!Facade.isLoggedIn()){
            clearScreen();
            ControllerSoutTables.soutUsers();
            System.out.println("1. Войти");
            System.out.println("2. Зарегистрироваться");
            System.out.println("3. Выйти");
            input = scanner.nextInt();
            scanner.nextLine();
            switch (input){
                case 1:{
                    System.out.println("Log in:");
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    UserService.LoginAttemptResult loginAttemptResult;
                    loginAttemptResult = Facade.logIn(username,password);
                    clearScreen();
                    switch (loginAttemptResult){
                        case WRONG_PASSWORD -> System.out.println("WRONG_PASSWORD");
                        case USERNAME_DOES_NOT_EXIST -> System.out.println("USERNAME_DOES_NOT_EXIST");
                        case SUCCESS -> System.out.println("SUCCESS");
                    }
                    try {
                        Thread.sleep(1000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    break;
                }
                case 2:{
                    System.out.println("Register:");
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.print("How much money do you have: ");
                    Integer money = scanner.nextInt();
                    Facade.registerNewUser(username,password,money);
                    clearScreen();
                    break;
                }
                case 3:{
                    return;
                }
            }
        }
        return;
    }


    public static void vendingMachine() {
        clearScreen();
        ControllerSoutTables.soutVendingMachine();
    }
}
