package com.gtech.suite;

import java.util.ArrayList;

import org.testng.annotations.*;
import com.gtech.util.DataSource;
//import com.gtech.util.ExecLinuxScripts;


@Test(groups = {})
public class BeforeAppEx {
	DataSource dd = null;
	
	
	/*@Parameters(value = {"localhost"})
	@BeforeSuite
	public  void beforeSuite(String localhost, String screenShot) throws Exception{
		//Reporter.setEscapeHtml(false);
		
		dd = new DataSource(localhost,screenShot);
		dd.loadProperties();
		DataSource.buffer();
		//dd.createXmlFile();
		DataSource.map.get("cleandb");
		if(DataSource.map.get("cleandb").contains("true")){
			//ExecLinuxScripts.excLinuxScriptsToCleanDB();
		}
	}*/
	
	
	
	@Parameters(value = {"localhost","browser"})
	@BeforeSuite
	public  void beforeSuite(String localhost,String browser) throws Exception{
		//Reporter.setEscapeHtml(false);
		/*ArrayList<String> al = new ArrayList<String>();
		al.add(localhost);
		al.add(browser);*/

		dd = new DataSource(localhost,browser);
		dd.loadProperties();
		DataSource.buffer();
		//dd.createXmlFile();
		/*DataSource.map.get("cleandb");
		if(DataSource.map.get("cleandb").contains("true")){
			//ExecLinuxScripts.excLinuxScriptsToCleanDB();
		}*/
	}
	
	//please enable beforTest4Suite,beforeSuite4Class and afterTest4Suite  
	@AfterSuite(groups = { "smokeTest" })
	public void afterSuite(){
		try{
			System.out.println("afterSuite");
			DataSource.map.clear();
			DataSource.buffer.clear();
			
			//cleaning 
		}catch(Exception e){
	System.out.println("error occured while closing the browser ,Error message:"+e.getMessage());
		}
	}
}
