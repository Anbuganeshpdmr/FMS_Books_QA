package PageObjects;

import BasePackage.BaseClassFMS;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.Properties;


public class AddBookPage  {


    @FindBy(id="received_date")
    WebElement receivedDate;

    @FindBy(id="publisher_id")
    WebElement selectPublisher;

    @FindBy(id="book_name")
    WebElement bookName;

    @FindBy(id="no_of_chapter")
    WebElement noOfChapters;

    @FindBy(id="isbn_number")
    WebElement isbnNumber;

    @FindBy(id="type_book")
    WebElement typeOfBook;

    @FindBy(id="complexity_level")
    WebElement complexityLevel;

    @FindBy(id="book_colour")
    WebElement bookColour;

    @FindBy(id="work_type")
    WebElement workType;

    @FindBy(id="prioity")
    WebElement priority;

    @FindBy(id="editing_received_date")
    WebElement editingReceivedDate;

    @FindBy(id="due_date")
    WebElement dueDate;

    @FindBy(id="pdmr_plan_date")
    WebElement pdmrPlanDate;

    @FindBy(id="book_stage")
    WebElement bookStage;

    @FindBy(id="book_file_upload")
    WebElement bookUploadBtn;

    @FindBy(id="book_submit")
    WebElement submitBookBtn;

    @FindBy(id="reset_book")
    WebElement resetBook;

    Properties bookProp;
    WebDriver driver;
    public AddBookPage(WebDriver driver){
        this.driver=driver;

        PageFactory.initElements(driver, this);
    }



    public void addNewBook(String receivedDate_, String publisherName_, String bookName_, String noOfChapters_,
                           String isbnNumber_, String typeOfBook_, String complexityLevel_, String bookColour_,
                           String priority_, String CEReceivedDate_, String clientDueDate_, String pdmrPlanDate_,
                           String stages_) throws InterruptedException {


        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        receivedDate.sendKeys(receivedDate_+ Keys.ENTER);

        Select pubSelect = new Select(selectPublisher);
        pubSelect.selectByVisibleText(publisherName_);

        Thread.sleep(1500);

        bookName.sendKeys(bookName_);

        noOfChapters.sendKeys(noOfChapters_);

        Thread.sleep(1500);
        isbnNumber.sendKeys(isbnNumber_);

        Select typeOfBookSelect = new Select(typeOfBook);
        typeOfBookSelect.selectByValue(typeOfBook_);

        Select compLevelSelect = new Select(complexityLevel);
        compLevelSelect.selectByValue(complexityLevel_);

        Thread.sleep(1500);
        Select bookColourSelect = new Select(bookColour);
        bookColourSelect.selectByValue(bookColour_);

        Select prioritySelect = new Select(priority);
        prioritySelect.selectByValue(priority_);

        editingReceivedDate.sendKeys(CEReceivedDate_+ Keys.ENTER);
        Thread.sleep(1500);
        dueDate.sendKeys(clientDueDate_+ Keys.ENTER);

        pdmrPlanDate.sendKeys(pdmrPlanDate_+ Keys.ENTER);

        Select stagesSelect = new Select(bookStage);
        stagesSelect.selectByValue(stages_);

    }

    public void addNewBook(String receivedDate_, int publisherIndex_, String bookName_, String noOfChapters_,
                           String isbnNumber_, String typeOfBook_, String complexityLevel_, String bookColour_,
                           String priority_, String CEReceivedDate_, String clientDueDate_, String pdmrPlanDate_,
                           String stages_) throws InterruptedException {


        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        receivedDate.sendKeys(receivedDate_+ Keys.ENTER);

        Select pubSelect = new Select(selectPublisher);
        pubSelect.selectByIndex(publisherIndex_);

        Thread.sleep(1500);

        bookName.sendKeys(bookName_);

        noOfChapters.sendKeys(noOfChapters_);

        Thread.sleep(1500);
        isbnNumber.sendKeys(isbnNumber_);

        Select typeOfBookSelect = new Select(typeOfBook);
        typeOfBookSelect.selectByValue(typeOfBook_);

        Select compLevelSelect = new Select(complexityLevel);
        compLevelSelect.selectByValue(complexityLevel_);

        Thread.sleep(1500);
        Select bookColourSelect = new Select(bookColour);
        bookColourSelect.selectByValue(bookColour_);

        Select prioritySelect = new Select(priority);
        prioritySelect.selectByValue(priority_);

        editingReceivedDate.sendKeys(CEReceivedDate_+ Keys.ENTER);
        Thread.sleep(1500);
        dueDate.sendKeys(clientDueDate_+ Keys.ENTER);

        pdmrPlanDate.sendKeys(pdmrPlanDate_+ Keys.ENTER);

        Select stagesSelect = new Select(bookStage);
        stagesSelect.selectByValue(stages_);

    }

    public void addFileForNewBook(String filepath_) throws InterruptedException {

        Thread.sleep(2000);
        bookUploadBtn.sendKeys(filepath_);
    }

    public void pressSubmitButton() throws InterruptedException {
        Thread.sleep(1500);
        submitBookBtn.click();
    }

    public void pressResetButton(){
        resetBook.click();
    }

    public boolean verifyMandatoryErrorMessageInTextFields(String elementName, String message) throws InterruptedException {

        if(elementName.contains("book name")){
            bookName.clear();
        } else if (elementName.contains("no of chapters")) {
            noOfChapters.clear();
        } else if (elementName.contains("publisher")) {

            Select pubSelect = new Select(selectPublisher);
            pubSelect.selectByVisibleText("Select Publisher");

        } else if (elementName.contains("type of book")) {

            Select typeOfBookSelect = new Select(typeOfBook);
            typeOfBookSelect.selectByVisibleText("Select Type");

        }else if (elementName.contains("complexity level")) {

            Select comLevelSelect = new Select(complexityLevel);
            comLevelSelect.selectByVisibleText("Select complexity level");

        }else if (elementName.contains("book colour")) {

            Select select = new Select(bookColour);
            select.selectByVisibleText("Select Book Colour");

        }else if (elementName.contains("priority")) {

            Select select = new Select(priority);
            select.selectByVisibleText("Select Priority");

        }else if (elementName.contains("stages")) {

            Select select = new Select(bookStage);
            select.selectByVisibleText("Select Stages");

        } else if (elementName.contains("received date")) {

            Actions action = new Actions(driver);
            action.doubleClick(receivedDate).click(receivedDate).sendKeys(Keys.DELETE).build().perform();
        }

        Thread.sleep(1500);
        submitBookBtn.click();

        try{
            Thread.sleep(1500);
            return driver.findElement(By.xpath("//*[text()='"+message+"']")).isDisplayed();
        } catch (NoSuchElementException e){
            return false;
        }
    }

    public boolean verifyMandatoryErrorMessageInDropDowns(String elementName, String message) throws InterruptedException {

        Thread.sleep(1500);
        submitBookBtn.click();

        try{
            Thread.sleep(1500);
            return driver.findElement(By.xpath("//*[text()='"+message+"']")).isDisplayed();
        } catch (NoSuchElementException e){
            return false;
        }
    }

    public String getSelectedPublisherName() throws InterruptedException {
        Select pubSelect = new Select(selectPublisher);
        Thread.sleep(2000);
        return pubSelect.getFirstSelectedOption().getText();
    }

    public String getBookname(){
        return driver.findElement(By.id("book_name")).getText();
    }

}
