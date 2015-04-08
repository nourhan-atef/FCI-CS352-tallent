package com.FCI.SWE.Services;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;

import com.FCI.SWE.Controller.Command;
import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class MessageNotification extends Command{
	
	
	public String excute(String n){
		
		System.out.println("notificTION");
		
        Vector<String> v=new Vector<String> ();
		
		JSONObject object = new JSONObject();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
       User uname=User.getCurrentActiveUser();
		Query gaeQuery = new Query("chatgroup");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("fname1").toString().equals(uname.getName())||entity.getProperty("fname2").toString().equals(uname.getName())
					||entity.getProperty("fname3").toString().equals(uname.getName())||
					entity.getProperty("fname4").toString().equals(uname.getName())||
					entity.getProperty("fname5").toString().equals(uname.getName())) {
				
				long key = entity.getKey().getId();
				Entity user_friendrequest = new Entity("chatgroup",key );
			       
				 String message=entity.getProperty("message").toString();
				 String conversationname=entity.getProperty("conversationname").toString();
				 String fname1=entity.getProperty("fname1").toString();
				 String fname2=entity.getProperty("fname2").toString();
				 String fname3=entity.getProperty("fname3").toString();
				 String fname4=entity.getProperty("fname4").toString();
				 String fname5=entity.getProperty("fname5").toString();
				 String name=entity.getProperty("name").toString();
				 JSONObject obj = new JSONObject();
				 obj.put("name", conversationname);
				 obj.put("msg", message);
				 v.add(obj.toString());
	
	}
}
		System.out.println(v.toString());
		return v.toString();
	}
}
