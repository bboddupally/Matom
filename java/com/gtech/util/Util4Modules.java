package com.gtech.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javax.xml.xpath.XPath;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.stringtemplate.v4.compiler.STParser.list_return;

public class Util4Modules {
	
	
	public WebDriver driver =SelectingWebDriver.getInstance();
	
	
	/*public static Logger log(){
		
		Logger loge = null;
		loge=Logger.getLogger(DataSource.extractSimpleClassName(Thread.currentThread().getStackTrace()[2].getClassName())+".class");
		//loge.setLevel(Level.ERROR);
		loge.setLevel(Level.INFO);
		try {
			//loge.addAppender(new FileAppender(new HTMLLayout(),"HTMLLOGS.html"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loge;
	}*/
	// This method is used for loading the page 
	static public ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) {
		return new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement returnWebElement = driver.findElement(locator);
				if (returnWebElement.isDisplayed()) {
					return returnWebElement;
				}
				return null;
			}
		};
	}
	DataSource dd = new DataSource();
	public static Util4Modules autoutil;
	public static  Util4Modules getInstance(){
		if(autoutil==null){
			autoutil= new Util4Modules();
		}
		return autoutil;
	}

	LinkedHashMap<String, LinkedHashMap<String, String>> 
	loginCredentials=null;

	public void copyloginCredentials(LinkedHashMap<String, LinkedHashMap<String, String>> credentials){
		loginCredentials=credentials;
	}
	/*public String fileAbsolutePath(String filename){
		File file = null;

		file=new File(DataSource.localhost+"/Data/"+filename);
		return file.getAbsolutePath();
	}*/

	//Accept Alert popUp
	public void acceptAlert(){
		try{
			WebElement alert =driver.switchTo().activeElement();
			alert.click();

		}catch(Exception e){
		}
	}
	public void acceptAlertwithEnter(){
		try{
			WebElement alert =driver.switchTo().activeElement();
			alert.sendKeys(Keys.ENTER);

		}catch(Exception e){
		}
	}
	public void acceptAlerts() {
		try{
			Alert alert =driver.switchTo().alert();
			alert.accept();
		}catch(Exception e){
		}
	}
	public void activeElement(){
		if(driver!=null){
			try{
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id='popoup_dialogDiv']//a/span[contains(text(),'close')]")).click();
				acceptAlerts();
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	public void selectOptions(String valueToSelect,String path){
		WebElement ele=null;
		List<WebElement> element = driver.findElements(By.xpath(path));
		
		if(element.size()!=0){
			for (WebElement webElement : element) {
				if(webElement.isDisplayed()){
					ele=webElement;
					break;
				}
			}
				
				for(WebElement option:new Select(ele).getOptions()){
					if(option.getText().equals(valueToSelect)){
						option.click(); 
						break;
					}
				}
		}else{
			Asserting.verifyEquals("null", valueToSelect);
		}
	
	}
	
	public void checkbox(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String path){
		Select s=new Select(driver.findElement(By.xpath(path)));
		for (Entry<String, String> entry2 : dataSheet(credentials,"checkbox").entrySet()) {
			for (WebElement option : driver.findElement(By.xpath(path)).findElements(By.tagName("checkbox"))) {
				if(entry2.getValue().equals(option.getText())){
					s.selectByVisibleText(entry2.getValue());
				}
			}
		}
	}
	
	public void clickOn(String path){
		Asserting.assertTrue(driver.findElement(By.xpath(path)).isDisplayed());
		driver.findElement(By.xpath(path)).click();
	}
	public void clickLink(String name){
		driver.findElement(By.linkText(name)).click();

	}

	public void clickButton(String path){
		
		List<WebElement> l1=driver.findElements(By.xpath(path));
		if(l1.size()!=0){
			for(WebElement el:l1){
				if(el.isDisplayed())
				{
					el.click();
					break;
				}
			}
		}
		else{
			Asserting.assertTrue(false);
		}
			//javaScriptToKillPopUP();
			//waitUntillDisplayed();
			
	}
	
	public void clickLoginButton(String path){
		try {
			sleep();
			driver.findElement(By.xpath(path)).click();
				
				try{
					Alert alert =driver.switchTo().alert();
					alert.accept();
				}catch(Exception e){
				}
			popUpClickOK();
			clickOnAlert();
			javaScriptToKillPopUP();
			waitUntillDisplayed();
		} catch (Exception e) {

		}
}
	
	/**
	 * 
	 */
	public void javaScriptToKillPopUP() {
		try {
			( ( JavascriptExecutor )driver ).executeScript( "window.onbeforeunload = function(e){};" );
		} catch (Exception e) {
		}
	}
	public void clickDisplayedButton(String button,String path){
		path=(path).replace("CONSTANT",button);
		if(isExists(path)){
			List<WebElement> list =driver.findElements(By.xpath(path));
			for (WebElement webElement : list) {
				if(webElement.isDisplayed()){
					webElement.click();
					popUpClickOK();
					waitUntillDisplayed();
					break;
				}
			}
		}
		
}
	
	public int countDisplayedElement(String path){
		int count =0;
		if(isExists(path)){
			
			List<WebElement> list =driver.findElements(By.xpath(path));
			for (WebElement webElement : list) {
				if(webElement.isDisplayed()){
					count++;

				}
			}
			
		}
		return count;
}
	
	public void simpleButton(String button,String path){
		sleep();
		if(driver.findElement(By.xpath((path).replace("CONSTANT",button))).isDisplayed()){
			driver.findElement(By.xpath((path).replace("CONSTANT",button))).click();
			try{
				driver.switchTo().alert().accept();
				loading(10);
			}catch(Exception e){
			}

		}else{
			Asserting.assertEquals(null, button);
			System.out.println("unable to find the location of :"+ button);
		}
		
		
	}
	
	public void clickButtonInTable(String headerName,String actionButton){
		if(actionButton!=null && headerName!=null )
		{
			String xpath=propFile("AEDButtonInTable").replace("CONSTANT",headerName).replace("REPLACE", actionButton);
			String xpath2=propFile("AEDButtonInTable2").replace("CONSTANT",headerName).replace("REPLACE", actionButton);

			if(isExists(xpath)){
				driver.findElement(By.xpath(xpath)).click();
				
				popUpClickOK();
			}else if(isExists(xpath2)){
				
				driver.findElement(By.xpath(xpath2)).click();
				popUpClickOK();
			}else{
				Asserting.assertEquals(null, headerName);
			}
		}else{
			if(headerName==null)
			Asserting.assertEquals(null, headerName);
			
			if(actionButton==null)
				Asserting.assertEquals(null, actionButton);
		}
		
	}
	/**
	 * 
	 */
	private void popUpClickOK() {
		if(isExists(propFile("popupClick"))){
			driver.findElement(By.xpath(propFile("popupClickOK"))).click();
			waitUntillDisplayed();
		}
	}
	
	public void navigateBack()
	{
		driver.navigate().back();
	}
	public void navigateForward(){
		driver.navigate().forward();
	}
	
	public void clickButton(String button,String path){

			sleep();
			String xpath=(path).replace("CONSTANT",button);
			
			List<WebElement> l1=driver.findElements(By.xpath(xpath));
			if(l1.size()!=0){
				for(WebElement el:l1){
					if(el.isDisplayed())
					{
						el.click();
						break;
					}
				}
			}else{
				Asserting.assertEquals(null, button);
			}
			
			
			
			
			
		/*	
			if(driver.findElement(By.xpath((path).replace("CONSTANT",button))).isDisplayed()){
				driver.findElement(By.xpath((path).replace("CONSTANT",button))).click();
				
				System.out.println(path);
				try{
					Alert alert =driver.switchTo().alert();
					alert.accept();
				}catch(Exception e){
				}
				
				
			}else{
				System.out.println(path);

				
				System.out.println("unable to find the location of :"+ button);
			}*/
			
		
	}
	/**
	 * 
	 */
	private void clickOnAlert() {
		acceptAlert();
		waitUntillDisplayed();
		acceptAlertwithEnter();
		try{
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			driver.switchTo().alert();
			driver.findElement(By.name("Biotracker")).sendKeys(Keys.ENTER);
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			
			
		}catch(Exception e){
		}
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
	}
	public void clickButtonforPopUp(String button,String path){
		if(driver.findElement(By.xpath((path).replace("CONSTANT",button))).isDisplayed()){
			driver.findElement(By.xpath((path).replace("CONSTANT",button))).click();
			try{
				Alert alert =driver.switchTo().alert();
				alert.accept();
			}catch(Exception e){
			}
			
			
		}
	}
	
	
	public void clickTab(String tab,String path){
		if(isExists(path.replace("CONSTANT",tab))){
			if( driver.findElement(By.xpath((path).replace("CONSTANT",tab))).isDisplayed()){
				driver.findElement(By.xpath((path).replace("CONSTANT",tab))).click();
			}else if(isExists("//*[@id='tabsNext']")&& driver.findElement(By.xpath("//*[@id='tabsNext']")).isDisplayed()){
					driver.findElement(By.xpath("//*[@id='tabsNext']")).click();
					if( driver.findElement(By.xpath((path).replace("CONSTANT",tab))).isDisplayed()){
						driver.findElement(By.xpath((path).replace("CONSTANT",tab))).click();
					}else if(isExists("//*[@id='tabsNext']")&& driver.findElement(By.xpath("//*[@id='tabsNext']")).isDisplayed()){
						driver.findElement(By.xpath("//*[@id='tabsNext']")).click();
						if( driver.findElement(By.xpath((path).replace("CONSTANT",tab))).isDisplayed()){
							driver.findElement(By.xpath((path).replace("CONSTANT",tab))).click();
						}else{
							Asserting.assertEquals(null, tab);
						}
						
					}
					
				}else{
					Asserting.assertEquals(null, tab);
				}
		}else{
			Asserting.assertEquals(null, tab);
		}
		
		
	}
	
	public void checkbox(String field,String label){
		String xpath=propFile("fieldset").replace("CONSTANT", field);
		String xpath2=propFile("label_withCheckBox").replace("CONSTANT", label);
		if(!driver.findElement(By.xpath(xpath+xpath2)).isSelected()){
			driver.findElement(By.xpath(xpath+xpath2)).click();
		}
	}
	public void checkbox(String label){
		String xpath2=propFile("label_withCheckBox").replace("CONSTANT", label);
		if(isExists(xpath2)){
			if(!driver.findElement(By.xpath(xpath2)).isSelected()){
				driver.findElement(By.xpath(xpath2)).click();
			}
		}else{
			Asserting.assertEquals("null", label);
		}
		
	}
	
	public boolean verify(String path){
		waitUntillDisplayed(path);
	loading(30);
		if(isExists(path)){
			return true;
		}else{
	Asserting.assertEquals(false,true);
		}
		return	false;
	}

	public void simpleclickButton(String button,String path){
		if(driver.findElement(By.xpath((path).replace("CONSTANT",button))).isDisplayed()){
			driver.findElement(By.xpath((path).replace("CONSTANT",button))).click();

		}else{
			System.out.println("unable to find the location of :"+ button);
		}
		//waitUntillDisplayed();
	}

	public void deleteRecords(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials) {
		selectCheckBoxes_in_Form_in_multipleForms(credentials, "select",propFile("modulelist"));
		clickButton(dataSheet(credentials, "button", "delete"), propFile("AEDButton"));
	}

public void verifyPage(String pageTitle,String xpathid,String headerName){
	String title=driver.getTitle().trim();
	String xpath=xpathid.replace("CONSTANT", headerName);
	if(!(title.equals(pageTitle))){
		Asserting.assertEquals(title, pageTitle);
	}else if(!isDisplayed(xpath)){
		Asserting.assertEquals(null, headerName);
	}
		
}

	// this is  generic method in selecting the checkboxes 
	public boolean clickCheckBoxInTablewithDynamicXpath(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials,
			String primaryKey, String id,  int x,String xpath_of_tableColum,String selectCheckBox,String selectCheckBoxName) {
		boolean result=false;
		String xpath2=null;
		xpath2=(xpath_of_tableColum.replace("CONSTANT", Integer.toString(x))).replace("REPLACE", id);
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int count =getCount(xpath2);
		for (Entry<String, String> entry2 : dataSheet(credentials,primaryKey).entrySet()){
			for(int i=1;i<=count ;i++){
					if(selectElementToClick(id, x, entry2, i,selectCheckBoxName)){
						sleep();
						driver.findElement(By.xpath((selectCheckBox.replace("CONSTANT", Integer.toString(i))).replace("REPLACE", id))).click();
						result=true;
				}
			}
		}
		return result;
	}


	public void loading(){
		for(int i=0;i<100;i++){
			sleep();
			sleep();
			sleep();
		}

	}
	public void loading(int time){
		for(int i=0;i<100;i++){
			sleep();
			if(i==time)
				break;
		}
	}
	
	public void loading(String path){
		WebDriverWait wait = new WebDriverWait(driver, 120); // wait for a maximum of 5 seconds
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));
	}
	
	public void selectdropdowninTable(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,
			String primaryKey, String id,  int x,boolean hasHyperlink,int columnNum){
		String xpath2=null;
		xpath2=(propFile("xpath_of_tableColum").replace("CONSTANT", Integer.toString(x))).replace("REPLACE", id);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		int count =getCount(xpath2);
		for (Entry<String, String> entry2 : dataSheet(credentials,primaryKey).entrySet()){
			for(int i=1;i<=count ;i++){
				if(entry2.getKey().trim().equals(driver.findElement(By.xpath((propFile("selectCheckBoxName").replace("/a", "").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x) )).replace("REPLACE", id))).getText().trim())){
					new Select(driver.findElement(By.xpath(propFile("selectCheckBoxName").replace("/a", "/select").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(columnNum)).replace("REPLACE", id)))).selectByVisibleText(entry2.getValue());
				}
			}
		}
	}

	public boolean clickCheckBoxInTable_InSpecific(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials,
			String primaryKey, String id,  int x,boolean hasHyperlink) {
		boolean result=false;
		String xpath2=null;
		xpath2=(propFile("special_xpath_of_tableColum").replace("CONSTANT", Integer.toString(x))).replace("REPLACE", id);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		int count =getCount(xpath2);

		try {
			for (Entry<String, String> entry2 : dataSheet(credentials,primaryKey).entrySet()){
				for(int i=1;i<=count ;i++){
					if(hasHyperlink){
						boolean isItText=true;
						String elementName= null;
						try {
							driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

							elementName=driver.findElement(By.xpath((propFile("special_selectCheckBoxName").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x) )).replace("REPLACE", id))).getText().trim();
							
						} catch (Exception e) {
							driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
							isItText=false;
						}
						if(isItText)
						if(entry2.getValue().trim().equals(elementName)){
							sleep();
							driver.findElement(By.xpath((propFile("special_selectCheckBox").replace("CONSTANT", Integer.toString(i))).replace("REPLACE", id))).click();
							result=true;
						}
					}else{
						if(entry2.getValue().trim().equals(driver.findElement(By.xpath((propFile("special_selectCheckBoxName").replace("/a", "").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x) )).replace("REPLACE", id))).getText().trim())){
							sleep();
							driver.findElement(By.xpath((propFile("special_selectCheckBox").replace("CONSTANT", Integer.toString(i))).replace("REPLACE", id))).click();
							result=true;
						}

					}

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
System.out.println("Exception occured in method > clickCheckBoxInTable_InSpecific ");
		}
		return result;
	}


	public String driverType(){
		String s=null;
	 s = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
		if(s.contains("Firefox")){
			return "fireFox";
		}else if(s.contains("MSIE 7.0")){
			return "iExplorer";
		}
		
		return s;
		
	}
	

	public boolean clickCheckBoxInTable_In_ShareTab(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials,
			String primaryKey, String id) {
		int x=4;

		boolean result=false;
		String xpath2=null;
		String xpath3=null;
		xpath2=(propFile("xpath_of_tableColum").replace("CONSTANT", Integer.toString(x))).replace("REPLACE", id);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		int count =getCount(xpath2);
		for (Entry<String, String> entry2 : dataSheet(credentials,primaryKey).entrySet()){
			for(int i=1;i<=count ;i++){
				xpath3=(propFile("selectCheckBoxName").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x) )).replace("REPLACE", id);
				if(entry2.getKey().trim().equals(driver.findElement(By.xpath(xpath3.replace("/a", "").trim())).getText().trim())){
					sleep();
					driver.findElement(By.xpath((propFile("selectCheckBox").replace("CONSTANT", Integer.toString(i))).replace("REPLACE", id))).click();
					List<WebElement> elements=driver.findElements(By.xpath(xpath3.replace("/a", "")+"/following-sibling::*/select/option"));
					for (WebElement option : elements)
					{
						if(entry2.getValue().trim().equalsIgnoreCase(option.getText())){
							option.click();
							break;
						}
					}				
					result=true;
				}
			}
		}
		return result;
	}
	public boolean clickOn_hyperlink(String element,String id){
		boolean result=false;
		int x=getIndex(propFile("xpath_of_indexRow").replace("REPLACE", id),"ID");
		int count =getCount((propFile("xpath_of_tableColum").replace("CONSTANT", Integer.toString(x))).replace("REPLACE", id));
		for(int i=1;i<=count ;i++){
			if(element.trim().equals(driver.findElement(By.xpath((propFile("selectCheckBoxName").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x) )).replace("REPLACE", id))).getText().trim())){
				sleep();
				driver.findElement(By.xpath((propFile("selectCheckBoxName").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x) )).replace("REPLACE", id))).click();
				result=true;
				break;
			}
		}
		if(result==false){
			System.out.println("unable to find the element in the table :"+element);
		}
		return result;
	}
	public boolean clickOnhyperlinkin_InTab(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials,
			String primaryKey, String id,  int indexOfElement) {
		boolean result=false;
		String xpath2=null;
		xpath2=(propFile("xpath_of_tableColum").replace("CONSTANT", Integer.toString(indexOfElement))).replace("REPLACE", id);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		int count =getCount(xpath2);
		for (Entry<String, String> entry2 : dataSheet(credentials,primaryKey).entrySet()){
			for(int i=1;i<=count ;i++){

				if(selectElementToClick(id, indexOfElement, entry2, i,propFile("selectCheckBoxName"))){
					sleep();
					driver.findElement(By.xpath((propFile("selectCheckBoxName").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(indexOfElement) )).replace("REPLACE", id))).click();					result=true;
				}
			}
		}
		return result;
	}

	private LinkedHashMap<String, String> dataSheet(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String parentKey){
		return credentials.get(parentKey.trim());
	}

	private String dataSheet(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String parentKey,String chieldKey){
		return credentials.get(parentKey.trim()).get(chieldKey.trim());
	}

	public void defaultActivefiltershouldbeselected(){
		Asserting.verifyEquals(driver.findElement(By.xpath("//*[@id='querie1']")).getAttribute("class"), "active");
	}

	
	//helps in selection of mouse over or to click on the element 
	private boolean extracted(String xpath,String mouseover) {
		boolean exists=false;
		boolean exist;
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Actions builder = new Actions(driver);
		exist = isExists(xpath);
		if(exist){
			WebElement tagElement = driver.findElement(By.xpath(xpath));
			if(tagElement.isDisplayed()){
				if(mouseover!=null){
					if(mouseover.contains("false")||mouseover.contains("False")){
						
						loading(30);
						tagElement.click();
					}else{
						findReports(xpath, builder, tagElement);
					}
				}else{
					findReports(xpath, builder, tagElement);
				}
				exists=true;	
			}
		}
		return exists;
	}

	//fill the fields
	public void fillFields(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String path){

		//	String callerMethodName  = new Throwable().getStackTrace()[1].getMethodName();
		for (Entry<String, String> entry2 : dataSheet(credentials,"field").entrySet()) {
			try{
				driver.findElement(By.xpath((path).replace("CONSTANT",propFile(entry2.getKey())))).sendKeys(entry2.getValue());
			}catch(Exception e){}
		}
	}


	// verification of labels 
	public void findLabels(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String adduserPage){
		new Throwable().getStackTrace()[1].getMethodName();
		try{
			for (Entry<String, String> entry : dataSheet(credentials,"label").entrySet()) {
				Asserting.verifyTrue(adduserPage.contains(dataSheet(credentials,"label").get(entry.getKey())));
			}
		}catch(Exception e){;
		}		
	}
	private void findReports(String xpath, Actions builder, WebElement tagElement) {
		placingTheMouseOverAnElement(builder, tagElement);
		if(xpath.contains("Reports")){
			placingTheMouseOverAnElement(builder, driver.findElement(By.xpath(propFile("toolbarspanfor_Reports").replace("CONSTANT", "Location Reports"))));
		}
	}

	/**
	 * @return
	 */
	public int getCount(String xpath) {
		int x=0;
		if(isExists(xpath)){
			List<WebElement> list= driver.findElements(By.xpath(xpath));
			x=list.size();
		}
		return x;
	}
	private List<WebElement> getElements(String xpath) {
		List<WebElement> list=null;
		if(isExists(xpath)){
			list= driver.findElements(By.xpath(xpath));
		}
		return list;
	}
	private int getIndex(String xpath,String element){
		int z=0;
		try {
			List<WebElement> list2= getElements(xpath);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			for(WebElement x:list2){

				if(x.getText().trim().equals(element.trim())){
					z=list2.indexOf(x);
					z=z+1;
				}
			}
		} catch (Exception e) {
Asserting.assertEquals(null, element);
		}
		return z;
	}


	/**
	 * @param id
	 * @return
	 */
	public int getIndexOfElement(String id,String element) {
		String xpath=null;
		xpath=propFile("xpath_of_indexRow").replace("REPLACE", id);
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		int x=getIndex(xpath,element.trim());
		return x;
	}

	public int getIndexOfElement_InSpecific(String id,String element) {
		String xpath=null;
		xpath=propFile("special_xpath_of_indexRow").replace("REPLACE", id);
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		int x=getIndex(xpath,element.trim());
		return x;
	}

	public String getText(String path){
		if(isExists(path)){
			return	driver.findElement(By.xpath(path)).getText();	
		}else{
			return null;
		}

	}
	public void hyperlink(String path){
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		//	new WebDriverWait(driver, 0).until(visibilityOfElementLocated(By.xpath(path)));
		//*[@id='moduleList']/tbody/tr[1]/td[4]
		sleep();
		driver.findElement(By.xpath("//*[@id='moduleList']/tbody/tr[1]/td[4]/a")).click();	
	}
	//this is super it helps u a lot 
	//this is used for verifying element existence 
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isExists(String xpath) {
		boolean exist;
		loading(10);
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		exist=((driver.findElements(By.xpath(xpath)).size()!=0));
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return exist;
	}
	
