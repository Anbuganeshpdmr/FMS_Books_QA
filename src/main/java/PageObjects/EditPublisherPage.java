package PageObjects;

import BasePackage.BaseClassFMS;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditPublisherPage extends BaseClassFMS {

    @FindBy(id="publisher_name")
    WebElement publisherNameBox;

    @FindBy(id="insert_publisher")
    WebElement updatePublisherBtn;

    @FindBy(xpath = "//*[@id='insert_publisher']/../*[text()='Close']")
    WebElement cancelBtn;

    public EditPublisherPage(){
        PageFactory.initElements(driver, this);
    }

    public boolean isErrorMessageDisplayed() throws InterruptedException {
        Thread.sleep(1000);
        try{
            return driver.findElement(
                    By.xpath("//*[text()='Publisher name already exist!']")).isDisplayed();
        }
        catch (NoSuchElementException e){
            return false;
        }
    }

    public void enterPublisherName(String publisherName){
        publisherNameBox.clear();
        publisherNameBox.sendKeys(publisherName);
    }

    public void pressUpdateButton(){
        updatePublisherBtn.click();
    }

    public void pressCancelButton(){
        cancelBtn.click();
    }

}
