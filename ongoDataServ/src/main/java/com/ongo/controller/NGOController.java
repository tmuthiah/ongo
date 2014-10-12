
package com.ongo.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.bson.BSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HttpServletBean;

import com.google.gson.JsonObject;
import com.hack.ongo.db.MongoDBDriver;
import com.hack.ongo.db.NGO;
import com.hack.ongo.db.User;
import com.mongodb.BasicDBObject;
import com.ongo.model.Employee;
import com.ongo.model.Status;

@Controller
@RequestMapping("/ngo")
public class NGOController {

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addNGO(@RequestBody NGO ngo) {
		try {
//			MongoDBDriver dbDriver = new MongoDBDriver();
			
			
			MongoDBDriver.insertNGO(ngo);
			return new Status(1, "NGO added Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(0, e.toString());
		}

	}

/*	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
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
*/	
	@RequestMapping(value = "/category/{category}/{city}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> searchByCategory(@PathVariable String category, @PathVariable String city) {
		List<NGO> ngoList = new ArrayList<NGO>();
		List<BasicDBObject> ngoDBList = new ArrayList<BasicDBObject>();
		try {
			
			 ngoDBList = MongoDBDriver.searchNGOByCriteria(category, city);
			
			for(BasicDBObject basicDB : ngoDBList){
				NGO ngo = new NGO();
				ngo.setAboutUs(basicDB.getString("aboutUs"));
											
				ngo.setCauses(basicDB.getString("causes").split(","));
				ngo.setContactUs(basicDB.getString("contactUs"));
				ngo.setCoordinatorEmailId(basicDB.getString("coordinatorEmailId"));
				ngo.setCoordinatorMobileNo(basicDB.getString("coordinatorMobileNo"));
				ngo.setCoordinatorName(basicDB.getString("coordinatorName"));
				ngo.setLocation(basicDB.getString("location"));
				ngo.setOrganisationName(basicDB.getString("organisationName"));

				ngoList.add(ngo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ngoList;

	}

	/*@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	List<NGO> getAllNGOs(@PathVariable("id") int id) {
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
*/}
