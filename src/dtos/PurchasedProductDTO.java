package dtos;

public class PurchasedProductDTO {
    private int id, productTypeId, quantity, userId;

    public PurchasedProductDTO(int id, int productTypeId, int quantity, int userId) {
        this.id = id;
        this.productTypeId = productTypeId;
        this.quantity = quantity;
        this.userId = userId;
    }

    public PurchasedProductDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
