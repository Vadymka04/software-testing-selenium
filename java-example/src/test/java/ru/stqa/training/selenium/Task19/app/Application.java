package ru.stqa.training.selenium.Task19.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.stqa.training.selenium.Task19.pages.CartPage;
import ru.stqa.training.selenium.Task19.pages.MainPage;
import ru.stqa.training.selenium.Task19.pages.ProductPage;

public class Application {
    private WebDriver driver;

    private MainPage mainPage;
    private ProductPage productPage;
    private CartPage cartPage;

    public Application() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    public void openMainPage(){
        mainPage.open();
    }

    public void addProductToCart(Integer num) {
        for (int i = 1; i <= num; i++) {
            Integer finalCount = mainPage.itemsInCartCount() + 1;
            mainPage.clickRandomProduct();
            productPage.selectProductSize("Small");
            productPage.addToCart();
            productPage.checkUpdateCart(finalCount);
            mainPage.open();
        }
    }

    public void deleteAllProductsFromCart() {
        cartPage.open();
        int rowsInTable = cartPage.rowsInTableSize();
        while (cartPage.productListIsPresent()) {
            if (cartPage.productsInCart() > 0) cartPage.clickProductInCart();
            cartPage.clickDelBtnProductInCart();
            rowsInTable--;
            int finalRowsInTable = rowsInTable;
            if (rowsInTable < 4) {
                cartPage.waitEmptyCart();
            } else {
                cartPage.waitDelProductFromTable(finalRowsInTable);
            }
        }
    }


    public void quit() {
        driver.quit();
        driver = null;
    }


}
