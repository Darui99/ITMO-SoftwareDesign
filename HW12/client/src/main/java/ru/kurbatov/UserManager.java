package ru.kurbatov;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserManager {
    private final Map<Integer, User> users;
    private final ExchangeProvider exchange;

    public UserManager() {
        users = new HashMap<>();
        exchange = new ExchangeProvider("localhost:8082");
    }

    public void checkUser(int userId) {
        if (!users.containsKey(userId)) {
            throw new RuntimeException("Unknown user");
        }
    }

    public boolean registerUser(int userId) {
        return users.putIfAbsent(userId, new User(userId)) == null;
    }

    public void buyStock(int userId, String stock, int number) {
        checkUser(userId);
        User user = users.get(userId);
        try {
            user.addToBalance(-exchange.buyStock(stock, number));
        } catch (Exception e) {
            if (e.getMessage().startsWith("User")) {
                exchange.sellStock(stock, number);
            }
            throw e;
        }
        user.addStockNumber(stock, number);
    }

    public void sellStock(int userId, String stock, int number) {
        checkUser(userId);
        User user = users.get(userId);
        if (user.getStockNumber(stock) < number) {
            throw new RuntimeException("Not enough stocks(" + stock + ") to sell for User " + userId);
        }
        user.addToBalance(exchange.sellStock(stock, number));
        user.addStockNumber(stock, -number);
    }

    public void addToUserBalance(int userId, int addendum) {
        checkUser(userId);
        users.get(userId).addToBalance(addendum);
    }

    public int calcUserTotalValue(int userId) {
        checkUser(userId);
        User user = users.get(userId);
        int res = user.getBalance();
        for (Map.Entry<String, Integer> elem : user.getStocksNumber().entrySet()) {
            res += exchange.getStockPrice(elem.getKey()) * elem.getValue();
        }
        return res;
    }

    public String getUserPortfolio(int userId) {
        checkUser(userId);
        return users.get(userId).getStocksNumber().entrySet().stream()
                .map(e -> e.getKey() + ": number = " + e.getValue() + ", cost = " + exchange.getStockPrice(e.getKey()))
                .collect(Collectors.joining("\n"));
    }
}
