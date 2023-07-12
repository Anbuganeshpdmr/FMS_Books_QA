package PageObjects;

import BasePackage.BaseClassFMS;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
/*import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;*/

public class AddPublisherPage extends BaseClassFMS {

    @FindBy(id="publisher_name")
    WebElement publisherNameBox;

    @FindBy(id="insert_publisher")
    WebElement acceptPublisherBtn;

    @FindBy(id="publisher_name_error")
    WebElement errorMessage;

    public AddPublisherPage(){
        PageFactory.initElements(driver,this);
    }

    public void addNewPublisherAndClick(String newPublisherName) throws InterruptedException {

        publisherNameBox.clear();
        publisherNameBox.sendKeys(newPublisherName);
        acceptPublisherBtn.click();
    }

    public void addPublisherButNavigateBack(String newPublisherName) throws InterruptedException {
        Thread.sleep(1500);

        publisherNameBox.clear();
        publisherNameBox.sendKeys(newPublisherName);

        WebElement cancelBtn = driver.findElement(By.xpath("//*[@id='insert_publisher']/../*[text()='Close']"));
        cancelBtn.click();

    }

    public boolean checkErrorMessageDisplayed() throws InterruptedException {
        Thread.sleep(1000);
        try{
            return driver.findElement(
                    By.xpath("//*[text()='Publisher name already exist!']")).isDisplayed();
        }
        catch (NoSuchElementException e){
            return false;
        }
    }

    public boolean isPublisherAddedSuccessfully(String publisherName) throws InterruptedException {
        Thread.sleep(1000);
        try{
            return driver.findElement(
                    By.xpath("//*[text()='Publisher added successfully']")).isDisplayed();
        }
        catch (NoSuchElementException e){
            return false;
        }

    }


}
