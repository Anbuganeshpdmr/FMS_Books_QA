package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditBookPage {

    @FindBy(id = "no_of_chapter")
    WebElement noOfChapterBox;

    @FindBy(id = "book_submit")
    WebElement updateBtn;

    WebDriver driver;

    public EditBookPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void increaseChapterByOne() throws InterruptedException {
        Thread.sleep(2000);
        String oldChCount = noOfChapterBox.getAttribute("value");
        int old_ChCount = Integer.parseInt(oldChCount);
        int upd_ChCount = old_ChCount + 1;
        noOfChapterBox.clear();
        noOfChapterBox.sendKeys(String.valueOf(upd_ChCount));
    }

    public void pressUpdateBtn(){
        updateBtn.click();
    }
}
