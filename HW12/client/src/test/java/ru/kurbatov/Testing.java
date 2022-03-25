package ru.kurbatov;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertEquals;

@Testcontainers
public class Testing {
    private static final int EXCHANGE_PORT = 8080;
    private static final int USER_PORT = 8081;
    private ConfigurableApplicationContext userServer;

    @Container
    private static final GenericContainer<?> EXCHANGE = new FixedHostPortGenericContainer<>("exchange:1.0-SNAPSHOT")
            .withFixedExposedPort(EXCHANGE_PORT, EXCHANGE_PORT).withExposedPorts(EXCHANGE_PORT);


    @Before
    public void startServers() {
        EXCHANGE.start();
        userServer = Application.run(new String[0]);
    }

    @After
    public void stopServers() {
        EXCHANGE.stop();
        userServer.stop();
    }


    private String sendThenReceive(int port, String endpoint, String params) {
        return URLHelper.sendThenReceive("http://localhost:" + port + "/" + endpoint + "?" + params);
    }

    private String createStock(String name, int number, int price) {
        return sendThenReceive(EXCHANGE_PORT, "register", "name=" + name + "&number=" + number + "&price=" + price);
    }

    private String createUser(int userId) {
        return sendThenReceive(USER_PORT, "register", "userId=" + userId);
    }

    private String buy(int userId, String stock, int number) {
        return sendThenReceive(USER_PORT, "buy", "userId=" + userId + "&stock=" + stock + "&number=" + number);
    }

    private String sell(int userId, String stock, int number) {
        return sendThenReceive(USER_PORT, "sell", "userId=" + userId + "&stock=" + stock + "&number=" + number);
    }

    private String deposit(int userId, int addendum) {
        return sendThenReceive(USER_PORT, "deposit", "userId=" + userId + "&addendum=" + addendum);
    }

    private String total(int userId) {
        return sendThenReceive(USER_PORT, "total", "userId=" + userId);
    }

    private String getPrice(String stock) {
        return sendThenReceive(EXCHANGE_PORT, "get_price", "name=" + stock);
    }

    private String setPrice(String stock, int price) {
        return sendThenReceive(EXCHANGE_PORT, "set_price", "name=" + stock + "&price=" + price);
    }

    private String portfolio(int userId) {
        return sendThenReceive(USER_PORT, "portfolio", "userId=" + userId);
    }


    @Test
    public void test() {
        String response;

        // creates
        response = createStock("yandex", 1000, 100);
        assertEquals(response, "Stock yandex was registered");

        response = createUser(1);
        assertEquals(response, "User 1 was registered");

        // deposit
        response = deposit(1, 1000000);
        assertEquals(response, "Successful deposit");

        // balance
        response = total(1);
        assertEquals(response, "1000000");

        // stock price
        response = getPrice("yandex");
        assertEquals(response, "100");

        // buy
        response = buy(1, "yandex", 500);
        assertEquals(response, "Purchase successful");

        // sell
        response = sell(1, "yandex", 100);
        assertEquals(response, "Sale successful");

        // user 2
        response = createUser(1);
        assertEquals(response, "User with this id already exists");
        response = createUser(2);
        assertEquals(response, "User 2 was registered");

        // stock 2
        response = createStock("yandex", 1, 10000000);
        assertEquals(response, "Stock with this name already exists");
        response = createStock("google", 1, 10000000);
        assertEquals(response, "Stock google was registered");

        // unknowns
        response = total(3);
        assertEquals(response, "Unknown user");
        response = getPrice("netflix");
        assertEquals(response, "Unknown stock");

        // bad buys
        response = buy(1, "yandex", 1000);
        assertEquals(response, "Stock(yandex) negative number");

        response = buy(1, "google", 1);
        assertEquals(response, "User 1: Negative balance");

        // check stocks correct after bad buy
        response = deposit(2, 10000000);
        assertEquals(response, "Successful deposit");

        response = buy(2, "google", 1);
        assertEquals(response, "Purchase successful");

        // bad sell
        response = sell(1, "yandex", 1000);
        assertEquals(response, "Not enough stocks(yandex) to sell for User 1");

        // bad deposit
        response = deposit(1, -10000000);
        assertEquals(response, "User 1: Negative balance");

        // price change and check balance
        response = setPrice("google", 1);
        assertEquals(response, "Price changed");

        response = total(2);
        assertEquals(response, "1");

        // portfolio check
        response = portfolio(1);
        assertEquals(response, "yandex: number = 400, cost = 100");

        response = portfolio(2);
        assertEquals(response, "google: number = 1, cost = 1");
    }
}



