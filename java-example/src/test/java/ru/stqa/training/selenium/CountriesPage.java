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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CountriesPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void countrySortCheck() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        WebElement tableCountries = driver.findElement(By.cssSelector("table.dataTable"));
        List<WebElement> countryRows = tableCountries.findElements(By.cssSelector("tr.row"));
        ArrayList<String> countryList = new ArrayList<>();
        for (int i = 0; i < countryRows.size(); i++) {
            countryList.add(countryRows.get(i).findElement(By.cssSelector("td:nth-child(5)")).getAttribute("textContent"));
            if (!countryRows.get(i).findElement(By.cssSelector("td:nth-child(6)")).getAttribute("textContent").equals("0")) {
                countryRows.get(i).findElement(By.cssSelector("td:nth-child(5)>a")).click();
                WebElement tableZones = driver.findElement(By.cssSelector("#table-zones"));
                List<WebElement> stateRows = tableZones.findElements(By.cssSelector("tr:not(.header):not(:last-child)"));
                ArrayList<String> stateList = new ArrayList<>();

                for (int s = 0; s < stateRows.size(); s++) {
                    stateList.add(stateRows.get(s).findElement(By.cssSelector("td:nth-child(3)")).getAttribute("textContent"));
                }

                List sortedStates = new ArrayList(stateList);
                Collections.sort(stateList);
                Assert.assertTrue(stateList.equals(sortedStates));
            }
            driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
            countryRows = driver.findElements(By.cssSelector("table.dataTable tr.row"));
        }

        List sortedCountries = new ArrayList(countryList);
//        Collections.sort(sortedCountries, Collections.reverseOrder());
        Collections.sort(sortedCountries);
        Assert.assertTrue(countryList.equals(sortedCountries));

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
