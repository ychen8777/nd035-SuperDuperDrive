package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialTests {

    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private LoginPage loginPage;
    private HomePageCredential homePageCredential;

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
        Thread.sleep(1600);
    }

    // user login
    @Test
    @Order(1)
    public void loginUser() {
        driver.get("http://localhost:" + port + "/login");
        this.loginPage = new LoginPage(driver);
        String testUsername = "SeleniumTest2";
        String testPassword = "SeleniumTest2";
        loginPage.loginUser(testUsername, testPassword);

        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "http://localhost:" + port + "/home";
        Assertions.assertEquals(actualUrl, expectedUrl);
    }

    @Test
    @Order(2)
    public void gotoCredentialTab() {
        driver.get("http://localhost:" + port + "/home");

        this.homePageCredential = new HomePageCredential(driver);
        this.homePageCredential.clickCredentialTab();

        WebElement credentialsTab = this.homePageCredential.getCredentialsTab();
        Assertions.assertTrue(Boolean.parseBoolean(credentialsTab.getAttribute("aria-selected")), "Note tab is not active.");

    }

}
