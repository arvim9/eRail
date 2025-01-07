package TestClasses;

import java.io.IOException;

import org.testng.annotations.Test;

import PageObject.HomePage;
import TestBase.BaseClass;
import junit.framework.Assert;


public class TC001_UseCaseTest1 extends BaseClass{
	
	@Test()
	public void useCase1() throws IOException
	{
		
		try
		{
			logger.info("******** Starting TC_001 UseCase1 Test ***********");	
			
			HomePage hp = new HomePage(driver);
			hp.setFromStation();
			hp.writeAllDataInExl();			
			hp.setCalDate(30);
			hp.ExcelDataComparison();
			
			Assert.assertTrue(true);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			Assert.assertTrue(false);
		}
		
		logger.info("******** Finished TC_001 UseCase1 Test ***********");	
	}

}
