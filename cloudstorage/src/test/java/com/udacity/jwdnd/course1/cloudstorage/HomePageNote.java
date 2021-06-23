package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageNote {

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    // Note modal
    @FindBy(id = "noteModal")
    private WebElement noteModal;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "noteSaveButton")
    private WebElement noteSubmitButton;

    @FindBy(xpath = "//button[text()='Close']")
    private WebElement closeButton;


    public HomePageNote(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void gotoNoteTab() {
        notesTab.click();
    }

    public void openNoteModal() {
        addNoteButton.click();
    }

    //Note modal
    public void closeModal() {
        closeButton.click();
    }

    public void addNewNote(String title, String description) {
        noteTitleField.sendKeys(title);
        noteDescriptionField.sendKeys(description);
        noteSubmitButton.click();
    }

    public WebElement getNotesTab() {
        return notesTab;
    }

    public WebElement getNoteModal() {
        return noteModal;
    }

    public WebElement getNoteSubmitButton() {
        return noteSubmitButton;
    }
}
