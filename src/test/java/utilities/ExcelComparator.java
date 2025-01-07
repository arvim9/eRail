package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.mail.imap.protocol.Status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ExcelComparator {
	
    public static void compareExcelSheets(String file1, String file2) throws IOException
    { 	
        // Open both Excel files
        FileInputStream fis1 = new FileInputStream(new File(file1));
        FileInputStream fis2 = new FileInputStream(new File(file2));

        // Create Workbook instances
        Workbook workbook1 = new XSSFWorkbook(fis1);
        Workbook workbook2 = new XSSFWorkbook(fis2);

        // Assuming you are comparing the first sheet in both workbooks
        Sheet sheet1 = workbook1.getSheetAt(0);
        Sheet sheet2 = workbook2.getSheetAt(0);

        boolean sheetsAreIdentical = compareSheets(sheet1, sheet2);

        if (sheetsAreIdentical) {
            System.out.println("The sheets are identical.");
        } else {
            System.out.println("The sheets are different.");
        }

        // Close the resources
        workbook1.close();
        workbook2.close();
        fis1.close();
        fis2.close();
    }

    public static boolean compareSheets(Sheet sheet1, Sheet sheet2) {
        boolean areEqual = true;

        // Check if both sheets have the same number of rows
        if (sheet1.getPhysicalNumberOfRows() != sheet2.getPhysicalNumberOfRows())
        {
            return false;
        }

        // Iterate through each row and compare cells
        for (int i = 0; i < sheet1.getPhysicalNumberOfRows(); i++) {
            Row row1 = sheet1.getRow(i);
            Row row2 = sheet2.getRow(i);

            // Check if both rows are null
            if (row1 == null && row2 != null || row1 != null && row2 == null)
            {
                return false;
            }

            // Compare each cell in the row
            for (int j = 0; j < row1.getPhysicalNumberOfCells(); j++) {
                Cell cell1 = row1.getCell(j);
                Cell cell2 = row2.getCell(j);

                // If one cell is null and the other isn't, they are different
                if ((cell1 == null && cell2 != null) || (cell1 != null && cell2 == null)) {
                    return false;
                }

                // If both cells are not null, compare their values
                if (cell1 != null && cell2 != null)
                {                	
                	 if (cell1.toString() != cell2.toString())                     
                		 System.out.println(cell1.toString()+"-- Cell data is Matched --"+ cell2.toString() );                     
                     else
                    	 System.out.println(cell1.toString()+ "-- Cell Data is not matched --"+ cell2.toString());                	                	
                }
            }
        }

        return areEqual;
    }
    
}
