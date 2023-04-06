package repository;

import my_map_db.*;

import dtos.*;

import java.io.*;
import java.util.*;

public class Repository {

    static {
        MyMapDB.productTypeTable.put(-1, new Product(-1,""));
        VendingMachineItem emptySlot = new VendingMachineItem();
        for(int i = -1; i<= VendingMachineCharacteristics.getMaxIndex(); i++){
            vendingMachineItemInsert(new VendingMachineItemDTO(i,-1,0,0));
        }
    }

    public static int numberOfUsers(){
        return MyMapDB.userTable.size();
    }
    public static void userInsert(UserDTO userDTO) {
        int recordId = userDTO.getId();
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        int money = userDTO.getMoney();
        User.UserType userType = null;
        switch (userDTO.getUserType()){
            case OPERATOR -> userType = User.UserType.OPERATOR;
            case CUSTOMER -> userType = User.UserType.CUSTOMER;
        }
        User newUser = new User(recordId, username, password, money, userType);
        MyMapDB.userTable.put(recordId, newUser);
    }

    public static UserDTO userSelectById(Integer userId) {
        return convertUserToDTO(MyMapDB.userTable.get(userId));
    }


    public static UserDTO userSelectByUserName(String userName) {
        return convertUserToDTO(MyMapDB.userTable.values().stream().filter(
                user -> user.getUserName().equals(userName)
        ).findFirst().orElse(null));
    }

    public static void productInsert(String name) {
        int newRecordId = MyMapDB.productTypeTable.size();
        Product newProduct = new Product(newRecordId, name);
        MyMapDB.productTypeTable.put(newRecordId, newProduct);
    }

    public static ProductDTO productById(Integer productId) {
        return convertProductToDTO(MyMapDB.productTypeTable.get(productId));
    }


    public static void vendingMachineItemInsert(VendingMachineItemDTO vendingMachineItemDTO) {
        int itemId = vendingMachineItemDTO.getId();
        int productTypeId = vendingMachineItemDTO.getProductTypeId();
        int price = vendingMachineItemDTO.getPrice();
        int quantity = vendingMachineItemDTO.getQuantity();
        VendingMachineItem newItem = new VendingMachineItem(itemId, productTypeId, price, quantity);
        MyMapDB.vendingMachineItemTable.put(itemId, newItem);
    }

    public static void vendingMachineItemQuantityInsert(Integer itemId, Integer quantity) {
        MyMapDB.vendingMachineItemTable.get(itemId).setQuantity(quantity);
    }

    public static VendingMachineItemDTO vendingMachineItemById(Integer itemId) {
        return convertVendingMachineItemToDTO(MyMapDB.vendingMachineItemTable.get(itemId));
    }

    public static void purchasedProductInsert(Integer productTypeId, Integer quantity, Integer userId) {
        int newRecordId = MyMapDB.purchasedProductTable.size() + 1;
        PurchasedProduct newPurchasedProduct = new PurchasedProduct(newRecordId, productTypeId, quantity, userId);
        MyMapDB.purchasedProductTable.put(newRecordId, newPurchasedProduct);
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
        userInsert(new UserDTO(1,1000000000,"admin", "admin", UserDTO.UserType.OPERATOR));
        userInsert(new UserDTO(2,5000,"Gena", "12345", UserDTO.UserType.CUSTOMER));
        productInsert("Sparkling water");
        productInsert("Sandwich");
        productInsert("Doritos");
        productInsert("Twix");
        productInsert("Haribo gummy bears");
        productInsert("Tuc");
        productInsert("IPhone 15");

        vendingMachineItemInsert(new VendingMachineItemDTO(1, 1, 100, 10));
        vendingMachineItemInsert(new VendingMachineItemDTO(3, 2, 150, 10));
        vendingMachineItemInsert(new VendingMachineItemDTO(9, 3, 200, 4));
        vendingMachineItemInsert(new VendingMachineItemDTO(12, 4, 100, 10));
        vendingMachineItemInsert(new VendingMachineItemDTO(19, 5, 180, 8));
        vendingMachineItemInsert(new VendingMachineItemDTO(21, 6, 100, 5));
        vendingMachineItemInsert(new VendingMachineItemDTO(22, 7, 10, 10));
        vendingMachineItemInsert(new VendingMachineItemDTO(25, 1, 10, 7));
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

    public static List<UserDTO> getUserTable() {
        return new ArrayList<UserDTO>(MyMapDB.userTable.values().stream().map(Repository::convertUserToDTO).toList());
    }

    public static List<ProductDTO> getProductTypeTable() {
        return new ArrayList<ProductDTO>(MyMapDB.productTypeTable.values().stream().map(Repository::convertProductToDTO).toList());
    }

    public static List<PurchasedProductDTO> getPurchasesByUserId(Integer userId) {
        return new ArrayList<PurchasedProductDTO>(MyMapDB.purchasedProductTable.values().stream().filter(
                purchasedProduct -> purchasedProduct.getUserId() == userId
        ).map(Repository::convertPurchasedProductToDTO).toList());
    }

    public static Map<Integer, VendingMachineItemDTO> getVendingMachineItemTable() {
        Map<Integer, VendingMachineItemDTO> vendingMachineItemDTOMap = new HashMap<Integer, VendingMachineItemDTO>();
        MyMapDB.vendingMachineItemTable.entrySet().forEach(itemId->{
            vendingMachineItemDTOMap.put(itemId.getKey(),convertVendingMachineItemToDTO(itemId.getValue()));
        });
        return vendingMachineItemDTOMap;
    }



    public static UserDTO convertUserToDTO(User user){
        UserDTO userDTO = null;
        if(user!=null){
            userDTO = new UserDTO();
            switch (user.getUserType()){
                case CUSTOMER -> userDTO.setUserType(UserDTO.UserType.CUSTOMER);
                case OPERATOR -> userDTO.setUserType(UserDTO.UserType.OPERATOR);
            }
            userDTO.setId(user.getUserId());
            userDTO.setMoney(user.getMoney());
            userDTO.setUsername(user.getUserName());
            userDTO.setPassword(user.getPassword());
        }
        return userDTO;
    }

    public static ProductDTO convertProductToDTO(Product product) {
        ProductDTO productDTO = null;
        if(product!=null){
            productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
        }
        return productDTO;
    }

    public static VendingMachineItemDTO convertVendingMachineItemToDTO(VendingMachineItem vendingMachineItem){
        VendingMachineItemDTO vendingMachineItemDTO = null;
        if(vendingMachineItem!=null) {
            vendingMachineItemDTO = new VendingMachineItemDTO();
            vendingMachineItemDTO.setPrice(vendingMachineItem.getPrice());
            vendingMachineItemDTO.setId(vendingMachineItem.getId());
            vendingMachineItemDTO.setProductTypeId(vendingMachineItem.getProductTypeId());
            vendingMachineItemDTO.setQuantity(vendingMachineItem.getQuantity());
        }
        return vendingMachineItemDTO;
    }

    private static PurchasedProductDTO convertPurchasedProductToDTO(PurchasedProduct purchasedProduct) {
        PurchasedProductDTO purchasedProductDTO = new PurchasedProductDTO();
        purchasedProductDTO.setId(purchasedProduct.getId());
        purchasedProductDTO.setProductTypeId(purchasedProduct.getProductTypeId());
        purchasedProductDTO.setUserId(purchasedProduct.getUserId());
        purchasedProductDTO.setQuantity(purchasedProduct.getQuantity());
        return purchasedProductDTO;
    }

}
