package com.xf.cs.en.DB;

//import java.util.ArrayList;
//import java.util.List;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoCredential;
//import com.mongodb.ServerAddress;
//import com.mongodb.client.MongoDatabase;
//  
//public class MongoDb_Test {  
//    public static void main(String[] args){  
//        try {  
//            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址  
//            //ServerAddress()两个参数分别为 服务器地址 和 端口  
//            ServerAddress serverAddress = new ServerAddress("localhost",27017);  
//            List<ServerAddress> addrs = new ArrayList<ServerAddress>();  
//            addrs.add(serverAddress);  
//              
//            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码  
//            MongoCredential credential = MongoCredential.createScramSha1Credential("username", "shujuku", "password".toCharArray());  
//            List<MongoCredential> credentials = new ArrayList<MongoCredential>();  
//            credentials.add(credential);  
//              
//            //通过连接认证获取MongoDB连接  
//            MongoClient mongoClient = new MongoClient(addrs,credentials);  
//              
//            //连接到数据库  
//            MongoDatabase mongoDatabase = mongoClient.getDatabase("shujuku");  
//            System.out.println("Connect to database successfully");  
//        } catch (Exception e) {  
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );  
//        }  
//    }  
//}


import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDb_Test{
   public static void main( String args[] ){
      try{      	  
    	  
    	  String json = " {" +
                  " 'school_code' : '111111', " +
                  " 'school_name' : '汉东政法大学', " +
                  " 'teacher_idcard' : '0000001', " +
                  " 'teacher_name' : '高育良' " +
                  " 'school_code' : '222222', " +
                  " 'school_name' : '西南政法大学', " +
                  " 'teacher_idcard' : '2222222', " +
                  " 'teacher_name' : '你' " +
                  " } ";

              @SuppressWarnings("resource")
              
			  MongoClient mongoClient = new MongoClient("localhost", 27017);
			
			  MongoDatabase database = mongoClient.getDatabase("dbName");
			
			  MongoCollection<Document> collection = database.getCollection("collectionName");  
			
			  Document document = Document.parse(json);
			
			  collection.insertOne(document );
			  
			  System.out.println("successful");
			        
      }catch(Exception e){
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
     }
   }
}


