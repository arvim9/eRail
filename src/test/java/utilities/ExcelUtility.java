package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	
	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path, path2;
	
	public ExcelUtility(String path)
	{
		this.path = path;
	}
	
	public ExcelUtility(String path, String path2)
	{
		this.path = path;
		this.path2 = path2;
	}
	
	public int getRowCount(String sheetName) throws IOException
	{
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		workbook.close();
		fi.close();		
		return rowCount;
	}
	
	public int getCellCount(String sheetName, int rownum) throws IOException
	{
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		int cellCount = row.getLastCellNum();
		workbook.close();
		fi.close();		
		
		return cellCount;
	}
	
	
	public String getCellData(String sheetName, int rownum, int colnum) throws IOException
	{		
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(colnum);
		
		
		DataFormatter formatter = new DataFormatter();
		String data;
		try
		{
			// Return the formatted value of a cell as a string regardless 
			data = formatter.formatCellValue(cell);
		}
		catch(Exception e)
		{
			data = "";
		}
		workbook.close();
		fi.close();				
		return data;
	}
	
	public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException
	{
		File xlfile = new File(path);
		if(!xlfile.exists())  // If file is not exist then create a new file.
		{
			workbook = new XSSFWorkbook();
			fo = new FileOutputStream(path);	
			workbook.write(fo);
		}
		
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		if(workbook.getSheetIndex(sheetName) == -1)		// If sheet does not exist then create new sheet.	
		{
			workbook.createSheet(sheetName);
		}
		sheet = workbook.getSheet(sheetName);
		
		
		if(sheet.getRow(rownum)==null)  // if row is not exist then create new row.
		{
			sheet.createRow(rownum);
		}
		row = sheet.getRow(rownum);
		cell = row.createCell(colnum);
		cell.setCellValue(data);
		fo = new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
	}
	
	public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException
	{
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(colnum);
		
		style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		workbook.write(fo);
		workbook.close();
		fo.close();
		fi.close();
	}
	
	public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException
	{
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(colnum);
		
		style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		workbook.write(fo);
		workbook.close();
		fo.close();
		fi.close();
	}
	
	
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
