package PageObjects;

import BasePackage.BaseClassFMS;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class QueryCheckPage extends BaseClassFMS {

    @FindBy(xpath = "//*[@id='boostrapModal-query']//button[text()='Close']")
    WebElement closeBtn;

    public QueryCheckPage() throws InterruptedException {
        Thread.sleep(500);
        PageFactory.initElements(driver,this);
    }

    public void checkTheQuery() throws InterruptedException {
        Thread.sleep(500);
        driver.findElement(
                By.xpath("//div[@id='query_modal_data']//div[@class='form-group row']//label")).click();

    }

    public void checkTheQueries() throws InterruptedException {
        Thread.sleep(2500);
        List<WebElement> queries = driver.findElements(
                By.xpath("//div[@id='query_modal_data']/div"));

        for(int i=0; i< queries.size();i++){

            Thread.sleep(2000);
            int webIndex = i+1;
            WebElement radioBtn = driver.findElement(By.xpath("//div[@id='query_modal_data']/div["+webIndex+"]//label/.."));
            Thread.sleep(2000);

            String radioBtnCSSValue = radioBtn.getCssValue("display");

            Thread.sleep(1000);
            if(radioBtnCSSValue.contains("block"))
            {
                Thread.sleep(1000);
                driver.findElement(By.xpath("//div[@id='query_modal_data']/div["+webIndex+"]//label")).click();
                try
                {
                    Thread.sleep(1000);
                    WebElement successToasterMsg = driver.findElement(
                            By.xpath("//div[text()='Query checked successfully']"));
                    Assert.assertTrue(successToasterMsg.isDisplayed());
                }
                catch (NoSuchElementException e)
                {
                    Assert.fail("Query SUCCESS Message not displayed");
                }
            }
            else
            {
                System.out.println("Query Already checked");
            }
        }
    }

    public boolean isSuccessMessageDisplayed() throws InterruptedException {
        Thread.sleep(500);
        try{
            WebElement successToasterMsg = driver.findElement(
                    By.xpath("//div[text()='Query checked successfully']"));
            return successToasterMsg.isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public void navigateBack(){
        closeBtn.click();
    }
}
