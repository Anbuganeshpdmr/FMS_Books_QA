package ViewChapters;

import BasePackage.BaseClassFMS;
import BasicUtils.BasicUlitilityMethods;
import BasicUtils.ExcelReader;
import PageObjects.AddBookPage;
import PageObjects.HomePage;
import PageObjects.ListOfBooksPage;
import PageObjects.ViewChapterPage;
import com.aventstack.extentreports.ExtentTest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static BasicUtils.BasicUlitilityMethods.*;
import static BasicUtils.BasicUlitilityMethods.getDate;
import static BasicUtils.TestngAnnotatedMethods.extentReports;
import static BasicUtils.TestngAnnotatedMethods.extentTest;

public class ViewChaptersPageTest extends BaseClassFMS {

    String currentBookName;

    String graphicsIconRedColor = "rgba(255, 0, 0, 1)";

    String graphicsIconBlueColor = "rgba(0, 0, 255, 1)";

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

    @Test(groups = {"view chapters","drag and drop"},enabled = false)
    public void verifyDragAndDrop_1() throws InterruptedException, IOException, InvalidFormatException {
        extentTest = extentReports.createTest("Check DRAG AND DROP won't take effect, until UPDATE REORDER is pressed")
                .assignAuthor("Anbu");

        //  Adding fresh book
        Thread.sleep(2000);
        String filePath = "src/test/resources/ViewChapters/ViewChaptersTestData.xlsx";
        String sheetName = "Sheet2";
        addSingleBook_Before_TestMethod(filePath, sheetName);

        extentTest.info("Book Added");

        Thread.sleep(2000);
        //  Clicking EYE icon

        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);
        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);
        extentTest.info("clicking EYE icon");
        extentTest.info("Navigating to view Chapters page");

        //driver.get("http://pdmrindia.in/fms_pub_testing/fms_books/view_book_chapter_list/22");

        String fileLocation = "src\\test\\resources\\ViewChapters\\";
        String fileName = "datas for 4 chapters.json";

        File file = new File(fileLocation,fileName);
        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);

        //  Running AUTOMATION - uploading JSON file.
        viewChapterPage.runAutomation_JSON(file.getAbsolutePath());
        extentTest.pass("Successfully ran AUTOMATION - uploaded JSON file");
        Thread.sleep(2000);

        //  perform Drag and drop
        int jumpDist = viewChapterPage.getJumpDist();

        WebElement rowCell1 = driver.findElement(By.xpath("//td[text()='1']"));
        WebElement rowCell2 = driver.findElement(By.xpath("//td[text()='2']"));
        WebElement rowCell3 = driver.findElement(By.xpath("//td[text()='3']"));
        WebElement rowCell4 = driver.findElement(By.xpath("//td[text()='4']"));

        String[] chapterNames_1 = viewChapterPage.getallChaptersNames();    //retrieving chapter names - 1st time
        String exp_DD_Chapter1 = chapterNames_1[0];                         //CH. @ 1st row
        extentTest.info("chapter name at 1st row: "+exp_DD_Chapter1.toUpperCase());
        extentTest.info("Perform Drag and drop");

        Actions ac1 = new Actions(driver);
        ac1.dragAndDropBy(rowCell1,0,(jumpDist*3)).perform();        // **  Dragging 1st cell to 3rd cell
        Thread.sleep(3000);

        String[] chapterNames_2 = viewChapterPage.getallChaptersNames();    //retrieving chapter names - 2nd time
        String act_DD_Chapter1 = chapterNames_2[2];                         //moved @ 3rd row

        extentTest.info("chapter name at 3rd row: "+act_DD_Chapter1.toUpperCase());

        Assert.assertEquals(act_DD_Chapter1,exp_DD_Chapter1,"Chapter name doesn't matches.");
        extentTest.pass("Chapter name "+act_DD_Chapter1.toUpperCase()+" is Drag and Dropped successfully");

        String exp_DD_Chapter2 = chapterNames_2[3];                         //CH. @ 4th row
        extentTest.info("chapter name at 4th row: "+exp_DD_Chapter2.toUpperCase());
        extentTest.info("Perform Drag and drop");

        ac1.dragAndDropBy(rowCell4,0,-(jumpDist*2)).perform();      //  **  Dragging 4rd cell to 2nd cell
        Thread.sleep(3000);

        String[] chapterNames_3 = viewChapterPage.getallChaptersNames();    //retrieving chapter names - 3rd time
        String act_DD_Chapter2 = chapterNames_3[1];                         //moved @ 2nd row

        extentTest.info("chapter name at 2nd row: "+act_DD_Chapter2.toUpperCase());

        Assert.assertEquals(act_DD_Chapter2,exp_DD_Chapter2,"Chapter name doesn't matches.");
        extentTest.pass("Chapter name "+act_DD_Chapter2.toUpperCase()+" is Drag and Dropped successfully");

        extentTest.info("Drag and Drop Done Locally.");
        extentTest.info("Refreshing the Page without UPDATING the Reorder");
        driver.get(driver.getCurrentUrl());
        extentTest.info("Checking current names with Initial set of names in Order");

        String[] chapterNames_4 = viewChapterPage.getallChaptersNames();    //  retrieving chapter names - after Refreshing page
        for (int i=0; i<chapterNames_4.length; i++)                         //  comparing 4th set against 1st set
        {
            Assert.assertEquals(chapterNames_4[i],chapterNames_1[i],"Chapter name Doesn't matches");
            extentTest.pass("Chapter: "+chapterNames_4[i]+" is in order");
        }
        extentTest.pass("Chapter names still in original place if REORDER is not UPDATED");

    }

    @Test(groups = {"view chapters","drag and drop"},enabled = false)
    public void verifyDragAndDrop_2() throws InterruptedException, IOException, InvalidFormatException {
        extentTest = extentReports.createTest("Check DRAG AND DROP takes effect correctly, when UPDATE REORDER is pressed")
                .assignAuthor("Anbu");

        //  Adding fresh book
        Thread.sleep(2000);
        String filePath = "src/test/resources/ViewChapters/ViewChaptersTestData.xlsx";
        String sheetName = "Sheet2";
        addSingleBook_Before_TestMethod(filePath, sheetName);

        extentTest.info("Book Added");

        Thread.sleep(2000);
        //  Clicking EYE icon

        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);
        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);
        extentTest.info("clicking EYE icon");
        extentTest.info("Navigating to view Chapters page");

        //driver.get("http://pdmrindia.in/fms_pub_testing/fms_books/view_book_chapter_list/22");

        String fileLocation = "src\\test\\resources\\ViewChapters\\";
        String fileName = "datas for 4 chapters.json";

        File file = new File(fileLocation,fileName);
        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);

        //  Running AUTOMATION - uploading JSON file.
        viewChapterPage.runAutomation_JSON(file.getAbsolutePath());
        extentTest.pass("Successfully ran AUTOMATION - uploaded JSON file");
        Thread.sleep(2000);

        //  perform Drag and drop
        int jumpDist = viewChapterPage.getJumpDist();

        WebElement rowCell1 = driver.findElement(By.xpath("//td[text()='1']"));
        WebElement rowCell2 = driver.findElement(By.xpath("//td[text()='2']"));
        WebElement rowCell3 = driver.findElement(By.xpath("//td[text()='3']"));
        WebElement rowCell4 = driver.findElement(By.xpath("//td[text()='4']"));

        String[] chapterNames_1 = viewChapterPage.getallChaptersNames();    //retrieving chapter names - 1st time
        String exp_DD_Chapter1 = chapterNames_1[0];                         //CH. @ 1st row
        extentTest.info("chapter name at 1st row: "+exp_DD_Chapter1.toUpperCase());
        extentTest.info("Perform Drag and drop");

        Actions ac1 = new Actions(driver);
        ac1.dragAndDropBy(rowCell1,0,(jumpDist*3)).perform();        // **  Dragging 1st cell to 3rd cell
        Thread.sleep(3000);

        String[] chapterNames_2 = viewChapterPage.getallChaptersNames();    //retrieving chapter names - 2nd time
        String act_DD_Chapter1 = chapterNames_2[2];                         //moved @ 3rd row

        extentTest.info("chapter name at 3rd row: "+act_DD_Chapter1.toUpperCase());

        Assert.assertEquals(act_DD_Chapter1,exp_DD_Chapter1,"Chapter name doesn't matches.");
        extentTest.pass("Chapter name "+act_DD_Chapter1.toUpperCase()+" is Drag and Dropped successfully");

        String exp_DD_Chapter2 = chapterNames_2[3];                         //CH. @ 4th row
        extentTest.info("chapter name at 4th row: "+exp_DD_Chapter2.toUpperCase());
        extentTest.info("Perform Drag and drop");

        ac1.dragAndDropBy(rowCell4,0,-(jumpDist*2)).perform();      //  **  Dragging 4rd cell to 2nd cell
        Thread.sleep(3000);

        String[] chapterNames_3 = viewChapterPage.getallChaptersNames();    //retrieving chapter names - 3rd time
        String act_DD_Chapter2 = chapterNames_3[1];                         //moved @ 2nd row

        extentTest.info("chapter name at 2nd row: "+act_DD_Chapter2.toUpperCase());

        Assert.assertEquals(act_DD_Chapter2,exp_DD_Chapter2,"Chapter name doesn't matches.");
        extentTest.pass("Chapter name "+act_DD_Chapter2.toUpperCase()+" is Drag and Dropped successfully");

        extentTest.info("Drag and Drop Done Locally.");

        viewChapterPage.clickReorderAndOK();
        extentTest.info("UPDATING the Reorder");
        extentTest.info("Checking current names with recently changed set of names in Order");

        String[] chapterNames_4 = viewChapterPage.getallChaptersNames();    //  retrieving chapter names - after Refreshing page
        for (int i=0; i<chapterNames_4.length; i++)                         //  comparing 4th set against 3rd set
        {
            Assert.assertEquals(chapterNames_4[i],chapterNames_3[i],"Chapter name Doesn't matches");
            extentTest.pass("Chapter: "+chapterNames_4[i]+" is in order");
        }
        extentTest.pass("Chapter names are Re-ordered successfully");
    }

    @Test(groups = {"view chapters","drag and drop"},enabled = false)
    public void verifyDragAndDrop_3() throws InterruptedException, IOException, InvalidFormatException {
        extentTest = extentReports.createTest("Check DRAG AND DROP won't take effect, if updating Re-order is cancelled midway")
                .assignAuthor("Anbu");

        //  Adding fresh book
        Thread.sleep(2000);
        String filePath = "src/test/resources/ViewChapters/ViewChaptersTestData.xlsx";
        String sheetName = "Sheet2";
        addSingleBook_Before_TestMethod(filePath, sheetName);

        extentTest.info("Book Added");

        Thread.sleep(2000);
        //  Clicking EYE icon

        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);
        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);
        extentTest.info("clicking EYE icon");
        extentTest.info("Navigating to view Chapters page");

        //driver.get("http://pdmrindia.in/fms_pub_testing/fms_books/view_book_chapter_list/22");

        String fileLocation = "src\\test\\resources\\ViewChapters\\";
        String fileName = "datas for 4 chapters.json";

        File file = new File(fileLocation,fileName);
        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);

        //  Running AUTOMATION - uploading JSON file.
        viewChapterPage.runAutomation_JSON(file.getAbsolutePath());
        extentTest.pass("Successfully ran AUTOMATION - uploaded JSON file");
        Thread.sleep(2000);

        //  perform Drag and drop
        int jumpDist = viewChapterPage.getJumpDist();

        WebElement rowCell1 = driver.findElement(By.xpath("//td[text()='1']"));
        WebElement rowCell2 = driver.findElement(By.xpath("//td[text()='2']"));
        WebElement rowCell3 = driver.findElement(By.xpath("//td[text()='3']"));
        WebElement rowCell4 = driver.findElement(By.xpath("//td[text()='4']"));

        String[] chapterNames_1 = viewChapterPage.getallChaptersNames();    //retrieving chapter names - 1st time
        String exp_DD_Chapter1 = chapterNames_1[0];                         //CH. @ 1st row
        extentTest.info("chapter name at 1st row: "+exp_DD_Chapter1.toUpperCase());
        extentTest.info("Perform Drag and drop");

        Actions ac1 = new Actions(driver);
        ac1.dragAndDropBy(rowCell1,0,(jumpDist*3)).perform();        // **  Dragging 1st cell to 3rd cell
        Thread.sleep(3000);

        String[] chapterNames_2 = viewChapterPage.getallChaptersNames();    //retrieving chapter names - 2nd time
        String act_DD_Chapter1 = chapterNames_2[2];                         //moved @ 3rd row

        extentTest.info("chapter name at 3rd row: "+act_DD_Chapter1.toUpperCase());

        Assert.assertEquals(act_DD_Chapter1,exp_DD_Chapter1,"Chapter name doesn't matches.");
        extentTest.pass("Chapter name "+act_DD_Chapter1.toUpperCase()+" is Drag and Dropped successfully");

        String exp_DD_Chapter2 = chapterNames_2[3];                         //CH. @ 4th row
        extentTest.info("chapter name at 4th row: "+exp_DD_Chapter2.toUpperCase());
        extentTest.info("Perform Drag and drop");

        ac1.dragAndDropBy(rowCell4,0,-(jumpDist*2)).perform();      //  **  Dragging 4rd cell to 2nd cell
        Thread.sleep(3000);

        String[] chapterNames_3 = viewChapterPage.getallChaptersNames();    //retrieving chapter names - 3rd time
        String act_DD_Chapter2 = chapterNames_3[1];                         //moved @ 2nd row

        extentTest.info("chapter name at 2nd row: "+act_DD_Chapter2.toUpperCase());

        Assert.assertEquals(act_DD_Chapter2,exp_DD_Chapter2,"Chapter name doesn't matches.");
        extentTest.pass("Chapter name "+act_DD_Chapter2.toUpperCase()+" is Drag and Dropped successfully");

        extentTest.info("Drag and Drop Done Locally.");

        viewChapterPage.clickReorderButCancel();
        extentTest.info("Cancelling the UPDATING REORDER process midway");
        driver.get(driver.getCurrentUrl());
        extentTest.info("Checking current names with Initial set of names in Order");

        String[] chapterNames_4 = viewChapterPage.getallChaptersNames();    //  retrieving chapter names - after Refreshing page
        for (int i=0; i<chapterNames_4.length; i++)                         //  comparing 4th set against 1st set
        {
            Assert.assertEquals(chapterNames_4[i],chapterNames_1[i],"Chapter name Doesn't matches");
            extentTest.pass("Chapter: "+chapterNames_4[i]+" is in order");
        }
        extentTest.pass("Chapter names still in original place if REORDER is Rejected");

    }

    @Test(groups = {"view chapters","drag and drop"},enabled = false)
    public void verifyDragAndDrop_4() throws InterruptedException, IOException, InvalidFormatException {
        extentTest = extentReports.createTest("verify UPDATE REORDER button vanishes once processed successfully")
                .assignAuthor("Anbu");

        //  Adding fresh book
        Thread.sleep(2000);
        String filePath = "src/test/resources/ViewChapters/ViewChaptersTestData.xlsx";
        String sheetName = "Sheet2";
        addSingleBook_Before_TestMethod(filePath, sheetName);

        extentTest.info("Book Added");

        Thread.sleep(3000);
        //  Clicking EYE icon

        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);
        listOfBooksPage.navigateToVIEWCHAPTERSPage(currentBookName);
        extentTest.info("clicking EYE icon");
        extentTest.info("Navigating to view Chapters page");

        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);

        Assert.assertTrue(viewChapterPage.isUpdateReorderBtnDisplayed(),"Button NOT PRESENT");
        extentTest.pass("UPDATE REORDER button available for freshly added book");

        //driver.get("http://pdmrindia.in/fms_pub_testing/fms_books/view_book_chapter_list/22");

        String fileLocation = "src\\test\\resources\\ViewChapters\\";
        String fileName = "datas for 4 chapters.json";

        File file = new File(fileLocation,fileName);


        //  Running AUTOMATION - uploading JSON file.
        viewChapterPage.runAutomation_JSON(file.getAbsolutePath());
        extentTest.pass("Successfully ran AUTOMATION - uploaded JSON file");
        Thread.sleep(2000);

        Assert.assertTrue(viewChapterPage.isUpdateReorderBtnDisplayed(),"Button NOT PRESENT");
        extentTest.pass("UPDATE REORDER button available when chapter details are added");

        viewChapterPage.clickReorderAndOK();
        Thread.sleep(4000);
        Assert.assertFalse(viewChapterPage.isUpdateReorderBtnDisplayed(),"Button should NOT PRESENT");
        extentTest.pass("UPDATE REORDER button vanishes after Re-ordering is processed");

    }

    @Test(groups = {"icon colour"}, enabled = false)
    public void verifyColor() throws InterruptedException {
        driver.get("http://pdmrindia.in/fms_pub_testing/fms_books/view_book_chapter_list/21");
        Thread.sleep(2000);
        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);
        System.out.println(viewChapterPage.getGraphicsIconColor("ab"));
    }

    @DataProvider(name = "provide toolTip Data - view chapters")
    public Object[][] provideDataFromExcel() throws IOException {
        String toolTipMsgFilePath = "src/test/resources/ViewChapters/ViewChaptersTestData.xlsx";
        String toolTipMsgSheetName = "Sheet5";
        return BasicUlitilityMethods.readExcelData(toolTipMsgFilePath,toolTipMsgSheetName);
    }

    @Test(dataProvider = "provide toolTip Data - view chapters",groups = {"tooltip"},enabled = false)
    public void verifyToolTipText(String elementName,String expectedToolTipText) throws InterruptedException{

        extentTest = extentReports
                .createTest("Verify Tool tip message in chapters page for: "+elementName.toUpperCase())
                .assignAuthor("Anbu");
        extentTest.info("Book added with file");

        Thread.sleep(2000);

        driver.get("http://pdmrindia.in/fms_pub_testing/fms_books/view_book_chapter_list/184");

        ViewChapterPage viewChapterPage = new ViewChapterPage(driver);
        String actualToolTipText = viewChapterPage.getToolTipText(elementName);

        if(!(actualToolTipText.equals(""))){
            Assert.assertTrue(actualToolTipText.equalsIgnoreCase(expectedToolTipText),"Tooltip message doesn't matches");
            extentTest.pass("Tool tip for "+elementName.toUpperCase()+ " : "+actualToolTipText.toUpperCase()+"--> verified");
        }else{
            extentTest.fail("Returning Null Character for "+elementName.toUpperCase());
        }

    }

    @Test
    public void priiint(){
        extentTest = extentReports.createTest("Helllllo");
        System.out.println("Hello");
    }

}
