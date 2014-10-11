package com.hack.ongo.db;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;
import com.hack.ongo.db.*;

import java.net.UnknownHostException;
import java.util.Arrays;

public class MongoDBDriver{
   
	   public static void main( String args[] ) throws Exception {
		   
		   System.out.println("Sample Mongo DB query result ....");
	        //create user
	        User user = createUser();
	        DBObject doc = createDBObject(user);
	         
	        MongoClient mongo = new MongoClient("localhost", 27017);
	        //Mongo mongo = new Mongo();
	        DB db = mongo.getDB("hacktest");
	         
	        DBCollection col = db.getCollection("users");
	         
	        System.out.println("dbobject == " + doc);
	        WriteResult result = col.insert(doc);

	        System.out.println(result.getN());
	        System.out.println(result.getLastConcern());

	        //read example
	        DBObject query = BasicDBObjectBuilder.start().add("_id", user.getId()).get();
	        DBCursor cursor = col.find(query);
	        while(cursor.hasNext()){
	        	DBObject object = cursor.next();
	            System.out.println(object);
	        }
	         
	        //update example
	        user.setName("Marimuthu");
	        doc = createDBObject(user);
	        result = col.update(query, doc);
	        System.out.println(result.getN());
	        System.out.println(result.getLastConcern());
	         
	        //delete example
	        //result = col.remove(query);
	        //System.out.println(result.getUpsertedId());
	        //System.out.println(result.getN());
	        //System.out.println(result.isUpdateOfExisting());
	        //System.out.println(result.getLastConcern());
	         
	        //close resources
	        mongo.close();
	    }
	   public static void insertData(User user) throws Exception	   {
		   DBObject doc = createDBObject(user);
       MongoClient mongo = new MongoClient("localhost", 27017);
       //Mongo mongo = new Mongo();
       DB db = mongo.getDB("hacktest");
        
       DBCollection col = db.getCollection("users");
       
       System.out.println("dbobject == " + doc);
       WriteResult result = col.insert(doc);

       System.out.println(result.getN());
       System.out.println(result.getLastConcern());

       //read example
       DBObject query = BasicDBObjectBuilder.start().add("_id", user.getId()).get();
       DBCursor cursor = col.find(query);
       while(cursor.hasNext()){
       	DBObject object = cursor.next();
           System.out.println(object);
       }
       
	   }
	 
	public static DBObject getUser(int id) throws UnknownHostException {
		System.out.println("getUser fun start mmmm id == " + id);
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("hacktest");
		BasicDBObject result = new BasicDBObject();
		
		DBCollection col = db.getCollection("users");

		//BasicDBObject keys = new BasicDBObject();
		//keys.put("id", id);
		BasicDBObject query = new BasicDBObject("_id", id);
		
		DBCursor cursor = col.find(query);
		try {
		while (cursor.hasNext()) {
			DBObject object = cursor.next();
			System.out.println(object);
			return object;
		}
		}	finally {
			   cursor.close();
			}
		return null;

	}
	   
	    public static DBObject createDBObject(User user) {
	        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
	                                 
	        docBuilder.append("_id", user.getId());
	        docBuilder.append("name", user.getName());
	        docBuilder.append("role", user.getRole());
	        docBuilder.append("isEmployee", user.getIsEmployee());
	        return docBuilder.get();
	    }
	 
	    private static User createUser() {
	        User u = new User();
	        u.setId(2);
	        u.setName("Pankaj");
	        u.setIsEmployee(true);
	        u.setRole("CEO");
	        return u;
	    }

   }
