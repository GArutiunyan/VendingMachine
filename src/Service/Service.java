package Service;
import MyMapDB.*;

import Repository.*;

public class Service {


    public static void buy(int userId, int vendingMachineItemId, int quantity){
        User user = Repository.userSelectById(userId);
        VendingMachineItem item = Repository.vendingMachineItemById(vendingMachineItemId);
        user.setMoney(user.getMoney()-quantity*item.getPrice());
        Repository.purchasedProductInsert(item.getProductTypeId(),quantity,userId);
    }
}
