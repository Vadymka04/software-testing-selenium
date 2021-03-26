package ru.stqa.training.selenium.Task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends Page {

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".options select")
    public Select selectSize;

    @FindBy(css = "#box-product .quantity [type=submit]")
    public WebElement addToCart;

    @FindBy(css = "#cart .quantity")
    public WebElement countProductsInCart;

    public void selectProductSize(String size) {
        if (isElementPresent(By.cssSelector(".options select"))) {
            selectSize.selectByValue(size);
        }
    }

    public void addToCart() {
        addToCart.click();
    }

    public void checkUpdateCart(Integer count) {
        wait.until(d -> countProductsInCart.getText().equals(count.toString()));
    }


}
