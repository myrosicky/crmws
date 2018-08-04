package org.stockws.model;

import java.io.Serializable;

public class MenuDO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
	  private String id;
	  public String getId() {
          return id;
	  }
	  public void setId(String id) {
	      this.id = id;
	  }
	  
    
	  private String name;
	  public String getName() {
          return name;
	  }
	  public void setName(String name) {
	      this.name = name;
	  }
	  
    
	  private String role;
	  public String getRole() {
          return role;
	  }
	  public void setRole(String role) {
	      this.role = role;
	  }
	  
    
	  private String time;
	  public String getTime() {
          return time;
	  }
	  public void setTime(String time) {
	      this.time = time;
	  }
	  
    
	  private String owner;
	  public String getOwner() {
          return owner;
	  }
	  public void setOwner(String owner) {
	      this.owner = owner;
	  }
	  
	
	 @Override
     public String toString() {
         return "MenuDO ["  
         	+ "id=" + id  + ", " 
         	+ "name=" + name  + ", " 
         	+ "role=" + role  + ", " 
         	+ "time=" + time  + ", " 
         	+ "owner=" + owner 
         + "]";
     }
    
}
