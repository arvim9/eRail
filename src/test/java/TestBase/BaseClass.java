package TestBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	public Properties p;
	public static WebDriver driver;
	public Logger logger;
	
	@BeforeClass()
	@Parameters({"os","browser"})
	public void setUp(String os, String br) throws IOException
	{
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);	
		logger = LogManager.getLogger(this.getClass());
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities cap = new DesiredCapabilities();
				
			// Deside Operating System based on xml paraeter.
			if(os.equalsIgnoreCase("windows"))
			{
				cap.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				cap.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching os available...");
				return;
			}
			
			// deside Browser based on xml parameter.
			
			switch(br.toLowerCase())
			{
				case "chrome": cap.setBrowserName("chrome"); break;
				case "edge": cap.setBrowserName("MicrosoftEdge"); break;
				default : System.out.println("Invalid browser..."); return;
			}
			// creating driver and passing 2 parameters....os and browser
			driver = new RemoteWebDriver(new URL("http://192.168.63.11:4444/wd/hub"), cap);
		}
		
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch (br.toLowerCase())
			{
				case "chrome": driver = new ChromeDriver(); break;
				case "firefox": driver = new FirefoxDriver(); break;
				case "edge": driver = new EdgeDriver(); break;
				default: System.out.println("Invalid browser name..."); return;
			}
		
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		//driver.get("https://tutorialsninja.com/demo/index.php?route=checkout/cart"); // hard coding URL value.
		driver.get(p.getProperty("appURL")); // Reading URL from properties file
	}
	
	@AfterClass()
	public void tearDown() throws InterruptedException
	{
		
		driver.quit();
	}
	
	
	public String captureScreen(String tname)throws IOException
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()); // Create time stamp...
		TakesScreenshot takeScreenshot = (TakesScreenshot)driver ;
		File sourceFile = takeScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp+".png";
		File targetFile = new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		//Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		return targetFilePath;		
	}
	
}
