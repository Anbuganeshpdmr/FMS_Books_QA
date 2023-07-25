package Books;

import BasePackage.BaseClassFMS;
import BasicUtils.ExcelReader;
import PageObjects.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static BasePackage.BaseClassFMS.driver;
import static BasicUtils.BasicUlitilityMethods.*;
import static BasicUtils.TestngAnnotatedMethods.extentReports;
import static BasicUtils.TestngAnnotatedMethods.extentTest;

public class AddBooksTest extends BaseClassFMS {

    @Test(groups = {"books","smoke","regression"},enabled = false)
    public void addBooksTest_1() throws InterruptedException, IOException, InvalidFormatException {
        extentTest = extentReports
                .createTest("Add new book and verify necessary icons are present")
                .assignAuthor("Anbu");


        Thread.sleep(2000);

        String filePath = "src/test/resources/Books/AddBooksTestData.xlsx";
        String sheetName = "AddBooks_1";

        Thread.sleep(2000);

        ExcelReader excelReader = new ExcelReader();
        List<Map<String, String>> bookdata =  excelReader.getData(filePath, sheetName);
        Map<String, String> book1 = bookdata.get(0);

        String receivedDate = getDate(Integer.parseInt(book1.get("Received Date").trim()));
        int publisherIndex = Integer.parseInt(book1.get("Publisher").trim());
        String bookName = book1.get("Book name") + getTimeStamp();
        String noOfChapters = book1.get("No of chapters");
        String ISBNNumber = getTimeStampNumber();
        String typeOfBook = book1.get("Type of book");
        String complexityLevel = book1.get("Complexity level");
        String bookColour = book1.get("Book color");
        String priority = book1.get("Priority");
        String CEReceivedDate = getDate(Integer.parseInt(book1.get("CE Received Date").trim()));
        String clientDueDate = getDate(Integer.parseInt(book1.get("Client Due Date").trim()));
        String PDMRPlanDate = getDate(Integer.parseInt(book1.get("PDMR Plan Date").trim()));
        String stages = book1.get("Stages");

        HomePage homePage = new HomePage(driver);
        homePage.navigateToListOfBooksPage();

        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);
        listOfBooksPage.navigateToAddBookPage();
        extentTest.info("Navigating to AddBook page");

        AddBookPage addBookPage = new AddBookPage(driver);
        Thread.sleep(2000);
        addBookPage.addNewBook(receivedDate, publisherIndex, bookName, noOfChapters, ISBNNumber, typeOfBook, complexityLevel,
                bookColour, priority, CEReceivedDate, clientDueDate, PDMRPlanDate, stages);

        String publisher = addBookPage.getSelectedPublisherName();

        Map<String, String> bookDatasProvided = new HashMap<>();

        bookDatasProvided.put("Received Date", receivedDate);
        bookDatasProvided.put("Publisher", publisher);
        bookDatasProvided.put("Book name", bookName);
        bookDatasProvided.put("No of chapters", noOfChapters);
        bookDatasProvided.put("ISBN Number", ISBNNumber);
        bookDatasProvided.put("Type of book", typeOfBook);
        bookDatasProvided.put("Complexity level", complexityLevel);
        bookDatasProvided.put("Book color", bookColour);
        bookDatasProvided.put("Priority", priority);
        bookDatasProvided.put("CE Received Date", CEReceivedDate);
        bookDatasProvided.put("Client Due Date", clientDueDate);
        bookDatasProvided.put("PDMR Plan Date", PDMRPlanDate);
        bookDatasProvided.put("Stages", stages);

        addBookPage.pressSubmitButton();
        extentTest.info("Gathering the book details provided");

        Map<String, String> bookDatasActual = listOfBooksPage.getBookDatas(bookName);
        extentTest.info("Gathering the book details from Table");

        Assert.assertEquals(bookDatasActual.get("Publisher"), bookDatasProvided.get("Publisher"));
        Assert.assertEquals(bookDatasActual.get("Book Name"), bookDatasProvided.get("Book name"));
        Assert.assertEquals(bookDatasActual.get("No of Chapters"), bookDatasProvided.get("No of chapters"));
        Assert.assertEquals(bookDatasActual.get("ISBN No"), bookDatasProvided.get("ISBN Number"));
        Assert.assertEquals(bookDatasActual.get("Type Book"), bookDatasProvided.get("Type of book"));
        Assert.assertEquals(bookDatasActual.get("Complexity level"), bookDatasProvided.get("Complexity level"));
        Assert.assertEquals(bookDatasActual.get("Book Colour"), bookDatasProvided.get("Book color"));
        extentTest.pass("Most of all the records are Matching");

