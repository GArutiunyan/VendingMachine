package dtos;


public class ItemRequestDTO {
    public Integer itemId;
    public Integer quantity;
    public RequestStatus requestStatus;

    public enum RequestStatus {
        INVALID, OK
    }

    public ItemRequestDTO(int itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
        requestStatus = RequestStatus.OK;
    }

    public ItemRequestDTO() {
        this.itemId = null;
        this.quantity = null;
        requestStatus = RequestStatus.INVALID;
    }
}
