package TestClasses;

import java.io.IOException;

import org.testng.annotations.Test;

import PageObject.MyAccountPage;
import PageObject.OrangeHRM;
import TestBase.BaseClass2;
import junit.framework.Assert;
import utilities.DataProviders;


public class TC002_OrangeHRMDDTest extends BaseClass2
{
	
	@Test(dataProvider="LoginData", dataProviderClass = DataProviders.class)
	public void orangeHRMDDTest(String email, String pass, String exp) throws IOException
	{	
		logger.info("******** Starting TC_002 UseCase2 Data Driven Test ***********");	
		
			OrangeHRM hrm = new OrangeHRM(driver);
			hrm.setUserName(email);
			hrm.setPassword(pass);
			hrm.clickLogin();		
			
			try
			{
				MyAccountPage ap = new MyAccountPage(driver);
				boolean loginStatus = ap.isMyAccoutnPageExist();
				
				if(exp.equalsIgnoreCase("Valid"))
				{
					if(loginStatus == true)
					{
						logger.info("******** Details are valid and user login Successful, TC Passed. ***********");	
						ap.clickLogout();
						Assert.assertTrue(true);
					}
					else
					{
						logger.info("******** Details are valid and user login Unsuccessful, TC Failed. ***********");
						Assert.assertTrue(false);
					}					
				}
				
				if(exp.equalsIgnoreCase("Invalid"))
				{
					if(loginStatus == true)
					{
						logger.info("******** Details are Invalid and user login successful, TC Failed. ***********");
						ap.clickLogout();
						Assert.assertTrue(false);
					}
					else
					{
						logger.info("******** Details are Invalid and user login unsuccessful, TC Passed ***********");
						Assert.assertTrue(true);
					}
				}
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				Assert.fail();
			}		
			
			logger.info("******** Starting TC_002 UseCase2 Data Driven Test ***********");	
	}
}
