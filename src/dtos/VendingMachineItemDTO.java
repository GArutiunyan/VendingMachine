package dtos;

public class VendingMachineItemDTO {
    private Integer id, productTypeId, price, quantity;

    public VendingMachineItemDTO() {
        this.id = null;
        this.productTypeId = null;
        this.price = null;
        this.quantity = null;
    }

    public VendingMachineItemDTO(Integer id, Integer productTypeId, Integer price, Integer quantity) {
        this.id = id;
        this.productTypeId = productTypeId;
        this.price = price;
        this.quantity = quantity;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
