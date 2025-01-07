package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage
{
	public MyAccountPage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath="//a[@href='/web/index.php/pim/viewMyDetails']//span") WebElement myAccount;
	@FindBy(xpath ="//body//div//div//div//header//div//div//ul//li//span//i") WebElement account;
	@FindBy(xpath="//a[normalize-space()='Logout']") WebElement lnk_Logout;
	
	
	public boolean isMyAccoutnPageExist()
	{
		try
		{			
			return myAccount.isDisplayed();
		}
		catch(Exception e)
		{
			
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public void clickLogout()
	{
		account.click();
		lnk_Logout.click();
	}
}
