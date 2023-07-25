package PageObjects;

import BasePackage.BaseClassFMS;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage{

    @FindBy(linkText = "List of Publisher")
    WebElement listOfPublisher;

    @FindBy(linkText = "Stages")
    WebElement stages;

    @FindBy(linkText = "List of Books")
    WebElement listOfBooksPage;

    WebDriver driver;

    /*@FindBy(linkText = "List of Books")
    WebElement assignedListPage;*/

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void navigateToListOfPublishersPage(){
        listOfPublisher.click();
    }

    public void navigateToFreshStage(){
        stages.click();
        driver.findElement(By.linkText("Fresh")).click();
    }

    public void navigateToListOfBooksPage(){
        listOfBooksPage.click();
    }

    public void navigateToAssignedListPage(){

        WebElement assignedListPage = driver.findElement(By.xpath("//*[text()='Assigned List']"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "arguments[0].scrollIntoView(true);", assignedListPage);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(assignedListPage));

        //WebElement assignedListPage = driver.findElement(By.xpath("//*[text()='Assigned List']"));

        assignedListPage.click();
    }

    public void freshPaginationSL(){
        driver.get("http://pdmrindia.in/fms_pub_testing/fms_books/stock_count_list/Fresh/FPG/Book");
    }
    public void authorCorrectionPaginationSL(){
        driver.get("http://pdmrindia.in/fms_pub_testing/fms_books/stock_count_list/AC/ACPG/Book");
    }
    public void printWebPaginationSL(){
        driver.get("http://pdmrindia.in/fms_pub_testing/fms_books/stock_count_list/Print/PPG/Book");
    }
    public void wordConversionPaginationSL(){
        driver.get("http://pdmrindia.in/fms_pub_testing/fms_books/stock_count_list/Fresh/WPG/Book");
    }
    public void freshGraphicsSL(){
        driver.get("http://pdmrindia.in/fms_pub_testing/fms_books/stock_count_list/Fresh/Graphics/Book");
    }
    public void fresh_QC_SL(){
        driver.get("http://pdmrindia.in/fms_pub_testing/fms_books/stock_count_list/Fresh/FQC/Book");
    }

    public void returnToDashboard(){
        driver.get("http://pdmrindia.in/fms_pub_testing/fms_books/main_dashboard");
    }



}
