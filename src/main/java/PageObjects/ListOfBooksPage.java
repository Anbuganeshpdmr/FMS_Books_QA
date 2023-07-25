package PageObjects;

import BasePackage.BaseClassFMS;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.File;
import java.util.*;

public class ListOfBooksPage {



    List<WebElement> headerRows;
    List<String> headerTexts;
    List<WebElement> bodyRows;

    int bookNameIndex;

    int publisherIndex;
    int actionIndex;

    @FindBy(xpath = "//input[@type='search']")
    WebElement listOfBooksSearch;

    @FindBy(xpath = "//*[text()='Add Book']")
    WebElement addBookBtn;

    @FindBy(id = "table_book")
    WebElement listOfBooksTable;

    @FindBy(id="filter_book_publisher_id")
    WebElement publisherFilter;

    @FindBy(id="filter_book_id")
    WebElement bookFilter;

    WebDriver driver;

    public ListOfBooksPage(WebDriver driver) throws InterruptedException {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        Thread.sleep(1500);

        headerRows = listOfBooksTable.findElements(By.xpath("thead/tr/th"));
        headerTexts = new ArrayList<String>(headerRows.size());

        for(int i=0; i< headerRows.size();i++){
            headerTexts.add(headerRows.get(i).getText());
        }

        bookNameIndex = headerTexts.indexOf("Book Name") + 1;
        actionIndex = headerTexts.indexOf("Action") + 1;
        publisherIndex = headerTexts.indexOf("Publisher")+1;
    }

    public void navigateToAddBookPage(){
        addBookBtn.click();
    }

    private void searchBooks(String bookname) throws InterruptedException {
        listOfBooksSearch.clear();
        listOfBooksSearch.sendKeys(bookname+Keys.ENTER);
        Thread.sleep(1500);
    }

    public Map<String, String> getBookDatas(String bookname) throws InterruptedException {
        Thread.sleep(2000);
        searchBooks(bookname);
        Thread.sleep(1500);

        bodyRows = listOfBooksTable.findElements(
                By.xpath("tbody/tr/td["+bookNameIndex+"]/a[text()='"+bookname+"']/../../td"));
        Thread.sleep(1500);

        Map<String, String> bookDatas = new LinkedHashMap<String, String>(headerTexts.size());

        for(int i=0; i< headerTexts.size();i++){
            bookDatas.put(headerTexts.get(i),bodyRows.get(i).getText());
        }

        return bookDatas;
    }

    public void navigateToChaptersOptionsPage(String bookname) throws InterruptedException {

        Thread.sleep(2000);
        driver.findElement(By.xpath(
                "//tr/td["+bookNameIndex+"]/a[text()='"+bookname+"']/../../td["+actionIndex+"]/a/i[@class='fa fa-eye']"))
                .click();
    }

    public void navigateToEditBookOptions(String bookname) throws InterruptedException {

        Thread.sleep(2000);
        driver.findElement(By.xpath(
                "//tr/td["+bookNameIndex+"]/a[text()='"+bookname+"']/../../td["+actionIndex+"]/a/i[@class='fa fa-edit']"))
                .click();
    }

    public String getPublisherName(String bookName) throws InterruptedException {
        Thread.sleep(1500);
        searchBooks(bookName);
        Thread.sleep(1500);
        Map<String, String> bookdatas = getBookDatas(bookName);
        return bookdatas.get("Publisher");
    }

    public void filterPublisher(String publisherName){
        Select select = new Select(publisherFilter);
        select.selectByVisibleText(publisherName);
    }

    public void filterBook(String bookName){
        Select select = new Select(bookFilter);
        select.selectByVisibleText(bookName);
    }

    public void navigateToVIEWBOOKCHAPTERLISTPage(String bookName) throws InterruptedException {
        Thread.sleep(1000);
        searchBooks(bookName);
        Thread.sleep(1500);

        driver.findElement(By.xpath("//a[text()='"+bookName+"']")).click();
    }

    public boolean isDeleteOptionAvailable(String bookName) throws InterruptedException {
        Thread.sleep(1000);
        searchBooks(bookName);
        Thread.sleep(1500);

        try{
            return driver.findElement(
                    By.xpath("//a[text()='"+bookName+"']/../..//i[@class='fa fa-trash delete_book_chap_data']"))
                    .isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }

    }

    public boolean isEditOptionAvailable(String bookName) throws InterruptedException {
        Thread.sleep(1000);
        searchBooks(bookName);
        Thread.sleep(1500);

        try{
            return driver.findElement(
                            By.xpath("//a[text()='"+bookName+"']/../..//i[@class='fa fa-edit']"))
                    .isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }

    }

    public boolean isViewChaptersOptionAvailable(String bookName) throws InterruptedException {
        Thread.sleep(1000);
        searchBooks(bookName);
        Thread.sleep(1500);

        try{
            return driver.findElement(
                            By.xpath("//a[text()='"+bookName+"']/../..//i[@class='fa fa-eye']"))
                    .isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }

    }