public void isMessageExists(String xpath ,String message){
	if(isExists(xpath.replace("CONSTANT", message))){
		Asserting.assertTrue(true);
	}else{
		Asserting.verifyEquals("null", message);
	}
}
	

	public boolean isDisplayed(String xpath) {
		boolean exist;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		exist=driver.findElement(By.xpath(xpath)).isDisplayed();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return exist;
	}
	
	public boolean isSelected(String xpath) {
		boolean exist;
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		exist=driver.findElement(By.xpath(xpath)).isSelected();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return exist;
	}

	public boolean isTextPresent(String path,String expectedresult){
		boolean exist =false;
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		loading(path);
		Asserting.assertEquals(driver.findElement(By.xpath(path)).getText(),expectedresult);
		if(driver.findElement(By.xpath(path)).getText().equals(expectedresult)){
			exist=true;
		}
		return exist;
	}
	public void isTitleExist(String expectedresult){
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		if(isExists("//title")){
			System.out.println(driver.findElement(By.xpath("//title")).getText());
			Asserting.assertEquals(driver.findElement(By.xpath("//title")).getText().trim(),expectedresult);
		}else{
			Asserting.assertEquals("null",propFile(expectedresult));

		}
	}
	
	public void isHeaderExists(String path,String data){
	if(path.contains("CONSTANT")){
		path=path.replace("CONSTANT", data);

	}
	
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		if(isExists(path)){
			System.out.println(driver.findElement(By.xpath(path)).getText());
			Asserting.assertEquals(driver.findElement(By.xpath(path)).getText().trim(),data);
		}else{
			Asserting.assertEquals("null",data);
		}
	}
	
	
	
	//search method
	/*public void labelwithsearchOption(String labelName,String path){
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		String xpath=null;
		if(path==null){
			xpath=propFile("label_withbutton");
		}else{
			xpath=path+propFile("label_withbutton");
		}
		xpath=propFile("label_withbutton");
		driver.findElement(By.xpath(xpath.replace("CONSTANT", labelName))).click();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}*/
	
	//search and clear options
	public void labelwithsearchOption(String labelName,String action){
		String xpath=propFile("label_withbutton").replace("CONSTANT", labelName).replace("REPLACE", action);
		if(isExists(xpath)){
			try {
				driver.findElement(By.xpath(xpath)).click();
			} catch (Exception e) {
Asserting.assertEquals(null, labelName);
			}
		}else{
			Asserting.assertEquals(null, labelName);
		}
		
		
	}

	public void labelwithsearchOption(String labelName,String action,Boolean requieredAssertion){
		String xpath=propFile("label_withbutton").replace("CONSTANT", labelName).replace("REPLACE", action);
		if(isExists(xpath)){
			driver.findElement(By.xpath(xpath)).click();
		}else{
			if(requieredAssertion){
				Asserting.assertEquals(null, labelName);
			}else{
				Asserting.verifyEquals(null, labelName);
			}
		}
		
		
	}
	public void labelwithsearchOption(String labelName,String action,Boolean requieredAssertion,String addpath){
		String xpath=null;
		xpath=propFile("label_withbutton").replace("CONSTANT", labelName).replace("REPLACE", action);
		if(isExists(addpath+xpath)){
			driver.findElement(By.xpath(addpath+xpath)).click();
		}else{
			if(requieredAssertion){
				Asserting.assertEquals(null, labelName);
			}else{
				Asserting.verifyEquals(null, labelName);
			}
		}
		
		
	}
	
	
	
	
	// click on the element using locators
	private void locate(String xpath) {
		if(isExists(xpath)){
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			driver.findElement(By.xpath(xpath)).click();
			try{
				driver.switchTo().alert().accept();
				loading(10);
			}catch(Exception e){
			}
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}else{
		
			try{
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				driver.findElement(By.xpath(xpath.trim())).click();
				driver.switchTo().alert().accept();
				loading(10);
			}catch(Exception e){
				Asserting.assertEquals("INVALIDPATH", "VALIDPATH");
			}
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
	
	}

	// to place mouse over the element in tool bar if mouseover is false then we will click on the element
	public void mouseOver(String element,String mouseover,String elementwithnospan){
		String xpath =propFile("toolbar").replace("CONSTANT",element);
		if(!extracted(xpath,mouseover)){
			if(!spanMouseOver(element,mouseover,elementwithnospan)){
				Asserting.assertEquals(null, element);	
			}
		}
	}
	//using action to place the courser at defined element position 
	public void placingTheMouseOverAnElement(Actions builder, WebElement tagElement) {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Action s=builder.moveToElement(tagElement).build();

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		try {
			Thread.currentThread();
			Thread.sleep(100);
			s.perform();
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			Thread.currentThread();
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}


	}

	public void waitTime(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String propFile(String propertiesName){
		return DataSource.map.get(propertiesName.trim());
	}

	public String randomString()
	{
		int length=9;
		Random rng = new Random();
		String characters="abcdefghijklmnopqrstuvwxyz";
		char[] text = new char[length];
		for (int i = 0; i < length; i++)
		{
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}
	public SecureRandom random = new SecureRandom();	
	public String randomStringwithNumbers()
	{
    return new BigInteger(50, random).toString(32);

	}

	public void replaceKeyValue(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String parentKey,String chieldKey,String replacevalueofakey){

		dataSheet(credentials, parentKey,chieldKey);
		credentials.get(parentKey).put(chieldKey, replacevalueofakey);

	}
	
	public boolean  simplifiedClickCheckBoxInTable(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String primaryKey,String tablePathid,String id,Boolean hasHyperlink ){
	return	clickCheckBoxInTable(credentials, primaryKey, tablePathid,getIndexOfElement(tablePathid,id), hasHyperlink);
	}

	
	//generic check box in a table
	public boolean clickCheckBoxInTable(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials,
			String primaryKey, String id,  int x,boolean hasHyperlink) {
		unCheckAllCheckBoxes();
		boolean result=false;
		String xpath2=null;

		xpath2=(propFile("xpath_of_tableColum").replace("CONSTANT", Integer.toString(x))).replace("REPLACE", id);
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int count =getCount(xpath2);
		ArrayList<String> arrayList = new ArrayList<String>();
		ArrayList<String> arrayList2 = new ArrayList<String>();
		if(hasHyperlink){
			for(int i=1;i<=count ;i++){
				if(isExists(propFile("selectCheckBoxName").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x)).replace("REPLACE", id))){
					arrayList.add(driver.findElement(By.xpath(propFile("selectCheckBoxName").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x)).replace("REPLACE", id))).getText().trim());
				}else{
					break;
				}
				}
		}else{
			for(int i=1;i<=count ;i++){
				if(isExists((propFile("selectCheckBoxName").replace("/a", "").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x) )).replace("REPLACE", id))){
					arrayList.add(driver.findElement(By.xpath((propFile("selectCheckBoxName").replace("/a", "").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x) )).replace("REPLACE", id))).getText().trim());
				}else{
					break;
				}
			}	
		}
		
		
		for(Entry<String, String> entry : dataSheet(credentials,primaryKey).entrySet()){
				if(!arrayList.contains(entry.getValue())){
					arrayList2.add(entry.getValue());
			}
		}
	
		for (Entry<String, String> entry2 : dataSheet(credentials,primaryKey).entrySet()){
			int sum=0;
			for(int i=1;i<=count ;i++){
				if(hasHyperlink){
					if(!selectElementToClick(id, x, entry2, i,propFile("selectCheckBoxName"))){
						
						if(arrayList2.contains(entry2.getValue())&& sum<1){
							sum++;
							Asserting.verifyEquals(null, entry2.getValue());
						}
					}else{
						sleep();
						driver.findElement(By.xpath((propFile("selectCheckBox").replace("CONSTANT", Integer.toString(i))).replace("REPLACE", id))).click();
						result=true;
						
					}
				}else{				
				
					if(!selectElementtoClickOn(id, x, entry2, i)){
						if(arrayList2.contains(entry2.getValue())&& sum<1){
							sum++;
							Asserting.verifyEquals(null, entry2.getValue());
						}
					}else{
						//sleep();
						driver.findElement(By.xpath((propFile("selectCheckBox").replace("CONSTANT", Integer.toString(i))).replace("REPLACE", id))).click();
						result=true;
					}
				}
			}
		}
		return result;
	}
	

	public boolean  simplifiedClickCheckBoxInTable(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String primaryKey,String tablePathid,String id,Boolean hasHyperlink,Boolean needAssertion ){
		
		loading(30);
		return	clickCheckBoxInTable(credentials, primaryKey, tablePathid,getIndexOfElement(tablePathid,id), hasHyperlink,needAssertion);
		}
	
