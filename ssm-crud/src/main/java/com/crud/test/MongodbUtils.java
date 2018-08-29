package com.crud.test;

import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
 
public class MongodbUtils {
	 static final String DBName = "th_hczq";
     static final String ServerAddress = "192.168.1.7"; 
     static final int PORT = 9430;
 
     @Test
     public void testMongoClient( ){
         try {
        	 //连接到mogodb服务
        	 MongoClient mongoClient = new MongoClient( "192.168.1.7" , 9430);
        	// 连接到数据库
             MongoDatabase mongoDatabase = mongoClient.getDatabase("th_hczq");  
             System.out.println("Connect to mongodb successfully");
         } catch (Exception e) {
        	 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         }
     }
 
     public static MongoDatabase getMongoDataBase(MongoClient mongoClient) {  
         MongoDatabase mongoDataBase = null;
         try {  
             if (mongoClient != null) {  
                   // 连接到数据库
                 mongoDataBase = mongoClient.getDatabase(DBName);  
                 System.out.println("Connect to DataBase successfully");
             } else {  
                 throw new RuntimeException("MongoClient不能够为空");  
             }  
         } catch (Exception e) {  
             e.printStackTrace();  
         }
         return mongoDataBase;
     }  
 
     public static void closeMongoClient(MongoDatabase mongoDataBase,MongoClient mongoClient ) {  
         if (mongoDataBase != null) {  
             mongoDataBase = null;  
         }  
         if (mongoClient != null) {  
             mongoClient.close();  
         }  
         System.out.println("CloseMongoClient successfully");  
 
     }  
}
