package org.stockws.model;

import java.io.Serializable;

public class QueryProductDO implements Serializable {

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
	  
    
	  private String price;
	  public String getPrice() {
          return price;
	  }
	  public void setPrice(String price) {
	      this.price = price;
	  }
	  
    
	  private String shopID;
	  public String getShopID() {
          return shopID;
	  }
	  public void setShopID(String shopID) {
	      this.shopID = shopID;
	  }
	  
	
	 @Override
     public String toString() {
         return "QueryProductDO ["  
         	+ "id=" + id  + ", " 
         	+ "name=" + name  + ", " 
         	+ "price=" + price  + ", " 
         	+ "shopID=" + shopID 
         + "]";
     }
    
}
