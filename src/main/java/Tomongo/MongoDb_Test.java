package Tomongo;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.xf.cs.en.DB.baidu;

public class MongoDb_Test{
   public static void main( String args[] ){
     
	   try{      	  
    	  
    	  String json = "{"+"'one':'第一个',"+"'two':'第二个',"+"'three':'第三个',"+"'four':'第四个'"+"}";
    			      // " {" + " 'school_code' : '111111', " + " 'school_name' : '汉东政法大学', " + " 'teacher_idcard' : '0000001', " +" 'teacher_name' : '高育良' " + " } ";

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
   
   public int add(baidu shujuku) {
		String json =toString();
		return -1;
	}
}


