package MyMapDB;

import java.io.Serializable;

public class Product implements Serializable {
    static final long SerialVersionUID = 58620001707L;
    private String name;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
