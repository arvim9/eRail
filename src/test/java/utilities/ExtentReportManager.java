package utilities;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestBase.BaseClass;
import TestBase.BaseClass2;

public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String reportName;
	
	public void onTestStart(ITestResult result) {
	     
	  }
	
	public void onTestSuccess(ITestResult result) {
	    	
		test = extent.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName()+"got successfully executed.");
	  }

	  
	public void onTestFailure(ITestResult result) {
		
		test = extent.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, result.getName()+"got failed.");
		test.log(Status.INFO,result.getThrowable().getMessage());
		
		try
		{
			Thread.sleep(2000);
			String imgPath = new BaseClass2().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);		
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	  }

	  
	public void onTestSkipped(ITestResult result) {
		
		test = extent.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+"got Skipped.");
		test.log(Status.INFO,result.getThrowable().getMessage());
		
	  }

	  
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	    // not implemented
	  }

	  
	public void onTestFailedWithTimeout(ITestResult result) {
	    onTestFailure(result);
	  }

	  
	public void onStart(ITestContext textContext) {

		/*
		 SimpleDateFormat df = new SimpleDateFormat("yyyy.mm.dd.HH.mm.ss");
	     Date dt = new Date(0);
	    String date =  df.format(dt);
	    
	    */
		
	    String timeStamp = new SimpleDateFormat("yyyy.mm.dd.HH.mm.ss").format(new java.util.Date()); // Create time stamp...
	    reportName = "Test Report"+timeStamp+".html";
	    sparkReporter = new ExtentSparkReporter(".\\reports\\"+reportName); 	// Specify the location of report...
	    sparkReporter.config().setDocumentTitle("eRail Automation Report");	// Set Title of the Report ...
	    sparkReporter.config().setReportName("eRail Functional Testing"); 	// Set the name of the report...
	    sparkReporter.config().setTheme(Theme.DARK);	// Set the theme of report...
	    
	    extent = new ExtentReports();
	    extent.attachReporter(sparkReporter);
	    extent.setSystemInfo("Application", "eRail");
	    extent.setSystemInfo("Module", "Admin");
	    extent.setSystemInfo("Sub Module", "Customer");
	    extent.setSystemInfo("User Name", System.getProperty("user.name"));
	    extent.setSystemInfo("Envoirnment", "QA");
	    
	    
	    String os = textContext.getCurrentXmlTest().getParameter("os");
	    extent.setSystemInfo("Operating System", os);
	    
	    String browser = textContext.getCurrentXmlTest().getParameter("browser");
	    extent.setSystemInfo("Browser", browser);
	    
	    List<String> includegroups = textContext.getCurrentXmlTest().getIncludedGroups();
	    if(!includegroups.isEmpty())
	    {
	    	extent.setSystemInfo("Groups", includegroups.toString());
	    }
	  }

	  
	public void onFinish(ITestContext context) {
	    	
		extent.flush();
		
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+reportName;
		File extentReport = new File(pathOfExtentReport);
		
		try
		{
			Desktop.getDesktop().browse(extentReport.toURI());			
		}
		catch(Exception e)
		{
			e.getStackTrace();
			
		}
		/*
		
		try
		{
			URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+reportName);
			
			ImageHtmlEmail email = new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("onlytestmail22@gmail.com","Testing@1234"));
			email.setSSLOnConnect(true);
			email.setFrom("onlytestmail22@gmail.com");
			email.setSubject("Test Report");
			email.setMsg("Please find the attache report...");
			email.addTo("arvindm@neuronimbuscom");
			email.attach(url,"extent report","Please check report...");
			email.send();
		}
		catch(Exception e)
		{
			e.getStackTrace();
		} 
		
		*/
		
		
	  }

	
}
