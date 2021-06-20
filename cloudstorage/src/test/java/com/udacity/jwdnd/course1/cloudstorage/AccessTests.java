package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.juli.logging.Log;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccessTests {

    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private SignupPage signupPage;
    private LoginPage loginPage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    @BeforeEach
    public void beforeEach() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    // before login
    @Test
    public void getHomePageBeforeLogin() {
        driver.get("http://localhost:" + port + "/home");
        String actualUrl= driver.getCurrentUrl();
        String expectedUrl= "http://localhost:" + port + "/login";
        Assertions.assertEquals(expectedUrl,actualUrl);
    }

    @Test
    public void getSignupPageBeforeLogin() {
        driver.get("http://localhost:" + port + "/signup");
        String actualUrl= driver.getCurrentUrl();
        String expectedUrl= "http://localhost:" + port + "/signup";
        Assertions.assertEquals(expectedUrl,actualUrl);
    }

    @Test
    public void getResultPageBeforeLogin() {
        driver.get("http://localhost:" + port + "/result");
        String actualUrl= driver.getCurrentUrl();
        String expectedUrl= "http://localhost:" + port + "/login";
        Assertions.assertEquals(expectedUrl,actualUrl);
    }

    // sign up user
    @Test
    public void signupUser() {
        getSignupPageBeforeLogin();
        this.signupPage = new SignupPage(driver);
        String testFirstName = "Selenium";
        String testLastName = "Test2";
        String testUsername = "SeleniumTest2";
        String testPassword = "SeleniumTest2";

        // successful sign up
        signupPage.signupUser(testFirstName, testLastName, testUsername, testPassword);
        WebElement returnMsg= driver.findElement(By.id("signup-success"));
        Assertions.assertNotNull(returnMsg, "success message not found");

        // unsuccessful sign up, username not available
        signupPage.signupUser(testFirstName, testLastName, testUsername, testPassword);
        returnMsg= driver.findElement(By.id("signup-error"));
        Assertions.assertNotNull(returnMsg, "error message not found");

    }

    // user login
    @Test
    public void loginUser() {
        getLoginPage();
        this.loginPage = new LoginPage(driver);
        String testUsername = "SeleniumTest2";
        String testPassword = "Selenium";

        // invalid username or password
        loginPage.loginUser(testUsername, testPassword);
        WebElement returnMsg= driver.findElement(By.id("login-error"));
        Assertions.assertNotNull(returnMsg, "login error message not found");

        // valid login
        testPassword = "SeleniumTest2";
        loginPage.loginUser(testUsername, testPassword);

        String actualUrl= driver.getCurrentUrl();
        String expectedUrl= "http://localhost:" + port + "/home";
        Assertions.assertEquals(actualUrl, expectedUrl);
    }

}
