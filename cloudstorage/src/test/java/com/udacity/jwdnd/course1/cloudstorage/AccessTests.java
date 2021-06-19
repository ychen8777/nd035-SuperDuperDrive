package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccessTests {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
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

}
