package BasicUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestListener;
import org.testng.ITestResult;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static BasePackage.BaseClassFMS.driver;
import static BasePackage.BaseClassFMS.reloadDashboard;
import static BasicUtils.BasicUlitilityMethods.getTimeStampNumber;
import static BasicUtils.TestngAnnotatedMethods.extentTest;


public class CustomTestListener implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {

        extentTest.pass(result.getName() + " is passed");
        extentTest.assignCategory(result.getMethod().getGroups());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String screenShotName = getTimeStampNumber();

        TakesScreenshot takesScreenshot = ((TakesScreenshot) driver);
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("Screenshots/"+screenShotName);

        try {
            FileHandler.copy(sourceFile,destFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        extentTest.addScreenCaptureFromPath(destFile.getPath());
        extentTest.fail(result.getThrowable());
        extentTest.assignCategory(result.getMethod().getGroups());

        reloadDashboard();
    }

    public void captureScreenshot(String fileName) throws IOException {

        String screenShotName = getTimeStampNumber();

        TakesScreenshot takesScreenshot = ((TakesScreenshot) driver);
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        //File destFile = new File("./Screenshots/"+ screenshotsSubFolderName+"/"+fileName);
        File destFile = new File("Screenshots/"+screenShotName);
        FileHandler.copy(sourceFile,destFile);
        //return destFile.getAbsolutePath();
    }


}
