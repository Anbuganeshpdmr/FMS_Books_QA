package PageObjects;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class ViewChapterPage {
    String chaptername = "ab";
    String chaptername1="bc";
    int booknameIndex = 3;
    int listofBooksActionINdex = 11;

    int chapterNameIndex = 2;
    int serialNumberIndex = 1;
    int msPageIndex = 3;
    int noOfTableIndex = 4;
    int noOffigureIndex = 5;
    int noOfCurrentStageIndex = 6;
    int actionIndex = 10;




    @FindBy(xpath = "//button[text()='Back']")
    WebElement back;

    @FindBy(xpath = "//input[@type='search']")
    WebElement search;

    WebDriver driver;


    public ViewChapterPage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }


    /**
     * Click automation button
     */
    public void clickAutomationButton() {
        WebElement automationBtn = driver.findElement(By.xpath("//button[@id='page_automations_model_open']"));
        automationBtn.click();
    }

    public void runAutomation_JSON(String jsonFilePath) throws InterruptedException {
        Thread.sleep(2000);
        WebElement automationBtn = driver.findElement(By.xpath("//button[@id='page_automations_model_open']"));
        automationBtn.click();
        Thread.sleep(2000);
        driver.findElement(By.id("files")).sendKeys(jsonFilePath);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='btn_front_end_auto']")).click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.findElement(By.xpath("//*[text()='Thanks!']")).isDisplayed());
        driver.findElement(By.xpath("//*[text()='OK']")).click();
    }

    public void runAutomation_JSON_ChkErrorMsg(String jsonFilePath) throws InterruptedException {
        Thread.sleep(2000);
        WebElement automationBtn = driver.findElement(By.xpath("//button[@id='page_automations_model_open']"));
        automationBtn.click();
        Thread.sleep(2000);
        driver.findElement(By.id("files")).sendKeys(jsonFilePath);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='btn_front_end_auto']")).click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.findElement(By.xpath("//*[text()='Cancelled']")).isDisplayed(),"No Error message is Displayed!");
        driver.findElement(By.xpath("//*[text()='OK']")).click();
    }

    public boolean isAutomationButtonDisplayed() throws InterruptedException {
        try{
            Thread.sleep(2000);
            return driver.findElement(By.xpath("//button[@id='page_automations_model_open']")).isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public void clickBackButton() {
        back.click();
    }


    public void searchBoxForChapterList(String chapterName) throws InterruptedException {
        search.clear();
        search.sendKeys(chapterName+ Keys.ENTER);
        Thread.sleep(2000);
    }


    /**
     * @param bookname enter book name which you want use and this  method used to click a book eye icon
     * @throws InterruptedException
     */
    //**************************************************************Eyeicon**************************************
    public void clickListOfBookeyeicon(String bookname) throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath("//tbody//td[" + booknameIndex + "]/a[text()='" + bookname + "']/ancestor::td/../td[" + listofBooksActionINdex + "]//i[@class='fa fa-eye']")).click();
        Thread.sleep(1000);

    }


    //******************************************************5Icons*******************************************
    public boolean isDeleteIcon(String chaptername) {
        try {
            return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]//a[text()='" + chaptername + "']/ancestor::td/..//i[@class='fa fa-trash delete_chapter_data']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;

        }
    }

    public boolean isGraphicIcon(String chaptername) {
        try {
            return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]//a[text()='" + chaptername + "']/ancestor::td/..//i[@class='fa fa-image']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    public boolean isEditIcon(String chaptername) {
        try {
            return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]//a[text()='" + chaptername + "']/ancestor::td/..//i[@class='fa fa-edit edit_chapter']")).isDisplayed();

        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isQueryAddIcon(String chaptername) {
        try {
            return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]//a[text()='" + chaptername + "']/ancestor::td/..//i[@class='ico-item fa fa-plus notice-alarm edit_newchapter']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    public boolean isProofHistory(String chaptername) {
        try {
            return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]//a[text()='" + chaptername + "']/ancestor::td/..//i[@class='fa fa-indent']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;

        }
    }

    /**
     * Retrieves the MS (Manuscript) page count associated with a chapter
     *
     * @param chaptername
     * @return
     */
    //**************************getting MS Pages,Figures,Table datas******************************************
    public String getMSPageCount(String chaptername) {
        return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername + "']/ancestor::td/../td[" + msPageIndex + "]")).getText();
    }

    /**
     * Retrieves the Figure (Graphic figures) count associated with a chapter
     *
     * @param chaptername
     * @return
     */
    public String getFiguresCount(String chaptername) {
        return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername + "']/ancestor::td/../td[" + noOffigureIndex + "]")).getText();

    }

    /**
     * Retrieves the tables count associated with a chapter
     *
     * @param chaptername
     * @return
     */
    public String getTablesCount(String chaptername) {
        return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername + "']/ancestor::td/../td[" + noOfTableIndex + "]")).getText();

    }

    /**
     * Retrieves the current stage department associated with a chapter
     *
     * @param chaptername
     * @return
     */
    public String getCurrentStageDepartment(String chaptername) {
        return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername + "']/ancestor::td/../td[" + noOfCurrentStageIndex + "]")).getText();

    }
    //************************************ChapternameEdit*************************************************************************

    /**
     * This method checks if the specified chapter name is displayed in the table by locating the corresponding element
     * It returns true if the chapter name is found and displayed, and false otherwise.
     *
     * @param chaptername
     * @return
     */
    public boolean isDisplayedChapterName(String chaptername) {
        try {
            return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername + "']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    /**
     * Clears the existing chapter name input field.
     * Enters the new chapter name into the input field.
     *
     * @param chaptername
     * @param enterNewChapterName
     * @throws InterruptedException
     */
    public void editChapterName(String chaptername, String enterNewChapterName) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername + "']")).click();
        driver.findElement(By.xpath("//input[@class='form-control input-sm']")).clear();
        driver.findElement(By.xpath("//input[@class='form-control input-sm']")).sendKeys(enterNewChapterName);
    }

    /**
     * This method cancels the ongoing edit operation by locating and clicking the cancel icon present in the form.
     *
     * @throws InterruptedException
     */
    public void clickCancelEditButton() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//i[@class='glyphicon glyphicon-remove']")).click();
        Thread.sleep(1000);
    }

    /**
     * This method confirm the ongoing edit operation by locating and clicking the cancel icon present in the form.
     *
     * @throws InterruptedException
     */
    public void clickConfirmEditButton() {
        driver.findElement(By.xpath("//button[@class='btn btn-success btn-sm editable-submit']")).click();
    }

    //********************************************DeleteIcon**********************************

    /**
     * This method is Performs a search for a book by entering the book name
     *
     * @param bookname
     */
    public void searchBookName(String bookname) {
        driver.findElement(By.xpath("//input[@class='form-control form-control-sm']")).sendKeys(bookname);
    }

    /**
     * @param chaptername enter chapter name which you want use and this method is used to click a delete icon for chapters list page
     * @throws InterruptedException
     */
    public void clickChapterDeleteIcon(String chaptername) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername + "']/ancestor::td/../td[" + actionIndex + "]/i[@class='fa fa-trash delete_chapter_data']")).click();
        Thread.sleep(2000);
    }


    /**
     * @throws InterruptedException "This method is used to cancel a delete action for Chapters list page"
     */
    public void clickcancelForChapterDelete() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='No, cancel Please!']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[text()='OK']")).click();
    }

    /**
     * @throws InterruptedException This method is used to confirm a delete action for Chapters list page"
     */
    public void clickchapterConfirmDelete() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Yes, Delete it!']")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//button[text()='OK']")).click();

    }

    /**
     * "This method is used to verify the chapter presence or not in ChaptersList Page"
     */
    public boolean isDisplayedchapter(String chapterName) {
        try {
            return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chapterName + "']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    //**************************************************EditIcon*************************************************************

    /**
     * Retrieves the content of the Current MS page associated with a chapter.
     *
     * @throws InterruptedException
     */
    public String getCurrentMSPageCount(String chaptername) throws InterruptedException {
        Thread.sleep(1000);
        return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername + "']//ancestor::td/../td[" + msPageIndex + "]")).getText();
    }

    /**
     * Verifies if the MS (Manuscript) page count for a chapter matches the provided value
     *
     * @param mspage
     * @return
     */
    public boolean isDisplayedMSPage(int mspage) {
        try {
            return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername + "']/ancestor::td//../td[" + msPageIndex + "][text()='" + mspage + "']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * This method performs the necessary steps to edit the MS page for a specific chapter. It clicks the edit icon
     * corresponding to the chapter, clears the existing input field for the MS page number, enters a new value into
     * the input field.
     *
     * @throws InterruptedException
     */
    public void editMSPageCountAndConfirm(int mspage) throws InterruptedException {
        driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername + "']/ancestor::td/../td[" + actionIndex + "]/i[@class='fa fa-edit edit_chapter']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@id='chapter_no_mspage']")).clear();
        driver.findElement(By.xpath("//input[@id='chapter_no_mspage']")).sendKeys(String.valueOf(mspage));
        Thread.sleep(2000);

    }

    public void editMSPageCountAndConfirm(String chapterName, String mspage) throws InterruptedException {
        Thread.sleep(2000);
        WebElement editChapterIcon = driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chapterName + "']/ancestor::td/../td[" + actionIndex + "]/i[@class='fa fa-edit edit_chapter']"));
        editChapterIcon.click();
        Thread.sleep(2000);
        WebElement mspageBox = driver.findElement(By.xpath("//input[@id='chapter_no_mspage']"));
        mspageBox.clear();
        mspageBox.sendKeys(mspage);

    }

    public void editMSPageCountAndConfirm(int chapterNumber, String msPage) throws InterruptedException {
        Thread.sleep(2000);

        List<WebElement> editChapterIcons = driver.findElements(By.xpath("//*[@class='fa fa-edit edit_chapter']"));
        WebElement editChapterIcon = editChapterIcons.get(chapterNumber - 1);
        editChapterIcon.click();

        Thread.sleep(2000);

        WebElement mspageBox = driver.findElement(By.xpath("//input[@id='chapter_no_mspage']"));
        WebElement updateBtn = driver.findElement(By.id("update_chapter"));
        mspageBox.clear();
        mspageBox.sendKeys(msPage);
        updateBtn.click();


    }

    public String getChapterRowColour(int chapterNumber) throws InterruptedException {
        Thread.sleep(2000);

        WebElement firstCell = driver.findElement(By.xpath("//table[@id='table_book_chapter_list']/tbody/tr["+chapterNumber+"]/td[1]"));
        return firstCell.getCssValue("background-color");

    }

    public String getGraphicsIconColor(String chapterName){
        WebElement grpIcon =  driver.findElement(By.xpath("//*[text()='"+chapterName+"']/../..//*[@class='fa fa-image']"));
        return grpIcon.getCssValue("color");
    }

    public String getToolTipText(String elementName) throws InterruptedException {


        Thread.sleep(2000);
        WebElement firstElement = null;
        try {

            if (elementName.equalsIgnoreCase("fresh query icon")) {
                firstElement = driver.findElement(By.xpath("(//*[@class='ico-item fa fa-question-circle notice-alarm edit_newchapter'])[1]"));
            } else if (elementName.equalsIgnoreCase("edit icon")) {
                firstElement = driver.findElement(By.xpath("(//*[@class='fa fa-edit edit_chapter'])[1]"));
            } else if (elementName.equalsIgnoreCase("delete icon")) {
                firstElement = driver.findElement(By.xpath("(//*[@class='fa fa-trash delete_chapter_data'])[1]"));
            } else if (elementName.equalsIgnoreCase("Bell icon")) {
                firstElement = driver.findElement(By.xpath("(//*[@class='ico-item fa fa-bell notice-alarm query_notifiation'])[1]"));
            } else if (elementName.equalsIgnoreCase("graphics icon RED")) {
                firstElement = driver.findElement(By.xpath("(//*[@data-original-title='Graphics Not Approved'])[1]"));
            } else if (elementName.equalsIgnoreCase("graphics icon BLUE")) {
                firstElement = driver.findElement(By.xpath("(//*[@data-original-title='Graphics Approved'])[1]"));
            } else if (elementName.equalsIgnoreCase("proof history icon")) {
                firstElement = driver.findElement(By.xpath("(//*[@class='fa fa-indent'])[1]"));
            } else if (elementName.equalsIgnoreCase("retrive chapter icon")) {
                firstElement = driver.findElement(By.xpath("(//*[@class='fa fa-reply-all page_reallocate'])[1]"));
            } else if (elementName.equalsIgnoreCase("query history icon")) {
                firstElement = driver.findElement(By.xpath("(//*[@title='Query History'])[1]"));
            }


            if (firstElement != null) {
                Actions actions = new Actions(driver);
                actions.moveToElement(firstElement).perform();

                Thread.sleep(2000);
                WebElement secondElement = firstElement.findElement(By.xpath("./following-sibling::div/div[@class='tooltip-inner']"));
                return secondElement.getText();

            }
        }catch (NoSuchElementException e){
            Assert.fail("Element is not present or available!! Pls. Check");
        }

        return "";

    }

    /**
     * This method cancels the ongoing editing operation by locating and clicking the close icon associated with the edit form.
     *
     * @throws InterruptedException
     */
    public void clickcancelEditIcon() throws InterruptedException {
        Thread.sleep(1500);
        driver.findElement(By.xpath("//*[@id='update_chapter']/../*[text()='Close']")).click();
        Thread.sleep(1500);
    }

    /**
     * This method confirm the ongoing editing operation by locating and clicking the confirm icon associated with the edit form.
     *
     * @throws InterruptedException
     */
    public void clickconfirmEditIcon() throws InterruptedException {

        Thread.sleep(2000);

        WebElement confirmBtn = driver.findElement(By.xpath("//*[@id='update_chapter']"));
        confirmBtn.click();
    }

