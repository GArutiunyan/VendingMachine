package MyMapDB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyMapDB implements Serializable {
    static final long SerialVersionUID = -48629262342433707L;
    private HashMap<Integer, User> userTable = new HashMap<Integer, User>();
    private HashMap<Integer, Product> productTypeTable = new HashMap<Integer, Product>();
    private HashMap<Integer, VendingMachineItem> vendingMachineItemTable = new HashMap<Integer, VendingMachineItem>();
    private HashMap<Integer, PurchasedProduct> purchasedProductTable = new HashMap<Integer, PurchasedProduct>();


    public MyMapDB(HashMap<Integer, User> userTable, HashMap<Integer, Product> productTypeTable, HashMap<Integer, VendingMachineItem> vendingMachineItemTable, HashMap<Integer, PurchasedProduct> purchasedProductTable) {
        this.userTable = userTable;
        this.productTypeTable = productTypeTable;
        this.vendingMachineItemTable = vendingMachineItemTable;
        this.purchasedProductTable = purchasedProductTable;
    }

    public MyMapDB() {

    }

    public void userInsert(String userName, String password, int money) {
        int newRecordId = userTable.size() + 1;
        User newUser = new User(newRecordId, userName, password, money, User.UserType.CUSTOMER);
        userTable.put(newRecordId, newUser);
    }

    public User userSelectById(Integer userId) {
        return userTable.get(userId);
    }

    public User userSelectByUserName(Integer userName) {
        return userTable.values().stream().filter(
                user -> user.getUserName().equals(userName)
        ).findFirst().get();
    }


    public void productInsert(String name) {
        int newRecordId = productTypeTable.size() + 1;
        Product newProduct = new Product(name);
        productTypeTable.put(newRecordId, newProduct);
    }

    public Product productById(Integer productId) {
        return productTypeTable.get(productId);
    }

    public void vendingMachineItemInsert(Integer productTypeId, Integer price, Integer quantity, Integer recordId) {
        if (vendingMachineItemTable.containsKey(recordId)) {
            VendingMachineItem item = vendingMachineItemTable.get(recordId);
            item.setPrice(price);
            item.setQuantity(quantity);
            item.setProductTypeId(productTypeId);
        } else {
            VendingMachineItem newItem = new VendingMachineItem(productTypeId, price, quantity);
            vendingMachineItemTable.put(recordId, newItem);
        }
    }

    public int priceOfItemById(Integer recordId) {
        return vendingMachineItemTable.get(recordId).getPrice();
    }

    public int quantityOfItemById(Integer recordId) {
        return vendingMachineItemTable.get(recordId).getQuantity();
    }

    public String nameOfItemById(Integer recordId) {
        int productTypeId = vendingMachineItemTable.get(recordId).getProductTypeId();
        return productTypeTable.get(productTypeId).getName();
    }

    public void purchasedProductInsert(Integer productTypeId, Integer quantity, Integer userId) {
        int newRecordId = purchasedProductTable.size() + 1;
        PurchasedProduct newPurchasedProduct = new PurchasedProduct(productTypeId, quantity, userId);
        purchasedProductTable.put(newRecordId, newPurchasedProduct);
    }

    public List<PurchasedProduct> getPurchasesByUserId(Integer userId) {
        return new ArrayList<PurchasedProduct>(purchasedProductTable.values().stream().filter(
                purchasedProduct -> purchasedProduct.getUserId() == userId
        ).toList());
    }
}
