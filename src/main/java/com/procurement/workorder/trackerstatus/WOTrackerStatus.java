package com.procurement.workorder.trackerstatus;
import com.interfaces.WOTrackerStatusInterface;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import java.util.List;
import java.util.Properties;

public class WOTrackerStatus implements WOTrackerStatusInterface {

    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private WOTrackerStatus(){
    }

//TODO Constructor
    public WOTrackerStatus(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface){
        this.properties = properties;
        this.page = page;
        this.loginPageInterface = loginPageInterface;
        this.logoutPageInterface = logoutPageInterface;
    }

    public void VendorTrackerStatus() {
        loginPageInterface.LoginMethod(properties.getProperty("VendorMailId"));
        page.pause();
        page.locator("//*[contains(text(), 'Work Orders')]").click();
        String poReferenceId = properties.getProperty("PoReferenceId");
        List<String> containerList = page.locator("#listContainer tr td").allTextContents();
        for(String tr : containerList){
            if(tr.contains(poReferenceId)){
                page.locator(".btn-link").first().click();
            }
        }
        String[] status = {"Material_Pick_Up", "ETD", "Arrival_Notification", "Import_Clearance", "Out_for_Delivery", "Delivery_Completed"};
        for(int i = 0; i < status.length; i++) {
            page.locator(".form-control.form-control-sm.flatpickr-custom.form-control.input").click();
            Locator today = page.locator("//span[@class='flatpickr-day today']").first();
            today.click();
            page.locator("#select2-statusId-container").last().click();
            page.locator("//li[contains(text(), '"+ status[i] +"')]").click();
            page.locator("#btnSubmit").click();
            page.locator(".bootbox-accept").click();
        }
        logoutPageInterface.LogoutMethod();
    }
}