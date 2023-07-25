package Books;

import BasicUtils.BasicUlitilityMethods;
import BasicUtils.ExcelReader;
import PageObjects.AddBookPage;
import PageObjects.HomePage;
import PageObjects.ListOfBooksPage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static BasePackage.BaseClassFMS.driver;
import static BasicUtils.BasicUlitilityMethods.*;
import static BasicUtils.BasicUlitilityMethods.getDate;
import static BasicUtils.TestngAnnotatedMethods.extentReports;
import static BasicUtils.TestngAnnotatedMethods.extentTest;

public class ListOfBooksTest {

    String bookName;

    @BeforeClass(groups = {"tooltip"})
    public void preConditions_1() throws InterruptedException, IOException, InvalidFormatException {

        String uploadFileLocation = "src\\test\\resources\\Books_test files\\";
        String uploadFileName = "fresh File.txt";

        String bookDataFilePath = "src/test/resources/Books/ListOfBooksTestData.xlsx";
        String bookDataSheetName = "BefClass_AddBook";

        Thread.sleep(2000);

        ExcelReader excelReader = new ExcelReader();
        List<Map<String, String>> bookdata =  excelReader.getData(bookDataFilePath, bookDataSheetName);
        Map<String, String> book1 = bookdata.get(0);

        String receivedDate = getDate(Integer.parseInt(book1.get("Received Date").trim()));
        int publisherIndex = Integer.parseInt(book1.get("Publisher").trim());
        bookName = book1.get("Book name") + getTimeStamp();
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
        File file = new File(uploadFileLocation,uploadFileName);
        addBookPage.addFileForNewBook(file.getAbsolutePath());
        Thread.sleep(2000);
        addBookPage.pressSubmitButton();
    }

    @DataProvider(name = "provide toolTip Data")
    public Object[][] provideDataFromExcel() throws IOException {
        String toolTipMsgFilePath = "src/test/resources/Books/ListOfBooksTestData.xlsx";
        String toolTipMsgSheetName = "Sheet1";
        return BasicUlitilityMethods.readExcelData(toolTipMsgFilePath,toolTipMsgSheetName);
    }

    @Test(dataProvider = "provide toolTip Data",groups = {"tooltip"})
    public void verifyToolTipText(String elementName,String expectedToolTipText) throws InterruptedException{

        extentTest = extentReports
                .createTest("Verify Tool tip message for: "+elementName.toUpperCase())
                .assignAuthor("Anbu");
        extentTest.info("Book added with file");

        Thread.sleep(2000);

        HomePage homePage = new HomePage(driver);
        homePage.navigateToListOfBooksPage();

        Thread.sleep(3000);

        ListOfBooksPage listOfBooksPage = new ListOfBooksPage(driver);
        String actualToolTipText = listOfBooksPage.getToolTipText(bookName, elementName);

        if(!(actualToolTipText.equals(""))){
            Assert.assertEquals(actualToolTipText, expectedToolTipText, "Tooltip message doesn't matches");
            extentTest.pass("Tool tip for "+elementName.toUpperCase()+" is verified");
        }else{
            extentTest.fail("Returning Null Character for "+elementName.toUpperCase());
        }

    }





}
