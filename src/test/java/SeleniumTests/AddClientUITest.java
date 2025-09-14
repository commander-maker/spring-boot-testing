package SeleniumTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class AddClientUITest {

    WebDriver driver = new ChromeDriver();

    @BeforeClass
    public void setup() {

        driver = new ChromeDriver();
        driver.get("http://localhost:8080/add-client");
    }

    @Test
    public void testAddClient() {
        driver.findElement(By.id("name")).sendKeys("John Doe");
        driver.findElement(By.id("email")).sendKeys("john@example.com");
        driver.findElement(By.id("submit")).click();

        String confirmation = driver.findElement(By.id("confirmation")).getText();
        Assert.assertEquals(confirmation, "Client added successfully!");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
