package my_map_db;

import java.io.Serializable;
import java.util.HashMap;

public class MyMapDB implements Serializable {
    public static final long SerialVersionUID = -48629262342433707L;
    public static HashMap<Integer, User> userTable = new HashMap<Integer, User>();
    public static HashMap<Integer, Product> productTypeTable = new HashMap<Integer, Product>();
    public static HashMap<Integer, VendingMachineItem> vendingMachineItemTable = new HashMap<Integer, VendingMachineItem>();
    public static HashMap<Integer, PurchasedProduct> purchasedProductTable = new HashMap<Integer, PurchasedProduct>();
}