public boolean  simplifiedClickCheckBoxInTable(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String primaryKey,String uniqueID,Boolean hasHyperlink,Boolean needAssertion ){
		
	String tablePathid =null;
	
	if(isExists(propFile("xpath_of_indexRow").replace("REPLACE", propFile("modulelist")))){
		tablePathid=propFile("modulelist");
	}else if(isExists(propFile("xpath_of_indexRow").replace("REPLACE", propFile("resultsList")))){
		tablePathid=propFile("resultsList");
	}else if(isExists(propFile("xpath_of_indexRow").replace("REPLACE", propFile("grouplistLeftID")))){
		tablePathid=propFile("grouplistLeftID");
		
	}else if(isExists(propFile("xpath_of_indexRow").replace("REPLACE", propFile("modules-tableid")))){
		tablePathid=propFile("modules-tableid");
	}else if(isExists(propFile("xpath_of_indexRow").replace("REPLACE", propFile("notifications-llRID")))){
		tablePathid=propFile("notifications-llRID");
	}
	else if(isExists(propFile("xpath_of_indexRow").replace("REPLACE", propFile("userGroupsList")))){
		tablePathid=propFile("userGroupsList");
	}
	
	//
	
	else{
		Asserting.assertEquals(null, "path","need to update the path");
	}
	return	clickCheckBoxInTable(credentials, primaryKey, tablePathid,getIndexOfElement(tablePathid,uniqueID), hasHyperlink,needAssertion);
		}
	
