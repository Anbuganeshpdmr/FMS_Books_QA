package ViewChapters;

import BasePackage.BaseClassFMS;
import BasicUtils.ExcelReader;
import PageObjects.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static BasicUtils.BasicUlitilityMethods.*;
import static BasicUtils.BasicUlitilityMethods.getChapterDataFromJSON;
import static BasicUtils.TestngAnnotatedMethods.extentReports;
import static BasicUtils.TestngAnnotatedMethods.extentTest;

public class ViewChaptersAutomationTest extends BaseClassFMS {

    String currentBookName;

    String bluecolor = "rgba(193, 238, 245, 1)";
    String nullcolor = "rgba(0, 0, 0, 0)";

    private void addSingleBook_Before_TestMethod(String filePath, String sheetName) throws InterruptedException, IOException, InvalidFormatException {
        Thread.sleep(2000);

        ExcelReader excelReader = new ExcelReader();
        List<Map<String, String>> bookdata =  excelReader.getData(filePath, sheetName);
        Map<String, String> book1 = bookdata.get(0);

        String receivedDate = getDate(Integer.parseInt(book1.get("Received Date").trim()));
        int publisherIndex = Integer.parseInt(book1.get("Publisher").trim());
        currentBookName = book1.get("Book name") + getTimeStamp();
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
        addBookPage.addNewBook(receivedDate, publisherIndex, currentBookName, noOfChapters, ISBNNumber, typeOfBook, complexityLevel,
                bookColour, priority, CEReceivedDate, clientDueDate, PDMRPlanDate, stages);
        addBookPage.pressSubmitButton();
    }

