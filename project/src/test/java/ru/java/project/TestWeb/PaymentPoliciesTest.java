package ru.java.project.TestWeb;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentPoliciesTest {
    private ChromeDriver driver;

    @BeforeEach
    public void openBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().setPosition(new org.openqa.selenium.Point(0, 0));
        driver.manage().window().setSize(new Dimension(1200, 1000));
    }

    @AfterEach
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void policyEditButton() {

        driver.get("http://localhost:8080/list_of_payment_policies");

        WebElement personBut = driver.findElement(By.xpath("/html/body/div/div/div/table/tbody/tr[1]/td[8]/a"));
        personBut.click();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        String i = driver.getTitle();
        assertEquals("Редактирование политики выплат", driver.getTitle());

    }

    @Test
    void policyAddButton() {

        driver.get("http://localhost:8080/list_of_payment_policies");

        var before = driver.findElements(By.tagName("tr")).size();

        WebElement personBut = driver.findElement(By.xpath("/html/body/div/div/div/a"));
        personBut.click();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals("Добавить новую политику выплат", driver.getTitle());

        WebElement type = driver.findElement(By.xpath("/html/body/div/div/div[3]/form/div[1]/select"));
        type.click();
        type = driver.findElement(By.xpath("/html/body/div/div/div[3]/form/div[1]/select/option[4]"));
        type.click();

        driver.findElement(By.xpath("/html/body/div/div/div[3]/form/div[5]/input")).sendKeys("День России");
        driver.findElement(By.xpath("/html/body/div/div/div[3]/form/div[6]/input")).sendKeys("5555");

        driver.findElement(By.xpath("/html/body/div/div/div[3]/form/button")).click();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        var after = driver.findElements(By.tagName("tr")).size();
        assertEquals(1 + before, after);

    }

    @Test
    void policyEditAndSave() {
        driver.get("http://localhost:8080/edit-policy/1");

        WebElement paymentInput = driver.findElement(By.xpath("/html/body/div/div[3]/div/div/form[1]/div[3]/input"));
        paymentInput.clear();
        paymentInput.sendKeys("10000");

        WebElement addButton = driver.findElement(By.xpath("/html/body/div/div[3]/div/div/form[1]/button"));
        addButton.click();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals("Список политик выплат", driver.getTitle());

        WebElement newPayment = driver.findElement(By.xpath("/html/body/div/div/div/table/tbody/tr[1]/td[7]"));
        String tmp =  newPayment.getText();
        assertEquals("10000.0", tmp);
    }

    @Test
    void checkHeader() {
        driver.get("http://localhost:8080/list_of_payment_policies");

        WebElement mainPage = driver.findElement(By.xpath("/html/body/header/nav/div/a"));
        mainPage.click();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals("Главная страница", driver.getTitle());

        WebElement employeePage = driver.findElement(By.xpath("/html/body/header/nav/div/div/ul/li[1]/a"));
        employeePage.click();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals("Список сотрудников", driver.getTitle());

        WebElement policyPage = driver.findElement(By.xpath("/html/body/header/nav/div/div/ul/li[2]/a"));
        policyPage.click();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals("Список политик выплат", driver.getTitle());

        WebElement projectPage = driver.findElement(By.xpath("/html/body/header/nav/div/div/ul/li[3]/a"));
        projectPage.click();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        assertEquals("Список проектов", driver.getTitle());

    }

}
