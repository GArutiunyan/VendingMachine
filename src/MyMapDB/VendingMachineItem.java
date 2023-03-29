package MyMapDB;

public class VendingMachineItem {
    private int productTypeId, price, quantity;

    public VendingMachineItem(int productTypeId, int price, int quantity) {
        this.productTypeId = productTypeId;
        this.price = price;
        this.quantity = quantity;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
