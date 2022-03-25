package ru.kurbatov;

public class Stock {
    private final String name;
    private int number;
    private int price;

    public Stock(String name, int number, int price) {
        this.name = name;
        this.number = number;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public void addToNumber(int addendum) {
        if (number + addendum < 0) {
            throw new RuntimeException("Stock(" + name + ") negative number");
        }
        number += addendum;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int newPrice) {
        price = newPrice;
    }
}
