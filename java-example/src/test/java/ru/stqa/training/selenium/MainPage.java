package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void stickersCheck() {
        driver.get("http://localhost/litecart/en/");

        WebElement blockMostPopular = driver.findElement(By.cssSelector("#box-most-popular"));
        WebElement blockCampaigns = driver.findElement(By.cssSelector("#box-campaigns"));
        WebElement blockLatestProducts = driver.findElement(By.cssSelector("#box-latest-products"));

        List<WebElement> menuItems = new ArrayList<>();
        menuItems.add(blockMostPopular);
        menuItems.add(blockCampaigns);
        menuItems.add(blockLatestProducts);

        for (int i=0; i<menuItems.size(); i++){
            List<WebElement> products = menuItems.get(i).findElements(By.cssSelector("div.content>ul.products>li"));
            for (int p=0; p<products.size(); p++){
                WebElement product = products.get(p);
                List<WebElement> sticker = product.findElements(By.cssSelector("a.link>div.image-wrapper>div.sticker"));
                Assert.assertTrue(sticker.size()==1);
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
