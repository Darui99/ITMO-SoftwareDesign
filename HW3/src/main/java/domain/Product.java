package domain;

import java.util.ArrayList;
import java.util.List;

public class Product implements DatabaseEntity {
    private final String name;
    private final int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    @Override
    public String getRowString() {
        return name + "\t" + price;
    }

    public static List<DatabaseEntity> convertList(List<Product> products) {
        return new ArrayList<>(products);
    }
}
