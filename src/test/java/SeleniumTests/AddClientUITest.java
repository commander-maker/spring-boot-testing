package SeleniumTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class AddClientUITest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // Remove headless to see the browser
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Go directly to the AddClient page route if your app uses routing
        driver.get("http://localhost:5173/"); // <-- adjust if needed
    }

    @Test
    public void testAddClient() {
        // Wait for the form container
        WebElement formContainer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".add-client-container"))
        );

        // Wait until the Name input is clickable
        WebElement nameInput = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("name"))
        );
        nameInput.sendKeys("John Doe");

        WebElement emailInput = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("email"))
        );
        emailInput.sendKeys("jnh@example.com");

        WebElement addressInput = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("address"))
        );
        addressInput.sendKeys("123 Street, City");

        WebElement submitButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("submit"))
        );
        submitButton.click();

        // Wait for confirmation message
        WebElement confirmation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success-message"))
        );

        Assert.assertEquals(confirmation.getText(), "Client added successfully!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
