package com.procurement.invoice.woinvoice.cancel;

import com.interfaces.*;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.Properties;

public class WoInvoiceCancel implements WoInvCancel {

    Page page;
    Properties properties;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;
    WOInvoiceCreateInterface woInvoiceCreateInterface;

    private WoInvoiceCancel(){
    }

//TODO Constructor
    public WoInvoiceCancel(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, WOInvoiceCreateInterface woInvoiceCreateInterface){
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.woInvoiceCreateInterface = woInvoiceCreateInterface;
    }

    public void WoInvoiceCancelMethod(){
        try {
        loginPageInterface.LoginMethod(properties.getProperty("Buyer"));
        page.waitForSelector(".nav-link   active").click();
        String woReferenceId = properties.getProperty("WorkOrderReferenceId");
        List<String> invoiceContainer = page.locator("#listContainer tr td").allTextContents();
        for(String tr : invoiceContainer){
            if (tr.contains(woReferenceId)){
                page.locator(".btn btn-sm btn-link p-0 text-primary").first().click();
            }
        }
        page.waitForSelector("#btnToSuspendInvoice").click();
        page.waitForSelector(".bootbox-input").fill("Cancelled");
        page.waitForSelector(".bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
        woInvoiceCreateInterface.VendorCreateWOInvoice();
        double finalGSTPercentage = woInvoiceCreateInterface.VendorGST();
        woInvoiceCreateInterface.SGDEquivalentEnable(finalGSTPercentage);
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}