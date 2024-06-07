package com.yokogawa.requestforquotations.create;
import com.microsoft.playwright.Page;
import com.yokogawa.login.LoginPageInterface;
import com.yokogawa.logout.LogoutPageInterface;
import java.util.Properties;

public class PocRfqCreate implements RfqCreate {

    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private PocRfqCreate(){
    }

//TODO Constructor
    public PocRfqCreate(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
    }

    public void BuyerLogin() {
        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
    }

    public void BuyerRfqCreate() {
        String title = properties.getProperty("Title");
        page.locator("//*[contains(text(),'"+ title +"')]").first().click();
        page.locator("#btnCreateRFQ").click();
    }

    public void RfQNotes() {
        page.locator("#notes").fill(properties.getProperty("RfQNotes"));
    }

    public void RFQCreate() throws InterruptedException {
        Thread.sleep(1000);
        page.locator("#btnCreate").click();
        page.locator("//button[contains(text(),'Yes')]").click();
        logoutPageInterface.LogoutMethod();
    }
}