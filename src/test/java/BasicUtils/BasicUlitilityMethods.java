package BasicUtils;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BasicUlitilityMethods {

    public static Sheet getSheetBySheetName(String filePath, String sheetName) throws FileNotFoundException {

        FileInputStream file = new FileInputStream(filePath);
        {
            Workbook workbook = null; // Load the workbook
            Sheet sheet;

            try {
                workbook = new XSSFWorkbook(file);
                sheet = workbook.getSheet(sheetName);
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return sheet;
        }
    }

    public static String getTimeStamp() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd_HHmmss");
        return currentDateTime.format(formatter);
    }

    public static String getTimeStampNumber() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        return currentDateTime.format(formatter);
    }

    public static String getDate(int daysToOffset){
        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return (LocalDate.now().plusDays(daysToOffset)).format(formatter);
    }

    private static LocalDate getRelativeDate(int daysOffset) {
        LocalDate currentDate = LocalDate.now();
        LocalDate relativeDate = currentDate.plusDays(daysOffset);
        return relativeDate;
    }

    public static String getRecentlyAddedFileName(){
        String downloadDir = System.getProperty("user.home") + "/Downloads";
        File dir = new File(downloadDir);
        File[] files = dir.listFiles();

        File mostRecentFile = null;
        long lastModifiedTime = Long.MIN_VALUE;

        for (File file : files) {
            if (file.isFile() && file.lastModified() > lastModifiedTime) {
                mostRecentFile = file;
                lastModifiedTime = file.lastModified();
            }
        }
        String fileName = "";
        if (mostRecentFile != null) {
            fileName = mostRecentFile.getName();

        }

        return fileName;

    }
}
