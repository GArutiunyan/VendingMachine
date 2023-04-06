package service;

import repository.*;

import dtos.*;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Service {


    public static List<UserDTO> getUserTable() {
        return Repository.getUserTable();
    }

    public static List<ProductDTO> getProductTypeTable() {
        return Repository.getProductTypeTable();
    }

    public static Map<Integer, VendingMachineItemDTO> getVendingMachineItemTable() {
        return Repository.getVendingMachineItemTable();
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


    public static boolean checkItemRequest(ItemRequestDTO itemRequestDTO){
        if (itemRequestDTO.itemId == null || itemRequestDTO.quantity == null) {
            System.out.println("ERROR");
            return false;
        }
        if(itemRequestDTO.quantity<0||itemRequestDTO.itemId >VendingMachineCharacteristics.getMaxIndex()|| itemRequestDTO.itemId <VendingMachineCharacteristics.getMinIndex()){
            System.out.println("ERROR");
            return false;
        }
        return true;
    }

    public static ItemRequestDTO stringToItemRequest(String stringItemRequest) {
        String[] input = stringItemRequest.replace(" ", "").split(":");
        if (input.length < 2) {
            return convertItemRequestToDTO(new ItemRequest());
        }
        int indexI = input[0].toUpperCase().charAt(0) - 'A';
        int indexJ = Integer.parseInt(input[1]);
        int quantity = -1;
        if (input.length > 2) {
            quantity = Integer.parseInt(input[2]);
        }
        int itemId = indexI * 5 + indexJ;
        return convertItemRequestToDTO(new ItemRequest(itemId, quantity));
    }

    public static ItemRequestDTO convertItemRequestToDTO(ItemRequest itemRequest){
        ItemRequestDTO itemRequestDTO = new ItemRequestDTO();
        switch (itemRequest.requestStatus){
            case OK -> itemRequestDTO.requestStatus = ItemRequestDTO.RequestStatus.OK;
            case INVALID -> itemRequestDTO.requestStatus = ItemRequestDTO.RequestStatus.INVALID;
        }
        itemRequestDTO.itemId = itemRequest.itemId;
        itemRequestDTO.quantity = itemRequest.quantity;
        return itemRequestDTO;
    }


    public static void buy(VendingMachineItemDTO vendingMachineItemDTO, int quantity) {
        UserDTO userDTO = UserService.currentUser;
        vendingMachineItemDTO.setQuantity(vendingMachineItemDTO.getQuantity() - quantity);
        userDTO.setMoney(userDTO.getMoney() - quantity * vendingMachineItemDTO.getPrice());
        Repository.purchasedProductInsert(vendingMachineItemDTO.getProductTypeId(), quantity, userDTO.getId());
    }

    public static void addItemsToVendingMachine(Integer itemId, Integer productTypeId, Integer price, Integer quantity) {
        System.out.println("Добавил itemId:"+itemId);
        Repository.vendingMachineItemInsert(new VendingMachineItemDTO(itemId, productTypeId, price, quantity));
    }

    public static void vendingMachineItemQuantityInsert(Integer itemId, Integer quantity) {
        Repository.vendingMachineItemQuantityInsert(itemId, quantity);
    }

    public static VendingMachineItemDTO vendingMachineItemById(Integer itemId) {
        return Repository.vendingMachineItemById(itemId);
    }

    public static void addNewProductType(String name) {
        Repository.productInsert(name);
    }

    public static ProductDTO productById(Integer productId) {
        return Repository.productById(productId);
    }

    public static void loadMyMapDB() {
        if (new File("Vending_Machine_data.dat").isFile()) {
            Repository.loadMyMapDBFromFile();
        } else {
            Repository.loadMyMapDBDefaultValues();
        }
    }

    public static List<PurchasedProductDTO> getPurchasesByUserId(Integer userId) {
        return Repository.getPurchasesByUserId(userId);
    }

    public static void saveMyMapDBToFile(){
        Repository.saveMyMapDBToFile();
    }

}
