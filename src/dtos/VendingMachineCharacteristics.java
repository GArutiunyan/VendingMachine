package dtos;

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