//   ****************************************************get alert for automation************************************

    /**
     * Checks if an alert message for automation is displayed.
     *
     * @return
     */
    public boolean isDisplayedAlertForAutomation() {
        try {
            return driver.findElement(By.xpath("//marquee[text()='Kindly run the automation script process in the chapters highlighted below.']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Checks if a graphic is approved for a specific chapter.
     *
     * @param chaptername
     * @return
     */
    //*****************************************************************verify Graphic Approved*************************************************
    public boolean isGraphicApproved(String chaptername) {
        try {
            return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername + "']/ancestor::td/..//i[@class='ico-item fa fa-question-circle notice-alarm edit_newchapter']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;

        }
    }

    //************************************************Edit chapter name NEW To ab and bc**********************************************************

    /**
     * Adds new chapters with specified names(ab and bc)
     * This method adds new chapters with the provided chapter names.
     *
     * @throws InterruptedException
     */
    public void editChaptersName() throws InterruptedException {
        String chaptername1 = "ab";
        String chaptername2 = "bc";
        Thread.sleep(3000);
        driver.findElement(By.xpath("//tbody//td[2]/a[text()='New'][1]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@class='form-control input-sm']")).clear();
        driver.findElement(By.xpath("//input[@class='form-control input-sm']")).sendKeys(chaptername1);
        driver.findElement(By.xpath("//i[@class='glyphicon glyphicon-ok']")).click();

        Thread.sleep(4000);
        driver.findElement(By.xpath("//tbody//td[2]/a[text()='New']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@class='form-control input-sm']")).clear();
        driver.findElement(By.xpath("//input[@class='form-control input-sm']")).sendKeys(chaptername2);
        driver.findElement(By.xpath("//i[@class='glyphicon glyphicon-ok']")).click();
    }

    public void clickUpdateReorder() {
        driver.findElement(By.xpath("//button[text()='Update Reorder']")).click();
    }

    public boolean isUpdateReorderBtnDisplayed(){
        try{
            return driver.findElement(By.xpath("//button[text()='Update Reorder']")).isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }
    }


    public void clickOkButton()throws InterruptedException{
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@class='confirm']")).click();
    }

    public void clickReorderAndOK() throws InterruptedException {
        Thread.sleep(2000);
        clickUpdateReorder();
        clickOkButton();
    }

    public void clickReorderButCancel() throws InterruptedException {
        Thread.sleep(2000);
        clickUpdateReorder();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[text()='Cancel']")).click();
        clickOkButton();
    }

    public void chapterDragandDrop(String chaptername1, String chaptername2) {
        WebElement draggableElement = driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername1 + "']//ancestor::td/../td[" + serialNumberIndex + "][text()='1']"));
        WebElement droppableElement = driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername2 + "']//ancestor::td/../td[" + serialNumberIndex + "][text()='2']"));
        Actions actions = new Actions(driver);
        //actions.clickAndHold(draggableElement).moveToElement(droppableElement).release(draggableElement).build().perform();
        //actions.dragAndDrop(draggableElement,droppableElement).build().perform();
        actions.moveToElement(draggableElement).clickAndHold(draggableElement).perform();
        actions.moveToElement(droppableElement).release(draggableElement).perform();
    }



    public boolean isDisplayedChapterReorder(String chaptername1) throws  InterruptedException{
        try {
            return driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername1 + "']//ancestor::td/../td[" + serialNumberIndex + "][text()='1']")).isDisplayed();
        }catch (NoSuchElementException e) {
            return false;
        }


    }

    public void addMsPages(int mspage)throws InterruptedException{
        driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername + "']/ancestor::td/../td[" + actionIndex + "]/i[@class='fa fa-edit edit_chapter']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@id='chapter_no_mspage']")).clear();
        driver.findElement(By.xpath("//input[@id='chapter_no_mspage']")).sendKeys(String.valueOf(mspage));
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='update_chapter']")).click();
        Thread.sleep(2500);
        driver.findElement(By.xpath("//tbody//td[" + chapterNameIndex + "]/a[text()='" + chaptername1 + "']/ancestor::td/../td[" + actionIndex + "]/i[@class='fa fa-edit edit_chapter']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@id='chapter_no_mspage']")).clear();
        driver.findElement(By.xpath("//input[@id='chapter_no_mspage']")).sendKeys(String.valueOf(mspage));
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@id='update_chapter']")).click();
        Thread.sleep(1500);
    }

    /**
     * @return array of current chapter names in order at particular instance - whenever called.
     */
    public  String[] getallChaptersNames(){
        List<WebElement> elements = driver.findElements(By.xpath("//tbody/tr/td[2]/a"));

        String[] chapterNames = new String[elements.size()];

        for(int i=0; i<chapterNames.length; i++){

            WebElement elem = elements.get(i);
            String ch_name = elem.getText();
            chapterNames[i] = ch_name;
        }

        return chapterNames;
    }

    public int getJumpDist(){
        WebElement rowCell1 = driver.findElement(By.xpath("//td[text()='1']"));
        WebElement rowCell2 = driver.findElement(By.xpath("//td[text()='2']"));
        WebElement rowCell3 = driver.findElement(By.xpath("//td[text()='3']"));


        Point p1 = rowCell1.getLocation();
        Point p2 = rowCell2.getLocation();
        Point p3 = rowCell3.getLocation();

        int jumpDist = (p2.getY()) - (p1.getY());
        return jumpDist;
    }







}



