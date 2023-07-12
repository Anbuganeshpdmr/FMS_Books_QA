package PageObjects;

import BasePackage.BaseClassFMS;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AssignedList extends BaseClassFMS {

    @FindBy(xpath = "//input[@type='search']")
    WebElement searchBox;

    public AssignedList(){
        PageFactory.initElements(driver,this);
    }

    public void searchChapter(String chapterName) throws InterruptedException {
        searchBox.sendKeys(chapterName+ Keys.ENTER);
        Thread.sleep(2000);
    }
}
