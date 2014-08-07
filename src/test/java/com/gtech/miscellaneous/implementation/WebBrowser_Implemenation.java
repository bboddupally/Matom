/**
 * 
 */
package com.gtech.miscellaneous.implementation;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import com.gtech.util.DataSource;
import com.gtech.util.SelectingWebDriver;
import com.gtech.util.Util4Modules;

/**
 * @author bhupesh.b
 *
 */
public class WebBrowser_Implemenation {
	
	
	WebDriver driver =SelectingWebDriver.getInstance();
	DataSource dataSource =new DataSource();
	
	Util4Modules autoutil=Util4Modules.getInstance();
	
	public String dataSheet(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String parentKey,String chieldKey){
		return credentials.get(parentKey.trim()).get(chieldKey.trim());
	}
	
	public LinkedHashMap<String, String> dataSheet(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String parentKey){
		return credentials.get(parentKey.trim());
	}
	
	@SuppressWarnings("static-access")
	public void GAME_openBrowser(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials) {
		
		driver.get(dataSheet(credentials, "parameters", "url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
	}
	
	public void GAME_closeBrowser() {
		System.out.println("tesing");
		Util4Modules.autoutil=null;
		SelectingWebDriver.driver=null;
		driver.quit();
		
	}
	
	
	
	
	
	

}
