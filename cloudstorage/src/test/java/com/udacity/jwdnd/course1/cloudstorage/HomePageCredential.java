package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageCredential {

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialButton;

   
}
