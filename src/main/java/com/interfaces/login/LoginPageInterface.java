package com.interfaces.login;
import com.microsoft.playwright.Page;

public interface LoginPageInterface {
    void LoginMethod();
    void LoginMethod(String approverMailId);
    void LoginMethod(String approverMailId, Page page);
}