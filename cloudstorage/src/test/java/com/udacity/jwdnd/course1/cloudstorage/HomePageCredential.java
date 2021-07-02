package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageCredential {

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialButton;

    // Credential modal
    @FindBy(id = "credentialModal")
    private WebElement credentialModal;

    @FindBy(xpath = "//*[@id=\"credentialModal\"]/div/div/div[3]/button[1]")
    private WebElement closeButton;

    @FindBy(id = "credentialSaveButton")
    private WebElement credentialSaveButton;

    public HomePageCredential(WebDriver driver){
        PageFactory.initElements(driver, this);
    }


    public void clickCredentialTab(){
        credentialsTab.click();
    }

    public void openCredentialModal() {
        addCredentialButton.click();
    }

    public void closeCredentialModal() {
        closeButton.click();
    }

    public WebElement getCredentialsTab() {
        return credentialsTab;
    }

    public WebElement getCredentialModal() {
        return credentialModal;
    }

    public WebElement getCredentialSaveButton() {
        return credentialSaveButton;
    }
}
