package Service;

import MyMapDB.*;

import Repository.*;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Service {


    public static List<User> getUserTable() {
        return Repository.getUserTable();
    }

    public static List<Product> getProductTypeTable() {
        return Repository.getProductTypeTable();
    }

    public static Map<Integer, VendingMachineItem> getVendingMachineItemTable() {
        return Repository.getVendingMachineItemTable();
    }

    public class VendingMachineCharacteristics {
        public static int maxItemsInSlot = 10;
        public static int width = 5;
        public static int height = 5;

        public static int getMinIndex() {
            return 1;
        }

        public static int getMaxIndex() {
            return width * height;
        }
    }

    public static class ItemRequest {
        public Integer itemId;
        public Integer quantity;
        public RequestStatus requestStatus;

        public enum RequestStatus {
            INVALID, OK
        }

        public ItemRequest(int itemId, int quantity) {
            this.itemId = itemId;
            this.quantity = quantity;
            requestStatus = RequestStatus.OK;
        }

        public ItemRequest() {
            this.itemId = null;
            this.quantity = null;
            requestStatus = RequestStatus.INVALID;
        }
    }


    public static boolean checkItemRequest(ItemRequest itemRequest){
        if (itemRequest.itemId == null || itemRequest.quantity == null) {
            System.out.println("ERROR");
            return false;
        }
        if(itemRequest.quantity<0||itemRequest.itemId >Service.VendingMachineCharacteristics.getMaxIndex()|| itemRequest.itemId <Service.VendingMachineCharacteristics.getMinIndex()){
            System.out.println("ERROR");
            return false;
        }
        return true;
    }

    public static ItemRequest stringToItemRequest(String stringItemRequest) {
        String[] input = stringItemRequest.replace(" ", "").split(":");
        if (input.length < 2) {
            return new ItemRequest();
        }
        int indexI = input[0].toUpperCase().charAt(0) - 'A';
        int indexJ = Integer.parseInt(input[1]);
        int quantity = -1;
        if (input.length > 2) {
            quantity = Integer.parseInt(input[2]);
        }
        int itemId = indexI * 5 + indexJ;
        return new ItemRequest(itemId, quantity);
    }


    public static void buy(VendingMachineItem vendingMachineItem, int quantity) {
        User user = UserService.currentUser;
        vendingMachineItem.setQuantity(vendingMachineItem.getQuantity() - quantity);
        user.setMoney(user.getMoney() - quantity * vendingMachineItem.getPrice());
        Repository.purchasedProductInsert(vendingMachineItem.getProductTypeId(), quantity, user.getUserId());
    }

    public static void addItemsToVendingMachine(Integer itemId, Integer productTypeId, Integer price, Integer quantity) {
        Repository.vendingMachineItemInsert(itemId, productTypeId, price, quantity);
    }

    public static void vendingMachineItemQuantityInsert(Integer itemId, Integer quantity) {
        Repository.vendingMachineItemQuantityInsert(itemId, quantity);
    }

    public static VendingMachineItem vendingMachineItemById(Integer itemId) {
        return Repository.vendingMachineItemById(itemId);
    }

    public static void addNewProductType(String name) {
        Repository.productInsert(name);
    }

    public static Product productById(Integer productId) {
        return Repository.productById(productId);
    }

    public static void loadMyMapDB() {
        if (new File("Vending_Machine_data.dat").isFile()) {
            Repository.loadMyMapDBFromFile();
        } else {
            Repository.loadMyMapDBDefaultValues();
        }
    }

    public static List<PurchasedProduct> getPurchasesByUserId(Integer userId) {
        return Repository.getPurchasesByUserId(userId);
    }


}
