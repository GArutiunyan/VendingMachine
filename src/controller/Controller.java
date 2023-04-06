package controller;

import facade.Facade;
import my_map_db.VendingMachineItem;
import service.Service;
import service.UserService;

import java.io.IOException;
import java.util.Scanner;

public class Controller {

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void threadSleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static Scanner scanner = new Scanner(System.in);


    public static void loadMyMapDB() {
        Facade.loadMyMapDB();
    }

    public static void editVendingMachineScreen() {
        if (!Facade.userIsOperator()) {
            return;
        }
        boolean done = false;
        while (!done) {
            System.out.println("Выберите ячейку");
            System.out.println("e - отмена. Формат ввода - столбец:строка:0");
            String stringItemRequest = scanner.nextLine();
            if (stringItemRequest.equals("e")) {
                break;
            }
            Service.ItemRequest itemRequest = Facade.stringToItemRequest(stringItemRequest);
            if (!Facade.checkItemRequest(itemRequest)) {
                System.out.println("ERROR");
                continue;
            }
            VendingMachineItem vendingMachineItem = Facade.vendingMachineItemById(itemRequest.itemId);
            //System.out.println(vendingMachineItem);
            System.out.println("Что сделать?");
            if(Facade.itemSlotIsOccupied(itemRequest.itemId)== Facade.ItemSlotStatus.OCCUPIED) {
                System.out.println("1. Восполнить запас");
                System.out.println("2. Изменить цену");
                System.out.println("3. Изменить продукт");
            }else {
                System.out.println("3. Добавить продукт");
            }
            System.out.println("4. отмена");
            int input = scanner.nextInt();
            if (input == 4) {
                done = true;
                break;
            }
            switch (input) {
                case 1: {
                    while (true) {
                        System.out.println("Сколько доложить?");
                        input = scanner.nextInt();
                        if (Facade.resupplyItemsInVendingMachine(itemRequest.itemId, input)) {
                            done = true;
                            break;
                        } else {
                            System.out.println("Не помещается.");
                        }
                        ;
                    }

                    break;
                }
                case 2: {
                    System.out.println("Новая цена:");
                    int price = scanner.nextInt();
                    int productTypeId = vendingMachineItem.getProductTypeId();
                    int quantity = vendingMachineItem.getQuantity();
                    Facade.addItemsToVendingMachineAttempt(itemRequest.itemId, productTypeId, price, quantity);
                    done = true;
                    break;
                }
                case 3: {
                    while (true) {
                        ControllerSoutTables.soutProductTypes();
                        System.out.println("id продукта:");
                        int productTypeId = scanner.nextInt();
                        System.out.println("количество");
                        int quantity = scanner.nextInt();
                        System.out.println("цена:");
                        int price = scanner.nextInt();
                        if (Facade.addItemsToVendingMachineAttempt(itemRequest.itemId, productTypeId, price, quantity)) {
                            System.out.println("DONE");
                            done = true;
                            break;
                        } else {
                            System.out.println("ОШИБКА");
                        }
                    }

                }
            }

        }
    }

    public static void buyScreen() {
        System.out.println("e - отмена. Формат ввода - столбец:строка:количество");
        while (true) {
            String stringItemRequest = scanner.nextLine();
            if (stringItemRequest.equals("e")) {
                break;
            }
            if (Facade.buyAttempt(stringItemRequest)) {
                break;
            }
            ;
        }
    }

    public static void mainMenu() {
        int input;
        while (Facade.isLoggedIn()) {
            clearScreen();
            System.out.println();
            ControllerSoutTables.soutVendingMachine();
            System.out.println();
            System.out.println();
            System.out.println("Previous orders:");
            ControllerSoutTables.soutUserOrders();
            System.out.println();
            System.out.println("Баланс: " + Facade.userMoney());
            System.out.println("1. Купить");
            if (Facade.userIsOperator()) {
                System.out.println("2. Добавить продукты в автомат");
                System.out.println("3. Добавить новый тип продукта");
            }
            System.out.println("4. Выйти");
            input = scanner.nextInt();
            scanner.nextLine();
            switch (input) {
                case 1: {
                    buyScreen();
                    break;
                }
                case 2: {
                    if (Facade.userIsOperator()) {
                        editVendingMachineScreen();
                    }
                    break;
                }
                case 3: {
                    ControllerSoutTables.soutProductTypes();
                    System.out.println("Название нового продука:");
                    String name = scanner.nextLine();
                    if(Facade.addNewProductTypeAttempt(name)){
                        System.out.println("Продукт добавлен");
                    }else {
                        System.out.println("ERROR");
                    }
                    break;
                }
                case 4: {
                    Facade.userLogOut();
                    break;
                }
                default: {
                    clearScreen();
                    System.out.println("ERROR");
                    threadSleep(1000);
                }
            }

        }
    }

    public static void loginScreen() {
        int input;
        while (!Facade.isLoggedIn()) {
            clearScreen();
            ControllerSoutTables.soutUsers();
            System.out.println("1. Войти");
            System.out.println("2. Зарегистрироваться");
            System.out.println("3. Выйти");
            input = scanner.nextInt();
            scanner.nextLine();
            switch (input) {
                case 1: {
                    System.out.println("Log in:");
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    UserService.LoginAttemptResult loginAttemptResult;
                    loginAttemptResult = Facade.logIn(username, password);
                    clearScreen();
                    switch (loginAttemptResult) {
                        case WRONG_PASSWORD -> System.out.println("WRONG_PASSWORD");
                        case USERNAME_DOES_NOT_EXIST -> System.out.println("USERNAME_DOES_NOT_EXIST");
                        case SUCCESS -> System.out.println("SUCCESS");
                    }
                    threadSleep(1000);
                    break;
                }
                case 2: {
                    System.out.println("Register:");
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.print("How much money do you have: ");
                    Integer money = scanner.nextInt();
                    Facade.registerNewUser(username, password, money);
                    clearScreen();
                    break;
                }
                case 3: {
                    return;
                }
            }
        }
        return;
    }

    public static void soutVendingMachine() {
        clearScreen();
        ControllerSoutTables.soutVendingMachine();
    }

    public static void saveMyMapDBToFile(){
        Facade.saveMyMapDBToFile();
    }

}
