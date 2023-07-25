package PageObjects;

import BasePackage.BaseClassFMS;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AssignChapterPage{
    @FindBy(xpath = "//*[@id='boostrapModal-assign_user']//*[text()='Close']")
    WebElement closeBtn;

    @FindBy(xpath = "//*[@id='boostrapModal-assign_user']//*[text()='Assign']")
    WebElement assignBtn;
    @FindBy(id = "assign_process")
    WebElement assignProcess;

    @FindBy(id = "assign_single_users")
    WebElement user;

    @FindBy(id = "assign_priority")
    WebElement priority;

    @FindBy(id = "assign_comments")
    WebElement comment;

    public AssignChapterPage(WebDriver driver) throws InterruptedException {
        this.driver=driver;
        Thread.sleep(1000);
        PageFactory.initElements(driver, this);
    }
    WebDriver driver;
    public void selectUser()throws InterruptedException{
        Thread.sleep(3000);
        Select select1=new Select(user);
        select1.selectByVisibleText("R. Sharmila");

    }
    public void selectProcess(){
        Select select2=new Select(assignProcess);
        select2.selectByVisibleText("Pagination");

    }

    public void selectPriority(){
        Select select3=new Select(priority);
        select3.selectByVisibleText("Low");
    }
    public void assignComments(){
        comment.sendKeys("Testing Comment");

    }
    public void submitButton(){
        assignBtn.click();
    }
    public void logout()throws InterruptedException{
        Thread.sleep(3000);
        driver.findElement(By.xpath("//i[@class='fa fa-power-off']")).click();
        Thread.sleep(1200);
        driver.findElement(By.xpath("//button[@class='confirm']")).click();
    }













}