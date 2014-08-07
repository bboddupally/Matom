/**
 * 
 */
package com.gtech.administration.implementation;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

import com.gtech.util.DataSource;
import com.gtech.util.SelectingWebDriver;
import com.gtech.util.Util4Modules;

/**
 * @author bhupesh.b
 *
 */
public class Login_Implementation  {

	
	WebDriver driver =SelectingWebDriver.getInstance();
	Util4Modules autoutil=Util4Modules.getInstance();
	DataSource dd= new DataSource();
	
	
	// for testing 
	public void signIn(LinkedHashMap<String, LinkedHashMap<String, String>> credentials){}

	
	public String propFile(String propertiesName){
		return DataSource.map.get(propertiesName.trim());
	}

	public String dataSheet(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String parentKey,String chieldKey){
		return credentials.get(parentKey.trim()).get(chieldKey.trim());
	}
	
	public LinkedHashMap<String, String> dataSheet(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String parentKey){
		return credentials.get(parentKey.trim());
	}


	
	public void GAME_SignIn(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials) {
		//autoutil.copyloginCredentials(credentials);
		
		for(Entry<String, String> entry : dataSheet(credentials, "field").entrySet())
		System.out.println(entry.getKey()+ ","+ entry.getValue());
			/*autoutil.verifyingLabelsAndTextTheFields(entry.getKey(), entry.getValue());
		autoutil.clickLoginButton(propFile("login_submit"));
		autoutil.acceptAlerts();
		
		try{
			Alert alert =driver.switchTo().alert();
			alert.accept();
		}catch(Exception e){
		}
		autoutil.acceptAlert();
		autoutil.waitUntillDisplayed();*/
	}
	
	
	


	

	




}
