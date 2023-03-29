package MyMapDB;

import java.io.Serializable;

public class PurchasedProduct implements Serializable {
    static final long SerialVersionUID = -112386707L;
    private int productTypeId, quantity, userId;

    public PurchasedProduct(int productTypeId, int quantity, int userId) {
        this.productTypeId = productTypeId;
        this.quantity = quantity;
        this.userId = userId;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public int getUserId() {
        return userId;
    }

    public int getQuantity() {
        return quantity;
    }
}
