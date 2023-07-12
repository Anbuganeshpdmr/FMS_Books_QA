package PageObjects;

import BasePackage.BaseClassFMS;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class AssignChaptersToSelfPage extends BaseClassFMS {

    @FindBy(xpath = "//*[@id='boostrapModal-assign_user']//*[text()='Self Assign Users']")
    WebElement titleElement;

    @FindBy(xpath = "//*[@id='boostrapModal-assign_user']//*[text()='Close']")
    WebElement closeBtn;

    @FindBy(xpath = "//*[@id='boostrapModal-assign_user']//*[text()='Assign']")
    WebElement assignBtn;

    @FindBy(id="assign_jobtype")
    WebElement jobType;

    @FindBy(id="assign_process")
    WebElement assignProcess;

    String jobTypeSelectionValue = "3";

    public AssignChaptersToSelfPage() throws InterruptedException {
        Thread.sleep(1000);
        PageFactory.initElements(driver,this);
    }

    public void confirmPageNavigation(){
        Assert.assertTrue(titleElement.isDisplayed());
    }

    public void navigateBack(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        closeBtn.click();
    }

    public void assignChapter(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        assignBtn.click();
    }

    public void selectJobTypes() throws InterruptedException {
        Thread.sleep(3000);
        Select select1 = new Select(jobType);
        select1.selectByValue(jobTypeSelectionValue);

        Thread.sleep(2000);
        Select select2 = new Select(assignProcess);
        select2.selectByValue(jobTypeSelectionValue);
    }


}
