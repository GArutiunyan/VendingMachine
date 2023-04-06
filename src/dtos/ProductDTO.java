package dtos;

public class ProductDTO {
    private String name;
    Integer id;

    public ProductDTO() {
        this.name = null;
        this.id = null;
    }

    public ProductDTO(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
