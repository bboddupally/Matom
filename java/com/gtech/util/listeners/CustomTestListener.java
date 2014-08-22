package com.gtech.util.listeners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.ScreenshotException;
import org.testng.IInvokedMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.Utils;

import com.gtech.util.Asserting;
import com.gtech.util.DataSource;
import com.gtech.util.SelectingWebDriver;

public class CustomTestListener extends TestListenerAdapter {
	//static Logger log =Util4Modules.log();
@Override
	public void afterInvocation(IInvokedMethod method, ITestResult result) {
	
	//log.info(method.getTestMethod()+" :afterTest Execution ");
	Reporter.setCurrentTestResult(result);
		if (method.isTestMethod()) {
			//System.out.println(result.getStatus());
			if(result.getStatus()==2){
				screenShot(result);
			}
			
			List<Throwable> verificationFailures = Asserting.getVerificationFailures();
			//if there are verification failures...
			if (verificationFailures.size() > 0) {
				//set the test to failed
				result.setStatus(ITestResult.FAILURE);
				//if there is an assertion failure add it to verificationFailures
				if (result.getThrowable() != null) {
					verificationFailures.add(result.getThrowable());
				}
				int size = verificationFailures.size();
				//if there's only one failure just set that
				if (size == 1) {
					result.setThrowable(verificationFailures.get(0));
					
				} else {
					//create a failure message with all failures and stack traces (except last failure)
					StringBuffer failureMessage = new StringBuffer("Multiple failures (").append(size).append("):\n\n");
					for (int i = 0; i < size-1; i++) {
						String error=null;
						failureMessage.append("Failure ").append(i+1).append(" of ").append(size).append(":\n");
						Throwable t = verificationFailures.get(i);
						String StackTrace = Utils.stackTrace(t, false)[1];
						if(StackTrace.contains(": expected [")){
							error=StackTrace.substring(StackTrace.indexOf(":")+1, StackTrace.indexOf("\n"));
							failureMessage.append(error).append("\n\n");
						}else{
							error=StackTrace.substring(StackTrace.indexOf(":")+1, StackTrace.indexOf("\n"));
							failureMessage.append(StackTrace).append("\n\n");
						}
	
						
					}
					//final failure
					Throwable last = verificationFailures.get(size-1);
					
					String temp=null;
					temp =last.getMessage();
					failureMessage.append("Failure ").append(size).append(" of ").append(size).append(":\n");
					if(!last.toString().contains(": expected [")){
						failureMessage.append(last.getMessage().subSequence(0, temp.indexOf(":")));

					}else{
						
					if(temp.contains("expected [")){
						//error=temp.substring(temp.indexOf("expected ["), temp.indexOf("[null]"));
						failureMessage.append(temp).append("\n\n");
					}else{
						failureMessage.append(temp);
					}

					}
					
					//set merged throwable
					Throwable merged = new Throwable(failureMessage.toString());
					merged.setStackTrace(last.getStackTrace());
					//merged.setStackTrace(stackTrace)
					result.setThrowable(merged);
					
				}
			}
		}
	}


	
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult result){
		try {
			//log.info(method.getTestMethod()+" :beforeTest Execution ");
			Thread.currentThread();
			Thread.sleep(400);
			
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//resolvingInternalIssue();
	}
	
	

	/**
	 * @param result
	 */
	public void screenShot(ITestResult result) {

		genericScreenShot(result.getName());
	}



	/**
	 * @param result
	 */
	public void genericScreenShot(String result) {
		//resolvingInternalIssue();
		WebDriver driver =SelectingWebDriver.getInstance();
		System.out.println("screenShot");
		//String s = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
		//System.out.println(s);
		String fileName=null;
	

		 try {
		        SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		        
		    	fileName=result + "_"+formater.format(Calendar.getInstance().getTime())+".png";  
		        
		        		        File f2=  new File("ScreenShot/"+fileName);
		           try {
   
		        	   if(DataSource.map.get("suite-browser").contains("fireFox")){
		        		   FileUtils.copyFile(((FirefoxDriver) driver).getScreenshotAs(OutputType.FILE),f2 );
		        		   Reporter.log(" &amp;<a href='"+"ScreenShot/"+fileName+"' height='100' width='100' /&amp;>  View Screenshot :"+result+" &amp;</a&amp;>");
		        	   System.out.println("../Report/ScreenShot"+fileName);
		        	   }
		        	   else if(DataSource.map.get("suite-browser").contains("MSIE 7.0")){
		        		   FileUtils.copyFile(((InternetExplorerDriver) driver).getScreenshotAs(OutputType.FILE),f2 );
		        		   Reporter.log(" &amp;<a href='"+"./ScreenShot/"+fileName+"' height='100' width='100' /&amp;>  View Screenshot :"+result+" &amp;</a&amp;>");
		        	   }
		        	   else if(DataSource.map.get("suite-browser").contains("chrome")){
		        		   FileUtils.copyFile(((ChromeDriver) driver).getScreenshotAs(OutputType.FILE),f2 );
				       		Reporter.log("<a href='"+"file:"+"ScreenShot/"+fileName+"' height='100' width='100'/>View Screenshot :"+result+"</a>");
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
	
}
