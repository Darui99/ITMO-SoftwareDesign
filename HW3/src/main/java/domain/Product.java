package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name);
    }

    public static List<DatabaseEntity> convertList(List<Product> products) {
        return new ArrayList<>(products);
    }
}
