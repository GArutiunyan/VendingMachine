package Repository;

import MyMapDB.*;
import Service.Service;

import java.io.*;
import java.util.*;

public class Repository {

    static {
        MyMapDB.productTypeTable.put(-1, new Product(-1,""));
        VendingMachineItem emptySlot = new VendingMachineItem();
        for(int i = -1; i<=Service.VendingMachineCharacteristics.getMaxIndex();i++){
            vendingMachineItemInsert(i,-1,0,0);
        }
    }


    public static void userInsert(String username, String password, int money) {
        int newRecordId = MyMapDB.userTable.size() + 1;
        User newUser = new User(newRecordId, username, password, money, User.UserType.CUSTOMER);
        MyMapDB.userTable.put(newRecordId, newUser);
    }
    public static void userInsert(String username, String password, int money, User.UserType userType) {
        int newRecordId = MyMapDB.userTable.size() + 1;
        User newUser = new User(newRecordId, username, password, money, userType);
        MyMapDB.userTable.put(newRecordId, newUser);
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
        int newRecordId = MyMapDB.productTypeTable.size();
        Product newProduct = new Product(newRecordId, name);
        MyMapDB.productTypeTable.put(newRecordId, newProduct);
    }

    public static Product productById(Integer productId) {
        return MyMapDB.productTypeTable.get(productId);
    }


    public static void vendingMachineItemInsert(Integer itemId, Integer productTypeId, Integer price, Integer quantity) {
        VendingMachineItem newItem = new VendingMachineItem(itemId, productTypeId, price, quantity);
        MyMapDB.vendingMachineItemTable.put(itemId, newItem);
    }

    public static void vendingMachineItemQuantityInsert(Integer itemId, Integer quantity) {
        MyMapDB.vendingMachineItemTable.get(itemId).setQuantity(quantity);
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


    public static void saveMyMapDBToFile() {
        try (ObjectOutputStream myMapDBFile = new ObjectOutputStream(new FileOutputStream("Vending_Machine_data.dat"))) {
            myMapDBFile.writeObject(MyMapDB.userTable);
            myMapDBFile.writeObject(MyMapDB.productTypeTable);
            myMapDBFile.writeObject(MyMapDB.vendingMachineItemTable);
            myMapDBFile.writeObject(MyMapDB.purchasedProductTable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadMyMapDBDefaultValues() {
        userInsert("admin", "admin", 1000000000, User.UserType.OPERATOR);
        userInsert("", "", 100000);
        userInsert("Gena", "12345", 50000);
        productInsert("Sparkling water");
        productInsert("Sandwich");
        productInsert("Doritos");
        productInsert("Twix");
        productInsert("Haribo gummy bears");
        productInsert("Tuc");
        productInsert("IPhone 15");
        vendingMachineItemInsert(1, 1, 100, 10);
        vendingMachineItemInsert(3, 2, 150, 10);
        vendingMachineItemInsert(9, 3, 200, 4);
        vendingMachineItemInsert(12, 4, 100, 10);
        vendingMachineItemInsert(19, 5, 180, 8);
        vendingMachineItemInsert(21, 6, 100, 5);
        vendingMachineItemInsert(22, 7, 10, 10);
        vendingMachineItemInsert(25, 1, 10, 7);
    }

    public static void loadMyMapDBFromFile() {
        try (ObjectInputStream myMapDBFile = new ObjectInputStream(new FileInputStream("Vending_Machine_data.dat"))) {

            MyMapDB.userTable = (HashMap<Integer, User>) myMapDBFile.readObject();
            MyMapDB.productTypeTable = (HashMap<Integer, Product>) myMapDBFile.readObject();
            MyMapDB.vendingMachineItemTable = (HashMap<Integer, VendingMachineItem>) myMapDBFile.readObject();
            MyMapDB.purchasedProductTable = (HashMap<Integer, PurchasedProduct>) myMapDBFile.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<User> getUserTable() {
        return new ArrayList<User>(MyMapDB.userTable.values().stream().toList());
    }

    public static List<Product> getProductTypeTable() {
        return new ArrayList<Product>(MyMapDB.productTypeTable.values().stream().toList());
    }

    public static Map<Integer, VendingMachineItem> getVendingMachineItemTable() {
//        return new ArrayList<VendingMachineItem>(MyMapDB.vendingMachineItemTable.values().stream().toList());
        return (Map<Integer,VendingMachineItem>)MyMapDB.vendingMachineItemTable.clone();

    }
}
