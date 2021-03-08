package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Task12 {
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
    }

    @Test
    public void checkCreationNewProduct() {
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.cssSelector("#content a.button:last-child")).click();

        String productName = createRandomName(7);

        WebElement general = driver.findElement(By.cssSelector("#tab-general"));
        general.findElement(By.cssSelector("[name=status][value='1']")).click();
        general.findElement(By.cssSelector("[name='name[en]'")).sendKeys(productName);
        general.findElement(By.cssSelector("[name=code")).sendKeys(createRandomDigit(6));
        general.findElement(By.xpath("//*[text()='Male']//parent::*//td/input")).click();
        File uploadFile = new File("src/test/resources/blue_duck.png");
        String filePath = uploadFile.getAbsolutePath();
        general.findElement(By.cssSelector("[name='new_images[]'")).sendKeys(filePath);
        general.findElement(By.cssSelector("[name='date_valid_from'")).sendKeys("01-01-2020");
        general.findElement(By.cssSelector("[name='date_valid_to'")).sendKeys("31-12-2022");

        driver.findElement(By.xpath("//a[text()='Information']")).click();
        WebElement information = driver.findElement(By.cssSelector("#tab-information"));
        Select selectManufact = new Select(information.findElement(By.cssSelector("[name=manufacturer_id]")));
        selectManufact.selectByVisibleText("ACME Corp.");
        information.findElement(By.cssSelector("[name=keywords]")).sendKeys(createRandomName(7));
        information.findElement(By.cssSelector("[name='short_description[en]']")).sendKeys(createRandomName(7));
        information.findElement(By.cssSelector(".trumbowyg-editor")).sendKeys(createRandomName(5) + " " + createRandomName(7));
        information.findElement(By.cssSelector("[name='head_title[en]']")).sendKeys(createRandomName(5));
        information.findElement(By.cssSelector("[name='meta_description[en]']")).sendKeys(createRandomName(4));

        driver.findElement(By.xpath("//a[text()='Prices']")).click();
        WebElement pricies = driver.findElement(By.cssSelector("#tab-prices"));
        pricies.findElement(By.cssSelector("[name=purchase_price]")).sendKeys(Keys.CONTROL + "A");
        pricies.findElement(By.cssSelector("[name=purchase_price]")).sendKeys(createRandomDigit(2) + "," + createRandomDigit(2));
        Select selectCurrency = new Select(pricies.findElement(By.cssSelector("[name=purchase_price_currency_code]")));
        selectCurrency.selectByValue("USD");
        pricies.findElement(By.cssSelector("[name='prices[USD]'")).sendKeys(createRandomDigit(2) + "," + createRandomDigit(2));
        driver.findElement(By.cssSelector(".button-set>[name=save]")).click();

        WebElement searhInput = driver.findElement(By.cssSelector("#content [name=query]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(searhInput).click().sendKeys(productName).sendKeys(Keys.ENTER).perform();

        List<WebElement> dataTable = driver.findElements(By.cssSelector("#content .dataTable tr:not(.header):not(:last-child)"));
        Assert.assertTrue(checkNewProductInTable(dataTable, productName));
    }

    public String createRandomName(int length) {
        Random r = new Random();
        String s = r.ints(65, 122)
                .filter(i -> (i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return s;
    }

    public String createRandomDigit(int length) {
        Random r = new Random();
        String s = r.ints(48, 57)
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return s;
    }

    public Boolean checkNewProductInTable(List<WebElement> data, String productName) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).findElement(By.cssSelector("td:nth-child(3)>a")).getText().equals(productName)) return true;
        }
        return false;
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
