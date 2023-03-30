package MyMapDB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class MyMapDB implements Serializable {
    static final long SerialVersionUID = -48629262342433707L;
    private static HashMap<Integer, User> userTable = new HashMap<Integer, User>();
    private static HashMap<Integer, Product> productTypeTable = new HashMap<Integer, Product>();
    private static HashMap<Integer, VendingMachineItem> vendingMachineItemTable = new HashMap<Integer, VendingMachineItem>();
    private static HashMap<Integer, PurchasedProduct> purchasedProductTable = new HashMap<Integer, PurchasedProduct>();



    public static void userInsert(String userName, String password, int money) {
        int newRecordId = userTable.size() + 1;
        User newUser = new User(newRecordId, userName, password, money, User.UserType.CUSTOMER);
        userTable.put(newRecordId, newUser);
    }

    public static User userSelectById(Integer userId) {
        return userTable.get(userId);
    }

    public static User userSelectByUserName(Integer userName) {
        return userTable.values().stream().filter(
                user -> user.getUserName().equals(userName)
        ).findFirst().get();
    }


    public static void productInsert(String name) {
        int newRecordId = productTypeTable.size() + 1;
        Product newProduct = new Product(newRecordId,name);
        productTypeTable.put(newRecordId, newProduct);
    }

    public static Product productById(Integer productId) {
        return productTypeTable.get(productId);
    }

    public static void vendingMachineItemInsert(Integer productTypeId, Integer price, Integer quantity, Integer recordId) {
        if (vendingMachineItemTable.containsKey(recordId)) {
            VendingMachineItem item = vendingMachineItemTable.get(recordId);
            item.setPrice(price);
            item.setQuantity(quantity);
            item.setProductTypeId(productTypeId);
        } else {
            VendingMachineItem newItem = new VendingMachineItem(recordId, productTypeId, price, quantity);
            vendingMachineItemTable.put(recordId, newItem);
        }
    }
    public static VendingMachineItem vendingMachineItemById(Integer itemId) {
        return vendingMachineItemTable.get(itemId);
    }


    public static void purchasedProductInsert(Integer productTypeId, Integer quantity, Integer userId) {
        int newRecordId = purchasedProductTable.size() + 1;
        PurchasedProduct newPurchasedProduct = new PurchasedProduct(newRecordId, productTypeId, quantity, userId);
        purchasedProductTable.put(newRecordId, newPurchasedProduct);
    }

    public static List<PurchasedProduct> getPurchasesByUserId(Integer userId) {
        return new ArrayList<PurchasedProduct>(purchasedProductTable.values().stream().filter(
                purchasedProduct -> purchasedProduct.getUserId() == userId
        ).toList());
    }
}
