package com.yokogawa.msa;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.yokogawa.login.LoginPageInterface;
import com.yokogawa.logout.LogoutPageInterface;
import com.yokogawa.variables.VariablesForNonCatalog;
import java.util.Properties;

public class PorInspectPO implements PorInspectPoInterface{

    Properties properties;
    VariablesForNonCatalog variablesForNonCatalog;
    LoginPageInterface loginPageInterface;
    Page page;
    LogoutPageInterface logoutPageInterface;

    private PorInspectPO(){
    }

//TODO Test Constructor
    public PorInspectPO(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
    }

    public PorInspectPO(VariablesForNonCatalog variablesForNonCatalog, Page page, LoginPageInterface loginPageInterface, LogoutPageInterface logoutPageInterface){
        this.variablesForNonCatalog = variablesForNonCatalog;
        this.page = page;
        this.loginPageInterface = loginPageInterface;
        this.logoutPageInterface = logoutPageInterface;
    }

    public void InspectCreatePO(){
        loginPageInterface.LoginMethod(properties.getProperty("AdminId"));
        page.locator("//*[contains(text(), 'Purchase Order Requests')]").click();
        String title = properties.getProperty("Title");
        page.locator("//span[contains(text(),'" + title + "')]").first().click();
        Locator createPOButton = page.locator("#createPOContainer");
        createPOButton.evaluate("(element) => { element.style.display = 'block'; }");
        createPOButton.click();
        page.waitForSelector(".bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
    }
}