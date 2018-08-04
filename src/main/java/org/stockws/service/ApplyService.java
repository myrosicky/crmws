package org.stockws.service;

import java.util.List;

import org.stockws.model.Apply;

public interface ApplyService {

	public List<Apply> query(String country, String area, String city);

}