public void findTab(String tab) {
	for(int i=0;i<10;i++){
		if(driver.findElement(By.xpath(propFile("tabs_show").replace("CONSTANT", tab))).isDisplayed()){
			driver.findElement(By.xpath(propFile("tabs_show").replace("CONSTANT", tab))).click();
			break;
		}else if(driver.findElement(By.xpath("//*[@id='tabsNext']")).isDisplayed()){
			driver.findElement(By.xpath("//*[@id='tabsNext']")).click();
		}else{
			Asserting.assertEquals("null", tab);
			break;
		}
			
	}
}



	public boolean clickCheckBoxInTable(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials,
			String primaryKey, String id,  int x,boolean hasHyperlink,boolean needAssertion) {
		
		unCheckAllCheckBoxes();
		boolean result=false;
		String xpath2=null;

		xpath2=(propFile("xpath_of_tableColum").replace("CONSTANT", Integer.toString(x))).replace("REPLACE", id);
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		
		int count =getCount(xpath2);
		if(count==0){
			Asserting.assertEquals(null, id);
		}
		ArrayList<String> arrayList = new ArrayList<String>();
		ArrayList<String> arrayList2 = new ArrayList<String>();
		if(hasHyperlink){
			for(int i=1;i<=count ;i++){
				if(isExists(propFile("selectCheckBoxName").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x)).replace("REPLACE", id))){
					arrayList.add(driver.findElement(By.xpath(propFile("selectCheckBoxName").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x)).replace("REPLACE", id))).getText().trim());
				}else{
					break;
				}
				}
		}else{
			for(int i=1;i<=count ;i++){
				if(isExists((propFile("selectCheckBoxName").replace("/a", "").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x) )).replace("REPLACE", id))){
					arrayList.add(driver.findElement(By.xpath((propFile("selectCheckBoxName").replace("/a", "").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x) )).replace("REPLACE", id))).getText().trim());
				}else{
					break;
				}
			}	
		}
		
		
		for(Entry<String, String> entry : dataSheet(credentials,primaryKey).entrySet()){
				if(!arrayList.contains(entry.getValue())){
					arrayList2.add(entry.getValue());
			}
		}
	
		for (Entry<String, String> entry2 : dataSheet(credentials,primaryKey).entrySet()){
			int sum=0;
			for(int i=1;i<=count ;i++){
				if(hasHyperlink){
					if(!selectElementToClick(id, x, entry2, i,propFile("selectCheckBoxName"))){
						
						if(arrayList2.contains(entry2.getValue())&& sum<1){
							sum++;
							if(!needAssertion){
								Asserting.verifyEquals(null, entry2.getValue());
							}else{
								Asserting.assertEquals(null, entry2.getValue());

							}
						}
					}else{
						result = clickCheckBoxInTableIndex(id, result, i);
						
					}
				}else{				
				
					if(!selectElementtoClickOn(id, x, entry2, i)){
						if(arrayList2.contains(entry2.getValue())&& sum<1){
							sum++;
							if(!needAssertion){
								Asserting.verifyEquals(null, entry2.getValue());
							}else{
								Asserting.assertEquals(null, entry2.getValue());

							}
						}
					}else{
						//sleep();
						result = clickCheckBoxInTableIndex(id, result, i);
						
						
					}
				}
			}
		}
		return result;
	}
	/**
	 * @param id
	 * @param result
	 * @param i
	 * @return
	 */
	private boolean clickCheckBoxInTableIndex(String id, boolean result, int i) {
		for(int t=1;t<5;t++){
			String path=(propFile("selectCBoxwithTemp").replace("CONSTANT", Integer.toString(i))).replace("REPLACE", id).replace("TEMP", Integer.toString(t));
			if(isExists(path)){
				driver.findElement(By.xpath(path)).click();
				result=true;
				break;
			}
		}
		return result;
	}
	
	public void verifyIndexTiltles(LinkedHashMap<String, String> entry){
		WebElement table = driver.findElement(By.id("poolingTable"));
		List<WebElement> cells = table.findElements(By.xpath("//tr/th"));
		@SuppressWarnings("unused")
		String temp=null;
int i=0;
	if(entry.size()==cells.size()){
		for(Entry<String, String> entry2:entry.entrySet()){
			
			if(!entry2.getValue().equals(cells.get(i).getText())){
				Asserting.verifyEquals(cells.get(i).getText(), entry2.getValue());
			}
			i++;
		}
	}else{
		Asserting.verifyEquals("actual elements count :"+cells.size(), "expected elements count :"+entry.size());
	}
		
	}
	
	
	public void extractDataFromTable() {
		WebElement table = driver.findElement(By.id("poolingTable"));
		List<WebElement> cells = table.findElements(By.xpath("//tr/th"));
		for (WebElement cell : cells) {
		Object CellValue = cell.getText();
		System.out.println("Cellvalue :" +CellValue.toString());
		}
		
		List<WebElement> cells2 = table.findElements(By.xpath("//tr//td/input"));
		for (WebElement cell : cells2) {
		Object CellValue = cell.getAttribute("value");
		System.out.println("Cellvalue :" +CellValue.toString());
		}

		
			List<WebElement> cells3 = table.findElements(By.xpath("//tr//td/select/option"));
		for (WebElement cell : cells3) {
		Object CellValue = cell.getText();
		System.out.println("Cellvalue :" +CellValue.toString());

		}
	}
	
	
	
	
	
	
	public String GetTextFromTable(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String primaryKey, String id,  int uniqueID,int NonUniqueID,boolean hasHyperlink){
		String xx="dsfhk";
		unCheckAllCheckBoxes();
		String xpath2=null;

		xpath2=(propFile("xpath_of_tableColum").replace("CONSTANT", Integer.toString(uniqueID))).replace("REPLACE", id);
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int count =getCount(xpath2);
		ArrayList<String> arrayList = new ArrayList<String>();
		ArrayList<String> arrayList2 = new ArrayList<String>();
		if(hasHyperlink){
			for(int i=1;i<=count ;i++){
				if(isExists(propFile("selectCheckBoxName").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(uniqueID)).replace("REPLACE", id))){
					arrayList.add(driver.findElement(By.xpath(propFile("selectCheckBoxName").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(uniqueID)).replace("REPLACE", id))).getText().trim());
				}else{
					break;
				}
				}
		}else{
			for(int i=1;i<=count ;i++){
				if(isExists((propFile("selectCheckBoxName").replace("/a", "").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(uniqueID) )).replace("REPLACE", id))){
					arrayList.add(driver.findElement(By.xpath((propFile("selectCheckBoxName").replace("/a", "").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(uniqueID) )).replace("REPLACE", id))).getText().trim());
				}else{
					break;
				}
			}	
		}
		
		//////////////////////////////////////////////////////////
		
		for(Entry<String, String> entry : dataSheet(credentials,primaryKey).entrySet()){
			if(!arrayList.contains(entry.getValue())){
				arrayList2.add(entry.getValue());
		}
	}

		
	for (Entry<String, String> entry2 : dataSheet(credentials,primaryKey).entrySet()){
		int sum=0;
		for(int i=1;i<=count ;i++){
			if(hasHyperlink){
				
			findTextfromElementTable(id, uniqueID,i,propFile("selectCheckBoxName"));
				
				
				if(!selectElementToClick(id, uniqueID, entry2, i,propFile("selectCheckBoxName"))){
					
					if(arrayList2.contains(entry2.getValue())&& sum<1){
						sum++;
						Asserting.verifyEquals(null, entry2.getValue());
					}
				}else{
					sleep();
					driver.findElement(By.xpath((propFile("selectCheckBox").replace("CONSTANT", Integer.toString(i))).replace("REPLACE", id))).click();
					//result=true;
					
				}
			}else{				
			
				if(!selectElementtoClickOn(id, uniqueID, entry2, i)){
					if(arrayList2.contains(entry2.getValue())&& sum<1){
						sum++;
						Asserting.verifyEquals(null, entry2.getValue());
					}
				}else{
					//sleep();
					driver.findElement(By.xpath((propFile("selectCheckBox").replace("CONSTANT", Integer.toString(i))).replace("REPLACE", id))).click();
					//result=true;
				}
			}
		}
	}
		
		
		////////////////////////////////////////////////////
		
	
		
		
		return xx;
	}
	
	public String findTextfromElementTable(String id, int indexOfElement, int i,String selectCheckBoxName ) {
		return driver.findElement(By.xpath(selectCheckBoxName.replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(indexOfElement)).replace("REPLACE", id))).getText().trim();
	}
	
	
	
	
	
	
	
	

	public boolean  select(LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String type,String path,String selectall){
		boolean selected =false;
		Select select=new Select(driver.findElement(By.xpath(path)));
		ArrayList<String> arrayList = new ArrayList<String>();
		ArrayList<String> arrayList2 = new ArrayList<String>();
		
		for (WebElement option : driver.findElement(By.xpath(path)).findElements(By.tagName("option"))) {
			arrayList.add(option.getText());
		}
		int count=arrayList.size();
		select.deselectAll();
		if(arrayList.size()!=0){
			if((selectall==null)||(selectall.equals("true"))){
				for(int i=0;i<count ;i++){
					select.selectByVisibleText(arrayList.get(i));
				}
				selected=true;
				
			}else{
				for(Entry<String, String> entry : dataSheet(credentials,type).entrySet()){
					if(!arrayList.contains(entry.getValue())){
						arrayList2.add(entry.getValue());
				}
			}
				for (Entry<String, String> entry2 : dataSheet(credentials,type).entrySet()){
					int sum=0;
					
					for(int i=0;i<count ;i++){
							if(!entry2.getValue().equals(arrayList.get(i))){
									if(arrayList2.contains(entry2.getValue())&& sum<1){
										sum++;
										Asserting.verifyEquals(null, entry2.getValue());
									}
							}else{
								sleep();
								select.selectByVisibleText(entry2.getValue());
								selected=true;
							}	
					}
				}
			}

		}else{
			selected=false;
		}
		
		
		
		
		
		
		return selected;
	}
	
	public void selectallCheckBox(){
		List<WebElement> elements = driver.findElements(By.xpath(propFile("selecall_checkBox")));
		for (WebElement webElement : elements) {
			if(webElement.isDisplayed()){
				webElement.click();
			}
		}
	}
	
	public void unCheckAllCheckBoxes(){
		List<WebElement> elements = driver.findElements(By.xpath(propFile("selecall_checkBox")));
		new Actions(driver);
		for (WebElement webElement : elements) {
			if(webElement.isDisplayed()&&webElement.isSelected()){
				try {
					webElement.click();
				} catch (Exception e) {
				}
			}
		}
	}
	

	public boolean selectAll(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String path) {
		boolean result=false;
		int count =0;
		List<WebElement> list= driver.findElement(By.xpath(path)).findElements(By.className("cbox"));
		driver.findElement(By.xpath(propFile("selectCheckBox").replace("CONSTANT", Integer.toString(1)))).click();
		//ignoring i check box 
		if(list.size()>0){
			count =list.size()-1;	
		}
		System.out.println(count);
		for (Entry<String, String> entry2 : dataSheet(credentials,"select").entrySet()){
			for(int i=1;i< count ;i++){
				if(entry2.getValue().trim().equals(driver.findElement(By.xpath(propFile("selectCheckBoxName").replace("CONSTANT", Integer.toString(i)))).getText().trim())){
					driver.findElement(By.xpath(propFile("selectCheckBox").replace("CONSTANT", Integer.toString(i)))).click();
					result=true;
				}
			}
		}
		return result;
	}



	public void selectCheckBox(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials,
			int count,String parentKey ,String xpathforName,String xpathforcheckbox) {
		for (Entry<String, String> entry2 : dataSheet(credentials ,parentKey).entrySet()){
			for(int i=1;i<=count ;i++){
				if(entry2.getValue().trim().equals(driver.findElement(By.xpath(propFile(xpathforName).replace("CONSTANT", Integer.toString(i)))).getText().trim())){
					clickButton(propFile(xpathforcheckbox).replace("CONSTANT", Integer.toString(i)));	
				}
			}
		}
	}

	public boolean selectCheckBoxes_in_Form_in_multipleForms(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String primaryKey,String id) {
		int x = getIndexOfElement(id,"ID");
		return clickCheckBoxInTable(credentials, primaryKey, id, x,true);
	}

	public void selectDate(String month,String year,String date,String path){
		driver.findElement(By.xpath(path)).click();
		selectOptions(month, propFile("user_selectMonth"));
		selectOptions(year,propFile("user_selectYear")); 
		driver.findElement(By.linkText(date)).click();	
	}

	public static void main(String args[]){

//Util4Modules modules= new Util4Modules();
	System.out.println(Thread.currentThread().getStackTrace());	
new Util4Modules().test();

}
public void test(){
	System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
}
	public void selectDropdownOne_In_ShareTab(String id,String selectElement) {
		new Select(driver.findElement(By.id(id))).selectByVisibleText(selectElement);
	}
	public void selectDropdownwith_textfield(
			String labelName,String SelectingValue) {
		String path=propFile("labelvalue").replace("CONSTANT",labelName);
		verifyingDropdownPath(SelectingValue, path);
	}
	public void selectDropdownwith_textfield(
			String labelName,String SelectingValue,String xtrapath) {
		String path=xtrapath+propFile("labelvalue").replace("CONSTANT",labelName);
		verifyingDropdownPath(SelectingValue, path);
	}
	
	/**
	 * @param SelectingValue
	 * @param path
	 */
	public void verifyingDropdownPath(String SelectingValue, String path) {
		verifyDropdownGeneric(SelectingValue, path);
	}
	/**
	 * @param SelectingValue
	 * @param path
	 */
	private void verifyDropdownGeneric(String SelectingValue, String path) {
		ArrayList<Object> list = new ArrayList<Object>();
		if(path!=null){
			list=verifyResult(path);
			if((Boolean)list.get(0)){
				selectRightpath(SelectingValue, list);
				
			}
			else{
				list.clear();
				list=verifyResult(path.replace("[contains(.", "[contains(text()").replace("*')]", "')]"));
				if((Boolean)list.get(0)){
					selectRightpath(SelectingValue, list);
					}
				else{
						Asserting.assertEquals(null, SelectingValue);
				}
			}
		}else{
			Asserting.assertEquals("path =null", null);
		}
	}
	
	/**
	 * @param SelectingValue
	 * @param list
	 */
	private void selectRightpath(String SelectingValue, ArrayList<Object> list) {
		try {
			String bestpath=(String)list.get(1);
			selectElementsfromDropDown(SelectingValue, bestpath);
		} catch (Exception e) {
		System.out.println("Error occured at test");
		}
	}
	/**
	 * @param SelectingValue
	 * @param bestpath
	 */
	public void selectElementsfromDropDown(String SelectingValue,String bestpath) {
		List<WebElement> element= driver.findElements(By.xpath(bestpath));
		for(WebElement element2:element){
			if(element2.isDisplayed()){
				loading(10);
				try {
					driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
					new Select(element2).selectByVisibleText(SelectingValue);
				} catch (Exception e) {
					Asserting.verifyEquals("null", SelectingValue);				}
			}
		}
	}

	
	//game apis 
	
	
	//veriyandSelectbrand_partner
	
	
	public void verifyandSelectbrand_partner(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials) throws InterruptedException{
		if(!(returnResult(dataSheet(credentials, "label","brand")) && returnResult(dataSheet(credentials, "label","partner"))) ){
			Thread.sleep(100);
			clickButton(dataSheet(credentials, "select","admin"),propFile("span"));
			clickButton(dataSheet(credentials, "tabs","tab1"),propFile("link"));
			clickButton(propFile("gmsSpecialxpath2searchBrand"));
			clickButton(dataSheet(credentials, "label","brand"),propFile("gmsSpecialxpath2findBrand"));
			clickButton(propFile("gmsSpecialxpath2searchpartner"));
			clickButton(dataSheet(credentials, "label","partner"),propFile("gmsSpecialxpath2findpartner"));
			clickButton(dataSheet(credentials, "button","selectChange"),propFile("button"));
}

	}
	
	
	
	
	
	
	
	
	
	

	private boolean returnResult(String brand) {
		boolean BandPExist= false;
	
		try {
			driver.findElement(By.xpath("//div[@id='user-wrapper']//span//div/span[contains(.,'"+brand+"')]"));
					
			BandPExist= true;
		} catch (NoSuchElementException e) {
			BandPExist= false;
		}
		
		return BandPExist;
	}
	
	


	
	
	
	
	public void selectDate(String name, String selected){
		WebElement element2= null;
		
		List<WebElement> element = driver.findElements(By.name(name));
		
		if(element.size()!=0){
			for (WebElement webElement : element) {
				if(webElement.isDisplayed()){
					element2=webElement;
					break;
				}
				
			}
			
		  //  new Select(element2).selectByVisibleText(selected);
		    
		    for(WebElement option:new Select(element2).getOptions()){
				if(option.getText().equals(selected)){
					option.click(); 
					break;
				}
			}

		}
		else{
			Asserting.verifyEquals(null, name);
		}
		
		
			
		
		
		
		
		
		
		
			
	}
	
	public void dropDown(String label, String LabelValue){
		
		String path =propFile("dropDownLabel").replace("CONSTANT", label);
		WebElement element2= null;
		List<WebElement> element = driver.findElements(By.xpath(path));
		if(element.size()!=0){
			for (WebElement webElement : element) {
				if(webElement.isDisplayed()){
					element2=webElement;
					break;
				}
				
			}
			
		    new Select(element2).selectByVisibleText(LabelValue);

		}
		
		else{
			Asserting.verifyEquals(null,label);

		}
		//Gender

	}
	
	
	public void selectcheckBox(String label){
		driver.findElement(By.xpath(propFile("label").replace("CONSTANT",label))).click();
	}
	
	
	
	
	public void verifyDropdownText(String label,String labelValue){
		Asserting.verifyEquals( new Select(driver.findElement(By.xpath(propFile("labelvalue").replace("CONSTANT", label)))).getFirstSelectedOption().getText().trim(), labelValue);
					}
	
	
	public void selectElementInMultiModuleLayout(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials) {
		int count =0;
		List<WebElement> list= driver.findElement(By.xpath("//*[@id='moduleLayout']")).findElements(By.className("cbox"));
		//ignoring i check box 
		if(list.size()>0){
			count =list.size()-1;	
		}
		for (Entry<String, String> entry2 : dataSheet(credentials,"select").entrySet()){
			for(int i=1;i< count ;i++){
				if(entry2.getValue().equals(driver.findElement(By.xpath(propFile("selectCheckBoxName").replace("CONSTANT", Integer.toString(i)))).getText())){
					driver.findElement(By.xpath(propFile("selectCheckBoxName").replace("CONSTANT", Integer.toString(i)))).click();	
				}
			}
		}
	}
	/// Column 

	public boolean selectElementtoClickOn(String id, int x,
			Entry<String, String> entry2, int i) {
		return entry2.getValue().trim().equals(driver.findElement(By.xpath((propFile("selectCheckBoxName").replace("/a", "").replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(x) )).replace("REPLACE", id))).getText().trim());
	}

	public boolean selectElementToClick(String id, int indexOfElement,
			Entry<String, String> entry2, int i,String selectCheckBoxName ) {
		return entry2.getValue().trim().equals(driver.findElement(By.xpath(selectCheckBoxName.replace("CONSTANT", Integer.toString(i)).replace("TEMP",Integer.toString(indexOfElement)).replace("REPLACE", id))).getText().trim());
	}

	public void sendkeys(String path,String parmeters){
		boolean result=isExists(path);
		if(result){
			driver.findElement(By.xpath(path)).sendKeys(parmeters);
		}else{
			Asserting.assertTrue(result);
		}


	}
	public void sendkeys(String path,String element,String parmeters){
		String xpath=path.replace("CONSTANT", element);
		boolean result=isExists(xpath);
		if(result){
			driver.findElement(By.xpath(xpath)).sendKeys(parmeters);
		}else{
			Asserting.assertTrue(result);
		}
	}

	public void uploadFile(String label,String labelValue,String filename){
		
		//label[contains(., 'Protocol*')]/following-sibling::*[contains(.,'Browse')]
		String uploadfile=propFile("upload-File").replace("CONSTANT", label).replace("REPLACE", labelValue);
		System.out.println(uploadfile);
		System.out.println(filename);
		if(isExists(uploadfile)){
			driver.findElement(By.xpath(uploadfile)).click();
			driver.findElement(By.xpath(uploadfile)).sendKeys(filename);
			waitUntillDisplayed();
		}
	}
	
	

	
	
	public void sharetab(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials) {
		clickOn_hyperlink(dataSheet(credentials, "field", "id"),propFile("modulelist"));
		clickButton(dataSheet(credentials,"tabs","tab1"),propFile("tabs_show"));
		clickButton(dataSheet(credentials,"button","add"),propFile("actionbutton1").replace("CONSTANT", propFile("sharing_header"))+propFile("actionbutton2"));
		clickButton(propFile("label").replace("CONSTANT", "Share With*"));
		selectDropdownOne_In_ShareTab("entity", dataSheet(credentials, "label", "Share With*"));
		clickButton(propFile("label").replace("CONSTANT", "Share With*"));
		clickCheckBoxInTable_In_ShareTab(credentials, "checkbox", propFile("shareTab_xpathid"));
		clickButton(propFile("label").replace("CONSTANT", "Share With*"));
		clickButton(dataSheet(credentials ,"button","save"),propFile("popUp-xpathid")+ propFile("PNCSButton"));
		verifyResultMessage(propFile("saveMessage"));
		try{
			//new Select(driver.findElement(By.id("entity"))).selectByVisibleText("User Groups");
			//selectDroupdownOne_In_ShareTab(credentials, "label");

		}catch (Exception e) {

			//*[@id='popoup_dialogDiv']//a/span[contains(text(),'close')]
			driver.findElement(By.xpath("//*[@id='popoup_dialogDiv']//a/span[contains(text(),'close')]")).click();

			//driver.findElement(By.xpath("//*[@id='popoup_dialogDiv']")).sendKeys(Keys.ESCAPE);
			acceptAlerts();

		}
		clickCheckBoxInTable(credentials, "parameters", propFile("xpath_sharingtabid"), getIndexOfElement(propFile("xpath_sharingtabid"), "Name"),false);
	}
	@SuppressWarnings("static-access")
	public void sleep(){
		try {
			Thread.currentThread().sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// to select element that does not exist in toolbar if mouseover is false then we will click on the element
	public boolean spanMouseOver(String element,String mouseover,String elementwithnospan ){
		
		try {
			driver.findElement(By.xpath(propFile("selectMoreintoolbar"))).click();
		} catch (Exception e) {
			Asserting.assertEquals(null, element,"unable to operate span mouseOver Operation");
		}
		//Identifying the element that has no span 
		if(elementwithnospan!=null){
			if(elementwithnospan.contains("true")||elementwithnospan.contains("True")){
				String xpath =propFile("toolbarwithnospan").replace("CONSTANT",element.trim());
				return extracted(xpath,mouseover);
			}else{
				String xpath =propFile("toolbarwithspan").replace("CONSTANT",element.trim());
				return extracted(xpath,mouseover);
			}

		}else{
			String xpath =propFile("toolbarwithspan").replace("CONSTANT",element.trim());
			if(!isExists(xpath)){
				xpath=propFile("toolbarwithnospan").replace("CONSTANT",element.trim());
			}
			
			return extracted(xpath,mouseover);
		}
	}

	//Select span elements by clicking on the element
	public void spanSelect(String element){
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			String xpath = null;
			boolean exist;
			xpath=	propFile("toolbarspan").replace("CONSTANT", element.trim().replace("+", " "));
			loading(8);
			exist = isExists(xpath);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			if(exist){
				locate(xpath);
			}else{
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				xpath =propFile("toolbarspan").replace("CONSTANT", element.trim());
				loading(30);
				exist = isExists(xpath);
				if(exist){
					locate(xpath);
				
				}else{
					driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					//this is specially for "96 well plates"  
					xpath =propFile("toolbarspan").replace("CONSTANT", element.trim()+" ");
					locate(xpath);
				}
			}
			driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			waitUntillDisplayed();
	}

	public void toscrollDown(){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		loading(3);
        js.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight," +
        "document.body.scrollHeight,document.documentElement.clientHeight));");
	}
	public void maxRecords(){
		new Select(driver.findElement(By.xpath(propFile("xpath_of_mainForm_in_multipleForms")+propFile("selectdropdown")))).selectByVisibleText("500");
	}
	/*public void verifyingLabels(String labelname) {
		String path=propFile("labelvalue").replace("CONSTANT",labelname);
		verifyingDpath(labelname, null, path);
	}*/
	public void verifyingLabelsAndClickOnSpecificFields(String labelName, String labelValue,String addpath){	
		String path=propFile("labelvalue").replace("CONSTANT",labelName).replace("::*", "::"+addpath);
	if(isExists(path)){
		driver.findElement(By.xpath(path)).click();
	}
	}
	
public void verifyingLabelsAndTextTheFields(String labelName, String labelValue){	
	String path=propFile("labelvalue").replace("CONSTANT",labelName);
	verifyingDpath(labelName, labelValue, path);
}

public void verifyingLabelsAndTextTheFields(String labelName, String labelValue,boolean requiredAsserting){	
	String path=propFile("labelvalue").replace("CONSTANT",labelName);
	verifyingDpath(labelName, labelValue, path,requiredAsserting);
}

public void verifyingLabelsAndTextTheFields(String labelName, String labelValue,String addpath){	
	String path=addpath+propFile("labelvalue").replace("CONSTANT",labelName);
	verifyingDpath(labelName, labelValue, path);
}

public void verifyingLabelsAndTextTheFields(String labelName, String labelValue,String addpath,boolean requiredAsserting){	
	String path=addpath+propFile("labelvalue").replace("CONSTANT",labelName);
	verifyingDpath(labelName, labelValue, path,requiredAsserting);
}
//require 
public boolean verifyingLabelsAndClickCheckBox(String labelName,String path){
	String xpath=null;
	boolean result=true;
	WebElement element=null;
if(labelName!=null){
	try {
		xpath=path.replace("::*", "::input[@type='checkbox']").replace("CONSTANT", labelName);
	} catch (Exception e) {
		result=false;
		Asserting.verifyEquals(null, labelName);
	}
	if(result){
		element=driver.findElement(By.xpath(xpath));
		if(element.isSelected()){
			element.click();
		}else{
			try {
				element.click();
			} catch (Exception e) {
				Asserting.verifyEquals(null, labelName);
			}
		}
	}
}
	return result;
	}

public void verifyAndTextTheFields(String labelName, String labelValue){	
String xpath =propFile("label_pholder").replace("CONSTANT", labelName);

	List<WebElement> list =driver.findElements(By.xpath(xpath));
if(list.size()!=0){

	for(WebElement wl:list){
		if(wl.isDisplayed()){
			wl.sendKeys(labelValue);
		}
	}

}else{
	
	Asserting.verifyEquals("null", labelValue);

}
}


/**
 * @param labelName
 * @param labelValue
 * @param path
 */
private void verifyingDpath(String labelName, String labelValue, String path) {
	ArrayList<Object > list = new ArrayList<Object>();
	
	list=verifyResultpath(path);
	if((Boolean) list.get(0)&& labelValue!=null){
		chooseRightElement(labelValue, list);
		
	}
	else{
	
		list.clear();
		list=verifyResultpath(path.replace("contains(.,", "contains(text(),").replace("*')]", "')]"));
		if((Boolean) list.get(0)&& labelValue!=null){
			chooseRightElement(labelValue, list);
		}else{
			Asserting.verifyEquals(null, labelName);
		}
	}
}





/**
 * @param labelName
 * @param labelValue
 * @param path
 */
private void verifyingDpath(String labelName, String labelValue, String path,boolean requiredAsserting) {
	ArrayList<Object > list = new ArrayList<Object>();
	list=verifyResultpath(path);
	if((Boolean) list.get(0)&& labelValue!=null){
		chooseRightElement(labelValue, list);
		
	}else{
		list.clear();
		list=verifyResultpath(path.replace("contains(.,", "contains(text(),").replace("*')]", "')]"));
		if((Boolean) list.get(0)&& labelValue!=null){
			chooseRightElement(labelValue, list);
		}else{
			if(requiredAsserting==true){
				Asserting.assertEquals(null, labelName);
			}else{
				Asserting.verifyEquals(null, labelName);
			}
			
		}
	}
}

/**
 * @param labelValue
 * @param list
 */
private void chooseRightElement(String labelValue, ArrayList<Object> list) {
	WebElement bestpath=(WebElement) list.get(1);
	passValues(labelValue, bestpath);
}

private void passValues(String labelValue, WebElement bestpath) {
	try {
		bestpath.clear();

		bestpath.sendKeys(labelValue);
	} catch (Exception e) {
		bestpath.sendKeys(labelValue);

		// TODO: handle exception
	}
}	

/**
 * @param credentials
 */
public void insertDatainTable(
		 LinkedHashMap<String, String> credentialsforCheckbox,LinkedHashMap<String, String> credentialsforField ) {
	String path=null;

	if(isExists("//*[@id='mimicjqgrid']/tbody")){
		path="//*[@id='mimicjqgrid']/tbody";
	}else if(isExists("//*[@id='poolingTable']/tbody")){
		path="//*[@id='poolingTable']/tbody";
	}else{
		Asserting.assertEquals("Invalidpath", "validpath");
	}
		
		
	LinkedHashMap<String, ArrayList<String>> map = new LinkedHashMap<String, ArrayList<String>>();
	for(Entry<String, String> entry2:credentialsforCheckbox.entrySet()){
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(entry2.getValue(),",");
		while(tokenizer.hasMoreTokens()){
			list.add(tokenizer.nextToken());
		}
		map.put(entry2.getKey(), list);
	}
	
	int count=2;
	String editable=null;
	int countkey=0;
for(@SuppressWarnings("unused") Entry<String, String> entry:credentialsforField.entrySet()){

	for(Entry<String, String> entry2:credentialsforCheckbox.entrySet()){
		int i=	getIndex(path+"/tr[1]/th",entry2.getKey());
				
		if(isExists(path+"/tr["+count+"]/td["+i+"]/input")){
			editable=	driver.findElement(By.xpath(path+"/tr["+count+"]/td["+i+"]/input")).getAttribute("readonly");
			if(editable==null){
				if(getMapValue(map, countkey, entry2)!=null){
					driver.findElement(By.xpath(path+"/tr["+count+"]/td["+i+"]/input")).clear();	
					driver.findElement(By.xpath(path+"/tr["+count+"]/td["+i+"]/input")).sendKeys(getMapValue(map, countkey, entry2));	
				}
			}
				
		}else{
			Asserting.verifyEquals("InvalidPath", "validPath");
		}
		
		
		
	}
	count++;
	countkey++;
}

}

/**
 * @param map
 * @param countkey
 * @param entry2
 */
private String getMapValue(LinkedHashMap<String, ArrayList<String>> map,
		int countkey, Entry<String, String> entry2) {
	@SuppressWarnings("unused")
	String x=null;
	try {
		if(map.get(entry2.getKey()).get(countkey).startsWith("#")){
			return null;
		}else{
			return x=map.get(entry2.getKey()).get(countkey);			
		}

	} catch (Exception e) {
return		x=null;
		
	}
}


private String getMapValue(LinkedHashMap<String, ArrayList<String>> map,
		int countkey, String entry2) {
	@SuppressWarnings("unused")
	String x=null;
	try {
		if(map.get(entry2).get(countkey).startsWith("#")){
			return null;
		}else{
			return x=map.get(entry2).get(countkey);			
		}

	} catch (Exception e) {
return		x=null;
		
	}
}




public void selectDatainTable(
		 LinkedHashMap<String, String> credentialsforCheckbox,LinkedHashMap<String, String> credentialsfordropdown ) {
	String path=null;

	if(isExists("//*[@id='mimicjqgrid']/tbody")){
		path="//*[@id='mimicjqgrid']/tbody";
	}else if(isExists("//*[@id='poolingTable']/tbody")){
		path="//*[@id='poolingTable']/tbody";
	}else{
		Asserting.assertEquals("Invalidpath", "validpath");
	}
		
		
	LinkedHashMap<String, ArrayList<String>> map = new LinkedHashMap<String, ArrayList<String>>();
	for(Entry<String, String> entry2:credentialsforCheckbox.entrySet()){
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(entry2.getValue(),",");
		while(tokenizer.hasMoreTokens()){
			list.add(tokenizer.nextToken());
		}
		map.put(entry2.getKey(), list);
	}
	
	int count=2;

	int countkey=0;
for(@SuppressWarnings("unused") Entry<String, String> entry:credentialsfordropdown.entrySet()){

	for(Entry<String, String> entry2:credentialsforCheckbox.entrySet()){
		int i=getIndex(path+"/tr[1]/th",entry2.getKey());
	
		if(isExists(path+"/tr["+count+"]/td["+i+"]/select")){
				if(getMapValue(map, countkey, entry2)!=null){
					selectElementsfromDropDown(getMapValue(map, countkey, entry2), path+"/tr["+count+"]/td["+i+"]/select");
				}
				
		}else{
			Asserting.verifyEquals("inValidPath", "validPAth");
		}
		
	}
	count++;
	countkey++;
}

}




public void selectCheckboxINTable(
		 LinkedHashMap<String, String> credentialsforCheckbox ,String indexElement) {
	
	String path=null;
	String path2=null;

	int numOfrows = 0;
	if(isExists("//*[@id='gview_auditTrailConfigGrid']/div[2]/div/table/thead")){
		path2="//*[@id='gview_auditTrailConfigGrid']/div[2]/div/table/thead";
	}else{
		Asserting.assertEquals("Invalidpath", "validpath");
	}
	int toClickElement=getIndex(path2+"/tr[1]/th",indexElement);
	// need to update the test case replace "AutoMultiple " with some constant
	if(isExists("//*[@id='auditTrailConfigGrid']/tbody/tr")){
	 numOfrows=	countDisplayedElement("//*[@id='auditTrailConfigGrid']/tbody/tr/td[3 and contains(.,'AutoMultiple') ]");

	 path="//*[@id='auditTrailConfigGrid']/tbody";

	}else{
		Asserting.assertEquals("Invalidpath", "validpath");
	}
		
		
	LinkedHashMap<String, ArrayList<String>> map = new LinkedHashMap<String, ArrayList<String>>();
	LinkedHashMap<String, WebElement> map2 = new LinkedHashMap<String, WebElement>();
	@SuppressWarnings("unused")
	LinkedHashMap<String, ArrayList<String>> map3 = new LinkedHashMap<String, ArrayList<String>>();
	
	for(Entry<String, String> entry2:credentialsforCheckbox.entrySet()){
		
		String[] str=entry2.getKey().split("_");
		
		@SuppressWarnings("unused")
		String moduleName=str[0];
		String ModuleAction=str[1];
				
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(entry2.getValue(),",");
		while(tokenizer.hasMoreTokens()){
			list.add(tokenizer.nextToken().trim());
		}
		map.put(ModuleAction, list);
	
	//int indexofColm =path+"/tr["+iteraterow+"]/td["+i+"]"	
	
	for (int iteraterow = 1; iteraterow <=numOfrows; iteraterow++) {
		
	//for(Entry<String, String> entry2:credentialsforCheckbox.entrySet()){
		int i=	getIndex(path2+"/tr[1]/th",ModuleAction);
		
		if(isExists(path+"/tr["+iteraterow+"]/td["+i+"]")&&isExists(path+"/tr["+iteraterow+"]/td["+toClickElement+"]/input[@type='checkbox']")){
				
					map2.put(driver.findElement(By.xpath(path+"/tr["+iteraterow+"]/td["+i+"]")).getText(), driver.findElement(By.xpath(path+"/tr["+iteraterow+"]/td["+toClickElement+"]/input[@type='checkbox']")));
		
		
		}else{
			Asserting.verifyEquals("InvalidPath", "validPath");
		}

	
}
	int countkey=0;
	for (int iteraterow = 1; iteraterow <=numOfrows; iteraterow++) {		
		int i=	getIndex(path2+"/tr[1]/th",ModuleAction);
				
		
		if(isExists(path+"/tr["+iteraterow+"]/td["+i+"]")){
			String name=getMapValue(map, countkey, ModuleAction);
				if(name!=null){
					if(map2.get(name)!=null){
						map2.get(name).click();
					}else{
						Asserting.verifyEquals(null, name);
					}
				}
				countkey++;
		}else{
			Asserting.verifyEquals("InvalidPath", "validPath");
		}
	
	

}
	map2.clear();
}

}



public void selectCheckboxINTable2(
		 LinkedHashMap<String, String> credentialsforCheckbox ,String xpathid,String selectIndexToClick ) {
	String path=null;
	String path2=null;
	int numOfrows = 0;
	
	if(isExists("//*[@id='gview_"+xpathid+"']/div[2]/div/table/thead")){
		path2="//*[@id='gview_"+xpathid+"']/div[2]/div/table/thead";
	}else{
		Asserting.assertEquals("Invalidpath", "validpath");
	}
	
	
	if(isExists("//*[@id='"+xpathid+"']/tbody/tr")){
		
	 numOfrows=getCount("//*[@id='"+xpathid+"']/tbody/tr");
	 path="//*[@id='"+xpathid+"']/tbody";

	}else{
		Asserting.assertEquals("Invalidpath", "validpath");
	}
	
		
	LinkedHashMap<String, ArrayList<String>> map = new LinkedHashMap<String, ArrayList<String>>();
	LinkedHashMap<String, WebElement> map2 = new LinkedHashMap<String, WebElement>();

	for(Entry<String, String> entry2:credentialsforCheckbox.entrySet()){
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(entry2.getValue(),",");
		while(tokenizer.hasMoreTokens()){
			list.add(tokenizer.nextToken().trim());
		}
		map.put(entry2.getKey(), list);
	}
	
	for(String str2:selectIndexToClick.split(",")){
	for (int iteraterow = 1; iteraterow <=numOfrows; iteraterow++) {
		
	for(Entry<String, String> entry2:credentialsforCheckbox.entrySet()){
		int i=	getIndex(path2+"/tr[1]/th",entry2.getKey());
		if(isExists(path+"/tr["+iteraterow+"]/td["+i+"]")){
					if(!str2.startsWith("#")){
						map2.put(driver.findElement(By.xpath(path+"/tr["+iteraterow+"]/td["+i+"]")).getText(), driver.findElement(By.xpath(path+"/tr["+iteraterow+"]/td["+getIndex(path2+"/tr[1]/th",str2)+"]/input[@type='checkbox']")));
	
					}
		}else{
			Asserting.verifyEquals("InvalidPath", "validPath");
		}

	}
}
	int countkey=0;
	for (int iteraterow = 1; iteraterow <=numOfrows; iteraterow++) {
		
	for(Entry<String, String> entry2:credentialsforCheckbox.entrySet()){
		int i=	getIndex(path2+"/tr[1]/th",entry2.getKey());
				
		
		if(isExists(path+"/tr["+iteraterow+"]/td["+i+"]")){
			String name=getMapValue(map, countkey, entry2);
				if(name!=null){
					if(map2.get(name)!=null){
						map2.get(name).click();
					}else{
						Asserting.assertEquals(null, name);
					}
				}
				countkey++;
		}else{
			Asserting.verifyEquals("InvalidPath", "validPath");
		}
	}
	

}
	map2.clear();
	}
	

}



private ArrayList<Object> verifyResultpath(String path ){
	boolean result=true;
	WebElement element =null;
	ArrayList<Object> arrayList = new ArrayList<Object>();
	String xpath=null;
	//
	if((isExists(path.replace("::*", "::input[@type='text']")))){
		
		xpath=path.replace("::*", "::input[@type='text']");
		element=findDisplayedElement(arrayList, xpath);
		
		
	}
	else if((isExists(path.replace("::*", "::input")))){
		xpath=path.replace("::*", "::input");
		element=	findDisplayedElement(arrayList, xpath);

	}else if((isExists(path.replace("::*", "::textarea")))){
		xpath=path.replace("::*", "::textarea ");
		element=findDisplayedElement(arrayList, xpath);

	}else if((isExists(path.replace("::*", "::*//input")))){
		xpath=path.replace("::*", "::*//input");
		element=findDisplayedElement(arrayList, xpath);
	}	
	else if((isExists(path))&&(isDisplayed(path))){
		xpath=path;
	}else{
		result=false;
}

	arrayList.add(result);
	arrayList.add(element);
	return arrayList;
}
private WebElement findDisplayedElement(ArrayList<Object> arrayList, String xpath) {
	WebElement element=null;
	List<WebElement> element2 =driver.findElements(By.xpath(xpath));
	for(WebElement element3:element2){
		if(element3.isDisplayed()){
			try {
				 element=element3	;	
						} catch (Exception e) {
				//element2.sendKeys(labelValue);

			}
		}
	}
	return element;
	
}




private ArrayList<Object> verifyResult(String path ){
	loading(10);
	boolean result=true;
	ArrayList<Object> arrayList = new ArrayList<Object>();
	String xpath=null;
	if((isExists(path.replace("::*", "::option")))&&(isDisplayed(path.replace("::*", "::option")))){
		xpath=path.replace("::*", "::option");
	}else if((isExists(path.replace("::*", "::select")))&&(isDisplayed(path.replace("::*", "::select")))){
		xpath=path.replace("::*", "::select");
	}else if((isExists(path+"/option"))&&(isDisplayed(path+"/option"))){
		xpath=path+"/option";
	}else if((isExists(path.replace("::*", "::*/select")))&&(isDisplayed(path.replace("::*", "::*/select")))){
		xpath=path.replace("::*", "::*/select");
	}
	else if((isExists(path))&&(isDisplayed(path))){
		xpath=path;
	}else{
		result=false;
	}
	arrayList.add(result);
	arrayList.add(xpath);
	return arrayList;
}





public void waitUntillDisplayed(String path){
	int count=1000;
	waitUntillDisplay(path,count);

}

public void waitUntillDisplayed(String path,int count){
	waitUntillDisplay(path,count);
}
/**
 * @param path
 */
private void waitUntillDisplay(String path,int count) {
	boolean test=false;
	
	sleep();
	if(isExists(path)){
		for(int i=0;i<count;i++){
			sleep();
			test=driver.findElement(By.xpath(path)).isDisplayed();
			if(!test){
				break;
			}
		}	
	}
}

public void waitUntillExist(String path){
	boolean test=false;
	for(int i=0;i<1000;i++){
		sleep();
		test=isExists(path);
		if(!test){
			break;
		}
	}	
}


public void waitUntillDisplayed(){
	String path="//*[@id=\"load\"]";
	boolean test=false;
	sleep();
	if(isExists(path)){
		for(int i=0;i<10000000;i++){
			sleep();
			test=driver.findElement(By.xpath(path)).isDisplayed();
			if(!test){
				break;
			}
		}	
	}

}


	public void selectall(String id){
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		driver.findElement(By.xpath(propFile("selectall_checkbox").replace("CONSTANT",id))).click();
		//driver.findElement(By.id("cb_"+id)).click();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
	}


	//sliding_message
	public void verifyResultMessage(String message){
		try {
			loading(10);
			Asserting.verifyEquals(driver.findElement(By.xpath(propFile("sliding_message"))).getText(),message );
			loading(10);
		}catch (Exception e) {
		}
	}
	
	public void assertResultMessage(String message){
		try {
			loading(15);
			if(isExists(propFile("sliding_message"))){
				Asserting.assertEquals(driver.findElement(By.xpath(propFile("sliding_message"))).getText(),message );
			}
			else{
				Asserting.assertEquals("", message);
			}
			loading(10);
			//autoutil.waitUntillExist(propFile("sliding_message"));
			
		}catch (Exception e) {
		}
	}


	public void verifytabs(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials,String parentKey ){
		boolean exist;
		boolean displayed;
		for (Entry<String, String> entry2 : dataSheet(credentials,parentKey).entrySet()) {
			exist =isExists(propFile("tabs_show").replace("CONSTANT",entry2.getValue() ));
			if(exist){
				displayed=driver.findElement(By.xpath(propFile("tabs_show").replace("CONSTANT",entry2.getValue()))).isDisplayed();
				if(displayed){
					Asserting.verifyEquals(driver.findElement(By.xpath(propFile("tabs_show").replace("CONSTANT",entry2.getValue()))).getText(),entry2.getValue());

				}else{
					Asserting.verifyEquals(null,entry2.getValue());
				}
			}else{
				Asserting.verifyEquals(null, entry2.getKey());
			}
		}

	}
	
//methods specific to passwordSettings module

	public void verifyingDefaultValues_inSpecific(String labelValue ,String path2){
		if(isExists(path2)){
			Asserting.verifyEquals(driver.findElement(By.xpath(path2)).getAttribute("value").trim(), labelValue);

	}else{
			Asserting.verifyEquals("null", "path");
		}
	}

	public void verifyingLabelsAndTextTheFields_inSpecific(String labelValue ,String path2){
		if(isExists(path2)){
			driver.findElement(By.xpath(path2)).clear();

			driver.findElement(By.xpath(path2)).sendKeys(labelValue);
		}else{
			Asserting.verifyEquals("null", "path");
		}
	}
	

public void  verifyDropDownDefaultList(String Name,String value,String path2){
	String path = path2.replace("CONSTANT",Name);
	if(isExists(path)){
		Asserting.verifyEquals(driver.findElement(By.xpath(path)).getText().trim(), value);
	}
}
public void selectDropdownwith_textfield_inSpecific(String SelectingName,String SelectingValue, String path2) {
	String path=path2.replace("CONSTANT",SelectingName);
	verifyDropdownGeneric(SelectingValue, path);
}
public void checkbox_inSpecific(String labelValue ,String path2) {
	if(isExists(path2)){
		
		if(labelValue.contains("Alphabets")){
			if(	driver.findElement(By.xpath(path2)).isSelected()){
				driver.findElement(By.xpath(path2)).click();
			}
				driver.findElement(By.xpath(path2)).click();
		}else if(labelValue.contains("Numbers")){
			String x=null;
			x=path2.replace("passwordCombination1", "passwordCombination2");
			if(	driver.findElement(By.xpath(x)).isSelected()){
				driver.findElement(By.xpath(x)).click();
			}
				driver.findElement(By.xpath(x)).click();
			
		}else if(labelValue.contains("Special Character")){
			String x=null;
			x=path2.replace("passwordCombination1", "passwordCombination3");

			if(	driver.findElement(By.xpath(x)).isSelected()){
				driver.findElement(By.xpath(x)).click();
			}
				driver.findElement(By.xpath(x)).click();
			
		}
		
	}else{
		Asserting.verifyEquals("null", "path");
	}
	
}

public void defaultActiveDisplayElement(String xpath){
	if(!(isExists(xpath)&&(isDisplayed(xpath)))){
		Asserting.verifyEquals("active", "");
	}
	
}

//methods specific to TestDesign

public void uploadFileInTestDesign(String label,String filename){

	String xpath=propFile("testdesign-uploadxpath").replace("CONSTANT", label);
	if(filename!=null){
		if(isExists(xpath)){
			driver.findElement(By.xpath(xpath)).sendKeys(filename);		
		}else{
			Asserting.assertEquals("null", label);
		}
	}else{
		Asserting.assertEquals("invalidFile", filename);

	}

}

public void openBrowser(String url) {
	
	driver.get(url);
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
}


public void closeBrowser() {
	System.out.println("tesing");
	Util4Modules.autoutil=null;
	SelectingWebDriver.driver=null;
	driver.quit();
	
}



public boolean verifyDropdownTextInTestDesign(String label,String labelValue){
return new Select(driver.findElement(By.xpath(propFile("labelvalue").replace("CONSTANT", label)))).getFirstSelectedOption().getText().trim().equals(labelValue);
				}




}
