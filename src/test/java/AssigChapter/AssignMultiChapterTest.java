package AssigChapter;

import BasePackage.BaseClassFMS;
import PageObjects.HomePage;
import PageObjects.ListOfBooksPage;
import org.testng.annotations.BeforeClass;
import BasicUtils.ExcelReader;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.Test;
import PageObjects.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static BasicUtils.BasicUlitilityMethods.*;
import static BasicUtils.TestngAnnotatedMethods.extentReports;
import static BasicUtils.TestngAnnotatedMethods.extentTest;

public class AssignMultiChapterTest extends BaseClassFMS {
    AddBookPage addBookPage;
    HomePage homePage;
    ViewChapterPage viewChapterPage;
    AssignedListPage assignedListPage;
    ListOfBooksPage listofbookpages;
    AssignChapterPage assignChapterPage;
    StagesPage stagesPage;
    String bookName;

    String chapterName = "ab";
    String changeUser="Sujatha P   ";
    String jobtype = "Fresh";
    String process = "Pagination";
    String username = "R. Sharmila";



    @BeforeClass
    public void setup() throws InterruptedException, IOException, InvalidFormatException {

        homePage = new HomePage(driver);
        Thread.sleep(4000);
        homePage.navigateToListOfBooksPage();

        listofbookpages = new ListOfBooksPage(driver);
        listofbookpages.navigateToAddBookPage();
        addBookPage = new AddBookPage(driver);
        Thread.sleep(2000);

        String filePath = "src/test/resources/ViewChapter/ViewChaptersTestData.xlsx";
        String sheetName = "Test_No_1_1";

        Thread.sleep(2000);

        ExcelReader excelReader = new ExcelReader();
        List<Map<String, String>> bookdata = excelReader.getData(filePath, sheetName);
        Map<String, String> book1 = bookdata.get(0);

        String receivedDate = getDate(Integer.parseInt(book1.get("Received Date").trim()));
        int publisher = Integer.parseInt(book1.get("Publisher").trim());
        bookName = book1.get("Book name") + getTimeStampNumber();
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


        System.out.println(bookName);         //mhvfjhfjy
        Thread.sleep(2000);
        addBookPage.pressSubmitButton();

        viewChapterPage = new ViewChapterPage(driver);
        viewChapterPage.searchBookName(bookName);         //hffjfdjde
        viewChapterPage.clickListOfBookeyeicon(bookName);
        Thread.sleep(2000);
        viewChapterPage.editChaptersName();
        viewChapterPage.addMsPages(800);
    }
    @Test
    public void assignMultipleChapter() throws InterruptedException{
        extentTest=extentReports.createTest("Assign Multiple chapter to Single user and validate the Jobtype, Process and username").assignAuthor("Vijayakumari");

        Thread.sleep(5000);
        stagesPage = new StagesPage(driver);
        Thread.sleep(5000);
        stagesPage.navigateToStages();
        stagesPage.navigateToFreshStage();
        stagesPage.search(bookName);
        Thread.sleep(1500);
        stagesPage.selectEyeIcon(bookName);
        Thread.sleep(1500);

        stagesPage.selectMultiChapters();
        stagesPage.clickAssignButton();
        Thread.sleep(2000);

        assignChapterPage = new AssignChapterPage(driver);
        assignChapterPage.selectUser();
        assignChapterPage.selectProcess();
        assignChapterPage.selectPriority();
        assignChapterPage.assignComments();
        assignChapterPage.submitButton();
        Thread.sleep(2000);

//checking assigned list and validate the correct inputdata
        homePage.navigateToAssignedListPage();
        assignedListPage = new AssignedListPage(driver);
        assignedListPage.searchBookName(bookName);
//Assertion part
        Assert.assertEquals(assignedListPage.isDisplayedJobtype(bookName),jobtype);
        extentTest.pass("assigned jobtype displayed");
        Assert.assertEquals(assignedListPage.isDisplayedProcess(bookName),process);
        extentTest.pass("assigned process displayed");
        Assert.assertEquals(assignedListPage.isDisplayedUserName(bookName),username);
        extentTest.pass("assigned username displayed");


    }
    @Test(enabled = true)
    public void changeJobtypeAndProcess() throws InterruptedException {
        extentTest=extentReports.createTest("able to reassign jobtype and process After Assign a chapter to user ").assignAuthor("Vijayakumari");

        Thread.sleep(5000);
        stagesPage = new StagesPage(driver);
        Thread.sleep(5000);
        stagesPage.navigateToStages();
        stagesPage.navigateToFreshStage();
        stagesPage.search(bookName);
        stagesPage.selectEyeIcon(bookName);

        stagesPage.clickjobtypeProcessEditIcon();
        stagesPage.changeJobType();
        stagesPage.changeProcess();
        stagesPage.clickReAssignJobtypeAndProcessSubmit();

//        Assertion
        Assert.assertTrue(stagesPage.isDisplayedReAssignedJobtype());
        extentTest.pass("Reassigned jobtype displayed");
        Assert.assertTrue(stagesPage.isDisplayedReAssignedProcess());
        extentTest.pass("Reasssigned process displayed");

    }

    @Test(enabled = true)
    public void changeUser() throws InterruptedException {
        extentTest=extentReports.createTest("able to change user After Assign a chapter to user").assignAuthor("Vijayakumari");
        Thread.sleep(5000);
        stagesPage=new StagesPage(driver);
        stagesPage.navigateToStages();
        stagesPage.navigateToFreshStage();
        stagesPage.search(bookName);
        stagesPage.selectEyeIcon(bookName);

        stagesPage.clickDropdownButton();
        stagesPage.clickChangeUserButton();
        stagesPage.changeUserEmpID(changeUser);
        stagesPage.clickTosaveChangeuserDetail();
//     Assertion
        Assert.assertEquals(stagesPage.getChangedUserName(),changeUser);
        extentTest.pass("user name should be change");


    }

    @Test(enabled = true)
    public void editAssignedMSPageCount() throws InterruptedException {
        extentTest=extentReports.createTest("catch alert for while clicking change user without edit MS page").assignAuthor("Vijayakumari");
        Thread.sleep(5000);
        stagesPage=new StagesPage(driver);
        stagesPage.navigateToStages();
        stagesPage.navigateToFreshStage();
        stagesPage.search(bookName);
        stagesPage.selectEyeIcon(bookName);

        Thread.sleep(1200);
        stagesPage.clickDropdownButton();
        stagesPage.clickChangeUserButton();
        stagesPage.changePageRange();
        stagesPage.clickTosaveChangeuserDetail();

        //Assertion
        Assert.assertTrue(stagesPage.isDisplayedEditMsPageAlert(),"alert showing after edit a MS page Count");
        extentTest.pass("Should show alert for mising page range");
        stagesPage.clickOkButton();
    }
    @Test(enabled = true)
    public void addNewUser() throws InterruptedException {
        extentTest = extentReports.createTest("Adding a new users ").assignAuthor("Vijayakumari");
        Thread.sleep(5000);
        stagesPage=new StagesPage(driver);
        stagesPage.navigateToStages();
        stagesPage.navigateToFreshStage();
        stagesPage.search(bookName);
        stagesPage.selectEyeIcon(bookName);
        Thread.sleep(2000);
        stagesPage.clickDropdownButton();

        stagesPage.clickaddNewEmployeeButton();
        stagesPage.userCheckboxClickWithoutEditMSpage();
//        Assert validation
        Assert.assertTrue(stagesPage.isDisplayedAlertForAddUserWithoutEditMSPageRange());
        extentTest.pass("should show alert for split page range to assign another user");
    }
























}