package PageObjects;

import BasePackage.BaseClassFMS;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AssignedListPage  {


    int bookNameIndex = 3;
    int chapterNameIndex = 4;
    int usernameIndex = 6;
    int jobtypeIndex = 8;
    int processIndex = 9;
    int actionIndex=24;
    int statusIndex=14;
    String jobtype = "Fresh";
    String process = "Pagination";

    String chapterName = "ab";
    String chapterName1 = "bc";
    String username = "R. Sharmila";
    Select select1;


    @FindBy(xpath = "//input[@type='search']")
    WebElement searchBox;

    WebDriver driver;

    public AssignedListPage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public void searchChapter(String chapterName) throws InterruptedException {
        searchBox.sendKeys(chapterName + Keys.ENTER);
        Thread.sleep(2000);
    }

    public void searchBookName(String bookName) {
        searchBox.sendKeys(bookName);
    }

    public String isDisplayedUserName(String bookname) {
            return driver.findElement(By.xpath("//tbody//td["+chapterNameIndex+"][text()='"+chapterName+"']/../td["+usernameIndex+"][text()='"+username+"']")).getText();
    }

    public String isDisplayedJobtype(String bookname) {
         return driver.findElement(By.xpath("//tbody//td["+chapterNameIndex+"][text()='"+chapterName+"']/../td["+jobtypeIndex+"][text()='"+jobtype+"']")).getText();


    }

    public String isDisplayedProcess(String bookname) {
        return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "][text()='" + chapterName + "']/../td[" + processIndex + "][text()='" + process + "']")).getText();

    }

    public void clickPlayButton()throws InterruptedException {
        Thread.sleep(2000);
        WebElement pause = driver.findElement(By.xpath("//i[@class='fa fa-solid fa-pause assigned_work_model']"));
        if (pause.isDisplayed()) {
            Thread.sleep(2000);
            driver.findElement(By.xpath("//i[@class='fa fa-solid fa-pause assigned_work_model']")).click();
            Thread.sleep(2000);
            driver.findElement(By.id("prod_assign_remarks_error")).sendKeys("Test Holded Remark");
            select1.selectByVisibleText("Hold");
            Thread.sleep(2000);
            driver.findElement(By.xpath("//button[@id='start_assigned_work']")).click();
        } else {
            driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "][text()='" + chapterName + "']/../td[" + actionIndex + "]/i[@class='fa fa-solid fa-play assigned_work_model']")).click();
        }
    }

    public void clickPlayButton1()throws InterruptedException{
        Thread.sleep(2000);
        driver.findElement(By.xpath("//tbody//td["+chapterNameIndex+"+][text()='"+chapterName1+"']/../td["+actionIndex+"]/i[@class='fa fa-solid fa-play assigned_work_model']")).click();
    }

    public void clickStartButton()throws InterruptedException{
        Thread.sleep(1500);
        driver.findElement(By.xpath("//button[@id='start_assigned_work']")).click();
    }

    public void clickPauseButton()throws InterruptedException{
        Thread.sleep(2200);
        driver.findElement(By.xpath("//i[@class='fa fa-solid fa-pause assigned_work_model']")).click();
    }
    public void selectProductionStatusHOLD()throws InterruptedException{
        Thread.sleep(1200);
        select1=new Select(driver.findElement(By.id("prod_assign_status")));
        select1.selectByVisibleText("Hold");
    }
    public void selectProductionStatusCOMPLETED()throws InterruptedException{
        Thread.sleep(1200);
        Select select1=new Select(driver.findElement(By.id("prod_assign_status")));
        select1.selectByVisibleText("Completed");
    }
    public void clickFinishButton() throws InterruptedException{
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@id='start_assigned_work']")).click();
    }
    public boolean isDispalyedRemarksAlert(){
        try {
            return driver.findElement(By.xpath("//p[text()='Enter The remarks']")).isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }
    }
    public void enterRemarks()throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.id("prod_assign_remarks_error")).sendKeys("Test Holded Remark");
    }
    public boolean isDisplayedholdStatus()throws InterruptedException{
        Thread.sleep(2000);
        try {
            return driver.findElement(By.xpath("//tbody//td["+chapterNameIndex+"][text()='"+chapterName+"']/../td["+statusIndex+"]//span[text()='Hold']")).isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }
    }
    public boolean isDisplayedCompletedStatus()throws InterruptedException{
        Thread.sleep(2000);
        try {
            return driver.findElement(By.xpath("//tbody//td["+chapterNameIndex+"][text()='"+chapterName+"']/../td["+statusIndex+"]//span[text()='Completed']")).isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }
    }
}
