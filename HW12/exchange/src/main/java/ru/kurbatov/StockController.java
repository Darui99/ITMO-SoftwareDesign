package ru.kurbatov;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
public class StockController {
    private final StockManager stockManager = new StockManager();

    private String wrapErrorHandle(Supplier<String> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/register")
    public String register(@RequestParam String name, @RequestParam int number, @RequestParam int price) {
        if (stockManager.registerStock(name, number, price)) {
            return "Stock " + name + " was registered";
        } else {
            return "Stock with this name already exists";
        }
    }

    @RequestMapping("/buy")
    public String buy(@RequestParam String name, @RequestParam int number) {
        return wrapErrorHandle(() -> Integer.toString(stockManager.buyStock(name, number)));
    }

    @RequestMapping("/sell")
    public String sell(@RequestParam String name, @RequestParam int number) {
        return wrapErrorHandle(() -> Integer.toString(stockManager.sellStock(name, number)));
    }

    @RequestMapping("/get_price")
    public String getPrice(@RequestParam String name) {
        return wrapErrorHandle(() -> Integer.toString(stockManager.getPrice(name)));
    }

    @RequestMapping("/set_price")
    public String setPrice(@RequestParam String name, @RequestParam int price) {
        return wrapErrorHandle(() -> {
            stockManager.setPrice(name, price);
            return "Price changed";
        });
    }
}
