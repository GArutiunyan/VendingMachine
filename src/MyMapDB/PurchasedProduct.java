package MyMapDB;

public class PurchasedProduct {
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
