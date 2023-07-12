package BasicUtils;

import PageObjects.LoginPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

import static BasePackage.BaseClassFMS.launchApplication;

public class TestngAnnotatedMethods {

    public static ExtentReports extentReports;
    public static ExtentSparkReporter sparkReporter;
    public static ExtentTest extentTest;

    @BeforeSuite
    public void initializeTest() throws IOException, InterruptedException {
        launchApplication();
        LoginPage loginPage = new LoginPage();
        loginPage.loginAsPaginationTL();

        extentReports = new ExtentReports();
        sparkReporter = new ExtentSparkReporter("spark12.html");
        extentReports.attachReporter(sparkReporter);
    }

    @AfterSuite
    public void closeSuite(){
        extentReports.flush();
    }




}
