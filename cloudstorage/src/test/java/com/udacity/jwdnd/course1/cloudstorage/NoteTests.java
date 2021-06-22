package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteTests {

    @LocalServerPort
    private int port;

    private static WebDriver driver;
    private LoginPage loginPage;
    private HomePageNote homePageNote;

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
    public void getNoteTab() {
        // WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        // noteTab.click();

        //WebElement addNoteButton =  driver.findElement(By.id("addNoteButton"));
        //Assertions.assertNotNull(addNoteButton, "Add new note button not found");
        this.homePageNote = new HomePageNote(driver);

        WebElement noteTab = this.homePageNote.getNotesTab();
        this.homePageNote.gotoNoteTab();
        Assertions.assertTrue(Boolean.parseBoolean(noteTab.getAttribute("aria-selected")), "Note tab is not active.");
    }

    @Test
    @Order(3)
    public void openNoteModal() {
        //homePageNote.getNoteModal();
        //this.noteModal = new NoteModal(driver);

        //WebElement saveNoteButton = driver.findElement(By.id("noteSubmit"));
        //Assertions.assertNotNull(saveNoteButton, "save changes button not found");

        this.homePageNote = new HomePageNote(driver);
        this.homePageNote.openNoteModal();
        WebElement noteModal = this.homePageNote.getNoteModal();
        Assertions.assertTrue(Boolean.parseBoolean(noteModal.getAttribute("aria-hidden")), "Add note modal is not showing.");
     }

    @Test
    @Order(4)
    public void closeNoteModal() {
        //noteModal.closeModal();
        //WebElement saveNoteButton = driver.findElement(By.id("noteSubmit"));
        //Assertions.assertNull(saveNoteButton, "Note modal not closed properly");

        this.homePageNote = new HomePageNote(driver);
        this.homePageNote.closeModal();
        WebElement noteModal = this.homePageNote.getNoteModal();
        Assertions.assertFalse(Boolean.parseBoolean(noteModal.getAttribute("aria-hidden")), "Add note modal failed to close.");
    }

}
