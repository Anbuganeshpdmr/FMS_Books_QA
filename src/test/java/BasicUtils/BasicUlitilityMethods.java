package BasicUtils;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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

    public static String deleteFileFromLocation(String filePath, String fileName){

        File file = new File(filePath, fileName);

        // Check if the file exists
        if (file.exists()) {
            // Attempt to delete the file
            if (file.delete()) {
                return "File deleted successfully";
            } else {
                return "Failed to delete the file";
            }
        } else {
            return "File not found";
        }
    }

    public static String deleteFileFromDownloads(String fileName){

        String filePath = System.getProperty("user.home") + "/Downloads";
        File file = new File(filePath, fileName);

        // Check if the file exists
        if (file.exists()) {
            // Attempt to delete the file
            if (file.delete()) {
                return "File deleted successfully";
            } else {
                return "Failed to delete the file";
            }
        } else {
            return "File not found";
        }
    }

    public static Object[][] readExcelData(String fileName, String sheetName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum();
        int columnCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i + 1);
            for (int j = 0; j < columnCount; j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    data[i][j] = "";  // Assign empty string for null cell
                } else {
                    try {
                        // Handle different cell types
                        switch (cell.getCellType()) {
                            case BLANK:
                                data[i][j] = "";
                                break;
                            case STRING:
                                data[i][j] = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                data[i][j] = String.valueOf(cell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                //data[i][j] = String.valueOf(cell.getBooleanCellValue());
                                data[i][j] = cell.getBooleanCellValue();
                                break;
                            case FORMULA:
                                data[i][j] = cell.getCellFormula();
                                break;
                            default:
                                data[i][j] = "";
                        }
                    } catch (Exception e) {
                        data[i][j] = "";  // Handle any exception by assigning empty string
                    }
                }
            }

        }
        workbook.close();
        fileInputStream.close();
        return data;
    }

    public static Object[][] readExcelData(String fileName, int sheetNumber) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(sheetNumber);

        int rowCount = sheet.getLastRowNum();
        int columnCount = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i + 1);
            for (int j = 0; j < columnCount; j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    data[i][j] = "";  // Assign empty string for null cell
                } else {
                    try {
                        // Handle different cell types
                        switch (cell.getCellType()) {
                            case BLANK:
                                data[i][j] = "";
                                break;
                            case STRING:
                                data[i][j] = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                data[i][j] = String.valueOf(cell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                //data[i][j] = String.valueOf(cell.getBooleanCellValue());
                                data[i][j] = cell.getBooleanCellValue();
                                break;
                            case FORMULA:
                                data[i][j] = cell.getCellFormula();
                                break;
                            default:
                                data[i][j] = "";
                        }
                    } catch (Exception e) {
                        data[i][j] = "";  // Handle any exception by assigning empty string
                    }
                }
            }

        }
        workbook.close();
        fileInputStream.close();
        return data;
    }

    public static Map<String, HashMap<String, String>> getChapterDataFromJSON(String filePath) throws FileNotFoundException, IOException {

        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);

            Map<String, HashMap<String, String>> chapters = new HashMap<>();

            if (jsonElement.isJsonArray()) {
                for (JsonElement element : jsonElement.getAsJsonArray()) {
                    HashMap<String, String> chapter;
                    if (element.isJsonObject()) {

                        chapter = new HashMap<>();

                        JsonObject jsonObject = element.getAsJsonObject();
                        //String fileName = jsonObject.entrySet().iterator().next().getKey();
                        JsonObject data = jsonObject.entrySet().iterator().next().getValue().getAsJsonObject();

                        chapter.put("No. of MS Pages",data.get("no_of_mspage").getAsString());
                        chapter.put("No. of Tables",data.get("no_of_table").getAsString());
                        chapter.put("No. of Figures",data.get("no_of_figure").getAsString());
                        chapter.put("Chapter Name",data.get("chapter_name").getAsString());

                        chapters.put(data.get("chapter_name").getAsString(),chapter);
                    }
                }
            }
            return chapters;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
