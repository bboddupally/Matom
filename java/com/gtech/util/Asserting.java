package com.gtech.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.ScreenshotException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Asserting {
	
	private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new LinkedHashMap<ITestResult, List<Throwable>>();

    public static void assertTrue(boolean condition) {
    	Assert.assertTrue(condition);
    }
    
    public static void assertTrue(boolean condition, String message) {
    	Assert.assertTrue(condition, message);
    }
    
    public static void assertFalse(boolean condition) {
    	Assert.assertFalse(condition);
    }
    
    public static void assertFalse(boolean condition, String message) {
    	Assert.assertFalse(condition, message);
    }
    
    public static void assertEquals(boolean actual, boolean expected) {
    	
    	Assert.assertEquals(actual, expected);
    }
    
    public static void assertEquals(Object actual, Object expected) {
    	Assert.assertEquals(actual, expected);
    }
    
    public static void assertEquals(Object[] actual, Object[] expected) {
    	Assert.assertEquals(actual, expected);
    }
    
    public static void assertEquals(Object actual, Object expected, String message) {
    	Assert.assertEquals(actual, expected, message);
    }
    
    public static void verifyTrue(boolean condition) {
    	try {
    		assertTrue(condition);
    	} catch(Throwable e) {
    		
    		screenShot();addVerificationFailure(e);
    	}
    }
    
    public static void verifyTrue(boolean condition, String message) {
    	try {
    		assertTrue(condition, message);
    	} catch(Throwable e) {
    		screenShot();addVerificationFailure(e);
    	}
    }
    
    public static void verifyFalse(boolean condition) {
    	try {
    		assertFalse(condition);
		} catch(Throwable e) {
			screenShot();addVerificationFailure(e);
		}
    }
    
    public static void verifyFalse(boolean condition, String message) {
    	try {
    		assertFalse(condition, message);
    	} catch(Throwable e) {
    		screenShot();addVerificationFailure(e);
    	}
    }
    
    public static void verifyEquals(boolean actual, boolean expected) {
    	try {
    		assertEquals(actual, expected);
		} catch(Throwable e) {
			screenShot();addVerificationFailure(e);
		}
    }

    public static void verifyEquals(Object actual, Object expected) {
    	try {
    		assertEquals(actual, expected);
		} catch(Throwable e) {
			screenShot();addVerificationFailure(e);
		}
    }
    
    public static void verifyEquals(Object[] actual, Object[] expected) {
    	try {
    		assertEquals(actual, expected);
		} catch(Throwable e) {
			screenShot();addVerificationFailure(e);
		}
    }

    public static void fail(String message) {
    	Assert.fail(message);
    }
    
	public static List<Throwable> getVerificationFailures() {
		List<Throwable> verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<Throwable>() : verificationFailures;
	}
	
	public static void addVerificationFailure(Throwable e) {
		List<Throwable> verificationFailures = getVerificationFailures();
		verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
		verificationFailures.add(e);
	}
	
	
	
	public static void screenShot()
			 {
	//if(DataSource.screenshot.equals("true")){
		
	
		 StackTraceElement[] list =null;
	String filename=null;
	try {
		 
		 list =Thread.currentThread().getStackTrace();
		 
		 for(StackTraceElement x:list){
			 if(x.getMethodName().contains("GAME_")){
				 filename= x.getMethodName();
			 }
		 }
		
	} catch (Exception e) {
		System.out.println("unable find the method name for screen shot");
	}
		WebDriver driver =SelectingWebDriver.getInstance();
		System.out.println("screenShot at asserting");
		String fileName=null;
	

		 try {
		        SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		        
		    	fileName=filename + "_"+formater.format(Calendar.getInstance().getTime())+".png";  
		        
		        		        File f2=  new File("./src/test/resources/Screenshot/"+fileName);
		           try {
   
		        	   if(DataSource.map.get("suite-browser").contains("fireFox")){
		        		   FileUtils.copyFile(((FirefoxDriver) driver).getScreenshotAs(OutputType.FILE),f2 );
		        		   Reporter.log(" &amp;<a href='"+"file:"+"ScreenShot/"+fileName+"' height='100' width='100' /&amp;>  View Screenshot :"+filename+" &amp;</a&amp;>");
		        	   System.out.println("../Report/ScreenShot"+fileName);
		        	   }
		        	   else if(DataSource.map.get("suite-browser").contains("iExplorer")){
		        		   FileUtils.copyFile(((InternetExplorerDriver) driver).getScreenshotAs(OutputType.FILE),f2 );
		        		   Reporter.log(" &amp;<a href='"+"ScreenShot/"+fileName+"' height='100' width='100' /&amp;>  View Screenshot :"+filename+" &amp;</a&amp;>");
		        	   }
		        	   else if(DataSource.map.get("suite-browser").contains("chrome")){
		        		   FileUtils.copyFile(((ChromeDriver) driver).getScreenshotAs(OutputType.FILE),f2 );
				       		Reporter.log("<a href='"+"file:"+"ScreenShot/"+fileName+"' height='100' width='100'/>View Screenshot :"+filename+"</a>");
		        	   }else{
		        		   System.out.println("please provide valid browser");
		        	   }
		       		
		           } catch (IOException e) {
		               e.printStackTrace();
		           }
		   } catch (ScreenshotException se) {
		       se.printStackTrace();
		   }
	}
	
			// }
	
}
