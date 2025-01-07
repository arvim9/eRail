package TestClasses;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DemoPage {

	private static final String Finaly = null;

	public static void main(String[] args) throws InterruptedException {
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://erail.in/");
		
		WebElement txt_fromStation = driver.findElement(By.xpath("//input[@placeholder='From Station']"));
		
		txt_fromStation.clear(); // clear the field.
		txt_fromStation.sendKeys("DEL"); // Enter the text in search field to open the dropdown list.
		Thread.sleep(2000);
		try
		{
			List<WebElement> dropdownOptions = driver.findElements(By.xpath("//div[@class='autocomplete']//div[@style='width:240px;float:left;overflow:hidden']")); // Update the XPath as needed
						
			for(int i=0; i<dropdownOptions.size(); i++)
			{
				if(i == (4-1))
				{
					System.out.println(dropdownOptions.get(i).getText()); // Print the 4th position station name in console.
					dropdownOptions.get(i).click(); //Select 4th position.					
					
					break;
				}
				
			}
					
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		
		
		finally {
            
			System.out.println("Execution Completed...");	
           driver.quit(); // Close the browser
        }
		
	}

}
