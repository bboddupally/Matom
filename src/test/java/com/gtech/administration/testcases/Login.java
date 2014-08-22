/**
 * 
 */
package com.gtech.administration.testcases;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gtech.administration.implementation.Login_Implementation;
import com.gtech.util.DataSource;

/**
 * @author bhupesh.b
 *
 */
@Test
public class Login {
	//static Logger log =Util4Modules.log();
	Login_Implementation  login = null;
	DataSource dd = new DataSource();

	public void getInstance() {
		if(login==null)
			login = new Login_Implementation();
	}
	
	
	
	@BeforeClass()
	public void beforeClass(){
		login = new Login_Implementation();
	}
	
//data providers	
	@DataProvider(parallel=true)
	public Object[][] DataProvider(Method m){
		Object[][] object =null;
		object =dd.dataDrive(m.getName());
		return object;
	}
	
	
	
	@DataProvider
	public Object[][] DataProvider4Iterations(Method m){
		Object[][] object =null;
		object =dd.dataDrive4Iteration(m.getName());
		return object;
	}
	

@Test(dataProvider="DataProvider4Iterations",priority=1,groups = { "smokeTest" ,"signIn"})
	public void GAME_SignIn(
			LinkedHashMap<String, LinkedHashMap<String, String>> credentials) {
	getInstance();login.GAME_SignIn(credentials);
	}

}
