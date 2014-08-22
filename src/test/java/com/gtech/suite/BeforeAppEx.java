package com.gtech.suite;

import org.testng.annotations.*;
import com.gtech.util.DataSource;
//import com.gtech.util.ExecLinuxScripts;


@Test(groups = {})
public class BeforeAppEx {
	DataSource dd = null;
	@Parameters(value = {"localhost,takescreenshot"})
	@BeforeSuite
	public  void beforeSuite(String localhost, String screenShot) throws Exception{
		//Reporter.setEscapeHtml(false);
		
		dd = new DataSource(localhost,screenShot);
		dd.loadProperties();
		DataSource.buffer();
		//dd.createXmlFile();
		/*DataSource.map.get("cleandb");
		if(DataSource.map.get("cleandb").contains("true")){
			//ExecLinuxScripts.excLinuxScriptsToCleanDB();
		}*/
	}
	
	
	
	@Parameters(value = {"localhost"})
	@BeforeSuite
	public  void beforeSuite(String localhost) throws Exception{
		//Reporter.setEscapeHtml(false);
		
		dd = new DataSource(localhost);
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
