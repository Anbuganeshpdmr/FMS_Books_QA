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
public class DeleteChapterTest extends BaseClassFMS {
    String chaptername = "ab";
    AddBookPage addBookPage;
    HomePage homePage;
    ViewChapterPage viewChapterPage;
    ListOfBooksPage listofbookpages;
    BasicUlitilityMethods basicUlitilityMethods;


   @BeforeMethod
   public void setup() throws InterruptedException, FileNotFoundException {

        homePage = new HomePage(driver);
        Thread.sleep(4000);
        homePage.navigateToListOfBooksPage();

        listofbookpages=new ListOfBooksPage(driver);
        listofbookpages.navigateToAddBookPage();


    }

    @Test
    public void cancleDelete1_1 () throws InterruptedException, IOException, InvalidFormatException {
        extentTest=extentReports.createTest("Cancel delete operation").assignAuthor("vijayakumari");

        addBookPage=new AddBookPage(driver);
        Thread.sleep(2000);

        String filePath = "src/test/resources/ViewChapter/ViewChaptersTestData.xlsx";
        String sheetName = "Test_No_1_1";

        Thread.sleep(2000);

        ExcelReader excelReader = new ExcelReader();
        List<Map<String, String>> bookdata =  excelReader.getData(filePath, sheetName);
        Map<String, String> book1 = bookdata.get(0);

        String receivedDate = getDate(Integer.parseInt(book1.get("Received Date").trim()));
        int publisher = Integer.parseInt(book1.get("Publisher").trim());
        String bookName = book1.get("Book name") + getTimeStampNumber();
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

        addBookPage.addNewBook(receivedDate,publisher,bookName,noOfChapters,ISBNNumber,typeOfBook,
                complexityLevel,bookColour,priority,CEReceivedDate,clientDueDate,PDMRPlanDate,stages);


        System.out.println(bookName);

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
        viewChapterPage.clickChapterDeleteIcon(chaptername);
        viewChapterPage.clickcancelForChapterDelete();
        Assert.assertTrue(viewChapterPage.isDisplayedChapterName(chaptername));
        extentTest.pass("old chapter name should show the chapters list");
    }



    @Test
    public void confirmDelete() throws InterruptedException, IOException, InvalidFormatException {
       Thread.sleep(2000);
        extentReports.createTest("Confirm delete operation").assignAuthor("vijayakumari");
        addBookPage=new AddBookPage(driver);
        Thread.sleep(2000);

        String filePath = "src/test/resources/ViewChapter/ViewChaptersTestData.xlsx";
        String sheetName = "Test_No_1_2";

        Thread.sleep(2000);

        ExcelReader excelReader = new ExcelReader();
        List<Map<String, String>> bookdata =  excelReader.getData(filePath, sheetName);
        Map<String, String> book1 = bookdata.get(0);

        String receivedDate = getDate(Integer.parseInt(book1.get("Received Date").trim()));
        int publisher = Integer.parseInt(book1.get("Publisher").trim());
        String bookName = book1.get("Book name") + getTimeStamp();
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

        addBookPage.addNewBook(receivedDate,publisher,bookName,noOfChapters,ISBNNumber,typeOfBook,
                complexityLevel,bookColour,priority,CEReceivedDate,clientDueDate,PDMRPlanDate,stages);
        System.out.println(bookName);

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
        viewChapterPage.clickChapterDeleteIcon(chaptername);
        viewChapterPage.clickchapterConfirmDelete();
        Thread.sleep(2000);
        Assert.assertFalse(viewChapterPage.isDisplayedChapterName(chaptername));
        extentTest.pass("New chapter name should show the chapters list");

    }

}