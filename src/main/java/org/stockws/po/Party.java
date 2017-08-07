package org.stockws.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.solr.client.solrj.beans.Field;

@Entity
@Table
public class Party implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id, valid;
	private CrmUser manager;
	private String name, category, visibleScope, remark;
	private Date manageDate;

	@org.springframework.data.annotation.Id
	@Field
	@SequenceGenerator(name = "PARTY_ID_SEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_ID_SEQ")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(insertable = false, columnDefinition = "number(1) default 1")
	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column
	public String getVisibleScope() {
		return visibleScope;
	}

	public void setVisibleScope(String visibleScope) {
		this.visibleScope = visibleScope;
	}

	@ManyToOne(targetEntity = CrmUser.class)
	@JoinColumn(name = "CrmUserID")
	public CrmUser getManager() {
		return manager;
	}

	public void setManager(CrmUser manager) {
		this.manager = manager;
	}

	@Column(updatable = false, columnDefinition = " date default sysdate", insertable = false)
	public Date getManageDate() {
		return manageDate;
	}

	public void setManageDate(Date manageDate) {
		this.manageDate = manageDate;
	}

	@Column
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		if (remark != null)
			this.remark = remark;
	}
}
