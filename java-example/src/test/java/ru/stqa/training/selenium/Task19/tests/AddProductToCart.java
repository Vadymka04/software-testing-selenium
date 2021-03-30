package ru.stqa.training.selenium.Task19.tests;

import io.cucumber.java8.En;
import ru.stqa.training.selenium.Task19.app.Application;

public class AddProductToCart implements En {

    private static Application application = new Application();
    static {
        Runtime.getRuntime().addShutdownHook(new Thread(()->{application.quit(); application=null;}));
    }

    public AddProductToCart() {

        When("^we add some product to cart$", () -> {
            application.openMainPage();
            application.addProductToCart(1);
        });
        Then("^number of items in the cart is growing on '(\\d+)'$", (Integer arg0) -> {
            application.checkUpdateCart();
        });

        When("^we delete all products from cart$", () -> {
            application.deleteAllProductsFromCart();
        });
        Then("^cart is empty$", () -> {
            application.checkThatCartIsEmpty();
        });
    }
}
