package Facade;

import MyMapDB.*;
import Repository.*;
import Service.*;

import java.util.List;
import java.util.Map;


public class Facade {


    public static List<User> getUserTable() {
        return Service.getUserTable();
    }

    public static List<Product> getProductTypeTable() {
        return Service.getProductTypeTable();
    }

    public static Map<Integer, VendingMachineItem> getVendingMachineItemTable() {
        return Service.getVendingMachineItemTable();
    }

    public static Service.ItemRequest stringToItemRequest(String stringItemRequest) {
        return Service.stringToItemRequest(stringItemRequest);
    }

    public static boolean checkItemRequest(Service.ItemRequest itemRequest){
        return Service.checkItemRequest(itemRequest);
    }

    public static List<PurchasedProduct> getPurchasedProductTable(int userId) {
        return Service.getPurchasesByUserId(userId);
    }

    public static int getUserId() {
        return UserService.getCurrentUser().getUserId();
    }

    public enum ItemSlotStatus {
        OCCUPIED, EMPTY, DOES_NOT_EXIST
    }

    public static Product productById(Integer productId) {
        return Service.productById(productId);
    }


    public static boolean buyAttempt(String stringItemRequest) {
        Service.ItemRequest itemRequest = stringToItemRequest(stringItemRequest);
        checkItemRequest(itemRequest);
        VendingMachineItem vendingMachineItem = Repository.vendingMachineItemById(itemRequest.itemId);
        if(vendingMachineItem.getQuantity() < itemRequest.quantity){
            System.out.println("Нет продуктов");
            return false;
        }
        if(vendingMachineItem.getPrice() * itemRequest.quantity > UserService.currentUser.getMoney()){
            System.out.println("Недостаточно средств");
            return false;
        }
        Service.buy(vendingMachineItem, itemRequest.quantity);
        return true;
    }

    public static boolean userIsOperator() {
        return UserService.currentUser.getUserType() == User.UserType.OPERATOR;
    }

    public static int userMoney() {
        return UserService.currentUser.getMoney();
    }
    public static boolean addItemsToVendingMachineAttempt(Integer itemId, Integer productTypeId, Integer price, Integer quantity) {

        if (Service.vendingMachineItemById(itemId).getQuantity() > Service.VendingMachineCharacteristics.maxItemsInSlot) {
            return false;
        }
        if (!userIsOperator()) {
            return false;
        }
        Service.addItemsToVendingMachine(itemId, productTypeId, price, quantity);
        return true;
    }
    public static void userLogOut(){
        UserService.logOut();
    }

    public static ItemSlotStatus itemSlotIsOccupied(Integer itemId) {
        if (itemId < Service.VendingMachineCharacteristics.getMinIndex() || itemId > Service.VendingMachineCharacteristics.getMaxIndex()) {
            return ItemSlotStatus.DOES_NOT_EXIST;
        }
        if (Service.vendingMachineItemById(itemId) == null || Service.vendingMachineItemById(itemId).getProductTypeId()==-1) {
            return ItemSlotStatus.EMPTY;
        }
        return ItemSlotStatus.OCCUPIED;
    }

    public static boolean resupplyItemsInVendingMachine(Integer itemId, Integer quantity) {
        VendingMachineItem vendingMachineItem = Service.vendingMachineItemById(itemId);
        int newQuantity = vendingMachineItem.getQuantity() + quantity;
        if (newQuantity > Service.VendingMachineCharacteristics.maxItemsInSlot) {
            return false;
        }
        if (!userIsOperator()) {
            return false;
        }
        Service.vendingMachineItemQuantityInsert(itemId, newQuantity);
        return true;
    }
    public static VendingMachineItem vendingMachineItemById(int itemId){
        return Service.vendingMachineItemById(itemId);
    }

    public static boolean addNewProductTypeAttempt(String name) {
        if (UserService.currentUser.getUserType() == User.UserType.OPERATOR) {
            Service.addNewProductType(name);
            return true;
        }
        return false;
    }

    public static void registerNewUser(String username, String password, Integer money) {
        UserService.registerNewUser(username, password, money);
    }

    public static boolean isLoggedIn() {
        return UserService.checkLogInStatus();
    }

    public static UserService.LoginAttemptResult logIn(String username, String password) {
        return UserService.logIn(username, password);
    }

    public static void loadMyMapDB(){
        Service.loadMyMapDB();
    }

    public static void saveMyMapDBToFile(){
        Service.saveMyMapDBToFile();
    }

}
