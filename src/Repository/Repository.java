package Repository;

import MyMapDB.*;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    public static User userInsert(String username, String password, int money) {
        int newRecordId = MyMapDB.userTable.size() + 1;
        User newUser = new User(newRecordId, username, password, money, User.UserType.CUSTOMER);
        MyMapDB.userTable.put(newRecordId, newUser);
        return newUser;
    }

    public static User userSelectById(Integer userId) {
        return MyMapDB.userTable.get(userId);
    }


    public static User userSelectByUserName(String userName) {
        return MyMapDB.userTable.values().stream().filter(
                user -> user.getUserName().equals(userName)
        ).findFirst().orElse(null);
    }

    public static void productInsert(String name) {
        int newRecordId = MyMapDB.productTypeTable.size() + 1;
        Product newProduct = new Product(newRecordId,name);
        MyMapDB.productTypeTable.put(newRecordId, newProduct);
    }

    public static Product productById(Integer productId) {
        return MyMapDB.productTypeTable.get(productId);
    }


    public static void vendingMachineItemInsert(Integer itemId, Integer productTypeId, Integer price, Integer quantity) {
        VendingMachineItem newItem = new VendingMachineItem(itemId, productTypeId, price, quantity);
        MyMapDB.vendingMachineItemTable.put(itemId, newItem);
    }
    public static VendingMachineItem vendingMachineItemById(Integer itemId) {
        return MyMapDB.vendingMachineItemTable.get(itemId);
    }

    public static void purchasedProductInsert(Integer productTypeId, Integer quantity, Integer userId) {
        int newRecordId = MyMapDB.purchasedProductTable.size() + 1;
        PurchasedProduct newPurchasedProduct = new PurchasedProduct(newRecordId, productTypeId, quantity, userId);
        MyMapDB.purchasedProductTable.put(newRecordId, newPurchasedProduct);
    }

    public static List<PurchasedProduct> getPurchasesByUserId(Integer userId) {
        return new ArrayList<PurchasedProduct>(MyMapDB.purchasedProductTable.values().stream().filter(
                purchasedProduct -> purchasedProduct.getUserId() == userId
        ).toList());
    }

}
