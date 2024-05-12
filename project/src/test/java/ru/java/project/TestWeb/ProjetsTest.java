package ru.java.project.TestWeb;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ProjetsTest {
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
    public void editProjectTest() {
        driver.get("http://localhost:8080/project-personal-page/1");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        String name = driver.findElement(By.xpath("/html/body/div/div/div[3]/div[1]/div/p[2]/span")).getText();

        driver.get("http://localhost:8080/edit-project/1");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        WebElement nameInput = driver.findElement(By.name("name"));
        nameInput.sendKeys(" edited");

        WebElement confirmButton = driver.findElement(By.cssSelector("button[type='submit']"));
        confirmButton.click();

        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        assertEquals(name + " edited", driver.findElement(By.xpath("/html/body/div/div/div[3]/div[1]/div/p[2]/span")).getText());
    }

    @Test
    void addProjectTest() {
        driver.get("http://localhost:8080/list_of_projects");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        var before = driver.findElements(By.tagName("tr")).size();

        driver.findElement(By.xpath("/html/body/div/a")).click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        driver.findElement(By.id("name")).sendKeys("Новый тесттовый проект");
        driver.findElement(By.id("start_date")).sendKeys("01012023");

        driver.findElement(By.xpath("/html/body/div/div/div[3]/form/button")).click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        assertTrue(driver.getPageSource().contains("Новый тесттовый проект"));

        var after = driver.findElements(By.tagName("tr")).size();
        assertEquals(1 + before, after);

    }

    @Test
    void deleteProjectTest() {
        driver.get("http://localhost:8080/list_of_projects");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        var before = driver.findElements(By.tagName("tr")).size();

        driver.get("http://localhost:8080/edit-project/5");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);


        var name = driver.findElement(By.xpath("/html/body/div/div/div[3]/h2")).getText();

        driver.findElement(By.xpath("/html/body/div/div/div[3]/form[2]/button")).click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

        var after = driver.findElements(By.tagName("tr")).size();
        assertEquals(before, after + 1);

        assertFalse(driver.getPageSource().contains(name));
    }

}
