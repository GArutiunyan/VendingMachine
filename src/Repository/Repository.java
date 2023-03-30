package Repository;

import MyMapDB.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static void loadMyMapDBDefaultValues(){
        userInsert("admin","admin",1000000000);
        userInsert("Gena","12345",50000);
        productInsert("Sparkling water");
        productInsert("Sandwich");
        productInsert("Doritos");
        productInsert("Twix");
        productInsert("Haribo gummy bears");
        productInsert("Tuc");
        productInsert("IPhone 15");
        vendingMachineItemInsert(1,1,100,10);
        vendingMachineItemInsert(3,2,150,10);
        vendingMachineItemInsert(1,3,200,4);
        vendingMachineItemInsert(1,4,100,10);
        vendingMachineItemInsert(1,5,180,8);
        vendingMachineItemInsert(1,6,100,5);
        vendingMachineItemInsert(1,7,10,10);
        vendingMachineItemInsert(1,16,10,7);
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

}
