package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Task13 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
//        driver = new FirefoxDriver();
//        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
//        InternetExplorerOptions options = new InternetExplorerOptions().setPageLoadStrategy(PageLoadStrategy.NONE);
//        driver = new InternetExplorerDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 5/*seconds*/);
    }

    @Test
    public void addProductToCartAndDel() {
        driver.get("http://localhost/litecart/en/");
        addProductToCart(itemsCountBase());
        addProductToCart(itemsCountBase());
        addProductToCart(itemsCountBase());
        deleteAllFromCart();
    }

    public void addProductToCart(Integer count) {
        Integer finalCount = count + 1;
        List<WebElement> listProducts = driver.findElements(By.cssSelector("#box-most-popular .products>li"));
        int randomNum = ThreadLocalRandom.current().nextInt(0, listProducts.size());

        listProducts.get(randomNum).click();
        if (isElementPresent(By.cssSelector(".options select"))) {
            Select selectSize = new Select(driver.findElement(By.cssSelector(".options select")));
            selectSize.selectByValue("Small");
        }

        driver.findElement(By.cssSelector("#box-product .quantity [type=submit]")).click();
        wait.until(d -> d.findElement(By.cssSelector("#cart .quantity")).getText().equals(finalCount.toString()));
        driver.findElement(By.cssSelector("#site-menu-wrapper .general-0>a")).click();
    }

    public void deleteAllFromCart() {
        driver.findElement(By.cssSelector("#cart-wrapper .link")).click();
        int rowsInTable = driver.findElements(By.cssSelector("#checkout-summary-wrapper tr:not(.header):not(:last-child)")).size();
        while (isElementPresent(By.cssSelector("#box-checkout-cart"))) {
            List<WebElement> productsInCart = driver.findElements(By.cssSelector("#box-checkout-cart .shortcuts>li"));
            List<WebElement> productsInTopCart = driver.findElements(By.cssSelector("#box-checkout-cart .items>li"));
            if (productsInCart.size() > 0) productsInCart.get(0).click();
            productsInTopCart.get(0).findElement(By.cssSelector("button[value=Remove]")).click();
            rowsInTable--;
            int finalRowsInTable = rowsInTable;
            if (rowsInTable < 4) {
                wait.until(d -> d.findElement(By.cssSelector("#checkout-cart-wrapper em")).getText().equals("There are no items in your cart."));
            } else {
                wait.until(d -> d.findElements(By.cssSelector("#checkout-summary-wrapper tr:not(.header):not(:last-child)")).size() == finalRowsInTable);
            }
        }
    }

    public Integer itemsCountBase() {
        return Integer.parseInt(driver.findElement(By.cssSelector("#cart .quantity")).getText());
    }

    public boolean isElementPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
