package Tomongo;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.mongodb.util.JSON;

public class ceshilianjie {
    public static void main(String[] args) {
        try {
            //创建Mongo数据库连接
            MongoClient client = new MongoClient("127.0.0.1",27017);
            //查询所有的数据库名
            List<String> databaseNames = client.getDatabaseNames();
            for(String name:databaseNames){
                System.out.println(name);
            }
            //获得一个数据库连接
            DB db = client.getDB("xxlong_db");
            //查询该数据库所有的集合名
            Set<String> collectionNames = db.getCollectionNames();
            for(String name:collectionNames){
                System.out.println(name);
            }

            //创建一个teacher集合
            //DBObject dbo = new BasicDBObject();
            //DBCollection  teacher_collection = db.createCollection("teacher3", dbo);
            DBCollection  teacher_collection = db.getCollection("teacher3");
            //向teacher集合中添加数据
            /*第一种方法，是使用BasicDBObject
             * { "_id" : ObjectId("55dbc9c744ae76d6d3f31483"), "name" : "xxlong", "age" : 100, "student" : [ "stu1", "stu2" ],　　　　　　　　　　"course": { "book" : "java" }}
               { "_id" : ObjectId("55dbc9c744ae76d6d3f31484")}
               { "_id" : ObjectId("55dbc9c744ae76d6d3f31485") }*/
            DBObject dbo1 = new BasicDBObject();
            dbo1.put("name", "xxlong");
            dbo1.put("age", 100);
            List<String> list = new ArrayList<String>();
            list.add("stu1");
            list.add("stu2");
            dbo1.put("student",list);
            DBObject dbo2 = new BasicDBObject();
            dbo2.put("book", "java");
            dbo1.put("course",dbo2);
            teacher_collection.insert(dbo1);
            DBObject db3 = new BasicDBObject();
            DBObject db4 = new BasicDBObject();
            //批量插入
            List<DBObject> listdbo= new ArrayList<DBObject>();
            listdbo.add(db3);
            listdbo.add(db4);
            teacher_collection.insert(listdbo);
            
            /*第二种方法是使用BasicDBObjectBuilder对象
            { "_id" : ObjectId("55dbee0544ae8180d0302af3"), "name" : "xxlong2", "age" : "101", "student" : [ "stu1", "stu2" ],　　　　　　　　 "course" : { "book" : "spring" } }
             */
            BasicDBObjectBuilder documentBuilder = BasicDBObjectBuilder.start().add("name", "xxlong2").add("age", "101");
            List<String> list2 = new ArrayList<String>();
            list.add("stu1");
            list.add("stu2");
            documentBuilder.add("student", list2);
            BasicDBObjectBuilder documentBuilder2 = BasicDBObjectBuilder.start().add("book","spring");
            documentBuilder.add("course", documentBuilder2.get());
            teacher_collection.insert(documentBuilder.get());
            
            /*第三种方法是使用Map对象
             { "_id" : ObjectId("55dbeebe44aeb01826bd4c93"), "student" : [ "stu1", "stu2" ], "name" : "xxlong3", "course" : 　　　　　　　　　　{ "book" : "c++" }, "age" : 103 }
             */
            Map<String, Object>  map1 =new HashMap<String, Object>();
            map1.put("name", "xxlong3");
            map1.put("age", 103);
//            List<String> list3 = new ArrayList<String>();
//            list.add("stu1");
//            list.add("stu2");
//            map1.put("student", list3);
//            Map<String, String> map2 =new HashMap<String, String>();
//            map2.put("book", "c++");
//            map1.put("course", map2);
            teacher_collection.insert(new BasicDBObject(map1));
            
            /*第四种方法，也就是最简单的，即直接插入JSON格式数据
             { "_id" : ObjectId("55dbf03844ae34d97f3864ee"), "name" : "xxlong4", "age" : 104, "student" : [ "stu1", "stu2" ],　　　　　　　　 "course" : { "book" : "c++" }}
             */
            String json =  "{ 'name' : 'xxlong4', 'age' : 104,'student' : [ 'stu1', 'stu2' ], 'course' : { 'book' :'c++' } }";
            DBObject dbObject =(DBObject)JSON.parse(json);
            teacher_collection.insert(dbObject);
            
            
            //删除第一个document
            DBObject dbo_delfirst = teacher_collection.findOne();
            teacher_collection.remove(dbo_delfirst);
            
            //删除指定的document
            DBObject dbo_specify = new BasicDBObject();
            //指定的_id
            dbo_specify.put("_id", new ObjectId("55dbf10444ae23aca6d08c57"));
            
            //指定name和age都要符合才能删除
            dbo_specify.put("name", "xxlong4");
            dbo_specify.put("age",100);
            teacher_collection.remove(dbo_specify);
            
            //指定值在数组中
            List<String> list4 = new ArrayList<String>();
            list.add("xxlong4");
            list.add("xxlong2");
            DBObject dbo_array = new BasicDBObject("$in",list4);
            DBObject dbo_in = new BasicDBObject();
            dbo_in.put("name", dbo_array);
            teacher_collection.remove(dbo_in);
            
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
}