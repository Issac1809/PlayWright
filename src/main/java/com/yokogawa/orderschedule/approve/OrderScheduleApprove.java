package com.yokogawa.orderschedule.approve;
import com.microsoft.playwright.Page;
import com.yokogawa.login.LoginPageInterface;
import com.yokogawa.logout.LogoutPageInterface;
import com.yokogawa.variables.VariablesForNonCatalog;
import java.util.List;
import java.util.Properties;

public class OrderScheduleApprove implements OrderScheduleApproveInterface{

    Properties properties;
    Page page;
    VariablesForNonCatalog variablesForNonCatalog;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private OrderScheduleApprove(){
    }

//TODO OS Create Constructor
    public OrderScheduleApprove(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
    }

    public OrderScheduleApprove(VariablesForNonCatalog variablesForNonCatalog, Page page, LoginPageInterface loginPageInterface, LogoutPageInterface logoutPageInterface){
        this.variablesForNonCatalog = variablesForNonCatalog;
        this.page = page;
        this.loginPageInterface = loginPageInterface;
        this.logoutPageInterface = logoutPageInterface;
    }

    public void OSApprove(){
        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
        page.pause();
        page.locator("//*[contains(text(), 'Purchase Orders')]").click();
        String poReferenceId = properties.getProperty("PoReferenceId");
        System.out.println(poReferenceId);
        List<String> containerList = page.locator("#listContainer tr td").allTextContents();
        for(String tr : containerList){
            if(tr.contains(poReferenceId)){
                page.locator(".btn-link").first().click();
            }
        }
        page.locator("#BtnToApproveOS").click();
        page.locator("#btnApprove").click();
        page.locator(".bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
    }
}