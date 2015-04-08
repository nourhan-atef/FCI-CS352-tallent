package com.FCI.SWE.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.UserEntity;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class UserController {
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@GET
	@Path("/signup")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}
	
	
	@GET
	@Path("/chatgroup")
	public Response chatgroup() {
		return Response.ok(new Viewable("/jsp/chatgroup")).build();
	}
	
	@GET
	@Path("/excutemessageNotificationService")
	public Response excuteService() {
		return Response.ok(new Viewable("/jsp/readMessage")).build();
	}
	
	
	@GET
	@Path("/excutefriendAcceptanceService")
	public Response excuteService2() {
		return Response.ok(new Viewable("/jsp/acceptFriend")).build();
	}
	
	
	@GET
	@Path("/writeMessage")
	public Response writeMessage() {
		return Response.ok(new Viewable("/jsp/writeMessage")).build();
	}
	@GET
	@Path("/signout")
	public Response signout() {
		String serviceUrl = "http://fci-talent.appspot.com/rest/SignOutService";
		String retJson = Connection.connect(serviceUrl, "","POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		return Response.ok(new Viewable("/jsp/entryPoint.jsp")).build();
	}

	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return enty point page (Home page of this application)
	 */
	@GET
	@Path("/")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}
	
	@GET
	@Path("/sendrequest")
	public Response sendrequest() {
		return Response.ok(new Viewable("/jsp/sendFriendRequest")).build();
	}

	@GET
	@Path("/makefriend")
	public Response makefriend1() {
		return Response.ok(new Viewable("/jsp/makefriend")).build();
	}
	
	@GET
	@Path("/sendmessage")
	public Response sendmessage() {
		return Response.ok(new Viewable("/jsp/sendmessage")).build();
	}
	
	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {

		String serviceUrl = "http://fci-talent.appspot.com/rest/RegistrationService";
		String urlParameters = "uname=" + uname + "&email=" + email
				+ "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			// System.out.println(retJson);
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return "Failed";
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		String urlParameters = "uname=" + uname + "&password=" + pass;

		String retJson = Connection.connect(
				"http://fci-talent.appspot.com/rest/LoginService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User user = User.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			return Response.ok(new Viewable("/jsp/home", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

	}
 
	@POST
	@Path("/sendrequest")
	@Produces("text/html")
	public Response sendrequest(@FormParam("uname") String uname) {
		String urlParameters = "uname=" + uname ;

		String retJson = Connection.connect(
				"http://fci-talent.appspot.com/rest/sendrequestService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User user = User.getUser(object.toJSONString());
			map.put("name", user.getName());
			return Response.ok(new Viewable("/jsp/hello", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

}
	
	@POST
	@Path("/makefriend")
	@Produces("text/html")
	public Response makefriend(@FormParam("uname") String uname,@FormParam("fname") String fname) {

		String urlParameters = "uname=" + uname + "&fname=" + fname;
		String retJson = Connection.connect(
				"http://fci-talent.appspot.com/rest/makefriendService",urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User user = User.getUser(object.toJSONString());
			map.put("name", user.getName());
			return Response.ok(new Viewable("/jsp/hello", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

}
	
	
	@POST
	@Path("/sendmessage")
	@Produces("text/html")
	public Response sendmessage(@FormParam("fname") String fname,@FormParam("message") String message) {
		String urlParameters = "fname=" + fname+ "&message=" + message; 

		String retJson = Connection.connect(
				"http://fci-talent.appspot.com/rest/sendmessageService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User user = User.getUser(object.toJSONString());
			map.put("fname", user.getName());
			map.put("message", user.getmessage());
			return Response.ok(new Viewable("/jsp/hello", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

}
	
	
	
	
	@POST
	@Path("/chatgroup")
	@Produces("text/html")
	public Response chatgroup(@FormParam("fname1") String fname1,
			@FormParam("fname2") String fname2,@FormParam("fname3") String fname3,
			@FormParam("fname4") String fname4,@FormParam("fname5") String fname5,
			@FormParam("conversationname") String conversationname) {
		String urlParameters = "fname1=" + fname1+"fname2=" + fname2+"fname3=" + fname3+
				"fname4=" + fname4+"fname5=" + fname5 +
				"conversationname=" + conversationname; 

		String retJson = Connection.connect(
				"http://fci-talent.appspot.com/rest/chatgroupService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User user = User.getUser(object.toJSONString());
			ChatSubject chat=new ChatSubject();
			
			map.put("fname1", user.getName());
			ChatObserver observer=new ChatObserver( user.getName());
			chat.addOserver(observer);
			map.put("fname2", user.getName2());
		    observer=new ChatObserver( user.getName2());
			chat.addOserver(observer);
			map.put("fname3", user.getName3());
			observer=new ChatObserver( user.getName3());
			chat.addOserver(observer);
			map.put("fname4", user.getName4());
			observer=new ChatObserver( user.getName4());
		    chat.addOserver(observer);
			map.put("fname5", user.getName5());
		    observer=new ChatObserver( user.getName5());
		    chat.addOserver(observer);
			map.put("convesationname", user.getconversationname());
			
			return Response.ok(new Viewable("/jsp/hello", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

}
	
	
	
	
	
	
	@POST
	@Path("/writeMessage")
	@Produces("text/html")
	public Response chatgroup(@FormParam("message") String message,
			@FormParam("conversationname") String conversationname) {
		String urlParameters = "conversationname=" + conversationname+
				"message"+message; 

		String retJson = Connection.connect(
				"http://fci-talent.appspot.com/rest/writeMessageService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User user = User.getUser(object.toJSONString());
			map.put("message", user.getmessage());
			map.put("convesationname", user.getconversationname());
			return Response.ok(new Viewable("/jsp/hello", map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

}
	
	
	
	
	
	
	
	
	
	
	
	@GET
	@Path("/notification")
	@Produces("text/html")
	public Response notification() {

		System.out.println("Enterr ");
		
        User user= User.getCurrentActiveUser();
		String urlParameters = user.getName();
		String retJson = Connection.connect(
				"http://fci-talent.appspot.com/rest/excutemessageNotificationService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
		/*	obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, Vector> map = new HashMap<String, Vector>();
			User user = User.getUser(object.toJSONString());
		
			System.out.print(Response.ok(new Viewable("/jsp/readMessage", map)).build());
		*/	
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", retJson);
			return Response.ok(new Viewable("/jsp/readMessage",map)).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}