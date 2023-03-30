package Service;
import MyMapDB.*;

import Repository.*;

import java.io.File;

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

    public static void fillMyMapDB(){
        if(new File("Vending_Machine_data.dat").isFile()){
            Repository.loadMyMapDBFromFile();
        }else{
            Repository.loadMyMapDBDefaultValues();
        }
    }

}
