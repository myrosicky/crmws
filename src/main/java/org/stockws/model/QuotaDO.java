package org.stockws.model;

import java.io.Serializable;

public class QuotaDO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
	  private String id;
	  public String getId() {
          return id;
	  }
	  public void setId(String id) {
	      this.id = id;
	  }
	  
    
	  private String user;
	  public String getUser() {
          return user;
	  }
	  public void setUser(String user) {
	      this.user = user;
	  }
	  
    
	  private String month;
	  public String getMonth() {
          return month;
	  }
	  public void setMonth(String month) {
	      this.month = month;
	  }
	  
    
	  private String time;
	  public String getTime() {
          return time;
	  }
	  public void setTime(String time) {
	      this.time = time;
	  }
	  
    
	  private int amount;
	  public int getAmount() {
          return amount;
	  }
	  public void setAmount(int amount) {
	      this.amount = amount;
	  }
	  
    
	  private String year;
	  public String getYear() {
          return year;
	  }
	  public void setYear(String year) {
	      this.year = year;
	  }
	  
    
	  private String state;
	  public String getState() {
          return state;
	  }
	  public void setState(String state) {
	      this.state = state;
	  }
	  
	
	 @Override
     public String toString() {
         return "QuotaDO ["  
         	+ "id=" + id  + ", " 
         	+ "user=" + user  + ", " 
         	+ "month=" + month  + ", " 
         	+ "time=" + time  + ", " 
         	+ "amount=" + amount  + ", " 
         	+ "year=" + year  + ", " 
         	+ "state=" + state 
         + "]";
     }
    
}
