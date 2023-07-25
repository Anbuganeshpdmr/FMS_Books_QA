package BasicUtils;

import BasePackage.BaseClassFMS;
import PageObjects.LoginPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;


public class TestngAnnotatedMethods extends BaseClassFMS {

    public static ExtentReports extentReports;
    public static ExtentSparkReporter sparkReporter;
    public static ExtentTest extentTest;

    @BeforeSuite
    public void initializeTest() throws IOException, InterruptedException {
        launchApplication();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginToApplication(prop1.getProperty("PaginationTLId"),prop1.getProperty("PaginationTLId"));

        extentReports = new ExtentReports();
        sparkReporter = new ExtentSparkReporter("Generated HTML Report.html");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setReportName("Application Test Report"); //Displayed inside Document
        sparkReporter.config().setDocumentTitle("Application Test Report"); //Displayed in Browser tab
        extentReports.attachReporter(sparkReporter);
    }

    @AfterSuite
    public void closeSuite(){
        extentReports.flush();
    }




}
