package ru.kurbatov;

import java.util.HashMap;
import java.util.Map;

public class User {
    private final int id;
    private final Map<String, Integer> stocksNumber;
    private int balance;

    public User(int id) {
        this.id = id;
        stocksNumber = new HashMap<>();
        this.balance = 0;
    }

    public int getId() {
        return id;
    }

    public Map<String, Integer> getStocksNumber() {
        return Map.copyOf(stocksNumber);
    }

    public int getStockNumber(String stock) {
        return stocksNumber.getOrDefault(stock, 0);
    }

    public int getBalance() {
        return balance;
    }

    public void addStockNumber(String stock, int addendum) {
        int newNumber = getStockNumber(stock) + addendum;
        if (newNumber < 0) {
            throw new RuntimeException("User " + id + ": Negative stock(" + stock + ") number");
        }
        if (newNumber == 0) {
            stocksNumber.remove(stock);
        } else {
            stocksNumber.put(stock, newNumber);
        }
    }

    public void addToBalance(int addendum) {
        int newBalance = balance + addendum;
        if (newBalance < 0) {
            throw new RuntimeException("User " + id + ": Negative balance");
        }
        balance = newBalance;
    }
}
