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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeesTest {
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
    void employeeAddAndSaveTest() {

        driver.get("http://localhost:8080/add-employee");
        WebElement nameInput = driver.findElement(By.id("name"));
        nameInput.sendKeys("Марфа");

        WebElement addressInput = driver.findElement(By.id("address"));
        addressInput.sendKeys("Мира 24");

        WebElement dateOfBirthInput = driver.findElement(By.id("dateOfBirth"));
        dateOfBirthInput.sendKeys("01011991");

        WebElement educationInput = driver.findElement(By.id("education"));
        educationInput.sendKeys("МФТИ");

        WebElement educationDegreeInput = driver.findElement(By.id("educationDegree"));
        educationDegreeInput.sendKeys("лейтенант");

        WebElement phoneInput = driver.findElement(By.id("phone"));
        phoneInput.sendKeys("8001234567");

        WebElement workExpInput = driver.findElement(By.id("workExp"));
        workExpInput.sendKeys("5");

        WebElement addButton = driver.findElement(By.cssSelector("button[type='submit']"));
        addButton.click();

        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        assertTrue(driver.getCurrentUrl().contains("/list_of_employees"));
        assertTrue(driver.getPageSource().contains("Марфа"));

    }

    @Test
    public void editEmployeeTest() {
        driver.get("http://localhost:8080/edit-employee/1");

        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        WebElement workExpInput = driver.findElement(By.name("work_exp"));
        workExpInput.clear();
        workExpInput.sendKeys("2");

        WebElement confirmButton = driver.findElement(By.cssSelector("button[type='submit']"));
        confirmButton.click();

        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        driver.get("http://localhost:8080/employee-personal-page/1");
        assertEquals("2", driver.findElement(By.xpath("/html/body/div/div/div[3]/div[1]/div/p[10]/span")).getText());

    }

    @Test
    public void dismissEmployeeTest() {
        driver.get("http://localhost:8080/edit-employee/5");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        WebElement confirmButton = driver.findElement(By.xpath("/html/body/div/div/div[3]/form[2]/button"));
        confirmButton.click();

        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        assertTrue(!(driver.getPageSource().contains("Окорокова Елена Владимировна")));
    }

    @Test
    void historyHrefsTest() {

        driver.get("http://localhost:8080/employee-personal-page/1");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        driver.findElement(By.xpath("/html/body/div/div/div[3]/div[1]/div/p[11]/a")).click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        assertEquals("История проектов сотрудника", driver.getTitle());

        driver.get("http://localhost:8080/employee-personal-page/1");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        driver.findElement(By.xpath("/html/body/div/div/div[3]/div[1]/div/p[12]/a")).click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        assertEquals("История должностей сотрудника", driver.getTitle());

        driver.get("http://localhost:8080/employee-personal-page/1");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        driver.findElement(By.xpath("/html/body/div/div/div[3]/div[1]/div/p[13]/a")).click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        assertEquals("История выплат", driver.getTitle());

    }

}
