package com.procurement.purchaseorderrequest.create;
import com.interfaces.PorCreateNonCatalog;
import com.microsoft.playwright.Page;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import java.util.Properties;

public class PocNonCatalogPorCreate implements PorCreateNonCatalog {

    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private PocNonCatalogPorCreate(){
    }

//TODO Constructor
    public PocNonCatalogPorCreate(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
    }

    public void BuyerPORCreate() {
        try {
        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
        page.waitForSelector("//*[contains(text(), 'Request For Quotations')]").click();
        String title = properties.getProperty("Title");
        page.locator("//span[contains(text(), '"+ title +"')]").first().click();
        page.locator("//a[contains(text(),' Create POR ')]").first().click();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void Justification(){
        try {
        page.waitForSelector("#below5L").click();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void TaxCode(){
        try {
        page.getByText("-- Select Tax Codes --").last().click();
        String taxCode = properties.getProperty("TaxCode");
        page.waitForSelector("//li[contains(text(),'"+ taxCode +"')]").click();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void PORNotes() {
        try {
        page.waitForSelector("#notes").fill(properties.getProperty("PorNotes"));
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }

    public void PORCreate(){
        try {
        page.waitForSelector("#btnCreate").click();
        page.waitForSelector("//button[contains(text(),'Yes')]").click();
        logoutPageInterface.LogoutMethod();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}