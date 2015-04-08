package com.FCI.SWE.Controller;

import java.util.Vector;

public interface Subject {
	
	  ChatObserver observer=null;
	  
	  public void addOserver(ChatObserver observer);
	  void notifyObserver();

}
