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

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminLogin {
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
//        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void myFirstTest() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        By header = By.xpath("//td[@id='content']/h1");

//        WebElement boxMenu = driver.findElement(By.cssSelector("#box-apps-menu"));
        List<WebElement> menuItems = driver.findElements(By.cssSelector("#box-apps-menu>#app-"));
        int menuItemCount = menuItems.size();

        for (int i = 0; i < menuItemCount; i++) {
            menuItems.get(i).click();
            String headerText = driver.findElement(header).getText();
            Assert.assertTrue(headerText.length() > 0);
            menuItems = driver.findElements(By.cssSelector("#box-apps-menu>#app-"));
//            List<WebElement> menuSubItems = menuItems.get(i).findElements(By.cssSelector(".docs"));
            List<WebElement> menuSubItems = driver.findElements(By.cssSelector("#box-apps-menu>#app->.docs>li")); //обновляем список - структура изменилась
            if (menuSubItems.size()!=0){
                for (int s = 0; s < menuSubItems.size(); s++){
                    menuSubItems.get(s).click();
                    headerText = driver.findElement(header).getText();
                    Assert.assertTrue(headerText.length() > 0);
                    menuSubItems = driver.findElements(By.cssSelector("#box-apps-menu>#app->.docs>li"));//обновляем список - структура изменилась
                    menuItems = driver.findElements(By.cssSelector("#box-apps-menu>#app-"));//обновляем список - структура изменилась
                }
            }
        }


    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
