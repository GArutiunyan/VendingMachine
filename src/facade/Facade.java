package facade;

import service.*;

import dtos.*;

import java.util.List;
import java.util.Map;


public class Facade {


    public static List<UserDTO> getUserTable() {
        return Service.getUserTable();
    }

    public static List<ProductDTO> getProductTypeTable() {
        return Service.getProductTypeTable();
    }

    public static Map<Integer, VendingMachineItemDTO> getVendingMachineItemTable() {
        return Service.getVendingMachineItemTable();
    }

    public static ItemRequestDTO stringToItemRequestDTO(String stringItemRequest) {
        return Service.stringToItemRequest(stringItemRequest);
    }

    public static ItemRequestDTO convertItemRequestToDTO(Service.ItemRequest itemRequest){
        return Service.convertItemRequestToDTO(itemRequest);
    }

    public static boolean checkItemRequest(ItemRequestDTO itemRequestDTO){
        return Service.checkItemRequest(itemRequestDTO);
    }

    public static List<PurchasedProductDTO> getPurchasedProductTable(int userId) {
        return Service.getPurchasesByUserId(userId);
    }

    public static int getUserId() {
        return UserService.getCurrentUser().getId();
    }

    public enum ItemSlotStatus {
        OCCUPIED, EMPTY, DOES_NOT_EXIST
    }

    public static ProductDTO productById(Integer productId) {
        return Service.productById(productId);
    }


    public static boolean buyAttempt(String stringItemRequest) {
        ItemRequestDTO itemRequestDTO = stringToItemRequestDTO(stringItemRequest);
        checkItemRequest(itemRequestDTO);
        VendingMachineItemDTO vendingMachineItemDTO = Service.vendingMachineItemById(itemRequestDTO.itemId);
        if(vendingMachineItemDTO.getQuantity() < itemRequestDTO.quantity){
            System.out.println("Нет продуктов");
            return false;
        }
        if(vendingMachineItemDTO.getPrice() * itemRequestDTO.quantity > UserService.currentUser.getMoney()){
            System.out.println("Недостаточно средств");
            return false;
        }
        Service.buy(vendingMachineItemDTO, itemRequestDTO.quantity);
        return true;
    }

    public static boolean userIsOperator() {
        return UserService.currentUser.getUserType() == UserDTO.UserType.OPERATOR;
    }

    public static int userMoney() {
        return UserService.currentUser.getMoney();
    }
    public static boolean addItemsToVendingMachineAttempt(Integer itemId, Integer productTypeId, Integer price, Integer quantity) {

        if (Service.vendingMachineItemById(itemId).getQuantity() > VendingMachineCharacteristics.maxItemsInSlot) {
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
        if (itemId < VendingMachineCharacteristics.getMinIndex() || itemId > VendingMachineCharacteristics.getMaxIndex()) {
            return ItemSlotStatus.DOES_NOT_EXIST;
        }
        Service.vendingMachineItemById(itemId);
        if (Service.vendingMachineItemById(itemId).getProductTypeId() == -1) {
            return ItemSlotStatus.EMPTY;
        }
        return ItemSlotStatus.OCCUPIED;
    }

    public static boolean resupplyItemsInVendingMachine(Integer itemId, Integer quantity) {
        VendingMachineItemDTO vendingMachineItemDTO = Service.vendingMachineItemById(itemId);
        int newQuantity = vendingMachineItemDTO.getQuantity() + quantity;
        if (newQuantity > VendingMachineCharacteristics.maxItemsInSlot) {
            return false;
        }
        if (!userIsOperator()) {
            return false;
        }
        Service.vendingMachineItemQuantityInsert(itemId, newQuantity);
        return true;
    }
    public static VendingMachineItemDTO vendingMachineItemById(int itemId){
        return Service.vendingMachineItemById(itemId);
    }

    public static boolean addNewProductTypeAttempt(String name) {
        if (UserService.currentUser.getUserType() == UserDTO.UserType.OPERATOR) {
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

    public static LoginAttemptResult logIn(String username, String password) {
        return UserService.logIn(username, password);
    }

    public static void loadMyMapDB(){
        Service.loadMyMapDB();
    }

    public static void saveMyMapDBToFile(){
        Service.saveMyMapDBToFile();
    }

}
