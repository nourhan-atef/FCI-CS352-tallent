package com.FCI.SWE.Controller;

import java.util.Vector;

public class ChatSubject implements Subject {
	Vector <Observer> obsvector;
	
	public ChatSubject()
	{
		this.obsvector=new Vector <Observer>();
		
	}

	@Override
	public void addOserver(ChatObserver  observer) {
		if(observer==null){
			throw new NullPointerException("Null Observer");
		}
		else{
			obsvector.add(observer);
		}
		
	}

	@Override
	public void notifyObserver() {

			for(Observer observer:obsvector){
				observer.addMessage();
				
			  
			
		}
		
	}

	

}
