package com.procurement.orderschedule.create;
import com.interfaces.OrderScheduleInterface;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.factory.PlayWrightFactory;
import com.interfaces.LoginPageInterface;
import com.interfaces.LogoutPageInterface;
import java.util.Properties;

public class OrderScheduleCreate implements OrderScheduleInterface {

    PlayWrightFactory playWrightFactory;
    Properties properties;
    Page page;
    LoginPageInterface loginPageInterface;
    LogoutPageInterface logoutPageInterface;

    private OrderScheduleCreate() {
    }

    public OrderScheduleCreate(LoginPageInterface loginPageInterface, Properties properties, Page page, LogoutPageInterface logoutPageInterface, PlayWrightFactory playWrightFactory) {
        this.loginPageInterface = loginPageInterface;
        this.properties = properties;
        this.page = page;
        this.logoutPageInterface = logoutPageInterface;
        this.playWrightFactory = playWrightFactory;
    }

    public void OSCreate() {
        try {
        loginPageInterface.LoginMethod(properties.getProperty("VendorMailId"));
        page.waitForSelector("//*[contains(text(), 'Purchase Orders')]").click();
        String title = properties.getProperty("Title");
        page.locator("//*[contains(text(), '" + title + "')]").first().click();
        String poReferenceId = page.waitForSelector("#referenceId").textContent();
        playWrightFactory.savePropertiesToFile(poReferenceId);
        page.waitForSelector("#btnCreateOR").click();
        Locator orderScheduleDate = page.locator(".scheduleDate-1").last();
        orderScheduleDate.click();
        Locator today = page.locator("//span[@class='flatpickr-day today']").first();
        today.click();
        page.waitForSelector("#btnCreate").click();
        page.waitForSelector(".bootbox-accept").click();
        logoutPageInterface.LogoutMethod();
        } catch (Exception error) {
            System.out.println("What is the error: " + error.getMessage());
        }
    }
}