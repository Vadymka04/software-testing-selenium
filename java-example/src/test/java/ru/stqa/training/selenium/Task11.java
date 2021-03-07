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
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Task11 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void createUserAndCheckLogin() {
        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.cssSelector("#box-account-login a")).click();

        String email = createRandomEmail();
        String password = createRandomName(8);
        //1
        WebElement createAccount = driver.findElement(By.cssSelector("#create-account"));
        createAccount.findElement(By.cssSelector("tr [name=firstname]")).sendKeys(createRandomName(8));
        createAccount.findElement(By.cssSelector("tr [name=lastname]")).sendKeys(createRandomName(12));
        createAccount.findElement(By.cssSelector("tr [name=address1]")).sendKeys(createRandomName(18) + " " + createRandomDigit(3));
        createAccount.findElement(By.cssSelector("tr [name=city]")).sendKeys(createRandomName(7));
        createAccount.findElement(By.cssSelector("tr [name=postcode]")).sendKeys(createRandomDigit(5));
        createAccount.findElement(By.cssSelector("tr .select2-selection__arrow")).click();

        List<WebElement> listCountry = driver.findElements(By.cssSelector(".select2-results li"));
        int i = 0;
        while (!listCountry.get(i).getText().equals("United States")) {
            i++;
        }
        listCountry.get(i).click();

        createAccount.findElement(By.cssSelector("tr [name=email]")).sendKeys(email);
        createAccount.findElement(By.cssSelector("tr [name=phone]")).sendKeys("+1" + createRandomDigit(8));
        createAccount.findElement(By.cssSelector("tr [name=password]")).sendKeys(password);
        createAccount.findElement(By.cssSelector("tr [name=confirmed_password]")).sendKeys(password);
        createAccount.findElement(By.cssSelector("button[name=create_account]")).click();

        //Так как листбокс с выбором зоны не доступен - будет ошибка, предложено выбрать зону и заново указать пароль
        createAccount = driver.findElement(By.cssSelector("#create-account"));
        Select selectZone = new Select(createAccount.findElement(By.cssSelector("tr [name=zone_code]")));
        selectZone.selectByIndex(3);
        createAccount.findElement(By.cssSelector("tr [name=password]")).sendKeys(password);
        createAccount.findElement(By.cssSelector("tr [name=confirmed_password]")).sendKeys(password);
        createAccount.findElement(By.cssSelector("button[name=create_account]")).click();
        //2
        driver.findElement(By.cssSelector("#box-account li:last-child>a")).click();
        //3
        driver.findElement(By.cssSelector("#box-account-login [name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("#box-account-login [name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("#box-account-login [name=login]")).click();
        //4
        driver.findElement(By.cssSelector("#box-account li:last-child>a")).click();
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

    public String createRandomNameLowCase(int length) {
        Random r = new Random();
        String s = r.ints(97, 122)
                .filter(i -> (i > 97))
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

    public String createRandomEmail() {
        return createRandomNameLowCase(8) + "@" + createRandomNameLowCase(8) + "." + createRandomNameLowCase(3);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
