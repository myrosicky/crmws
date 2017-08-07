package org.stockws.po;

public class DictionaryItem {
	private String name,value,comment;

	public DictionaryItem(String name2, String value2, String comment2) {
		setName(name2);
		setValue(value2);
		setComment(comment2);
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getComment() {
		return comment;
	}

	public void setName(String name) {
		if (name != null)
			this.name = name;
	}

	public void setValue(String value) {
		if (value != null)
			this.value = value;
	}

	public void setComment(String comment) {
		if (comment != null)
			this.comment = comment;
	}
	
	
}
