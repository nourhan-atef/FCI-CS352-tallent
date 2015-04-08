package com.FCI.SWE.ServicesModels;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Controller.ChatSubject;
import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 */
public class UserEntity {
	private String name;
	private String email;
	private String password;
	private String conversationname;
	private String message;
	private long id;
	private String status;

	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public UserEntity(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	private void setId(long id){
		this.id = id;
	}
	
	public void setStatus(String status){
		this.status=status;
	}
	public void setconversationname(String conversationname){
		this.conversationname=conversationname;
	}
	public String getconversationname(){
		return conversationname;
	}
	
	public String getmessage(){
		return message;
	}
	
	public long getId(){
		return id;
	}
	
	public void setmessage(String message){
		this.message=message;
	}
	
	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
	}

	public String getStatus() {
		return status;
	}
	/**
	 * 
	 * This static method will form UserEntity class using user name and
	 * password This method will serach for user in datastore
	 * 
	 * @param name
	 *            user name
	 * @param pass
	 *            user password
	 * @return Constructed user entity
	 */

	public static UserEntity getUser(String name, String pass) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("name").toString().equals(name)
					&& entity.getProperty("password").toString().equals(pass)) {
				UserEntity returnedUser = new UserEntity(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(), entity.getProperty("password").toString());
				returnedUser.setId(entity.getKey().getId());
				return returnedUser;
			}
		}

		return null;
	}
	

	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean saveUser() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("users", list.size() + 1);

		employee.setProperty("name", this.name);
		employee.setProperty("email", this.email);
		employee.setProperty("password", this.password);
		datastore.put(employee);

		return true;

	}
	
	
	public Boolean savemessage(String fname,String message) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("message");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity message1 = new Entity("message", list.size() + 1);

		message1.setProperty("name",User.getCurrentActiveUser().getName());
		message1.setProperty("fname",fname);
		message1.setProperty("message",message);
		datastore.put(message1);

		return true;

	}
	
	/**
	    wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
	 */
	public static UserEntity getFriend(String name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("name").toString().equals(name)) {
				UserEntity returnedUser = new UserEntity(entity.getProperty("name").toString(),"","");
				return returnedUser;
			}
		}

		return null;
	}
	
	public Boolean savefriendrequest(String fname ) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("request");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity user_friendrequest = new Entity("request", list.size() + 1);
       
		user_friendrequest.setProperty("name",User.getCurrentActiveUser().getName());
		user_friendrequest.setProperty("fname",fname);
		user_friendrequest.setProperty("status","bending");
		
		
		datastore.put(user_friendrequest);

		return true;

	}
	public Boolean savechatgroup(String fname1,String fname2,String fname3,String fname4,
			String fname5,String conversationname) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("chatgroup");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity message1 = new Entity("chatgroup", list.size() + 1);

		message1.setProperty("name",User.getCurrentActiveUser().getName());
		message1.setProperty("fname1",fname1);
		message1.setProperty("fname2",fname2);
		message1.setProperty("fname3",fname3);
		message1.setProperty("fname4",fname4);
		message1.setProperty("fname5",fname5);
		message1.setProperty("conversationname",conversationname);
		datastore.put(message1);

		return true;

	}
	
	public Boolean savewriteMessage(String message,String conversationname) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("chatgroup");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("conversationname").toString().equals(conversationname)) {
		        Entity message1 = new Entity("chatgroup", list.size() + 1);
		        message1.setProperty("message",message);
		        datastore.put(message1);
		        ChatSubject chat=new ChatSubject();
		        chat.notifyObserver();
	          }
		
	  }
		return null;
	}
	

	
	
	}
