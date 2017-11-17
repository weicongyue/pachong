package Tomongo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.xf.cs.en.DB.baidu;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
public class duqushuchu {

	
	public static void main(String[] args) throws IOException {
		
		File input = new File("F:/webmagic/zhidao.baidu.com/new.txt");  
		org.jsoup.nodes.Document doc = Jsoup.parse(input,"UTF-8","http://zhidao.baidu.com/"); 
		Elements one = doc.getElementsByClass("tableone"); 
		Elements two = doc.getElementsByClass("tabletwo");
		Elements three = doc.getElementsByClass("tablethree");
		Elements four = doc.getElementsByClass("tablefour");
		
		
		ByteArrayOutputStream baoStream = new ByteArrayOutputStream(1024);
		PrintStream cacheStream = new PrintStream(baoStream);
		PrintStream oldStream = System.out;
		System.setOut(cacheStream);//不打印到控制台
		//System.out.println("System.out.println:test");
		System.out.println(  
				   "\"{" +"\""+ "+" +"\""+ "'one':'"   +  one.text()+"',"
				  +"\""+ "+"+"\""+ "'two':'"   +  two.text()   + "',"
				  +"\""+ "+"+"\""+ "'three':'"   +  three.text() + "',"
				  +"\""+ "+"+"\""+ "'four':'"   +  four.text()  + "'"
				  +"\""+ "+"+"\"}"+"\""
             );
		String strMsg = baoStream.toString();
		System.setOut(oldStream);//还原到控制台输出
		System.out.println(strMsg);
		
		
//		System.out.println(jsonResult);
		  
		   try{      	  
	    	  
	    	 // String json ="{"+"'one':'第一个',"+"'two':'第二个',"+"'three':'第三个',"+"'four':'第四个'"+"}" ;
			   String json =strMsg;
	    			     
	    	  
	              @SuppressWarnings("resource")
	              
				  MongoClient mongoClient = new MongoClient("localhost", 27017);
				
				  MongoDatabase database = mongoClient.getDatabase("dbName");
				
				  MongoCollection<Document> collection = database.getCollection("collectionName");  
				
				  DBObject document = (DBObject)JSON.parse(json);
				 
				
				  collection.insertOne((Document) document);
				  
				  System.out.println("successful");
				        
	      }catch(Exception e){
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	     }
	   }
	   

//	public static JSONObject stringToObjec(String strMsg ){
//		
//    	if(StringUtils.isBlank(strMsg))
//    	    {
//    		return null;
//    	    }
//    	JSONObject json = (JSONObject)JSONObject.parse(strMsg);
//    	   return json;
//    	}
		
	
//  public static  String objectToString(Student student ){
//		
//    	
//    	JSONObject obj = (JSONObject)JSONObject.toJSON(student);
//    	String str = obj.toJSONString();
//    	   return str;
//    	}
//	

	public int add(baidu shujuku) {
			String json =toString();
			return -1;
		}
		
	}
	

