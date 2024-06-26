package com.procurement.dispatchnotes.dnreturn;
import com.interfaces.DnEdit;
import com.interfaces.DnReturn;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import com.microsoft.playwright.Page;
import java.util.List;
import java.util.Properties;

public class PocDnReturn implements DnReturn {

    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    DnEdit dnEdit;

    private PocDnReturn(){
    }

//TODO Constructor
    public PocDnReturn(LoginPageInterface loginPageInterface, Properties properties, Page page,LogoutPageInterface logoutPageInterface, DnEdit dnEdit){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.dnEdit = dnEdit;
    }

    public void PocDnReturnMethod() {
        try {
            loginPageInterface.LoginMethod(properties.getProperty("LogisticsManager"));
            page.waitForSelector("//*[contains(text(), 'Dispatch Notes')]").click();
            String poReferenceId = properties.getProperty("PoReferenceId");
            List<String> containerList = page.locator("#listContainer tr td").allTextContents();
            for (String tr : containerList) {
                if (tr.contains(poReferenceId)) {
                    page.locator(".btn-link").first().click();
                }
            }
            page.waitForSelector("#dropdownMenuButton").click();
            page.waitForSelector("#btnToReturn").click();
            page.waitForSelector(".bootbox-input").fill("Returned");
            page.waitForSelector(".bootbox-accept").click();
            logoutPageInterface.LogoutMethod();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}