    public boolean isDownloadOptionAvailable(String bookName) throws InterruptedException {
        Thread.sleep(1000);
        searchBooks(bookName);
        Thread.sleep(1500);

        try{
            return driver.findElement(
                            By.xpath("//a[text()='"+bookName+"']/../..//i[@class='fa fa-cloud-download']"))
                    .isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }

    }

    private void attemptDeletingTheBook(String bookName) throws InterruptedException {
        Thread.sleep(1000);
        searchBooks(bookName);
        Thread.sleep(1500);
        Assert.assertTrue(isDeleteOptionAvailable(bookName));
        driver.findElement(
                        By.xpath("//a[text()='"+bookName+"']/../..//i[@class='fa fa-trash delete_book_chap_data']"))
                .click();
    }

    public void deleteTheBook(String bookName) throws InterruptedException {
        attemptDeletingTheBook(bookName);

    }

    public void abortDeletingTheBook(String bookName) throws InterruptedException {
        attemptDeletingTheBook(bookName);

    }

    public void navigateToEDITBOOKPage(String bookName) throws InterruptedException {
        Thread.sleep(1000);
        searchBooks(bookName);
        Thread.sleep(1500);
        Assert.assertTrue(isEditOptionAvailable(bookName));

        driver.findElement(
                        By.xpath("//a[text()='"+bookName+"']/../..//i[@class='fa fa-edit']"))
                .click();
    }

    public void navigateToVIEWCHAPTERSPage(String bookName) throws InterruptedException {
        Thread.sleep(1000);
        searchBooks(bookName);
        Thread.sleep(1500);
        Assert.assertTrue(isViewChaptersOptionAvailable(bookName));

        driver.findElement(
                        By.xpath("//a[text()='"+bookName+"']/../..//i[@class='fa fa-eye']"))
                .click();
    }

    public void downloadFile(String bookName) throws InterruptedException {
        Thread.sleep(1000);
        searchBooks(bookName);
        Thread.sleep(1500);
        Assert.assertTrue(isViewChaptersOptionAvailable(bookName));

        driver.findElement(
                        By.xpath("//a[text()='"+bookName+"']/../..//i[@class='fa fa-cloud-download']"))
                .click();
    }

    public String getDownloadedFileName(String downloadPath) {
        File downloadDir = new File(downloadPath);
        File[] files = downloadDir.listFiles();
        if (files != null && files.length > 0) {
            File mostRecentFile = Arrays.stream(files)
                    .filter(File::isFile)
                    .max(Comparator.comparingLong(File::lastModified))
                    .orElse(null);
            if (mostRecentFile != null) {
                return mostRecentFile.getName();
            }
        }
        return null;
    }

    public String getBookRowColour(String bookName) throws InterruptedException {
        searchBooks(bookName);
        Thread.sleep(1000);

        String rowColour = driver.findElement(By.xpath("//*[text()='"+bookName+"']/.."))
                .getCssValue("background-color");
        return rowColour;
    }

    public int getCurrentDisplayBooksCount() throws InterruptedException {
        Thread.sleep(1000);
        return driver.findElements(By.xpath("//*[@id='table_book']/tbody/tr")).size();

    }

    public int getOverallBooksCount() throws InterruptedException {
        Thread.sleep(1000);
        return driver.findElements(By.xpath("//tbody/tr")).size();
    }

    public void verifyBookPresence(String publisherName, String bookName) throws InterruptedException {

        filterPublisher(publisherName);
        Thread.sleep(2000);
        filterBook(bookName);
        Thread.sleep(2000);



    }

    public String getToolTipText(String bookName, String elementName) throws InterruptedException {

        searchBooks(bookName);
        WebElement firstElement = null;

        switch (elementName){
            case "eye icon":
                firstElement = driver.findElement(By.xpath("//a[text()='"+bookName+"']/../..//i[@class='fa fa-eye']"));
                break;

            case "delete icon":
                firstElement = driver.findElement(By.xpath("//a[text()='"+bookName+"']/../..//i[@class='fa fa-trash delete_book_chap_data']"));
                break;

            case "edit icon":
                firstElement = driver.findElement(By.xpath("//a[text()='"+bookName+"']/../..//i[@class='fa fa-edit']"));
                break;

            case "download icon":
                firstElement = driver.findElement(By.xpath("//a[text()='"+bookName+"']/../..//i[@class='fa fa-cloud-download']"));
                break;

            case "book name":
                firstElement = driver.findElement(By.xpath("//a[text()='"+bookName+"']"));
                break;
        }

        if (firstElement != null) {
            Actions actions = new Actions(driver);
            actions.moveToElement(firstElement).perform();

            Thread.sleep(2000);
            WebElement secondElement = firstElement.findElement(By.xpath("./following-sibling::div/div[@class='tooltip-inner']"));
            return secondElement.getText();
            //return driver.findElement(By.xpath("//a[text()='"+bookName+"']/../..//i[@class='fa fa-eye']/following-sibling::div/div[@class='tooltip-inner']")).getText();
            //return firstElement.findElement(By.xpath("./following-sibling::div/div[@class='tooltip-inner']")).getText();

        }

        return "";

    }

}
