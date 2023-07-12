package BasePackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class BaseClassFMS {

    public static WebDriver driver;

    public static Properties prop1;

    public static void launchApplication() throws IOException {
        FileInputStream file = new FileInputStream("src/main/resources/credential.properties");
        prop1 = new Properties();
        prop1.load(file);
        file.close();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(prop1.getProperty("url"));
    }

    public static void reloadDashboard(){
        driver.get(prop1.getProperty("dashboardURL"));
    }


}

