package ru.kurbatov;

import java.util.HashMap;
import java.util.Map;

public class StockManager {
    private final Map<String, Stock> stocks;

    public StockManager() {
        stocks = new HashMap<>();
    }

    public void checkStock(String name) {
        if (!stocks.containsKey(name)) {
            throw new RuntimeException("Unknown stock");
        }
    }

    public boolean registerStock(String name, int number, int price) {
        return stocks.putIfAbsent(name, new Stock(name, number, price)) == null;
    }

    public int buyStock(String name, int number) {
        checkStock(name);
        stocks.get(name).addToNumber(-number);
        return getPrice(name) * number;
    }

    public int sellStock(String name, int number) {
        checkStock(name);
        stocks.get(name).addToNumber(number);
        return getPrice(name) * number;
    }

    public int getPrice(String name) {
        checkStock(name);
        return stocks.get(name).getPrice();
    }

    public void setPrice(String name, int newPrice) {
        checkStock(name);
        stocks.get(name).setPrice(newPrice);
    }
}
