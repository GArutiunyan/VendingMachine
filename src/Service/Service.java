package Service;
import MyMapDB.*;

import Repository.*;

public class Service {

    public static boolean buyAttempt(int vendingMachineItemId, int quantity){
        VendingMachineItem vendingMachineItem = Repository.vendingMachineItemById(vendingMachineItemId);
        if(vendingMachineItem.getQuantity()>=quantity && vendingMachineItem.getPrice()*quantity<=UserService.currentUser.getMoney()){
            buy(vendingMachineItem,quantity);
            return true;
        }else {
            return false;
        }
    }
    public static void buy(VendingMachineItem vendingMachineItem, int quantity){
        User user = UserService.currentUser;
        vendingMachineItem.setQuantity(vendingMachineItem.getQuantity()-quantity);
        user.setMoney(user.getMoney()-quantity*vendingMachineItem.getPrice());
        Repository.purchasedProductInsert(vendingMachineItem.getProductTypeId(),quantity,user.getUserId());
    }

    public static void addItemsToVendingMachine(Integer itemId, Integer productTypeId, Integer price, Integer quantity){
        Repository.vendingMachineItemInsert(itemId,productTypeId,price,quantity);
    }

    public static void addNewProductType(String name){
        Repository.productInsert(name);
    }

}
