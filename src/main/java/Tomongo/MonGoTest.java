package Tomongo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MonGoTest {
    public static void main(String[] args)throws IOException {
        try {
        	//链接momgodb数据库
        	 MongoClient client = new MongoClient("127.0.0.1",27017);
             //查询所有的数据库名
             @SuppressWarnings("deprecation")
			List<String> databaseNames = client.getDatabaseNames();
             for(String name:databaseNames){
                 System.out.println(name);
             }
             //获得一个数据库连接
             @SuppressWarnings("deprecation")
			DB db = client.getDB("baidushujuku");
             //查询该数据库所有的集合名
             Set<String> collectionNames = db.getCollectionNames();
             for(String name:collectionNames){
                 System.out.println(name);
             }

            
             DBCollection  teacher_collection = db.getCollection("teacher3");
        
         
               //循环读取本地文件 
		 		String filepath="F:/webmagic/zhidao.baidu.com";
		 		File file=new File(filepath);
		 		if(file.isDirectory()){
	 			String[] filelist=file.list();
	 			for(int i=0;i<filelist.length;i++){
				File input=new File(filepath+"\\"+filelist[i]);
             
            // File input = new File("F:/webmagic/zhidao.baidu.com/new.txt");  //单个文档输入时候的测试用这一句
             Document doc = Jsoup.parse(input,"UTF-8","http://zhidao.baidu.com/"); 
             Elements one = doc.getElementsByClass("tableone"); 
             String a=one.text();//map直接存储不了Elements ，所以转换为String
             Elements two = doc.getElementsByClass("tabletwo");
             String b=two.text();
             Elements three = doc.getElementsByClass("tablethree");
             String c=three.text();
             Elements four = doc.getElementsByClass("tablefour");
             String d=four.text();
             
            //使用Map对象
	            
	            Map<String, Object>  map1 =new HashMap<String, Object>();
	            map1.put("one", a);
	            map1.put("two", b);
	            map1.put("three", c);
	            map1.put("four", d);
	           
	            teacher_collection.insert(new BasicDBObject(map1));
            
           System.out.println("完成！");
	 			    }
	 			  }
		 		}
           
	 			  
        catch (Exception e) {
            e.printStackTrace();
            }
          }
        } 
    
