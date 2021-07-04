package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.Assert;

import java.util.List;

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

    @Test
    @Order(3)
    public void openCredentialModal() {
        this.homePageCredential = new HomePageCredential(driver);
        this.homePageCredential.openCredentialModal();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(this.homePageCredential.getCredentialSaveButton()));

        WebElement credentialModal = this.homePageCredential.getCredentialModal();
        Assertions.assertFalse(Boolean.parseBoolean(credentialModal.getAttribute("aria-hidden")), "Add credential modal is not showing.");

    }

    @Test
    @Order(4)
    public void closeCredentialModal() throws InterruptedException {
        this.homePageCredential = new HomePageCredential(driver);
        this.homePageCredential.closeCredentialModal();

        Thread.sleep(1000);

        WebElement credentialModal = this.homePageCredential.getCredentialModal();
        Assertions.assertTrue(Boolean.parseBoolean(credentialModal.getAttribute("aria-hidden")), "Credential modal is not closed.");
    }

    @Test
    @Order(5)
    public void addCredential() throws InterruptedException {
        this.homePageCredential = new HomePageCredential(driver);
        this.homePageCredential.openCredentialModal();

        String url = "www.seleniumtest.io";
        String username = "test1";
        String password = "testtest";

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(this.homePageCredential.getCredentialSaveButton()));

        this.homePageCredential.addCredential(url, username, password);
        Thread.sleep(800);

        this.homePageCredential.getCredentialSaveButton().click();

        // land to result page
        String actualUrl = driver.getCurrentUrl();
        String resultUrl = "http://localhost:" + port + "/result/success";
        Assertions.assertEquals(actualUrl, resultUrl);

        // return to home page
        driver.get("http://localhost:" + port + "/home");
        this.homePageCredential.clickCredentialTab();

        String actualCredentialUrl = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/th")).getAttribute("innerHTML");
        String actualUsername = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[2]")).getAttribute("innerHTML");
        String actualPassword = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[3]")).getAttribute("innerHTML");

        Assertions.assertEquals(url, actualCredentialUrl);
        Assertions.assertEquals(username, actualUsername);
        Assertions.assertNotEquals(password, actualPassword);   // displayed password should be encrypted

    }

    @Test
    @Order(6)
    public void deleteCredential() {
        // return to home page
        driver.get("http://localhost:" + port + "/home");
        this.homePageCredential = new HomePageCredential(driver);
        this.homePageCredential.clickCredentialTab();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        //WebElement submitButton = wait.until(webDriver -> webDriver.findElement(By.id("noteSaveButton")));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a"))));

        WebElement deleteButton = driver.findElement(By.xpath("//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a"));
        deleteButton.click();

        // land to result page
        String actualUrl = driver.getCurrentUrl();
        String resultUrl = "http://localhost:" + port + "/result/success";
        Assertions.assertEquals(actualUrl, resultUrl);

        // return to home page
        driver.get("http://localhost:" + port + "/home");
        this.homePageCredential.getCredentialsTab();

        // Check the table is empty
        List<WebElement> tableRow = driver.findElements(By.xpath("//*[@id='credentialTable']/tbody/tr"));
        Assertions.assertEquals(0, tableRow.size());

    }

}
