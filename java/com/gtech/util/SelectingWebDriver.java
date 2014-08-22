package com.gtech.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SelectingWebDriver {
	static String selectbrowser=null;
//static public  WebDriver driver ;

//static Logger log =Util4Modules.log();
public static WebDriver driver;
//this is single ton single object i.e driver 
public static  WebDriver getInstance() {
	
	
	
	WebDriver driver2 = null;
	if(driver==null){
		
		selectbrowser=DataSource.browser;
		if(selectbrowser.equals("fireFox")){
			FirefoxProfile profile = new FirefoxProfile();
			try{
				
				//log.info("location of firefox:"+ propFile("suite-fireFoxBrowserPath"));
				
				driver2 = new FirefoxDriver(new FirefoxBinary(new File(propFile("suite-fireFoxBrowserPath"))),profile);
			
			}catch (Exception e) {
				//log.error("please provide the path of firefox browser in \"SelectingBrowser\" class ");
			}
		}
		else if(selectbrowser.equals("iExplorer")){
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			  ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			  ieCapabilities.setJavascriptEnabled(true);
			  driver2 = new InternetExplorerDriver(ieCapabilities);
		}
		else if(selectbrowser.equals("safari")){
//			driver2=  new SafariDriver();
		}
		else if(selectbrowser.equals("chrome")){
			if(DataSource.localhost.equals("false")){
				DesiredCapabilities capability = DesiredCapabilities.firefox();
				try {
					driver2=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			
			else if(DataSource.localhost.equals("true")){
				System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
				driver2 = new ChromeDriver();
				driver2.manage().deleteAllCookies();
			}else{
				System.out.println("please provide locahost as true or false");
			}
			
			//driver.manage().
		}else {
			System.out.println("Please provide proper browser name");
			Asserting.assertEquals("null",selectbrowser );
		}
		SelectingWebDriver.driver=driver2;
		System.out.println(driver.hashCode());
		return driver;	
}else{
	return driver;
}
	
}
public static String propFile(String propertiesName){
	return DataSource.map.get(propertiesName.trim());
}


public  WebDriver  startClient() throws IOException {
		return getInstance();
		
		
}
public static void stopClient(){
	driver.quit();
}

}
