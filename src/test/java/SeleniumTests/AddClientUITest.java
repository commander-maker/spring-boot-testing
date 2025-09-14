package SeleniumTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AddClientUITest {

    private WebDriver driver;

    @BeforeClass
    public void setup() {
        // Automatically downloads and sets up ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Chrome headless options for CI
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");               // Run without GUI
        options.addArguments("--no-sandbox");             // Required for CI environments
        options.addArguments("--disable-dev-shm-usage");  // Avoid memory issues in CI

        // Initialize driver
        driver = new ChromeDriver(options);

        // Open the add-client page
        driver.get("http://localhost:8080/add-client");
    }

    @Test
    public void testAddClient() {
        // Fill in the form
        driver.findElement(By.id("name")).sendKeys("John Doe");
        driver.findElement(By.id("email")).sendKeys("john@example.com");

        // Submit the form
        driver.findElement(By.id("submit")).click();

        // Verify confirmation message
        String confirmation = driver.findElement(By.id("confirmation")).getText();
        Assert.assertEquals(confirmation, "Client added successfully!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close the browser
        }
    }
}
