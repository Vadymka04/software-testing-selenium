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
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Task14 {
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
    public void switchBetweenWindows() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.cssSelector("#content .button")).click();
        List<WebElement> elementsInTable = driver.findElements(By.cssSelector("#content form tr"));
        String originalWindow = driver.getWindowHandle();
        for (int i = 0; i < elementsInTable.size(); i++) {
            if (elementsInTable.get(i).findElements(By.cssSelector("[target=_blank]")).size() > 0) {
                Set<String> oldWindowsSet = driver.getWindowHandles();
                elementsInTable.get(i).findElement(By.cssSelector("[target=_blank]")).click();
                wait.until(d -> d.getWindowHandles().size() > oldWindowsSet.size());
                Set<String> newWindowsSet = driver.getWindowHandles();
                newWindowsSet.removeAll(oldWindowsSet);
                String newWindowHandle = newWindowsSet.iterator().next();
                driver.switchTo().window(newWindowHandle);
                driver.close();
                driver.switchTo().window(originalWindow);
            }
        }
    }


    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
