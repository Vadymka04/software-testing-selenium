package ru.stqa.training.selenium.Task19.tests;

import org.junit.Test;

public class AddProductToCartAndDel extends TestBase{

    @Test
    public void addAndDelProductsToCart(){
        app.openMainPage();
        app.addProductToCart(3);
        app.deleteAllProductsFromCart();
    }

}
