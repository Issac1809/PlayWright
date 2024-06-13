package com.procurement.requestforquotations.suspend;
import com.interfaces.*;
import com.microsoft.playwright.Page;
import com.interfaces.PrApprove;
import com.interfaces.PrAssign;
import com.interfaces.PrEdit;
import com.interfaces.PrSendForApproval;

import java.util.Properties;

public class PocRfqSuspend implements RfqSuspend {

    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    Properties properties;
    Page page;
    RfqEdit rfqEdit;
    PrEdit prEdit;
    PrSendForApproval prSendForApproval;
    PrApprove prApprove;
    PrAssign prAssign;
    RfqCreate rfqCreate;

//TODO Constructor
    public PocRfqSuspend(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, RfqEdit rfqEdit, PrEdit prEdit,
                         PrSendForApproval prSendForApproval, PrApprove prApprove, PrAssign prAssign, RfqCreate rfqCreate){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.rfqEdit = rfqEdit;
        this.prEdit = prEdit;
        this.prSendForApproval = prSendForApproval;
        this.prApprove = prApprove;
        this.prAssign = prAssign;
        this.rfqCreate = rfqCreate;
    }

    public void SuspendRfqEdit() throws InterruptedException {
        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
        page.locator("//*[contains(text(), 'Request For Quotations')]").click();
        String title = properties.getProperty("Title");
        page.locator("//span[contains(text(), '"+ title +"')]").first().click();
        page.locator("#btnToSuspendRfq").click();
        page.locator(".bootbox-input").fill("Suspended");
        page.locator("bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
        rfqEdit.RfqEditMethod();
    }

    public void SuspendPREdit() throws InterruptedException {
        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
        page.locator("//*[contains(text(), 'Request For Quotations')]").click();
        String title = properties.getProperty("Title");
        page.locator("//span[contains(text(), '"+ title +"')]").first().click();
        page.locator("#btnToSuspendRfq").click();
        page.locator(".bootbox-input").fill("Suspended");
        page.locator("bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
        prEdit.PrEditMethod();
        prSendForApproval.NonCatalogPrSendForApproval();
        prApprove.Approve();
        prAssign.BuyerManagerLogin();
        prAssign.BuyerManagerAssign();
        rfqCreate.BuyerLogin();
        rfqCreate.BuyerRfqCreate();
        rfqCreate.RfQNotes();
        rfqCreate.RFQCreate();
    }
}