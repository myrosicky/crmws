package org.stockws.service;

import java.util.List;

import org.business.models.applysystem.Apply;

public interface ApplyService {

	public List<Apply> query(String area, String country, String province, String city);

}
