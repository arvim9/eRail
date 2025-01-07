package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrangeHRM extends BasePage
{
	
	public OrangeHRM(WebDriver driver)
	{
		super(driver);
	}
	
	
	@FindBy(xpath = "//input[@placeholder='Username']") WebElement txt_UserName ;
	@FindBy(xpath = "//input[@placeholder='Password']") WebElement txt_Password ;
	@FindBy(xpath = "//button[@type='submit']") WebElement btn_Login;
	
	
	public void setUserName(String uName)
	{
		txt_UserName.click();
		txt_UserName.clear();
		txt_UserName.sendKeys(uName);
	}
	
	public void setPassword(String pass)
	{
		txt_Password.click();
		txt_Password.clear();
		txt_Password.sendKeys(pass);
	}
	
	public void clickLogin()
	{
		btn_Login.click();
	}

}
