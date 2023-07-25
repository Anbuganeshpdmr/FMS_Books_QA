package ViewChapters;


import BasePackage.BaseClassFMS;
import BasicUtils.BasicUlitilityMethods;
import BasicUtils.ExcelReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import PageObjects.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static BasicUtils.BasicUlitilityMethods.*;
import static BasicUtils.TestngAnnotatedMethods.extentReports;
import static BasicUtils.TestngAnnotatedMethods.extentTest;
public class EditChapterNameTest extends BaseClassFMS {

    String chaptername = "ab";
    String newchaptername = "xyz";
    AddBookPage addBookPage;

    HomePage homePage;
    ViewChapterPage viewChapterPage;
    ListOfBooksPage listofbookpage;
    BasicUlitilityMethods basicUlitilityMethods;


    @BeforeMethod
    public void setup() throws InterruptedException, FileNotFoundException {

        homePage = new HomePage(driver);
        Thread.sleep(4000);
        homePage.navigateToListOfBooksPage();

        listofbookpage = new ListOfBooksPage(driver);
        listofbookpage.navigateToAddBookPage();

    }

    @Test
    public void cancelEdit() throws InterruptedException, IOException, InvalidFormatException {
        extentTest = extentReports.createTest("cancel edit chapter name").assignAuthor("Vijayakumari");
        addBookPage = new AddBookPage(driver);
        Thread.sleep(2000);

        String filePath = "src/test/resources/ViewChapter/ViewChaptersTestData.xlsx";
        String sheetName = "cancelEdit";

        Thread.sleep(2000);

        ExcelReader excelReader = new ExcelReader();
        List<Map<String, String>> bookdata = excelReader.getData(filePath, sheetName);
        Map<String, String> book1 = bookdata.get(0);

        String receivedDate = getDate(Integer.parseInt(book1.get("Received Date").trim()));
        int publisher = Integer.parseInt(book1.get("Publisher").trim());
        String bookName = book1.get("Book name") + getTimeStampNumber();
        Thread.sleep(2000);

        String bookname = addBookPage.getBookname();
        Thread.sleep(2000);

        String noOfChapters = book1.get("No of chapters");
        String ISBNNumber = getTimeStamp();
        String typeOfBook = book1.get("Type of book");
        String complexityLevel = book1.get("Complexity level");
        String bookColour = book1.get("Book color");
        String priority = book1.get("Priority");
        String CEReceivedDate = getDate(Integer.parseInt(book1.get("CE Received Date").trim()));
        String clientDueDate = getDate(Integer.parseInt(book1.get("Client Due Date").trim()));
        String PDMRPlanDate = getDate(Integer.parseInt(book1.get("PDMR Plan Date").trim()));
        String stages = book1.get("Stages");

        addBookPage.addNewBook(receivedDate, publisher, bookName, noOfChapters, ISBNNumber, typeOfBook,
                complexityLevel, bookColour, priority, CEReceivedDate, clientDueDate, PDMRPlanDate, stages);


        Thread.sleep(2000);
        addBookPage.pressSubmitButton();
        Thread.sleep(2000);
        homePage.navigateToListOfBooksPage();
        Thread.sleep(2000);
        viewChapterPage = new ViewChapterPage(driver);
        viewChapterPage.searchBookName(bookName);
        viewChapterPage.clickListOfBookeyeicon(bookName); // Add book name
        Thread.sleep(2000);
        viewChapterPage.editChaptersName();
        viewChapterPage.editChapterName("ab", newchaptername);
        viewChapterPage.clickCancelEditButton();
        Assert.assertTrue(viewChapterPage.isDisplayedChapterName("ab"));
        extentTest.pass("this chapter could not present in chapters list");
    }
    @Test
    public void confirmEdit() throws InterruptedException, IOException, InvalidFormatException {
        extentTest = extentReports.createTest("confirm edit chapter name").assignAuthor("Vijayakumari");
        addBookPage = new AddBookPage(driver);
        Thread.sleep(2000);

        String filePath = "src/test/resources/ViewChapter/ViewChaptersTestData.xlsx";
        String sheetName = "cancelEdit";

        Thread.sleep(2000);

        ExcelReader excelReader = new ExcelReader();
        List<Map<String, String>> bookdata = excelReader.getData(filePath, sheetName);
        Map<String, String> book1 = bookdata.get(0);

        String receivedDate = getDate(Integer.parseInt(book1.get("Received Date").trim()));
        int publisher = Integer.parseInt(book1.get("Publisher").trim());
        String bookName = book1.get("Book name") + getTimeStampNumber();
        Thread.sleep(2000);

        String bookname = addBookPage.getBookname();
        Thread.sleep(2000);

        String noOfChapters = book1.get("No of chapters");
        String ISBNNumber = getTimeStamp();
        String typeOfBook = book1.get("Type of book");
        String complexityLevel = book1.get("Complexity level");
        String bookColour = book1.get("Book color");
        String priority = book1.get("Priority");
        String CEReceivedDate = getDate(Integer.parseInt(book1.get("CE Received Date").trim()));
        String clientDueDate = getDate(Integer.parseInt(book1.get("Client Due Date").trim()));
        String PDMRPlanDate = getDate(Integer.parseInt(book1.get("PDMR Plan Date").trim()));
        String stages = book1.get("Stages");

        addBookPage.addNewBook(receivedDate, publisher, bookName, noOfChapters, ISBNNumber, typeOfBook,
                complexityLevel, bookColour, priority, CEReceivedDate, clientDueDate, PDMRPlanDate, stages);

        Thread.sleep(2000);
        addBookPage.pressSubmitButton();
        Thread.sleep(2000);
        homePage.navigateToListOfBooksPage();
        Thread.sleep(2000);
        viewChapterPage = new ViewChapterPage(driver);
        viewChapterPage.searchBookName(bookName);
        viewChapterPage.clickListOfBookeyeicon(bookName); // Add book name
        Thread.sleep(2000);
        viewChapterPage.editChaptersName();
        viewChapterPage.editChapterName("ab", newchaptername);
        viewChapterPage.clickConfirmEditButton();
        Assert.assertTrue(viewChapterPage.isDisplayedChapterName(newchaptername));
        extentTest.pass("New chapter name should present in chapters list");

    }
}

