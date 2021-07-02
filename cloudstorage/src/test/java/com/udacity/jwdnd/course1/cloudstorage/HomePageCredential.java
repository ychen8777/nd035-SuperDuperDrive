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

    public HomePageCredential(WebDriver driver){
        PageFactory.initElements(driver, this);
    }


    public void clickCredentialTab(){
        credentialsTab.click();
    }

    public WebElement getCredentialsTab() {
        return credentialsTab;
    }
}
