package ru.kurbatov;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
public class UserController {
    private final UserManager userManager = new UserManager();

    private String wrapErrorHandle(Runnable task, String success) {
        try {
            task.run();
        } catch (Exception e) {
            return e.getMessage();
        }
        return success;
    }

    private String wrapErrorHandle(Supplier<String> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/register")
    public String register(@RequestParam int userId) {
        if (userManager.registerUser(userId)) {
            return "User " + userId + " was registered";
        } else {
            return "User with this id already exists";
        }
    }

    @RequestMapping("/buy")
    public String buy(@RequestParam int userId, @RequestParam String stock, @RequestParam int number) {
        return wrapErrorHandle(() -> userManager.buyStock(userId, stock, number), "Purchase successful");
    }

    @RequestMapping("/sell")
    public String sell(@RequestParam int userId, @RequestParam String stock, @RequestParam int number) {
        return wrapErrorHandle(() -> userManager.sellStock(userId, stock, number), "Sale successful");
    }

    @RequestMapping("/deposit")
    public String deposit(@RequestParam int userId, @RequestParam int addendum) {
        return wrapErrorHandle(() -> userManager.addToUserBalance(userId, addendum), "Successful deposit");
    }

    @RequestMapping("/total")
    public String total(@RequestParam int userId) {
        return wrapErrorHandle(() -> Integer.toString(userManager.calcUserTotalValue(userId)));
    }

    @RequestMapping("/portfolio")
    public String portfolio(@RequestParam int userId) {
        return wrapErrorHandle(() -> userManager.getUserPortfolio(userId));
    }
}
