package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Task10 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void productCheck() {
        driver.get("http://localhost/litecart/en/");
        List<WebElement> products = driver.findElements(By.cssSelector("#box-campaigns li"));
        String productName = products.get(0).findElement(By.cssSelector(".name")).getText();
        String price = products.get(0).findElement(By.cssSelector(".regular-price")).getText();
        String priceColor = products.get(0).findElement(By.cssSelector(".regular-price")).getCssValue("color");
        Double priceFontSize = Double.parseDouble(products.get(0).findElement(By.cssSelector(".regular-price")).getCssValue("font-size").replace("px", ""));
        Double discountPriceFontSize = Double.parseDouble(products.get(0).findElement(By.cssSelector(".campaign-price")).getCssValue("font-size").replace("px", ""));
        String discountPrice = products.get(0).findElement(By.cssSelector(".campaign-price")).getText();
        String discountPriceColor = products.get(0).findElement(By.cssSelector(".campaign-price")).getCssValue("color");
        String discountFont = products.get(0).findElement(By.cssSelector(".campaign-price")).getCssValue("font-weight");

        products.get(0).click();
        assertEquals(driver.findElement(By.cssSelector("#box-product .title")).getText(), productName); //а) на главной странице и на странице товара совпадает текст названия товара
        assertEquals(driver.findElement(By.cssSelector("#box-product .price-wrapper .regular-price")).getText(), price); //б) на главной странице и на странице товара совпадают цены (обычная)
        assertEquals(driver.findElement(By.cssSelector("#box-product .price-wrapper .campaign-price")).getText(), discountPrice); //б) на главной странице и на странице товара совпадают цены (акционная)
        assertTrue(checkColor(priceColor, "price")); //в) обычная цена серая (можно считать, что "серый" цвет это такой, у которого в RGBa представлении одинаковые значения для каналов R, G и B)
        assertEquals(driver.findElement(By.cssSelector("#box-product .price-wrapper .regular-price")).getCssValue("text-decoration-line"), "line-through");//в) обычная цена зачёркнутая
        assertTrue(checkColor(discountPriceColor, "discontPrice"));//г) акционная красная на главной
        assertTrue(checkColor(driver.findElement(By.cssSelector("#box-product .price-wrapper .campaign-price")).getCssValue("color"), "discontPrice"));//г акционная красная на странице товара
        assertEquals(discountFont, "700");//г) акционная цена на главной страницен жирная
        assertEquals(driver.findElement(By.cssSelector("#box-product .price-wrapper .campaign-price")).getCssValue("font-weight"), "700" );//г) акционная цена на страницен товара жирная
        assertTrue(priceFontSize<discountPriceFontSize);//д) акционная цена крупнее, чем обычная(главная)
        assertTrue(Double.parseDouble(driver.findElement(By.cssSelector("#box-product .price-wrapper .regular-price")).getCssValue("font-size").replace("px", ""))<
                Double.parseDouble(driver.findElement(By.cssSelector("#box-product .price-wrapper .campaign-price")).getCssValue("font-size").replace("px", ""))); //д) акционная цена крупнее, чем обычная(страница товара)

    }

    public Boolean checkColor(String color, String price) {

        String[] numbers = color.replace("rgba(", "").replace(")", "").split(",");
        int r = Integer.parseInt(numbers[0].trim());
        int g = Integer.parseInt(numbers[1].trim());
        int b = Integer.parseInt(numbers[2].trim());
        if (price == "price") {
            if (r == g && r == b) return true;
        } else if (price == "discontPrice") {
            if (g == 0 && b == 0) return true;
        }
        return false;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
