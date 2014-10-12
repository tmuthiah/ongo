package com.ongo.controller;

import org.bson.BSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
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
	Status insertUserData(@RequestBody User user) {
		String returnMsg = null;
		try {
			System.out.println("before calling MongoDBDriver.insertUserData");
			returnMsg = MongoDBDriver.insertUserData(user);
			System.out.println("After calling MongoDBDriver.insertUserData");
			if (returnMsg.equals("failure-user already existes")) {
				System.out.println("failed insert record MongoDBDriver.insertUserData");
				return new Status(1, "User already Exist !");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, e.toString());
		}
		System.out.println("Successfully inserted record MongoDBDriver.insertUserData");
		return new Status(0, "User added Successfully !");

	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status validateLoginData(@RequestBody User user) {
		String returnMsg = null;
		try {
			System.out.println("before calling MongoDBDriver.validateLoginData");
			returnMsg = MongoDBDriver.validateLoginData(user);
			System.out.println("After calling MongoDBDriver.validateLoginData");
			if (returnMsg.equals("failure-user invalid login credential")) {
				System.out.println("password validation failed ");
				return new Status(1, "Invalid Login ID or Password entered !");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, e.toString());
		}
		System.out.println("password authentication success");
		return new Status(0, "User validation Successfully !");

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String searchUserDataById(@PathVariable("id") int id) {
		Gson gson = new Gson();
		User user = new User();
		try {
			BasicDBObject basicDBObject = (BasicDBObject) MongoDBDriver.searchUserDataById(id);

			user.setId(basicDBObject.getInt("_id"));
			user.setName(basicDBObject.getString("name"));
			user.setRole(basicDBObject.getString("role"));
			user.setIsEmployee(basicDBObject.getBoolean("isEmployee"));
			user.setlPhone(basicDBObject.getString("mPhone"));
			user.setlPhone(basicDBObject.getString("lPhone"));
			user.setPassword(basicDBObject.getString("password"));
			user.setCuase(basicDBObject.getString("cuase"));
			user.setCuase(basicDBObject.getString("CurrentAddress"));
			user.setCurrentAddress(basicDBObject.getString("permenantAddress"));
			user.setAppealCategory(basicDBObject.getString("appealCategory"));
			user.setAppealInfo(basicDBObject.getString("appealInfo"));
			user.setAppealDate(basicDBObject.getString("appealDate"));
			user.setAppealExpiryDate(basicDBObject.getString("appealExpiryDate"));
		     
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(user);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	Status deleteUserDataById(@PathVariable("id") int user) {
		try {
			MongoDBDriver.deleteUserDataById(user);
			return new Status(1, "User deleted Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, e.toString());
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public @ResponseBody
	Status updateUserDataById(@PathVariable int id, @RequestBody User newValue) {
		try {
			MongoDBDriver.updateUserDataById(id, newValue);
			return new Status(1, "User added Successfully !");
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, e.toString());
		}

	}

}
