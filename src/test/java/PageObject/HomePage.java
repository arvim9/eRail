package PageObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.google.common.collect.Table.Cell;

import freemarker.template.utility.DateUtil;
import utilities.ExcelComparator;

public class HomePage extends BasePage
{	
	public HomePage(WebDriver driver)
	{
		super(driver);
	}
		
	@FindBy(xpath="//input[@placeholder='From Station']") WebElement txt_fromStation;
	@FindBy(xpath = "//tbody//tr//a[2]") WebElement cal_RtArrow;
		
	
	public void setFromStation() throws InterruptedException
	{	
		txt_fromStation.click();
		txt_fromStation.clear();	
						
			txt_fromStation.sendKeys("DEL"); // Enter the search text to open the dropdown list.
			Thread.sleep(1000);
						
			List<WebElement> dropdownOptions = driver.findElements(By.xpath("//div[@class='autocomplete']//div[@style='width:240px;float:left;overflow:hidden']"));
			
			int tot_count = dropdownOptions.size();
			
			//System.out.println(tot_count);
			
			if(tot_count > 3)
			{						
				for(int i=0; i<tot_count; i++)
				{
					if(i == (4-1))
					{
						System.out.println("Selected value is: "+dropdownOptions.get(i).getText()); // Print the 4th position station name in console.
						dropdownOptions.get(i).click(); //Select 4th position.					
						break;						
					}
					
					if (dropdownOptions == null || dropdownOptions.isEmpty()) 
			        {
						System.out.println("Dropdown option is not have any value...");
			            //return;
			        }
			        else
			        {
			        	String filePath = System.getProperty("user.dir") + "\\testData\\Exp_StationName.xlsx";
				        File outputFile = new File(filePath);

				        if (outputFile.exists())
				        {
				            System.out.println("Warning: File already exists and will be overwritten.");
				        }
				        
				        try
				        {
							FileOutputStream file = new FileOutputStream(filePath);
							XSSFWorkbook workbook = new XSSFWorkbook();
							XSSFSheet sheet = workbook.createSheet("Sheet1");
							int k = 0;
							for(WebElement el:dropdownOptions)
							{						
								XSSFRow row1 = sheet.createRow(k);							
								row1.createCell(0).setCellValue(el.getText());						
								k++;
							}
								workbook.write(file);						
								workbook.close();
								file.close();
								System.out.println("File created successfully...!");
				        }
				        catch(Exception e)
				        {
				        	System.out.println("Somthing went wrong: " +e.getMessage());
				        }				        
				   }
				}
						
			}
			else
			{
				System.out.println("4th position can not select because after search only "+tot_count+" value is fined. ");
			}	
	}		
		
		
	
	public void writeAllDataInExl() throws IOException, InterruptedException
	{
		
		//txt_fromStation.click();
		txt_fromStation.clear();
		txt_fromStation.sendKeys("DEL");
		Thread.sleep(5000);
		
		List<WebElement> dropdownOptions1 = driver.findElements(By.xpath("//div[@class='autocomplete']//div"));
				
		        if (dropdownOptions1 == null || dropdownOptions1.isEmpty()) 
		        {
		        	System.out.println("No data available in dropdownOptions.");
		            return;
		        }	 
		        
		        String filePath = System.getProperty("user.dir") + "\\testData\\All_StationName.xlsx";
		        File outputFile = new File(filePath);

		        if (outputFile.exists())
		        {
		            System.out.println("Warning: File already exists and will be overwritten.");
		        }
		        
		        try
		        {
					FileOutputStream file = new FileOutputStream(filePath);
					XSSFWorkbook workbook = new XSSFWorkbook();
					XSSFSheet sheet = workbook.createSheet("Sheet1");
					int k = 0;
					for(WebElement el:dropdownOptions1)
					{						
						XSSFRow row1 = sheet.createRow(k);							
						row1.createCell(0).setCellValue(el.getText());						
						k++;
					}
						workbook.write(file);						
						workbook.close();
						file.close();
						System.out.println("File created successfully with all data...!");
		        }
		        catch(Exception e)
		        {
		        	System.out.println("Somthing went wrong: " +e.getMessage());
		        }
	}
	
	
	public void ExcelDataComparison() throws IOException
	{
		String file1 = System.getProperty("user.dir")+"\\testData\\Exp_StationName.xlsx";
        String file2 = System.getProperty("user.dir")+"\\testData\\All_StationName.xlsx";
        
    	ExcelComparator ec = new ExcelComparator();
    	ec.compareExcelSheets(file1, file2);
	}
	
	
	
	public void setCalDate(int day)
	{
		try
		{
			for(int i =0; i<day; i++)
			{
				cal_RtArrow.click();
			}
			
			WebElement selectedDate = driver.findElement(By.xpath("//input[@title='Select Departure date for availability']"));
			
			System.out.println("New Selected date is: " +selectedDate.getAttribute("value"));
			
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
}

