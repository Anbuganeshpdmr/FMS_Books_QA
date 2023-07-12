package PageObjects;

import BasePackage.BaseClassFMS;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ListOfPublishersPage extends BaseClassFMS {
    @FindBy(id = "add_publisher")
    WebElement addPublisherBtn;

    @FindBy(xpath = "//input[@type='search']")
    WebElement publisherSearchBox;

    String NumberOfBooks_Index = "3";
    public ListOfPublishersPage(){
        PageFactory.initElements(driver,this);
    }

    public void navigateToAddPublisherPage() throws InterruptedException {

        addPublisherBtn.click();
        Thread.sleep(2000);
    }

    public boolean verifyPublisher(String publisherNameToCheck) throws InterruptedException {

        Thread.sleep(2000);
        publisherSearchBox.clear();
        publisherSearchBox.sendKeys(publisherNameToCheck);
        Thread.sleep(4000);

        try
        {
            Thread.sleep(2500);
            driver.findElement(By.xpath("//tbody/tr/td[text()='"+publisherNameToCheck+"']"));
            Thread.sleep(2500);
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }

    }

    public String getBookcount(String publisherName) throws InterruptedException {
        Thread.sleep(2000);
        publisherSearchBox.clear();
        publisherSearchBox.sendKeys(publisherName);
        Thread.sleep(4000);

        try
        {
            Thread.sleep(2500);
            return driver.findElement(By.xpath("//*[text()='"+publisherName+"']/../td["+NumberOfBooks_Index+"]"))
                    .getText();

        }
        catch (NoSuchElementException e)
        {
            return "nothing";
        }
    }

    public boolean isEditOptionAvailable(String publisherName) throws InterruptedException {
        Thread.sleep(2000);
        publisherSearchBox.clear();
        publisherSearchBox.sendKeys(publisherName);
        Thread.sleep(4000);

        try{
            return driver.findElement(
                    By.xpath("//*[text()='"+publisherName+"']/..//i[@class='fa fa-edit edit_data']"))
                    .isDisplayed();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    public boolean isDeleteOptionAvailable(String publisherName) throws InterruptedException {
        Thread.sleep(2000);
        publisherSearchBox.clear();
        publisherSearchBox.sendKeys(publisherName);
        Thread.sleep(4000);

        try{
            return driver.findElement(
                            By.xpath("//*[text()='"+publisherName+"']/..//i[@class='fa fa-trash delete_data']"))
                    .isDisplayed();
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    public void checkForAddPublisherPageNavigation() throws InterruptedException {
        Thread.sleep(1000);
        if(!(driver.findElement(By.id("publisher_name")).isDisplayed())){
            navigateToAddPublisherPage();
        }
    }

    public String getRandomPublisherName(){
        return driver.findElement(By.xpath("//*[@id='table_publisher']//tr[1]/td[2]")).getText();
    }

    public void navigateToEditPublisherPage(String publisherName) throws InterruptedException {
        Thread.sleep(2000);
        publisherSearchBox.clear();
        publisherSearchBox.sendKeys(publisherName);
        Thread.sleep(2000);

        driver.findElement(
                By.xpath("//*[text()='"+publisherName+"']/..//i[@class='fa fa-edit edit_data']"))
                .click();
    }

    private void attempDeletePublisher(String publisherName) throws InterruptedException {
        Thread.sleep(2000);
        publisherSearchBox.clear();
        publisherSearchBox.sendKeys(publisherName);
        Thread.sleep(2000);

        driver.findElement(
                        By.xpath("//*[text()='"+publisherName+"']/..//i[@class='fa fa-trash delete_data']"))
                .click();


    }

    public void confirmDeletePublisher(String publisherName) throws InterruptedException {

        attempDeletePublisher(publisherName);

    }

}
