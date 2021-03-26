package ru.stqa.training.selenium.Task19.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends Page{

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public CartPage open() {
        driver.get("http://localhost/litecart/en/checkout");
        return this;
    }

    @FindBy(css="#checkout-summary-wrapper tr:not(.header):not(:last-child)")
    public List<WebElement> rowsInTable;

    @FindBy(css = "#box-checkout-cart .shortcuts>li")
    public List<WebElement> productsInCart;

    @FindBy(css="button[value=Remove]")
    public WebElement btnRemove;

    @FindBy(css="#checkout-cart-wrapper em")
    public WebElement emptyCartText;

    public int rowsInTableSize(){
        return rowsInTable.size();
    }

    public int productsInCart(){
        return productsInCart.size();
    }

    public void clickProductInCart(){productsInCart.get(0).click();}

    public void clickDelBtnProductInCart(){
        btnRemove.click();
    }

    public void waitEmptyCart(){
        wait.until(d -> emptyCartText.getText().equals("There are no items in your cart."));
    }

    public void waitDelProductFromTable(int productCount){
        wait.until(d -> rowsInTable.size() == productCount);
    }

    public boolean productListIsPresent(){
        return isElementPresent(By.id("box-checkout-cart"));
    }
}
