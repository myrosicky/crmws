package org.stockws.po;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.solr.client.solrj.beans.Field;

/**
 * the persistence class of Buser Table
 * 
 * @author liangli
 * 
 */

@Entity
@Table
public class CrmUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private int valid;
	private long  id;
	private String username, password, general_ip, remark, category;
	private Date registerDate, loginLastTime;

	@org.springframework.data.annotation.Id
	@Field
	@SequenceGenerator(name = "id_SEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_SEQ")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(insertable = false, columnDefinition = "number(1) default 1")
	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	@Field
	@Column(nullable = false, length = 20)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(nullable = false, length = 100)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length = 40)
	public String getGeneral_ip() {
		return general_ip;
	}

	public void setGeneral_ip(String general_ip) {
		this.general_ip = general_ip;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(updatable = false, columnDefinition = " date default sysdate", insertable = false)
	public Date getregisterDate() {
		return registerDate;
	}

	public void setregisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@Column(columnDefinition = " date default sysdate", insertable = false)
	public Date getLoginLastTime() {
		return loginLastTime;
	}

	public void setLoginLastTime(Date loginLastTime) {
		if (loginLastTime != null)
			this.loginLastTime = loginLastTime;
	}

	@Column
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
