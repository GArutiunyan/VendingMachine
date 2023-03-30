package Service;
import MyMapDB.*;

import Repository.*;

public class Service {
    public static void buy(VendingMachineItem vendingMachineItem, int quantity){
        User user = UserService.currentUser;
        vendingMachineItem.setQuantity(vendingMachineItem.getQuantity()-quantity);
        user.setMoney(user.getMoney()-quantity*vendingMachineItem.getPrice());
        Repository.purchasedProductInsert(vendingMachineItem.getProductTypeId(),quantity,user.getUserId());
    }

    public static void addItemsToVendingMachine(Integer itemId, Integer productTypeId, Integer price, Integer quantity){
        Repository.vendingMachineItemInsert(itemId,productTypeId,price,quantity);
    }
    public static VendingMachineItem vendingMachineItemById(Integer itemId) {
        return Repository.vendingMachineItemById(itemId);
    }

    public static void addNewProductType(String name){
        Repository.productInsert(name);
    }

}
