package Publisher;

import BasePackage.BaseClassFMS;
import BasicUtils.BasicUlitilityMethods;
import BasicUtils.ExcelReader;
import PageObjects.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static BasicUtils.BasicUlitilityMethods.getDate;
import static BasicUtils.BasicUlitilityMethods.getTimeStamp;
import static BasicUtils.TestngAnnotatedMethods.extentReports;
import static BasicUtils.TestngAnnotatedMethods.extentTest;

public class AddPublisherTest extends BaseClassFMS {

    @Test(groups = {"smoke","regression"},enabled = true)
    public void addPublisherTest_1() throws InterruptedException {
        extentTest = extentReports
                .createTest("Add new publisher and verify added publisher; Then validate Book count and presence of Delete button")
                .assignAuthor("Anbu");

        HomePage homePage = new HomePage(driver);
        homePage.navigateToListOfPublishersPage();

        ListOfPublishersPage listOfPublishersPage = new ListOfPublishersPage(driver);
        listOfPublishersPage.navigateToAddPublisherPage();
        extentTest.info("Navigated to add publisher page");

        String publisherName = "Demo" + getTimeStamp();

        AddPublisherPage addPublisherPage = new AddPublisherPage();
        addPublisherPage.addNewPublisherAndClick(publisherName);
        extentTest.info("Added new publisher but yet to verify..");

        Assert.assertTrue(listOfPublishersPage.verifyPublisher(publisherName));
        extentTest.pass("Added Publisher name is present and verified");

        extentTest.info("Need to ensure other icon details and bookcount");

        Assert.assertEquals(listOfPublishersPage.getBookcount(publisherName),"0");
        extentTest.pass("Initial book count is Zero'0', Verified");

        Assert.assertTrue(listOfPublishersPage.isEditOptionAvailable(publisherName));
        extentTest.pass("Edit icon is available for new publisher");

        Assert.assertTrue(listOfPublishersPage.isDeleteOptionAvailable(publisherName));
        extentTest.pass("Delete icon is available for new publisher");

    }

    @Test(groups = {"regression"},enabled = true)
    public void addPublisherTest_2() throws InterruptedException, IOException, InvalidFormatException {
        extentTest = extentReports
                .createTest("Add new book to the publisher and ensure Delete icon vanishes, Also check book count increases furthur")
                .assignAuthor("Anbu");

        HomePage homePage = new HomePage(driver);
        homePage.navigateToListOfPublishersPage();

        ListOfPublishersPage listOfPublishersPage = new ListOfPublishersPage(driver);
        listOfPublishersPage.navigateToAddPublisherPage();
        extentTest.info("Navigated to add publisher page");

        String filePath = "src/test/resources/Publisher/AddPublisherTestData.xlsx";
        String sheetName = "AddPublisherTest_2";

        Thread.sleep(2000);

        ExcelReader excelReader = new ExcelReader();
        List<Map<String, String>> bookdata =  excelReader.getData(filePath, sheetName);
        Map<String, String> book1 = bookdata.get(0);
        Map<String, String> book2 = bookdata.get(1);

        String publisherName = book1.get("Publisher") + getTimeStamp();

        AddPublisherPage addPublisherPage = new AddPublisherPage();
        addPublisherPage.addNewPublisherAndClick(publisherName);
        extentTest.info("Added new publisher but yet to verify..");


        String receivedDate = getDate(Integer.parseInt(book1.get("Received Date").trim()));
        String publisher = publisherName;
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

        homePage.navigateToListOfBooksPage();

        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);
        listOfBooksPage.navigateToAddBookPage();


        AddBookPage addBookPage = new AddBookPage(driver);
        Thread.sleep(2000);
        addBookPage.addNewBook(receivedDate,publisher,bookName,noOfChapters,ISBNNumber,typeOfBook,complexityLevel,
                bookColour,priority,CEReceivedDate,clientDueDate,PDMRPlanDate,stages);

        addBookPage.pressSubmitButton();
        extentTest.info("Added One Book to the publisher");

        homePage.navigateToListOfPublishersPage();

        String currentBookCount = listOfPublishersPage.getBookcount(publisherName);

        Assert.assertEquals(currentBookCount,"1");
        extentTest.pass("BookCount increased by 1");

        Assert.assertFalse(listOfPublishersPage.isDeleteOptionAvailable(publisherName), "Delete icon is present");
        extentTest.pass("Delete icon vanishes");

        receivedDate = getDate(Integer.parseInt(book2.get("Received Date").trim()));
        publisher = publisherName;
        bookName = book2.get("Book name") + getTimeStamp();
        noOfChapters = book2.get("No of chapters");
        ISBNNumber = getTimeStamp();
        typeOfBook = book2.get("Type of book");
        complexityLevel = book2.get("Complexity level");
        bookColour = book2.get("Book color");
        priority = book2.get("Priority");
        CEReceivedDate = getDate(Integer.parseInt(book2.get("CE Received Date").trim()));
        clientDueDate = getDate(Integer.parseInt(book2.get("Client Due Date").trim()));
        PDMRPlanDate = getDate(Integer.parseInt(book2.get("PDMR Plan Date").trim()));
        stages = book2.get("Stages");

