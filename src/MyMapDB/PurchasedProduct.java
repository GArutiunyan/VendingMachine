package MyMapDB;

import java.io.Serializable;

public class PurchasedProduct implements Serializable {
    static final long SerialVersionUID = -112386707L;
    private int id, productTypeId, quantity, userId;

    public PurchasedProduct(int id, int productTypeId, int quantity, int userId) {
        this.id = id;
        this.productTypeId = productTypeId;
        this.quantity = quantity;
        this.userId = userId;
    }

    public int getId() {
        return id;
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
