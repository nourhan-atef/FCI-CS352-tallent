package com.FCI.SWE.Models;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class User {
	private long id;
	private String name;
	private String email;
	private String password;
	private String message;
	private String conversationname;
	private static User currentActiveUser;
	private String name1;
	private String name2;
	private String name3;
	private String name4;
	private String name5;

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
	private User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;

	}
	
	private void setId(long id){
		this.id = id;
	}
	private void setconversationname(String conversationname){
		this.conversationname = conversationname;
	}
	private void setmessage(String message){
		this.message = message;
	}
	
	public long getId(){
		return id;
	}

	public String getName() {
		return name;
	}
	public String getName1() {
		return name1;
	}
	public String getName2() {
		return name2;
	}
	public String getName3() {
		return name3;
	}
	public String getName4() {
		return name4;
	}
	public String getName5() {
		return name5;
	}
	
	public String getconversationname() {
		return conversationname;
	}
	public String getmessage() {
		return message;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
	}
	
	
	public static User getCurrentActiveUser(){
		return currentActiveUser;
	}
	public static void setCurrentActiveUsernull(){
		currentActiveUser =null;
	}
	/**
	 * 
	 * This static method will form UserEntity class using json format contains
	 * user data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed user entity
	 */
	
	public static User getUser(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			currentActiveUser = new User(object.get("name").toString(), object.get(
					"email").toString(), object.get("password").toString());
			currentActiveUser.setId(Long.parseLong(object.get("id").toString()));
			return currentActiveUser;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


}