        homePage.navigateToListOfBooksPage();

        listOfBooksPage.navigateToAddBookPage();

        Thread.sleep(2000);
        addBookPage.addNewBook(receivedDate,publisher,bookName,noOfChapters,ISBNNumber,typeOfBook,complexityLevel,
                bookColour,priority,CEReceivedDate,clientDueDate,PDMRPlanDate,stages);
        addBookPage.pressSubmitButton();
        extentTest.info("Added Second Book to the publisher");

        homePage.navigateToListOfPublishersPage();

        currentBookCount = listOfPublishersPage.getBookcount(publisherName);

        Assert.assertEquals(currentBookCount,"2");
        extentTest.pass("BookCount Changed to 2");
    }

    @DataProvider(name = "different publisher name")
    public Object[][] provideData1() throws IOException {
        String filePath = "src/test/resources/Publisher/AddPublisherTestData.xlsx";
        String sheetName = "AddPublisherTest_3";
        return BasicUlitilityMethods.readExcelData(filePath,sheetName);
    }

    @Test(groups = {"regression"},dataProvider = "different publisher name",enabled = true)
    public void addPublisherTest_3(String Sl_No,String pub_Name, boolean expectedResult) throws InterruptedException {

        extentTest = extentReports
                .createTest("Testing publisher name with valid and invalid characters; Input Data: "+pub_Name)
                .assignAuthor("Anbu");

        HomePage homePage = new HomePage(driver);
        homePage.navigateToListOfPublishersPage();

        ListOfPublishersPage listOfPublishersPage = new ListOfPublishersPage(driver);
        listOfPublishersPage.navigateToAddPublisherPage();
        listOfPublishersPage.checkForAddPublisherPageNavigation();
        extentTest.info("Navigated to add publisher page");

        AddPublisherPage addPublisherPage = new AddPublisherPage();
        addPublisherPage.addNewPublisherAndClick(pub_Name);
        extentTest.info("Publisher name entered...");


        if(addPublisherPage.isPublisherAddedSuccessfully(pub_Name) == expectedResult){
            if(expectedResult){
                extentTest.pass("As the publisher name("+pub_Name+") is valid, It is accepted");
            }else {
                extentTest.pass("As the publisher name("+pub_Name+") is Not valid, It is rejected");
            }
        }else {
            Assert.fail("Acceptance of Publisher name is not behaving as expected");
        }
    }

    @Test(groups = {"regression"},enabled = true)
    public void addPublisherTest_4() throws InterruptedException {
        extentTest = extentReports
                .createTest("Press CANCEL button while adding publisher and Verify publisher not added")
                .assignAuthor("Anbu");

        HomePage homePage = new HomePage(driver);
        homePage.navigateToListOfPublishersPage();

        ListOfPublishersPage listOfPublishersPage = new ListOfPublishersPage(driver);
        listOfPublishersPage.navigateToAddPublisherPage();
        extentTest.info("Navigated to add publisher page");

        String publisherName = "AB_4_"+ getTimeStamp();

        AddPublisherPage addPublisherPage = new AddPublisherPage();
        addPublisherPage.addPublisherButNavigateBack(publisherName);
        extentTest.info("Typed publisher name but pressed CANCEL button");

        Assert.assertFalse(listOfPublishersPage.verifyPublisher(publisherName));
        extentTest.pass("Publisher name is not Added");
    }

    @Test(groups = {"regression"},enabled = true)
    public void addPublisherTest_5() throws InterruptedException {
        extentTest = extentReports
                .createTest("Enter existing publisher name and verify the error message")
                .assignAuthor("Anbu");

        HomePage homePage = new HomePage(driver);
        homePage.navigateToListOfPublishersPage();

        ListOfPublishersPage listOfPublishersPage = new ListOfPublishersPage(driver);
        listOfPublishersPage.navigateToAddPublisherPage();
        extentTest.info("Navigated to add publisher page");

        String publisherName = "AB_5_" + getTimeStamp();

        AddPublisherPage addPublisherPage = new AddPublisherPage();
        addPublisherPage.addNewPublisherAndClick(publisherName);
        extentTest.info("Created one publisher");

        listOfPublishersPage.navigateToAddPublisherPage();

        addPublisherPage.addNewPublisherAndClick(publisherName);
        extentTest.info("Trying to enter previous publisher name");

        Assert.assertTrue(addPublisherPage.checkErrorMessageDisplayed());
        extentTest.pass("Error Message is displayed..");
    }

}