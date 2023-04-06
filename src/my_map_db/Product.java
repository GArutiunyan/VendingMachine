package my_map_db;

import java.io.Serializable;

public class Product implements Serializable {
    static final long SerialVersionUID = 58620001707L;
    private String name;
    int id;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Product() {
        this.id = -1;
        this.name = "";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
