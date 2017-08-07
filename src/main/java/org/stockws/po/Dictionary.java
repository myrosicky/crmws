package org.stockws.po;

public class Dictionary {
	public enum Status {

		ONGOING("ONGOING","进行中")
		, COMPLETED("COMPLETED","已完成")
		, HANDOVER("HANDOVER","交给同事")
		;
		
		DictionaryItem dict;
		Status(String value, String comment){
			dict = new DictionaryItem("status",value,comment);
		}
		public DictionaryItem getDictionary(){
			return dict;
		}

	}
	
	public enum Category {

		EMAIL("EMAIL","邮件")
		,PHONE("PHONE","电话");
		
		DictionaryItem dict;
		Category(String value, String comment){
			dict = new DictionaryItem("category",value,comment);
		}
		public DictionaryItem getDictionary(){
			return dict;
		}

	}

}
