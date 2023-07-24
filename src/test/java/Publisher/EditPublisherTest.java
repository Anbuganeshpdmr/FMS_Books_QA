package Publisher;

import BasePackage.BaseClassFMS;
import PageObjects.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static BasicUtils.BasicUlitilityMethods.getTimeStamp;
import static BasicUtils.TestngAnnotatedMethods.extentReports;
import static BasicUtils.TestngAnnotatedMethods.extentTest;

public class EditPublisherTest extends BaseClassFMS {

    @Test(groups = {"smoke","regression"}, enabled = true)
    public void editPublisherTest_1() throws InterruptedException {
        extentTest = extentReports
                .createTest("Edit new publishername and verify other details are intact")
                .assignAuthor("Anbu");

        HomePage homePage = new HomePage(driver);
        homePage.navigateToListOfPublishersPage();

        ListOfPublishersPage listOfPublishersPage = new ListOfPublishersPage(driver);
        listOfPublishersPage.navigateToAddPublisherPage();

        String publisherName_1 = "EP1_"+getTimeStamp();
        AddPublisherPage addPublisherPage = new AddPublisherPage();
        addPublisherPage.addNewPublisherAndClick(publisherName_1);

        extentTest.info("New publisher added");

        listOfPublishersPage.navigateToEditPublisherPage(publisherName_1);
        String publisherName_2 = "EP2_"+getTimeStamp();

        EditPublisherPage editPublisherPage = new EditPublisherPage();

        Thread.sleep(2000);
        editPublisherPage.enterPublisherName(publisherName_2);
        editPublisherPage.pressUpdateButton();
        extentTest.info("Publisher name modified");

        Assert.assertEquals(listOfPublishersPage.getBookcount(publisherName_2),"0");
        extentTest.pass("BookCount is still ZERO");

        Assert.assertTrue(listOfPublishersPage.isEditOptionAvailable(publisherName_2));
        extentTest.pass("Edit Icon is available");

        Assert.assertTrue(listOfPublishersPage.isDeleteOptionAvailable(publisherName_2));
        extentTest.pass("Delete icon is available");
    }



}
