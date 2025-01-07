package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	// Data Provider 1
	
	@DataProvider(name = "LoginData")
	public String [][] getData() throws IOException
	{
		String path = ".\\testData\\Login Data.xlsx";
		
		ExcelUtility xlutil = new ExcelUtility(path);
		int row = xlutil.getRowCount("Sheet1");
		int col = xlutil.getCellCount("Sheet1",1);
		
		System.out.println("Row Count is: "+row +" Col count is: "+col );
		
		String loginData[][] = new String[row][col];
		
		for(int i = 1; i<=row; i++)
		{
			for(int j = 0; j<col; j++)
			{
				loginData[i-1][j] = xlutil.getCellData("Sheet1", i, j);
			}
		}
		
		return loginData;
		
	}	
	
}