        Assert.assertTrue(listOfBooksPage.isEditOptionAvailable(bookName));
        extentTest.pass("Edit option available");

        Assert.assertTrue(listOfBooksPage.isDeleteOptionAvailable(bookName));
        extentTest.pass("Delete option available");

        Assert.assertTrue(listOfBooksPage.isViewChaptersOptionAvailable(bookName));
        extentTest.pass("viewChapters option available");

        Assert.assertFalse(listOfBooksPage.isDownloadOptionAvailable(bookName));
        extentTest.pass("Download option is NOT available");
    }

    @Test(groups = {"books","regression"},enabled = false)
    public void addBooksTest_2() throws InterruptedException, IOException, InvalidFormatException {
        extentTest = extentReports
                .createTest("Upload file while adding book and verify by downloading the same")
                .assignAuthor("Anbu");

        Thread.sleep(2000);

        String fileLocation = "src\\test\\resources\\Books_test files\\";
        String fileName = "fresh File.txt";

        String fileDeletionMessage = deleteFileFromDownloads(fileName);

        if(!((fileDeletionMessage.contains("File deleted successfully")) || (fileDeletionMessage.contains("File not found"))))
        {
            Assert.fail(fileDeletionMessage);
            extentTest.fail(fileDeletionMessage+"Please check and close the file");
        }
        extentTest.info("Concerned File would be deleted now if present");

        String excelFilePath = "src/test/resources/Books/AddBooksTestData.xlsx";
        String sheetName = "AddBooks_2";

        Thread.sleep(2000);

        ExcelReader excelReader = new ExcelReader();
        List<Map<String, String>> bookdata =  excelReader.getData(excelFilePath, sheetName);
        Map<String, String> book1 = bookdata.get(0);

        String receivedDate = getDate(Integer.parseInt(book1.get("Received Date").trim()));
        int publisherIndex = Integer.parseInt(book1.get("Publisher").trim());
        String bookName = book1.get("Book name") + getTimeStamp();
        String noOfChapters = book1.get("No of chapters");
        String ISBNNumber = getTimeStampNumber();
        String typeOfBook = book1.get("Type of book");
        String complexityLevel = book1.get("Complexity level");
        String bookColour = book1.get("Book color");
        String priority = book1.get("Priority");
        String CEReceivedDate = getDate(Integer.parseInt(book1.get("CE Received Date").trim()));
        String clientDueDate = getDate(Integer.parseInt(book1.get("Client Due Date").trim()));
        String PDMRPlanDate = getDate(Integer.parseInt(book1.get("PDMR Plan Date").trim()));
        String stages = book1.get("Stages");

        HomePage homePage = new HomePage(driver);
        homePage.navigateToListOfBooksPage();

        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);
        listOfBooksPage.navigateToAddBookPage();

        AddBookPage addBookPage = new AddBookPage(driver);
        Thread.sleep(2000);
        addBookPage.addNewBook(receivedDate,publisherIndex,bookName,noOfChapters,ISBNNumber,typeOfBook,complexityLevel,
                bookColour,priority,CEReceivedDate,clientDueDate,PDMRPlanDate,stages);
        File file = new File(fileLocation,fileName);
        addBookPage.addFileForNewBook(file.getAbsolutePath());
        Thread.sleep(2000);
        addBookPage.pressSubmitButton();
        extentTest.info("Book added with file");

        Assert.assertTrue(listOfBooksPage.isDownloadOptionAvailable(bookName));
        extentTest.pass("Download option is available");

        listOfBooksPage.downloadFile(bookName);

        Thread.sleep(2000);
        String downloadedFileName = getRecentlyAddedFileName();
        Assert.assertEquals(downloadedFileName, fileName, "Downloaded file doesn't matches");
        extentTest.pass("Downloaded file matches with the Uploaded file");

    }





}
