package QKART_TESTNG;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Platform;

public class ExcelUtils {
    public static final String currentDir = System.getProperty("user.dir"); // Main Directory of the
                                                                            // project
    public static String testDataExcelPath = null; // Location of Test data excel file
    public static String fileName = null;
    private static XSSFWorkbook workBook; // Excel WorkBook
    private static XSSFSheet sheet; // Excel Sheet
    private static XSSFCell cell; // Excel cell
    private static XSSFRow row; // Excel row
    public static int rowNumber; // Row Number
    public static int columnNumber; // Column Number

    public static FileOutputStream fos = null;

    /**
     * use this method to create FileInputStream set excel file and excel sheet to excelWBook
     * excelWSheet variables.
     * 
     * @param testDataExcelFileName
     * @param sheetName
     * @throws IOException
     */
    public static void setExcelFileSheet(String testDataExcelFileName, String sheetName)
            throws IOException {
        testDataExcelPath = currentDir + "/src/test/resources/";

        // Open the Excel file
        fileName = testDataExcelPath + testDataExcelFileName;
        FileInputStream ExcelFile = new FileInputStream(fileName);
        workBook = new XSSFWorkbook(ExcelFile);
        sheet = workBook.getSheet(sheetName);
        // System.out.println(workBook.getSheetName(0));
    }

    /**
     * use this method takes row number as a parameter and returns the data of given row number.
     * 
     * @param RowNum
     * @return
     */
    public static XSSFRow getRowData(int RowNum) {
        row = sheet.getRow(RowNum);
        return row;
    }

    /**
     * use this method to update the the column value
     * 
     * @param colName
     * @param rowNum
     * @param result
     * @return
     */

    public static boolean setCellData(String colName, int rowNum, String result) {
        try {
            int col_Num = -1;
            row = sheet.getRow(0);

            // Find column index by matching the given column header name
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equals(colName)) {
                    col_Num = i;
                }
            }

            sheet.autoSizeColumn(col_Num);
            row = sheet.getRow(rowNum - 1);

            // Create row if it doesn't exist
            if (row == null) {
                row = sheet.createRow(rowNum - 1);
            }

            cell = row.getCell(col_Num);

            if (cell == null) {
                // Create cell if it doesn't exist
                cell = row.createCell(col_Num);
            }
            if (result instanceof String) {
                // Set cell value
                cell.setCellValue((String) result);
            }

            fos = new FileOutputStream(fileName);
            workBook.write(fos);
            fos.close();

            System.out.println("Excel file has been updated successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /*
     * Use this method to close the workbook
     */
    public static void closeWorkbook() throws IOException {
        workBook.close();
    }
}
