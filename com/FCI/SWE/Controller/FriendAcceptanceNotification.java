package com.FCI.SWE.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class FriendAcceptanceNotification extends Command {

	public String excute(String n){
		
		
		Vector<String> v=new Vector<String> ();
		
		JSONObject object = new JSONObject();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
       User uname=User.getCurrentActiveUser();
		Query gaeQuery = new Query("request");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("name").toString().equals(uname.getName())) {
				
				long key = entity.getKey().getId();
				Entity user_friendrequest = new Entity("request",key );
			       
				 String fname=entity.getProperty("fname").toString();
				 v.add(fname);
					Map<String, Vector> map = new HashMap<String, Vector>();
					User user = User.getUser(object.toJSONString());
					map.put("fname", v);
					System.out.print(Response.ok(new Viewable("/jsp/acceptFriend", map)).build());
				 
				
			}

		
		
		
		}
		return n;
	}
}
