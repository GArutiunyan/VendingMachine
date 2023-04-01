package Controller;

import Facade.Facade;
import MyMapDB.*;
import Service.Service;

import java.util.*;

public class ControllerSoutTables {
    public static void soutUsers() {
        List<User> users = Facade.getUserTable();
        int usernameMaxLength = 8;
        int passwordMaxLength = 8;
        for (User user : users) {
            int usernameLength = user.getUserName().length();
            if (usernameLength > usernameMaxLength) {
                usernameMaxLength = usernameLength;
            }
            int passwordLength = user.getPassword().length();
            if (passwordLength > passwordMaxLength) {
                passwordMaxLength = passwordLength;
            }
        }
        usernameMaxLength++;
        passwordMaxLength++;
        String format = "%" + usernameMaxLength + "s%" + passwordMaxLength + "s%9s";
        System.out.format(format, "username", "password", "type");
        System.out.println();
        for (User user : users) {
            System.out.format(format, user.getUserName(), user.getPassword(), user.getUserType());
            System.out.println();
        }
//        List<Product> products = Facade.getProductTypeTable();


    }


    public static class SoutVendingMachine {

        public static char[] frameFragments = {'╔', '═', '╗', '║', '╝', '╚', '╟', '╤', '╢', '╧', '│', '─', '┼'};
        public static Map<Integer,Integer> maxItemWidth = new HashMap<>();
        public static boolean maxItemWidthIsFilled = false;
        public static int widthOfMachine;
        public static int heightOfMachine;
        public static int maxOfCell;

        static Map<Integer, VendingMachineItem> vendingMachineItems;
        public static void fillMaxItemWidth(){
            widthOfMachine = Service.VendingMachineCharacteristics.width;
            heightOfMachine = Service.VendingMachineCharacteristics.height;
            maxOfCell = Service.VendingMachineCharacteristics.getMaxIndex();
            vendingMachineItems = Facade.getVendingMachineItemTable();

            for (int indexJ = 1; indexJ <= widthOfMachine; indexJ++){
                int maxIndexJItemWidth = 0;
                for (int i = 1; i <= maxOfCell;i+=widthOfMachine){
                    VendingMachineItem vendingMachineItem = vendingMachineItems.get(i);
                    String name = Facade.productById(vendingMachineItem.getProductTypeId()).getName();
                    String price = ""+ vendingMachineItem.getPrice();
                    String quantity = "" + vendingMachineItem.getQuantity()+"/"+Service.VendingMachineCharacteristics.maxItemsInSlot;

                    if(maxIndexJItemWidth<name.length()){
                        maxIndexJItemWidth = name.length();
                    }
                    if(maxIndexJItemWidth<price.length()){
                        maxIndexJItemWidth = price.length();
                    }
                    if(maxIndexJItemWidth<quantity.length()){
                        maxIndexJItemWidth = quantity.length();
                    }
                }
                maxItemWidth.put(maxItemWidth.size()+1,maxIndexJItemWidth);
            }
//            maxItemWidth.put(-1,0);
            maxItemWidth.put(0,0);
            maxItemWidth.put(6,0);
            maxItemWidthIsFilled = true;
        }

        static String drawFrame(int indexI, int indexJ) {
            StringBuilder result = new StringBuilder();

            int width = Service.VendingMachineCharacteristics.width;
            int height = Service.VendingMachineCharacteristics.height;
            if(indexI>height){
                if(indexJ>width){
                    return frameFragments[4]+"";
                }
                char frame;
                if(indexJ==1){
                    frame = frameFragments[5];
                }else {
                    frame = frameFragments[1];
                }
                return frame + (""+frameFragments[1]).repeat(maxItemWidth.get(indexJ));
            }
            if(indexJ>width){
                return frameFragments[8]+"";
            }

            if (indexI == 1) {
                if (indexJ == 1) {
                    result.append(frameFragments[0]);
                } else {
                    result.append(frameFragments[7]);
                }
                result.append(frameFragments[1] * maxItemWidth.get(indexJ));
            } else {
                if (indexJ == 1) {
                    result.append(frameFragments[6]);
                } else {
                    result.append(frameFragments[12]);
                }
                result.append((""+frameFragments[11]).repeat(maxItemWidth.get(indexJ)));
            }
            return result.toString();
        }

        public static void soutVendingMachine() {

            if (!maxItemWidthIsFilled) {
                fillMaxItemWidth();
            }

            for (int i = 0; i <= Service.VendingMachineCharacteristics.height + 1; i++) {
                int widthOfMachine = Service.VendingMachineCharacteristics.width;
                for (int j = 0; j <= widthOfMachine+1;j++){
                    VendingMachineItem vendingMachineItem;
                    int itemId = i*j;
                    if (Facade.itemSlotIsOccupied(itemId)== Facade.ItemSlotStatus.OCCUPIED){
                        vendingMachineItem = vendingMachineItems.get(itemId);
                    }else {
                        vendingMachineItem = vendingMachineItems.get(-1);
                    }
                    System.out.print(i+" "+j+" "+ drawLayerOfCell(LayerOfCell.NAME,vendingMachineItem,i,j));
                }
                System.out.println();
            }
        }


        enum LayerOfCell {
            FRAME,NAME, PRICE, QUANTITY
        }

        static String middleAlignment(String initialString, int indexJ){
            StringBuilder result = new StringBuilder();
//            System.out.println(indexJ);
            String middleAlignment = " ".repeat(Math.max(0, (maxItemWidth.get(indexJ) - initialString.length()) / 2));
            result.append(middleAlignment);
            result.append(initialString);
            result.append(middleAlignment);
            if (result.length()<maxItemWidth.get(indexJ)+1){
                result.append(" ");
            }
            return result.toString();
        }
        static String drawLayerOfCell(LayerOfCell layerOfCell, VendingMachineItem vendingMachineItem,int indexI, int indexJ) {
            if(vendingMachineItem==null){
                System.out.println("NULL GOVNO");
            }
            char frame;
            if (indexJ == 1||indexJ>Service.VendingMachineCharacteristics.width) {
                frame = frameFragments[3];
            } else {

                frame = frameFragments[10];
            }
            switch (layerOfCell) {
                case FRAME:{
                    return drawFrame(indexI,indexJ);
                }
                case NAME: {
                    String name = Facade.productById(vendingMachineItem.getProductTypeId()).getName();
                    return frame+middleAlignment(name,indexJ);
                }
                case PRICE:{
                    String price = ""+ vendingMachineItem.getPrice();
                    return frame+middleAlignment(price,indexJ);
                }
                case QUANTITY:{
                    String quantity = "" + vendingMachineItem.getQuantity()+"/"+Service.VendingMachineCharacteristics.maxItemsInSlot;
                    return frame+middleAlignment(quantity,indexJ);
                }
            }
            return "";
        }
    }

    public static void soutVendingMachine() {
        SoutVendingMachine.soutVendingMachine();
    }
}
