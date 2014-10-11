package com.ongo.controller;

import org.bson.BSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.hack.ongo.db.MongoDBDriver;
import com.hack.ongo.db.User;
import com.mongodb.BasicDBObject;
import com.ongo.model.Employee;
import com.ongo.model.Status;

@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addUser(@RequestBody User user) {
		try {
//			MongoDBDriver dbDriver = new MongoDBDriver();
			MongoDBDriver.insertData(user);
			return new Status(1, "User added Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(0, e.toString());
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	User getUser(@PathVariable("id") int id) {
		User user = new User();
		try {
//			MongoDBDriver dbDriver = new MongoDBDriver();
//			user = (User) MongoDBDriver.getUser(id);
			BasicDBObject basicDBObject = (BasicDBObject) MongoDBDriver.getUser(id);
			user.setId(basicDBObject.getInt("_id"));
			user.setName(basicDBObject.getString("name"));
			user.setRole(basicDBObject.getString("role"));
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

}
