package PageObjects;
import BasePackage.BaseClassFMS;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public class StagesPage  {
    private Select select11;
    private Select select12;
    int booknameIndex = 3;

    int actionIndex = 9;

    int StatusIndex = 14;
    int usernameIndex=15;
    String chapterName = "ab";
    int jobtypeIndex = 12;
    int processIndex = 13;

    int chapterNameIndex = 6;
    WebDriver driver;
    @FindBy(xpath = "//input[@type='search']")
    WebElement search;

    public StagesPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }



    public void navigateToStages() {
        driver.findElement(By.xpath("//span[text()='Stages']")).click();
    }

    public void navigateToFreshStage() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[text()='Fresh']")).click();
    }

    public void navigateToAuthorRevisionsStage() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[text()='Author Revisions']")).click();

    }

    public void navigateToprintAndWebStage() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[text()='Print/Web']")).click();

    }

    public void selectEyeIcon(String bookname)throws InterruptedException {
        driver.findElement(By.xpath("//tbody//td[" + booknameIndex + "]/a[text()='" + bookname + "']//..//..//td[" + actionIndex + "]//i[@class='fa fa-eye']")).click();
        Thread.sleep(3000);
    }

    public void search(String bookname) throws InterruptedException{
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(bookname);
        Thread.sleep(3000);
    }

    public void clickAssignButton() throws InterruptedException {
        Thread.sleep(1500);
        driver.findElement(By.xpath("//button[@id='chapter_assign_modal']")).click();
    }


    public void selectMultiChapters()throws InterruptedException{
        Thread.sleep(1500);
        driver.findElement(By.xpath("//th/div[@class='checkbox info']")).click();
        Thread.sleep(1500);
    }



    public void selectSingleChapter() throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "][text()='" + chapterName + "']/../td[1]")).click();
    }

    public String getUserName() {
        return driver.findElement(By.xpath("//tbody//td[6][text()='ab']//..//td[15]//button")).getText();
    }

    public void clickjobtypeProcessEditIcon() {
        driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "][text()='" + chapterName + "']/../td[" + StatusIndex + "]//i[@class='fa fa-edit jtype_process_reassign']")).click();
    }

    public void changeJobType() throws InterruptedException {
        Select select11 = new Select(driver.findElement(By.id("reassign_jobtype")));
        Thread.sleep(2000);
        select11.selectByVisibleText("AC");
        Thread.sleep(1500);

    }
//**********************************change jobtype and process**************************************************************
    public void changeProcess() throws  InterruptedException{
        Thread.sleep(1500);
        Select select12 = new Select(driver.findElement(By.id("reassign_process")));
        select12.selectByVisibleText("AC_COR");
        Thread.sleep(1500);
    }

    public void clickReAssignJobtypeAndProcessSubmit()throws InterruptedException{
        Thread.sleep(2000);
        driver.findElement(By.id("reassign_submit")).click();
    }

    public boolean isDisplayedReAssignedJobtype() throws InterruptedException{
        Thread.sleep(2000);
        try {
            return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "][text()='" + chapterName + "']/../td[" + jobtypeIndex + "][text()='AC']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isDisplayedReAssignedProcess() throws InterruptedException{
        Thread.sleep(2000);
        try {
            return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "][text()='" + chapterName + "']/../td[" + processIndex + "][text()='AC_COR']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    public void clickDropdownButton()throws InterruptedException{
        Thread.sleep(2200);
        driver.findElement(By.xpath("//tbody//td["+chapterNameIndex+"][text()='"+chapterName+"']/../td["+usernameIndex+"]//button[@data-toggle='dropdown']")).click();
    }
    public void clickaddNewEmployeeButton()throws InterruptedException{
        driver.findElement(By.xpath("//i[@class='fa fa-user-plus add_new_employee']")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("add_multiple_users")).click();

    }

    public void clickChangeUserButton()throws InterruptedException{
        Thread.sleep(2000);
        driver.findElement(By.xpath("//i[@class='fa fa-caret-square-o-down change_user_article']")).click();

    }
    public void userCheckboxClickWithoutEditMSpage(){
        driver.findElement(By.xpath("//label[@for='checkbox-1460-multiple']")).click();
    }
    public boolean isDisplayedAlertForAddUserWithoutEditMSPageRange(){
        try {
            return driver.findElement(By.xpath("//p[text()='MS Page range already finished. so split page range to assign another user']")).isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public void changeUserEmpID(String changeUser)throws InterruptedException{
        Thread.sleep(1500);
        Select select13=new Select( driver.findElement(By.id("change_employee_id")));
        select13.selectByVisibleText(changeUser);
        Thread.sleep(1500);

    }
    public void changePageRange()throws InterruptedException{
        Thread.sleep(1500);
        driver.findElement(By.id("employee_page_range")).clear();
        driver.findElement(By.id("employee_page_range")).sendKeys("1-400");
        Thread.sleep(1500);
//        (//input[@id='employee_page_range'])[1]
    }
    public boolean isDisplayedEditMsPageAlert(){
        try {
            return driver.findElement(By.xpath("//p[text()='page range 401-800 missing.']")).isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }
    }
    public void clickOkButton()throws InterruptedException{
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@class='confirm']")).click();
        Thread.sleep(1000);
    }
    public void clickTosaveChangeuserDetail()throws InterruptedException{
        Thread.sleep(3000);
        driver.findElement(By.xpath("(//button[@id='change_user_submit'])[1]")).click();
        Thread.sleep(1500);
    }
    public void ClickToCancelchangeUserDetails(){
        driver.findElement(By.xpath("(//button[@id='change_user_submit'])[1]/..//button[text()='Close']")).click();
    }
    public String getChangedUserName(){
        return driver.findElement(By.xpath("//tbody//td[6][text()='ab']/../td[15]//div//button[text()]")).getText();
    }





}






