package Facade;

import MyMapDB.*;
import Repository.*;
import Service.*;

public class Facade {

    public enum ItemSlotStatus{
        OCCUPIED,EMPTY,DOES_NOT_EXIST
    }
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
        if(Service.vendingMachineItemById(itemId).getQuantity()+quantity>10){
            return false;
        }
        if (UserService.currentUser.getUserType()== User.UserType.OPERATOR) {
            Service.addItemsToVendingMachine(itemId, productTypeId, price, quantity);
            return true;
        }
        return false;
    }

    public static ItemSlotStatus itemSlotIsOccupied(Integer itemId){
        if(itemId<1||itemId>25){
            return ItemSlotStatus.DOES_NOT_EXIST;
        }
        if(Service.vendingMachineItemById(itemId)==null){
            return ItemSlotStatus.EMPTY;
        }
        return ItemSlotStatus.OCCUPIED;
    }
    public static boolean resupplyItemsInVendingMachine(Integer itemId, Integer quantity){
        VendingMachineItem vendingMachineItem = Service.vendingMachineItemById(itemId);
        if(vendingMachineItem.getQuantity()+quantity>10){
            return false;
        }
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
