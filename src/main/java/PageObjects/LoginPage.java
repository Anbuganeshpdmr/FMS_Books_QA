package PageObjects;

import BasePackage.BaseClassFMS;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class LoginPage  {

    //WebDriver driver;
    @FindBy(id="username")
    WebElement usernameBox;

    @FindBy(id="password")
    WebElement passwordBox;

    @FindBy(className = "frm-submit")
    WebElement loginBtn;

    WebDriver driver;

    public LoginPage(WebDriver driver) throws IOException {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void enterUsername(String userName){
        usernameBox.sendKeys(userName);
    }

    public void enterPassword(String password){
        passwordBox.sendKeys(password);
    }

    public void clickLogin(){
        loginBtn.click();
    }

    /*public void loginAsPaginationTL() throws InterruptedException {

        String paginationTLId = prop1.getProperty("PaginationTLId");

        usernameBox.sendKeys(paginationTLId);
        passwordBox.sendKeys(paginationTLId);
        loginBtn.click();

        Thread.sleep(2000);
    }

    public void loginAsPaginationUser() throws InterruptedException {

        String paginationUserId = prop1.getProperty("PaginationUserId");

        usernameBox.sendKeys(paginationUserId);
        passwordBox.sendKeys(paginationUserId);
        loginBtn.click();

        Thread.sleep(2000);
    }*/

    public void loginToApplication(String username, String password) throws InterruptedException {

        usernameBox.sendKeys(username);
        passwordBox.sendKeys(password);
        loginBtn.click();

        Thread.sleep(2000);
    }

    public String getPageTitle(){
        return driver.getTitle();
    }

}
