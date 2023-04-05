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

    public static void soutProductTypes() {
        List<Product> products = Facade.getProductTypeTable();
        int nameMaxLength = 4;
        int idMaxLength = 2;
        for (Product product : products) {
            int productNameLength = product.getName().length();
            if (productNameLength > nameMaxLength) {
                nameMaxLength = productNameLength;
            }
            int idLength = ("" + product.getId()).length();
            if (idLength > idMaxLength) {
                idMaxLength = idLength;
            }
        }
        idMaxLength++;
        nameMaxLength++;
        String format = "%" + idMaxLength + "s%" + nameMaxLength + "s";
        System.out.format(format, "id", "name");
        System.out.println();
        for (Product product : products) {
            System.out.format(format, product.getId(), product.getName());
            System.out.println();
        }
    }

    public static void soutUserOrders() {
        List<PurchasedProduct> purchasedProducts = Facade.getPurchasedProductTable(Facade.getUserId());
        int nameMaxLength = 4;
        int quantityMaxLength = 8;
        for (PurchasedProduct purchasedProduct : purchasedProducts) {
            int productNameLength = Facade.productById(purchasedProduct.getProductTypeId()).getName().length();
            if (productNameLength > nameMaxLength) {
                nameMaxLength = productNameLength;
            }
            int quantityLength = ("" + purchasedProduct.getQuantity()).length();
            if (quantityLength > quantityMaxLength) {
                quantityMaxLength = quantityLength;
            }
        }
        quantityMaxLength++;
        nameMaxLength++;
        String format = "%" + nameMaxLength + "s%" + quantityMaxLength + "s";
        System.out.format(format, "name", "quantity");
        System.out.println();
        for (PurchasedProduct purchasedProduct : purchasedProducts) {
            System.out.format(format, Facade.productById(purchasedProduct.getProductTypeId()).getName(), purchasedProduct.getQuantity());
            System.out.println();
        }
    }


    public static class SoutVendingMachine {

        public static char[] frameFragments = {'╔', '═', '╗', '║', '╝', '╚', '╟', '╤', '╢', '╧', '│', '─', '┼'};
        public static Map<Integer, Integer> maxItemWidth;
        public static int widthOfMachine;
        public static int heightOfMachine;
        public static int maxItemsInCell;
        public static int maxCellsInMachine;

        static Map<Integer, VendingMachineItem> vendingMachineItems;

        public static void fillListsForSout() {
            maxItemWidth = new HashMap<>();
            widthOfMachine = Service.VendingMachineCharacteristics.width;
            heightOfMachine = Service.VendingMachineCharacteristics.height;
            maxItemsInCell = Service.VendingMachineCharacteristics.maxItemsInSlot;
            maxCellsInMachine = Service.VendingMachineCharacteristics.getMaxIndex();
            vendingMachineItems = Facade.getVendingMachineItemTable();

            for (int indexJ = 0; indexJ < widthOfMachine; indexJ++) {
                int maxIndexJItemWidth = 0;
                for (int i = 1; i <= maxCellsInMachine; i += widthOfMachine) {
                    VendingMachineItem vendingMachineItem = vendingMachineItems.get(indexJ + i);
                    String name = Facade.productById(vendingMachineItem.getProductTypeId()).getName();
                    String price = "" + vendingMachineItem.getPrice();
                    String quantity = "" + vendingMachineItem.getQuantity() + "/" + Service.VendingMachineCharacteristics.maxItemsInSlot;

                    if (maxIndexJItemWidth < name.length()) {
                        maxIndexJItemWidth = name.length();
                    }
                    if (maxIndexJItemWidth < price.length()) {
                        maxIndexJItemWidth = price.length();
                    }
                    if (maxIndexJItemWidth < quantity.length()) {
                        maxIndexJItemWidth = quantity.length();
                    }
                }
                maxItemWidth.put(maxItemWidth.size() + 1, maxIndexJItemWidth);
            }
//            maxItemWidth.put(-1,0);
            maxItemWidth.put(0, 0);
            maxItemWidth.put(6, 0);
        }

        static String drawFrame(int indexI, int indexJ) {
            StringBuilder result = new StringBuilder();

            int width = Service.VendingMachineCharacteristics.width;
            int height = Service.VendingMachineCharacteristics.height;
            if (indexI > height) {
                if (indexJ > width) {
                    return frameFragments[4] + "";
                }
                char frame;
                if (indexJ == 1) {
                    frame = frameFragments[5];
                } else {
                    frame = frameFragments[9];
                }
                return frame + ("" + frameFragments[1]).repeat(maxItemWidth.get(indexJ) + 1);
            }

            if (indexI == 1) {
                if (indexJ == 1) {
                    result.append(frameFragments[0]);
                } else if (indexJ > width) {
                    result.append(frameFragments[2]);
                    return result.toString();
                } else {
                    result.append(frameFragments[7]);
                }
                result.append(("" + frameFragments[1]).repeat(maxItemWidth.get(indexJ) + 1));
                return result.toString();
            } else {
                if (indexJ > width) {
                    return frameFragments[8] + "";
                } else if (indexJ == 1) {
                    result.append(frameFragments[6]);
                } else {
                    result.append(frameFragments[12]);
                }
                result.append(("" + frameFragments[11]).repeat(maxItemWidth.get(indexJ) + 1));
            }
            return result.toString();
        }


        public static void soutNumberOfColumns() {
            System.out.print("   ");
            for (int columnNumber = 1; columnNumber <= widthOfMachine; columnNumber++) {
                int cellWidth = maxItemWidth.get(columnNumber) + 2;
                String format = "%" + cellWidth + "s";
                String columnNumberCenter = ("" + columnNumber + " ".repeat(cellWidth / 2));
                System.out.format(format, columnNumberCenter);
            }
            System.out.println();
        }

        public static void soutNumberOfRows(int i, LayerOfCell layerOfCell) {
            if (layerOfCell != LayerOfCell.NAME) {
                System.out.print("   ");
                return;
            }
            char rowChar = 'A' - 1;
            rowChar += i;
            System.out.print(" " + rowChar + " ");
        }

        public static void soutVendingMachine() {

            fillListsForSout();

            soutNumberOfColumns();
            for (int i = 1; i <= heightOfMachine + 1; i++) {
                for (LayerOfCell layerOfCell : LayerOfCell.values()) {
                    for (int j = 0; j <= widthOfMachine + 1; j++) {
                        if (j == 0) {
                            if (i <= heightOfMachine) {
                                soutNumberOfRows(i, layerOfCell);
                            } else {
                                System.out.print("   ");
                            }
                            continue;
                        }
                        if (i > heightOfMachine && layerOfCell != LayerOfCell.FRAME) {
                            continue;
                        }
                        VendingMachineItem vendingMachineItem;
                        int itemId = (i - 1) * widthOfMachine + j;
                        if (Facade.itemSlotIsOccupied(itemId) == Facade.ItemSlotStatus.OCCUPIED) {
                            vendingMachineItem = vendingMachineItems.get(itemId);
                        } else {
                            vendingMachineItem = vendingMachineItems.get(-1);
                        }
                        System.out.print(drawLayerOfCell(layerOfCell, vendingMachineItem, i, j));
                    }
                    if (i > heightOfMachine) {
                        continue;
                    }
                    System.out.println();
                }
            }
        }


        enum LayerOfCell {
            FRAME, NAME, PRICE, QUANTITY
        }

        static String middleAlignment(String initialString, int indexJ) {
            StringBuilder result = new StringBuilder();
//            System.out.println(indexJ);
            String middleAlignment = " ".repeat(Math.max(0, (maxItemWidth.get(indexJ) - initialString.length()) / 2));
            result.append(middleAlignment);
            result.append(initialString);
            while (result.length() < maxItemWidth.get(indexJ) + 1) {
                result.append(" ");
            }
            return result.toString();
        }

        static String drawLayerOfCell(LayerOfCell layerOfCell, VendingMachineItem vendingMachineItem, int indexI, int indexJ) {

            char frame;
            if (indexJ == 1 || indexJ > widthOfMachine) {
                frame = frameFragments[3];
            } else {
                frame = frameFragments[10];
            }

            if (layerOfCell != LayerOfCell.FRAME && indexJ > widthOfMachine) {
                return "" + frame;
            }

            if (layerOfCell != LayerOfCell.FRAME && vendingMachineItem.getProductTypeId() == -1) {
                return frame + middleAlignment("", indexJ);
            }
            switch (layerOfCell) {
                case FRAME: {
                    return drawFrame(indexI, indexJ);
                }
                case NAME: {
                    String name = Facade.productById(vendingMachineItem.getProductTypeId()).getName();
//                    name+=vendingMachineItem.getId();
                    return frame + middleAlignment(name, indexJ);
                }
                case PRICE: {
                    String price = "" + vendingMachineItem.getPrice() + "rub";
                    return frame + middleAlignment(price, indexJ);
                }
                case QUANTITY: {
                    String quantity = "" + vendingMachineItem.getQuantity() + "/" + Service.VendingMachineCharacteristics.maxItemsInSlot;
                    return frame + middleAlignment(quantity, indexJ);
                }
            }
            return "";
        }
    }

    public static void soutVendingMachine() {
        SoutVendingMachine.soutVendingMachine();
    }


}
