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

public class Task17 {
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
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 3/*seconds*/);

    }

    @Test
    public void checkConsoleMessage() {
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=0");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //открываем поддериктории
        List<WebElement> elementsInTable = driver.findElements(By.cssSelector(".dataTable tr:not(.header):not(:last-child)"));
        int size = elementsInTable.size();
        for (int i = 1; i < size; i++) {
            if (elementsInTable.get(i).findElements(By.cssSelector("td:nth-child(3) .fa-folder")).size() > 0) {
                elementsInTable.get(i).findElement(By.cssSelector("td:nth-child(3) a")).click();
                Assert.assertTrue(driver.manage().logs().get("browser").getAll().size() == 0);
                elementsInTable = driver.findElements(By.cssSelector(".dataTable tr:not(.header):not(:last-child)"));
                size = elementsInTable.size();
            }
        }
        //открываем странички с товарами
        elementsInTable = driver.findElements(By.cssSelector(".dataTable tr:not(.header):not(:last-child)"));
        for (int i = 1; i < elementsInTable.size(); i++) {
            if (elementsInTable.get(i).findElements(By.cssSelector("td:nth-child(3) .fa-folder-open")).size() == 0) {
                elementsInTable.get(i).findElement(By.cssSelector("td:nth-child(3) a")).click();
                Assert.assertTrue(driver.manage().logs().get("browser").getAll().size() == 0);
                driver.navigate().back();
                elementsInTable = driver.findElements(By.cssSelector(".dataTable tr:not(.header):not(:last-child)"));
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
