package com.yz.searchservlet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoDBJDBC{
	MongoClient mongoClient;
	MongoDatabase mongoDatabase;
	MongoCollection<Document> collection;
	{
		mongoClient = new MongoClient("localhost", 27017);
		mongoDatabase = mongoClient.getDatabase("MS");
		System.out.println("Connect to database successfully");
		collection = mongoDatabase.getCollection("application");
		System.out.println("集合 application 选择成功");
	}
	
   public  void selectAll( ){
      try{   
         /** 
         * 1. 获取迭代器FindIterable<Document> 
         * 2. 获取游标MongoCursor<Document> 
         * 3. 通过游标遍历检索出的文档集合 
         * */  
         FindIterable<Document> findIterable = collection.find();  
         MongoCursor<Document> mongoCursor = findIterable.iterator();  
         while(mongoCursor.hasNext()){  
            System.out.println(mongoCursor.next());  
         }  
      
      }catch(Exception e){
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      }
   }
   
   public static Map<String, Integer> sortMap(Map<String, Integer> oldMap) {
       ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(oldMap.entrySet());
       Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

           @Override
           public int compare(Entry<java.lang.String, Integer> arg0,
                   Entry<java.lang.String, Integer> arg1) {
               return arg0.getValue() - arg1.getValue();
           }
       });
       Map<String, Integer> newMap = new LinkedHashMap<String, Integer>();
       for (int i = list.size()-1; i>=0; i--) {
           newMap.put(list.get(i).getKey(), list.get(i).getValue());
       }
       return newMap;
   }
   
   public  List<String> selectByKeywords(String keywords,String excludeSessionId ,int count){
	   List<String> list=new ArrayList<String>();
	   Map<String, Integer> allOtherCount = new HashMap<String, Integer>();
	   List<Set<String>> lSets = selectBySet(keywords,excludeSessionId);
	   System.out.println("包含关键字的全部set	"+lSets);
	   for (Set<String> set : lSets) {
			for (String keywordsString : set) {
				Integer integer = allOtherCount.get(keywordsString);
				if (integer != null) {
					allOtherCount.put(keywordsString, integer+1);
				} else {
					allOtherCount.put(keywordsString, 1);
				}
			}
	}
	   allOtherCount.remove(keywords);
	   System.out.println("无顺序	"+allOtherCount);
	   Map<String, Integer> map = sortMap(allOtherCount);
	   System.out.println("有顺序	"+map);
	   int i=0;
		for (String key:map.keySet()) {
			if (i<count) {
				list.add(key);
				i++;
			} else {
				break;
			}
		
		
		}
	   return list;
   }
   
public  List<Set<String>> selectBySet(String keywords,String excludeSessionId){
	   List<Set<String>> lSets =new ArrayList<Set<String>>();
	   List<String> list=null;
	   try{   
		   /** 
		    * 1. 获取迭代器FindIterable<Document> 
		    * 2. 获取游标MongoCursor<Document> 
		    * 3. 通过游标遍历检索出的文档集合 
		    * */  
		/*   BasicDBObject queryObject = new BasicDBObject();
		   queryObject.containsValue(keywords);
		   FindIterable<Document> findIterable = collection.find(queryObject);
		   有问题
		   */
		   FindIterable<Document> findIterable = collection.find();
//		   String ageStr = "function (){return parseFloat(this.age) > 20 && parseFloat(this.age) <= 40};";
//		   queryObject.put("$where",ageStr);
//		   StringBuilder sb = new StringBuilder();
//		   sb.append("function(){return '").append(setKeywords).append("' in this.set;}");
//		   System.out.println(sb);
//		   FindIterable<Document> findIterable = collection.find(Filters.where(sb.toString()));
		   MongoCursor<Document> mongoCursor = findIterable.iterator();  
		   while(mongoCursor.hasNext()){  
			   Document document = mongoCursor.next();
			   if (!document.get("sessionId").equals(excludeSessionId)){
				   list =  (List<String>)document.get("set");
				   if (list.contains(keywords)) {
					   Set<String> set = new HashSet<String>(list);
					   lSets.add(set);
					
				}
			}
			   
		   }  
		   
	   }catch(Exception e){
		   System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	   }
	   return lSets;
   }
   public  Set<String> selectBySessionId(String sessionId){
	   List<String> list=null;
	   try{   
		   /** 
		    * 1. 获取迭代器FindIterable<Document> 
		    * 2. 获取游标MongoCursor<Document> 
		    * 3. 通过游标遍历检索出的文档集合 
		    * */  
		   FindIterable<Document> findIterable = collection.find(Filters.eq("sessionId", sessionId));  
		   MongoCursor<Document> mongoCursor = findIterable.iterator();  
		   while(mongoCursor.hasNext()){  
			   Document document = mongoCursor.next();
			   list =  (List<String>)document.get("set");
		   }  
		   
	   }catch(Exception e){
		   System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	   }
	   if (list!=null) {
		   return new HashSet<String>(list);
	} 
	   return null;
   }
	
	public void insert(String sessionId,Set<String> set){
		 try{   
	         /** 
	         * 1. 创建文档 org.bson.Document 参数为key-value的格式 
	         * 2. 创建文档集合List<Document> 
	         * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document) 
	         * */
			 Map<String,Object> map= new HashMap<String,Object>();
			 map.put("sessionId",sessionId);
			 map.put("set",set);
	         Document document = new Document(map);  
	         List<Document> documents = new ArrayList<Document>();  
	         documents.add(document);  
	         collection.insertMany(documents);  
	         System.out.println("文档插入成功");  
	      }catch(Exception e){
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
		
	}
	
	public void update(String sessionId,Set<String> set){
		try{   
			/** 
			 * 1. 创建文档 org.bson.Document 参数为key-value的格式 
			 * 2. 创建文档集合List<Document> 
			 * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document) 
			 * */
			Map<String,Object> map= new HashMap<String,Object>();
//			Set<String> set = new HashSet<String>();
//			set.add("周杰伦");
//			set.add("林俊杰");
//			set.add("徐佳莹");
			map.put("sessionId",sessionId);
			map.put("set",set);
			Document document = new Document(map);
			collection.updateMany(Filters.eq("sessionId", sessionId), new Document("$set",document)); 
			System.out.println("文档更新成功");  
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		
	}
	public void deleteAll(){
		try{   
			collection.deleteMany(Filters.exists("_id"));
			System.out.println("当前集合文档全部删除成功");  
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		
	}
   
   
}