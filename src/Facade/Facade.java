package Facade;

import MyMapDB.*;
import Repository.*;
import Service.*;

public class Facade {
    public static boolean buyAttempt(int vendingMachineItemId, int quantity){
        VendingMachineItem vendingMachineItem = Repository.vendingMachineItemById(vendingMachineItemId);
        if(vendingMachineItem.getQuantity()>=quantity && vendingMachineItem.getPrice()*quantity<= UserService.currentUser.getMoney()){
            Service.buy(vendingMachineItem,quantity);
            return true;
        }else {
            return false;
        }
    }

    public static boolean addItemsToVendingMachineAttempt(Integer itemId, Integer productTypeId, Integer price, Integer quantity){
        if (UserService.currentUser.getUserType()== User.UserType.OPERATOR) {
            Service.addItemsToVendingMachine(itemId, productTypeId, price, quantity);
            return true;
        }
        return false;
    }

    public static boolean itemSlotIsOccupied(Integer itemId){
        return Service.vendingMachineItemById(itemId)!=null;
    }
    public static boolean resupplyItemsInVendingMachine(Integer itemId, Integer quantity){
        VendingMachineItem  vendingMachineItem = Service.vendingMachineItemById(itemId);
        return addItemsToVendingMachineAttempt(itemId, vendingMachineItem.getProductTypeId(), vendingMachineItem.getPrice(), quantity);
    }

    public static boolean addNewProductTypeAttempt(String name){
        if (UserService.currentUser.getUserType()== User.UserType.OPERATOR) {
            Service.addNewProductType(name);
            return true;
        }
        return false;
    }

    public static Service.UserService.LoginAttemptResult login(String username, String password){
        return UserService.logIn(username, password);
    }

    public static void registerNewUser(String username, String password){
        UserService.registerNewUser(username,password,100000);
    }

}
