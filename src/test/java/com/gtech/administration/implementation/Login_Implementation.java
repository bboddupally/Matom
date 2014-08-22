/**
 * 
 */
package com.gtech.administration.implementation;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

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
		
			autoutil.clickButton(dataSheet(credentials,"button","sign"),propFile("link"));
		for(Entry<String, String> entry : dataSheet(credentials, "dropdown").entrySet())
			autoutil.dropDown(entry.getKey(), entry.getValue());
		for(Entry<String, String> entry : dataSheet(credentials, "date").entrySet())
			autoutil.selectDate(entry.getKey(), entry.getValue());
		for(Entry<String, String> entry : dataSheet(credentials, "field").entrySet())
			autoutil.verifyingLabelsAndTextTheFields(entry.getKey(), entry.getValue());
		for(Entry<String, String> entry : dataSheet(credentials, "checkbox").entrySet())
			autoutil.selectcheckBox(entry.getValue());

	}
	
	
	

}