    @Test(groups = {"automation","view chapters"},enabled = false)
    public void verifyAutomation_1() throws InterruptedException, IOException, InvalidFormatException {
        extentTest = extentReports.createTest("Verify the Datas from the JSON-Automation is correctly fetched").assignAuthor("Anbu");

        //  Adding fresh book
        Thread.sleep(2000);
        String filePath = "src/test/resources/ViewChapters/ViewChaptersTestData.xlsx";
        String sheetName = "Sheet1";
        addSingleBook_Before_TestMethod(filePath, sheetName);

        extentTest.info("Book Added");

        Thread.sleep(2000);
        //  Clicking EYE icon

        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);
        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);
        extentTest.info("clicking EYE icon");
        extentTest.info("Navigating to view Chapters page");

        //  retrieving data from JSON file and storing locally.

        String fileLocation = "src\\test\\resources\\ViewChapters\\";
        String fileName = "datas for 3 chapters.json";
        String jsonFileAbsLocation = fileLocation+fileName;

        File file = new File(fileLocation,fileName);
        Map<String, HashMap<String, String>> chaptersData = getChapterDataFromJSON(jsonFileAbsLocation);

        //  Running AUTOMATION - uploading JSON file.

        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);
        viewChapterPage.runAutomation_JSON(file.getAbsolutePath());
        extentTest.pass("Successfully ran AUTOMATION - uploaded JSON file");

        extentTest.info("verifying datas fetched from JSON file");

        assert chaptersData != null;
        for(String chapterName: chaptersData.keySet()){
            HashMap<String, String> chapterData = chaptersData.get(chapterName);
            extentTest.info("verifying data for chapter: "+chapterName);
            //  verifying chapter name

            Assert.assertTrue(viewChapterPage.isDisplayedchapter(chapterName),"chapter name: "+chapterName+" is not Present");
            extentTest.pass("current Chapter is present");

            //  verifying MS Page count
            String expMsPages = chapterData.get("No. of MS Pages");
            String actMsPages = viewChapterPage.getMSPageCount(chapterName);
            Assert.assertEquals(actMsPages, expMsPages, "MS Page count Doesn't matches");
            extentTest.pass("MS Page count for "+chapterName+" Matches");

            //  verifying Tables count
            String expTableCount = chapterData.get("No. of Tables");
            String actTableCount = viewChapterPage.getTablesCount(chapterName);
            Assert.assertEquals(actTableCount, expTableCount, "Tables count Doesn't matches");
            extentTest.pass("Tables count for "+chapterName+" Matches");

            //  verifying figures count
            String expFigures = chapterData.get("No. of Figures");
            String actFigures = viewChapterPage.getFiguresCount(chapterName);
            Assert.assertEquals(actFigures, expFigures, "Figures count Doesn't matches");
            extentTest.pass("Figures count for "+chapterName+" Matches");
        }
    }

    @Test(groups = {"automation","view chapters","smoke"},enabled = false)
    public void verifyAutomation_2() throws InterruptedException, IOException, InvalidFormatException {
        extentTest = extentReports.createTest("Check all chapters have BLUE color initially")
                .assignAuthor("Anbu");

        Thread.sleep(2000);
        String filePath = "src/test/resources/ViewChapters/ViewChaptersTestData.xlsx";
        String sheetName = "Sheet4";                                            //  Adding fresh book
        addSingleBook_Before_TestMethod(filePath, sheetName);

        extentTest.info("Book Added");

        Thread.sleep(2000);

        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);          //  Clicking EYE icon - view chapter page
        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);
        extentTest.info("Navigating to view Chapters page");


        String fileLocation_1 = "src\\test\\resources\\ViewChapters\\";         //  Getting 1st JSON file - two chapters
        String fileName_1 = "Auto. Test 2 chapters.json";
        File file_1 = new File(fileLocation_1,fileName_1);

        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);
        viewChapterPage.runAutomation_JSON(file_1.getAbsolutePath());           //  Running AUTOMATION - 1st time

        extentTest.pass("Ran AUTOMATION - for TWO chapters");            //   Verifying two chapters
        extentTest.info("Two chapters namely ONE, TWO are added");
        Thread.sleep(4000);

        Assert.assertTrue(viewChapterPage.isDisplayedchapter("ONE"),"chapter name: ONE is not Present");
        extentTest.pass("Chapter: ONE is present");
        Assert.assertTrue(viewChapterPage.isDisplayedchapter("TWO"),"chapter name: TWO is not Present");
        extentTest.pass("Chapter: TWO is present");

        extentTest.info("PreTest-Data for Ch:'TWO' all must be 20");       //  Testing the TestData (Ch:TWO --> 20)
        Assert.assertEquals(viewChapterPage.getMSPageCount("TWO"),"20","PreData modified; CHECK !!");
        Assert.assertEquals(viewChapterPage.getTablesCount("TWO"),"20","PreData modified; CHECK !!");
        Assert.assertEquals(viewChapterPage.getFiguresCount("TWO"),"20","PreData modified; CHECK !!");
        extentTest.pass("PreData for Ch:TWO for All datas are --> 20; Verified");


        HomePage homePage = new HomePage(driver);                               //  Navigating to edit book page - to edit ch..
        homePage.navigateToListOfBooksPage();

        listOfBooksPage.navigateToEDITBOOKPage(currentBookName);

        EditBookPage editBookPage = new EditBookPage(driver);
        editBookPage.increaseChapterByOne();                                    //  Chapter count is incremented by one
        editBookPage.pressUpdateBtn();
        extentTest.info("Chapter count is incremented by one");

        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);            //  Clicking EYE icon - view chapter page

        String fileLocation_2 = "src\\test\\resources\\ViewChapters\\";
        String fileName_2 = "Auto. Test 3 chapters.json";                       //  Getting 2nd JSON file - three chapters
        File file_2 = new File(fileLocation_2,fileName_2);

        String jsonFileAbsLocation_2 = fileLocation_2+fileName_2;               //  Validating the Neg. Data Should NOT be 20
        Map<String, HashMap<String, String>> chaptersData = getChapterDataFromJSON(jsonFileAbsLocation_2);
        assert chaptersData != null;
        HashMap<String, String> chapterData = chaptersData.get("TWO");
        Assert.assertNotEquals(chapterData.get("No. of MS Pages"),"20","Neg. TestData Shouldn't be 20");
        Assert.assertNotEquals(chapterData.get("No. of Tables"),"20","Neg. TestData Shouldn't be 20");
        Assert.assertNotEquals(chapterData.get("No. of Figures"),"20","Neg. TestData Shouldn't be 20");
        extentTest.info("Data for Ch:TWO is edited (not 20) in new JSON file., to check for Modification");


        viewChapterPage.runAutomation_JSON(file_2.getAbsolutePath());           //  Running AUTOMATION - 2nd time
        extentTest.pass("Ran AUTOMATION - for Three chapters");
        extentTest.info("Ch: THREE is newly added");                     //   Verifying third chapters


        Thread.sleep(4000);

        Assert.assertTrue(viewChapterPage.isDisplayedchapter("ONE"),"chapter name: ONE is not Present");
        Assert.assertTrue(viewChapterPage.isDisplayedchapter("TWO"),"chapter name: TWO is not Present");
        extentTest.pass("Chapters: ONE, TWO is present");
        Assert.assertTrue(viewChapterPage.isDisplayedchapter("THREE"),"newly added ch: THREE is not Present");
        extentTest.pass("Newly added Ch: THREE is also present");

        extentTest.info("Details for Ch: 'TWO' is modified and uploaded...");
        extentTest.info("Test: --> But The modification for existing chapter should not be considered");
        extentTest.info("Validating Datas for chapter: 'TWO'");

        String actMsPages = viewChapterPage.getMSPageCount("TWO");
        Assert.assertEquals(actMsPages, "20", "MS Page count should be 20");
        extentTest.pass("Still: MS Page count=20 verified");

        String actTableCount = viewChapterPage.getTablesCount("TWO");
        Assert.assertEquals(actTableCount, "20", "Tables count should be 20");
        extentTest.pass("Still: Tables count=20 verified");

        String actFigures = viewChapterPage.getFiguresCount("TWO");
        Assert.assertEquals(actFigures, "20", "Figures count should be 20");
        extentTest.pass("Still: Figures count=20 verified");

    }

    @Test(groups = {"automation","view chapters"},enabled = false)
    public void verifyAutomation_3() throws IOException, InterruptedException, InvalidFormatException {
        extentTest = extentReports.createTest("Check freshly added books have all chapters HIGH-LIGHTED")
                .assignAuthor("Anbu");

        Thread.sleep(2000);
        String filePath = "src/test/resources/ViewChapters/ViewChaptersTestData.xlsx";
        String sheetName = "Sheet4";                                            //  Adding fresh book with 2 chapters
        addSingleBook_Before_TestMethod(filePath, sheetName);

        extentTest.info("Book Added");

        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);          //  Clicking EYE icon - view chapter page
        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);
        extentTest.info("Navigating to view Chapters page");

        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);          //  Verifying the HIGHlight colour
        Assert.assertEquals(viewChapterPage.getChapterRowColour(1),bluecolor,"Colour Needs to be blue");
        Assert.assertEquals(viewChapterPage.getChapterRowColour(2),bluecolor,"Colour Needs to be blue");
        extentTest.pass("All the chapters are Highlighted");

        Assert.assertTrue(viewChapterPage.isDisplayedAlertForAutomation(),"Alert message NOT displayed");
        extentTest.pass("Alert Message for Automation is displayed");
    }

    @Test(groups = {"automation","view chapters"},enabled = false)
    public void verifyAutomation_4() throws IOException, InterruptedException, InvalidFormatException {
        extentTest = extentReports.createTest("Check manually editing Removes highlight for the Chapter.")
                .assignAuthor("Anbu");

        Thread.sleep(2000);
        String filePath = "src/test/resources/ViewChapters/ViewChaptersTestData.xlsx";
        String sheetName = "Sheet4";                                            //  Adding fresh book with 2 chapters
        addSingleBook_Before_TestMethod(filePath, sheetName);

        extentTest.info("Book Added");


        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);          //  Clicking EYE icon - view chapter page
        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);
        extentTest.info("Navigating to view Chapters page");

        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);          //  Verifying the HIGHlight colour

        viewChapterPage.editMSPageCountAndConfirm(1,"300");
        //viewChapterPage.clickConfirmEditButton();
        Thread.sleep(2000);
        Assert.assertEquals(viewChapterPage.getChapterRowColour(1),nullcolor,"Highlight is not removed");
        extentTest.pass("Edited chapter has highlight removed");

        Assert.assertEquals(viewChapterPage.getChapterRowColour(2),bluecolor,"Colour Needs to be blue");
        extentTest.pass("Non-edited chapters are still Highlighted");
    }

    @Test(groups = {"automation","view chapters"},enabled = false)
    public void verifyAutomation_5() throws IOException, InterruptedException, InvalidFormatException {
        extentTest = extentReports.createTest("Check, Even if All chapters are edited manually, Still Prompt for AUTOMATION exists")
                .assignAuthor("Anbu");

        Thread.sleep(2000);
        String filePath = "src/test/resources/ViewChapters/ViewChaptersTestData.xlsx";
        String sheetName = "Sheet4";                                            //  Adding fresh book with 2 chapters
        addSingleBook_Before_TestMethod(filePath, sheetName);

        extentTest.info("Book Added");


        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);          //  Clicking EYE icon - view chapter page
        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);
        extentTest.info("Navigating to view Chapters page");

        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);          //  Verifying the HIGHlight colour after editing

        viewChapterPage.editMSPageCountAndConfirm(1,"300");
        Thread.sleep(2000);
        Assert.assertEquals(viewChapterPage.getChapterRowColour(1),nullcolor,"Highlight is not removed");
        Thread.sleep(2000);
        viewChapterPage.editMSPageCountAndConfirm(2,"300");
        Thread.sleep(2000);
        Assert.assertEquals(viewChapterPage.getChapterRowColour(2),nullcolor,"Highlight is not removed");
        extentTest.pass("All chapters has highlight removed");

        Assert.assertTrue(viewChapterPage.isDisplayedAlertForAutomation(),"Alert message NOT displayed");
        extentTest.pass("Alert Message for Automation is displayed");    // Prompt for Automation displayed

    }

    @Test(groups = {"automation","view chapters"},enabled = true)
    public void verifyAutomation_6() throws IOException, InterruptedException, InvalidFormatException {
        extentTest = extentReports.createTest("AUTOMATION prompt and AUTOMATION btn disappears after Running AUTOMATION correctly")
                .assignAuthor("Anbu");

        Thread.sleep(2000);
        String filePath = "src/test/resources/ViewChapters/ViewChaptersTestData.xlsx";
        String sheetName = "Sheet4";                                            //  Adding fresh book with 2 chapters
        addSingleBook_Before_TestMethod(filePath, sheetName);

        extentTest.info("Book Added for 2 chapters");


        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);          //  Clicking EYE icon - view chapter page
        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);
        extentTest.info("Navigating to view Chapters page");

        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);          //  Verifying the HIGHlight colour after editing

        String fileLocation_1 = "src\\test\\resources\\ViewChapters\\";         //  Getting 1st JSON file - two chapters
        String fileName_1 = "Auto. Test 2 chapters.json";
        File file_1 = new File(fileLocation_1,fileName_1);
        extentTest.info("Running automation for exact count(2) of chapters");

        viewChapterPage.runAutomation_JSON(file_1.getAbsolutePath());           //  running AUTOMATION - 2 Ch's


        Thread.sleep(3000);
        Assert.assertEquals(viewChapterPage.getChapterRowColour(1),nullcolor,"Highlight is not removed");
        Assert.assertEquals(viewChapterPage.getChapterRowColour(2),nullcolor,"Highlight is not removed");
        extentTest.pass("All chapters has highlight removed");

        Assert.assertFalse(viewChapterPage.isDisplayedAlertForAutomation(),"Alert message is still displaying !!");
        extentTest.pass("Alert Message for Automation not displayed");    // Prompt for Automation displayed

        Assert.assertFalse(viewChapterPage.isAutomationButtonDisplayed(),"Automation BTN is still displayed !!");
        extentTest.pass("Automation Button also not displayed..");

    }

    @Test(groups = {"automation","view chapters"},dependsOnMethods = {"verifyAutomation_6"},enabled = true)
    public void verifyAutomation_7() throws InterruptedException {
        extentTest = extentReports.createTest("Increasing the CH.Count will prompt for AUTOMATION even though Automation is Ran before.")
                .assignAuthor("Anbu");

        Thread.sleep(2000);                                               //    Book is already added frm Dependent method

        extentTest.info("Book Added from Previous method will be used");
        extentTest.info("Book name is: "+currentBookName);

        HomePage homePage = new HomePage(driver);                               //  Starting TEST from  Homepage
        homePage.navigateToListOfBooksPage();

        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);          //  Validate Ch. Count = 2 before starting Test
        Assert.assertEquals(listOfBooksPage.getChapterCount(currentBookName), "2", "Ch. Count must be only 2 for this Test");
        extentTest.info("Current Ch. Count = 2");

        listOfBooksPage.navigateToEDITBOOKPage(currentBookName);                //  Proceed to EditBook Page to edit Ch.

        EditBookPage editBookPage = new EditBookPage(driver);
        editBookPage.increaseChapterByOne();                                    //  Increasing Ch.Count from 2 --> 3
        editBookPage.pressUpdateBtn();
        extentTest.info("Increasing the Chapter Count");

        Thread.sleep(3000);                                               //  Validate Ch. Count = 3  now, before continuing Test

        Assert.assertEquals(listOfBooksPage.getChapterCount(currentBookName), "3", "Ch. Count should be 3 now");
        extentTest.info("Increased the Ch. Count from 2 to 3");

        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);            //  Clicking EYE icon - view chapter page
        extentTest.info("Navigating to view Chapters page");

        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);
        Thread.sleep(3000);                                               //    Checking Highlights for respective chapters

        Assert.assertEquals(viewChapterPage.getChapterRowColour(1),nullcolor,"Highlight should be removed");
        Assert.assertEquals(viewChapterPage.getChapterRowColour(2),nullcolor,"Highlight should be removed");
        extentTest.pass("First '2' chapters has highlight removed");
        Assert.assertEquals(viewChapterPage.getChapterRowColour(3),bluecolor,"Chapter should be highlighted");
        extentTest.pass("Newly added chapter is highlighted");

        Assert.assertTrue(viewChapterPage.isDisplayedAlertForAutomation(),"Alert message is not displaying");
        extentTest.pass("Alert Message for Automation is displayed");    // Prompt for Automation displayed

        Assert.assertTrue(viewChapterPage.isAutomationButtonDisplayed(),"Automation BTN is not displayed !!");
        extentTest.pass("Automation Button also displayed..");

    }

    @Test(groups = {"automation","view chapters"},enabled = false)
    public void verifyAutomation_8() throws IOException, InterruptedException, InvalidFormatException {
        extentTest = extentReports.createTest("verify running AUTOMATION for lesser CH.Count (json count < current count) prompts ERROR message")
                .assignAuthor("Anbu");

        Thread.sleep(2000);
        String filePath = "src/test/resources/ViewChapters/ViewChaptersTestData.xlsx";
        String sheetName = "Sheet4";                                            //  Adding fresh book with 2 chapters
        addSingleBook_Before_TestMethod(filePath, sheetName);

        extentTest.info("Book Added with 2 chapters");


        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);          //  Clicking EYE icon - view chapter page
        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);
        extentTest.info("Navigating to view Chapters page");

        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);

        String fileLocation_1 = "src\\test\\resources\\ViewChapters\\";         //  Getting 1st JSON file - one chapters
        String fileName_1 = "Auto. Test 1 chapters.json";
        File file_1 = new File(fileLocation_1,fileName_1);
        extentTest.info("Running automation with lesser count(1) than current Ch.Count(2)");

        viewChapterPage.runAutomation_JSON_ChkErrorMsg(file_1.getAbsolutePath());           //  running AUTOMATION - 1 Ch
        extentTest.pass("running AUTOMATION lesser than current Ch.Count displays ERROR message");
        driver.get(driver.getCurrentUrl());

        Thread.sleep(3000);
        Assert.assertEquals(viewChapterPage.getChapterRowColour(1),bluecolor,"Highlight is removed");
        Assert.assertEquals(viewChapterPage.getChapterRowColour(2),bluecolor,"Highlight is removed");
        extentTest.pass("All chapters is still being as Original");

        Assert.assertTrue(viewChapterPage.isDisplayedAlertForAutomation(),"Alert message is not displaying !!");
        extentTest.pass("Alert Message for Automation is displayed");    // Prompt for Automation displayed

        Assert.assertTrue(viewChapterPage.isAutomationButtonDisplayed(),"Automation BTN is not displayed !!");
        extentTest.pass("Automation Button also displayed..");

    }

    @Test(groups = {"automation","view chapters"},enabled = false)
    public void verifyAutomation_9() throws IOException, InterruptedException, InvalidFormatException {
        extentTest = extentReports.createTest("verify running AUTOMATION for greater CH.Count (json count > current count) prompts ERROR message")
                .assignAuthor("Anbu");

        Thread.sleep(2000);
        String filePath = "src/test/resources/ViewChapters/ViewChaptersTestData.xlsx";
        String sheetName = "Sheet4";                                            //  Adding fresh book with 2 chapters
        addSingleBook_Before_TestMethod(filePath, sheetName);

        extentTest.info("Book Added with 2 chapters");


        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);          //  Clicking EYE icon - view chapter page
        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);
        extentTest.info("Navigating to view Chapters page");

        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);

        String fileLocation_1 = "src\\test\\resources\\ViewChapters\\";         //  Getting 1st JSON file - three chapters
        String fileName_1 = "Auto. Test 3 chapters.json";
        File file_1 = new File(fileLocation_1,fileName_1);
        extentTest.info("Running automation with greater count(3) than current Ch.Count(2)");

        viewChapterPage.runAutomation_JSON_ChkErrorMsg(file_1.getAbsolutePath());           //  running AUTOMATION - 3 Ch's
        extentTest.pass("running AUTOMATION greater than current Ch.Count displays ERROR message");
        driver.get(driver.getCurrentUrl());

        Thread.sleep(3000);
        Assert.assertEquals(viewChapterPage.getChapterRowColour(1),bluecolor,"Highlight is removed");
        Assert.assertEquals(viewChapterPage.getChapterRowColour(2),bluecolor,"Highlight is removed");
        extentTest.pass("All chapters is still being as Original");

        Assert.assertTrue(viewChapterPage.isDisplayedAlertForAutomation(),"Alert message is not displaying !!");
        extentTest.pass("Alert Message for Automation is displayed");    // Prompt for Automation displayed

        Assert.assertTrue(viewChapterPage.isAutomationButtonDisplayed(),"Automation BTN is not displayed !!");
        extentTest.pass("Automation Button also displayed..");

    }

}
