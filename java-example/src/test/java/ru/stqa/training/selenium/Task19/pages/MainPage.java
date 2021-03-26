package ru.stqa.training.selenium.Task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MainPage extends Page {
    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MainPage open() {
        driver.get("http://localhost/litecart/en/");
        return this;
    }

    @FindBy(css = "#box-most-popular .products>li")
    private List<WebElement> listProducts;

    @FindBy(css = "#cart .quantity")
    private WebElement itemsInCart;

    @FindBy(css = ".options select")
    private WebElement productOptionSize;

    public void clickRandomProduct() {
        listProducts.get(ThreadLocalRandom.current().nextInt(0, listProducts.size())).click();
    }

    public Integer itemsInCartCount() {
        return Integer.parseInt((itemsInCart).getText());
    }

}
