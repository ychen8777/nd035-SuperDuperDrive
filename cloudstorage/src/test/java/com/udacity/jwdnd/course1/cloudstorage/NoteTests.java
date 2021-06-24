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

import java.util.List;

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

    @Test
    @Order(5)
    public void addNewNote() throws InterruptedException {
        openNoteModal();
        //Thread.sleep(1000);
        //this.homePageNote.closeModal();

        //*[@id="noteTable"]/tbody[1]/tr/th
        String noteTitle = "1 note by Selenium";
        String noteDesc = "first note by Selenium";
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //WebElement submitButton = wait.until(webDriver -> webDriver.findElement(By.id("noteSaveButton")));
        wait.until(ExpectedConditions.elementToBeClickable(this.homePageNote.getNoteSubmitButton()));

        // Thread.sleep(1000);
        this.homePageNote.addNewNote(noteTitle, noteDesc);

        Thread.sleep(1000);

        // land to result page
        String actualUrl = driver.getCurrentUrl();
        String resultUrl = "http://localhost:" + port + "/result/success";
        Assertions.assertEquals(actualUrl, resultUrl);

        // return to home page
        driver.get("http://localhost:" + port + "/home");
        this.homePageNote.gotoNoteTab();

        // check the note added
        //*[@id="noteTable"]/tbody/tr/th
        //*[@id="noteTable"]/tbody/tr/td[2]
        String actualTitle = driver.findElement(By.xpath("//*[@id='noteTable']/tbody/tr/th")).getAttribute("innerHTML");
        String actualDesc = driver.findElement(By.xpath("//*[@id='noteTable']/tbody/tr/td[2]")).getAttribute("innerHTML");
        //System.out.println(actualTitle + ":" + actualDesc);

        Assertions.assertEquals(noteTitle, actualTitle);
        Assertions.assertEquals(noteDesc, actualDesc);

    }

    //*[@id="noteTable"]/tbody/tr/td[1]/button
    //*[@id="noteTable"]/tbody/tr/td[1]/a

    //*[@id="noteTable"]/tbody/tr[2]/th
    //*[@id="noteTable"]/tbody/tr[2]/td[2]

    @Test
    @Order(6)
    public void editNote() throws InterruptedException {
        String newTitle = "Edited by Selenium";
        String newDesc = "Note desc edited by Selenium";

        // open edit note modal
        this.homePageNote = new HomePageNote(driver);
        WebElement editNoteButton = driver.findElement(By.xpath("//*[@id='noteTable']/tbody/tr/td[1]/button"));
        editNoteButton.click();
        Thread.sleep(1000);
        WebElement editNoteModal = this.homePageNote.getEditNoteModal();
        Assertions.assertFalse(Boolean.parseBoolean(editNoteModal.getAttribute("aria-hidden")), "Edit note modal is not showing.");

        // send new note information to modal
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //WebElement submitButton = wait.until(webDriver -> webDriver.findElement(By.id("noteSaveButton")));
        wait.until(ExpectedConditions.elementToBeClickable(this.homePageNote.getEditNoteSubmitButton()));

        this.homePageNote.clearEditField();
        Thread.sleep(1000);
        this.homePageNote.editNote(newTitle, newDesc);

        Thread.sleep(1000);

        // land to result page
        String actualUrl = driver.getCurrentUrl();
        String resultUrl = "http://localhost:" + port + "/result/success";
        Assertions.assertEquals(actualUrl, resultUrl);

        // return to home page
        driver.get("http://localhost:" + port + "/home");
        this.homePageNote.gotoNoteTab();

        // check the note after edit
        //*[@id="noteTable"]/tbody/tr/th
        //*[@id="noteTable"]/tbody/tr/td[2]
        String actualTitle = driver.findElement(By.xpath("//*[@id='noteTable']/tbody/tr/th")).getAttribute("innerHTML");
        String actualDesc = driver.findElement(By.xpath("//*[@id='noteTable']/tbody/tr/td[2]")).getAttribute("innerHTML");
        //System.out.println(actualTitle + ":" + actualDesc);

        Assertions.assertEquals(newTitle, actualTitle);
        Assertions.assertEquals(newDesc, actualDesc);

    }

    @Test
    @Order(7)
    public void deleteNote(){

        // return to home page
        driver.get("http://localhost:" + port + "/home");
        this.homePageNote = new HomePageNote(driver);
        this.homePageNote.gotoNoteTab();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        //WebElement submitButton = wait.until(webDriver -> webDriver.findElement(By.id("noteSaveButton")));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='noteTable']/tbody/tr/td[1]/a"))));

        WebElement deleteButton = driver.findElement(By.xpath("//*[@id='noteTable']/tbody/tr/td[1]/a"));
        deleteButton.click();

        // land to result page
        String actualUrl = driver.getCurrentUrl();
        String resultUrl = "http://localhost:" + port + "/result/success";
        Assertions.assertEquals(actualUrl, resultUrl);

        // return to home page
        driver.get("http://localhost:" + port + "/home");
        this.homePageNote.gotoNoteTab();

        // Check the table is empty
        List<WebElement> tableRow = driver.findElements(By.xpath("//*[@id='noteTable']/tbody/tr"));
        Assertions.assertEquals(0, tableRow.size());

    }



}
