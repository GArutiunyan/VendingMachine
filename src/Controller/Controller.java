package Controller;

import Facade.Facade;
import Service.Service;
import Service.UserService;

import java.util.Scanner;

public class Controller {
    static Scanner scanner = new Scanner(System.in);
    public static boolean start(){
        int input;
        while(!Facade.isLoggedIn()){
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
                    switch (loginAttemptResult){
                        case WRONG_PASSWORD -> System.out.println("WRONG_PASSWORD");
                        case USERNAME_DOES_NOT_EXIST -> System.out.println("USERNAME_DOES_NOT_EXIST");
                        case SUCCESS -> System.out.println("SUCCESS");
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
                    break;
                }
                case 3:{
                    return false;
                }
            }
        }
        return true;
    }
}
