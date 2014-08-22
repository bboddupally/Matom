package com.gtech.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import com.gtech.util.Helper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class DataSource {
	public  static String localhost=null;
	public static String screenshot=null;

public DataSource(){
	
		
	}
	
	
	
	
	public DataSource(String args, String args2){
		DataSource.localhost=args;
		DataSource.screenshot=args2;
	}
	
	public DataSource(String args){
		DataSource.localhost=args;
	}
	
	public static LinkedHashMap<String,String> map = new LinkedHashMap<String, String>();
	Object[][] obj2=null;
	Object[][] obj = null;
public static LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String, String>>> buffer;
public static LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String, String>>>> bufferIteratons;
 	static Helper helper= new Helper();
	static Helper helperIterations= new Helper();
	
	public static void buffer() throws Exception{
		buffer = new LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String, String>>>();
		bufferIteratons = new LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String, String>>>>();
		
		FileInputStream fis = new FileInputStream(new File("src/test/resources/IterateData.xls"));
		FileInputStream fileInputStream4Iterations = new FileInputStream(new File("src/test/resources/IterateData.xls"));
		Workbook workbook4Iterations = WorkbookFactory.create(fileInputStream4Iterations);
		Workbook wb = WorkbookFactory.create(fis);
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		for (int i = 0; i < workbook4Iterations.getNumberOfSheets(); i++) {
			Sheet sheet = workbook4Iterations.getSheetAt(i);
			LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String, String>>> bufferIteratonsInternal = new LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String, String>>>();
			List<ExcelUtil> excelTCObj= helperIterations.parseExcelTestCases(sheet);
			int count= excelTCObj.size();
			for(int j=0;j<count;j++){
					bufferIteratonsInternal.put(excelTCObj.get(j).name.trim(), ExcelUtil.hash(excelTCObj,j)) ;				
			}
			
			bufferIteratons.put(sheet.getSheetName(), bufferIteratonsInternal);
		}
	
		
		
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			List<ExcelUtil> excelTCObj= helper.parseExcelTestCases(sheet);
			int count= excelTCObj.size();
			for(int j=0;j<count;j++){
				if(!excelTCObj.get(j).name.trim().startsWith("#")){
				buffer.put(excelTCObj.get(j).module.trim()+"_"+excelTCObj.get(j).name.trim(), ExcelUtil.hash(excelTCObj,j)) ;
				}
			}
		}
		fileInputStream4Iterations.close();
		System.out.println(bufferIteratons.size());
		fis.close();
		System.out.println(buffer.size());
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public Object[][] dataDrive4Iteration(String methodName){
 		LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String, String>>> bufferIteratonsInternal = new LinkedHashMap<String,LinkedHashMap<String,LinkedHashMap<String, String>>>();
		bufferIteratonsInternal =bufferIteratons.get(extractSimpleClassName(Thread.currentThread().getStackTrace()[2].getClassName())+"_"+methodName);
		ArrayList<String> arrayList =null;
		arrayList= new ArrayList<String>();
		for(Entry<String, LinkedHashMap<String, LinkedHashMap<String, String>>> entry:bufferIteratonsInternal.entrySet())
		{
			if(!entry.getKey().startsWith("#"))
			{
				arrayList.add(entry.getKey());
			}
		}
	
		int siz =arrayList.size();
		obj2 = new Object[siz][1];
		for(int i=0;i<siz;i++){
			obj2[i][0]=bufferIteratonsInternal.get(arrayList.get(i));
		}
		arrayList.clear();
		return obj2; 
	}

	public Object[][] dataDrive(String methodName){	
		
		obj = new Object[1][1];
		try{
		obj[0][0]=buffer.get(extractSimpleClassName(Thread.currentThread().getStackTrace()[2].getClassName())+"_"+methodName);
		}catch(Exception e){
			System.out.println("please enable beforTest4Suite,beforeSuite4Class and afterTest4Suite  constants in Excuted class");
		}
		//rows  /columns
		return obj;
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	
	
	
	public static String extractSimpleClassName(String fullClassName) {
	    if ((null == fullClassName) || ("".equals(fullClassName)))
	      return "";

	    int lastDot = fullClassName.lastIndexOf('.');
	    if (0 > lastDot)
	      return fullClassName;

	    return fullClassName.substring(++lastDot);
	  }

	
	private static String readFile(String path) throws IOException {
		  FileInputStream stream = new FileInputStream(new File(path));
		  try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    /* Instead of using default, pass in a decoder. */
		    return Charset.defaultCharset().decode(bb).toString();
		  }
		  finally {
		    stream.close();
		  }
		}
	
	
	
	//static Logger log = Logger.getLogger("OneTWO.class");
	
	public  void loadProperties(){
		Properties prop = new Properties();
		//localhost=projectPath;
		try {
			prop.load(new StringReader(readFile("src/test/resources/Games-APP-Util.properties").replace(" ", "~"))); } catch (Exception e) {e.printStackTrace();}
		for(Object str: prop.keySet()) {
			map.put(((String)str).trim().replace("~", " ").trim(),prop.getProperty((String) str).trim().replace("~", " ").trim() );
		}
		
	}
	
	
	public String propFile(String propertiesName){
		return DataSource.map.get(propertiesName.trim());
	}
	
	
/*	public void createXmlFile() {
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("D:/Automation/DemoAutomation2/properties.xml", "UTF-8");
			writer.println("<properties>");
			for(Entry<String, String> entry:map.entrySet()){
				if(entry.getKey().startsWith("reports-"))
					writer.println(entry.getKey()+"="+entry.getValue())	;
			}
			writer.println("</properties>");
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	

	//public LinkedHashMap<String, LinkedHashMap<String, String>> getCredentils(String methodName){
	//	return buffer.get(methodName);
	//}
	
	
}
