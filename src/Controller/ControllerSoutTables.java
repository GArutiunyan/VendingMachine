package Controller;

import Facade.Facade;
import MyMapDB.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ControllerSoutTables {
    public static void soutUsers(){
        List<User> users = Facade.getUserTable();
        int usernameMaxLength = 8;
        int passwordMaxLength = 8;
        for(User user:users){
            int usernameLength = user.getUserName().length();
            if (usernameLength>usernameMaxLength){
                usernameMaxLength = usernameLength;
            }
            int passwordLength = user.getPassword().length();
            if (passwordLength>passwordMaxLength){
                passwordMaxLength = passwordLength;
            }
        }
        usernameMaxLength++;
        passwordMaxLength++;
        String format = "%"+usernameMaxLength+"s%"+passwordMaxLength+"s%9s";
        System.out.format(format, "username", "password", "type");
        System.out.println();
        for(User user:users){
            System.out.format(format, user.getUserName(), user.getPassword(), user.getUserType());
            System.out.println();
        }
//        List<Product> products = Facade.getProductTypeTable();


    }

    public static void soutVendingMachine() {
        List<VendingMachineItem> vendingMachineItems = Facade.getVendingMachineItemTable();

    }
}
