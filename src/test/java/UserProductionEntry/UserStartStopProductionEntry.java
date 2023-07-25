package UserProductionEntry;

import BasePackage.BaseClassFMS;
import BasicUtils.ExcelReader;
import PageObjects.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static BasicUtils.BasicUlitilityMethods.*;
import static BasicUtils.BasicUlitilityMethods.getDate;
import static BasicUtils.TestngAnnotatedMethods.extentReports;
import static BasicUtils.TestngAnnotatedMethods.extentTest;

public class UserStartStopProductionEntry extends BaseClassFMS {
    AddBookPage addBookPage;
    HomePage homePage;
    ViewChapterPage viewChapterPage;

    ListOfBooksPage listofbookpages;
    AssignChapterPage assignChapterPage;
    StagesPage stagesPage;
    AssignedListPage assignedListPage;
    String bookName;
    LoginPage loginPage;

    String chapterName = "ab";
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


        System.out.println(bookName);
        Thread.sleep(2000);
        addBookPage.pressSubmitButton();
        viewChapterPage = new ViewChapterPage(driver);
        viewChapterPage.searchBookName(bookName);
        viewChapterPage.clickListOfBookeyeicon(bookName);
        Thread.sleep(2000);
        viewChapterPage.editChaptersName();

        viewChapterPage.addMsPages(800);

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
    }
    @Test
    public void userStartProctution() throws InterruptedException, IOException {
        extentTest=extentReports.createTest("testing");
        Thread.sleep(4000);
        assignChapterPage.logout();
        loginPage=new LoginPage(driver_user_1);
        Thread.sleep(2000);
        loginPage.loginToApplication("1671","1671");
//        Thread.sleep(3000);
//        homePage.navigateToUserAssignedListPage();
        assignedListPage=new AssignedListPage(driver_user_1);
        Thread.sleep(2000);
        assignedListPage.searchBookName(bookName);
        Thread.sleep(3000);
        assignedListPage.clickPlayButton();
        assignedListPage.clickStartButton();

        assignedListPage.clickPauseButton();

        assignedListPage.selectProductionStatusHOLD();
        assignedListPage.clickFinishButton();
        Assert.assertTrue(assignedListPage.isDispalyedRemarksAlert());

        assignedListPage.enterRemarks();
        assignedListPage.clickFinishButton();
        Assert.assertTrue(assignedListPage.isDisplayedholdStatus());


    }


}
