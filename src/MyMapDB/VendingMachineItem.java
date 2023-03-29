package MyMapDB;

import java.io.Serializable;

public class VendingMachineItem implements Serializable {
    static final long SerialVersionUID = 51238620001707L;
    private int id,productTypeId, price, quantity;

    public VendingMachineItem(int id, int productTypeId, int price, int quantity) {
        this.id = id;
        this.productTypeId = productTypeId;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
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
