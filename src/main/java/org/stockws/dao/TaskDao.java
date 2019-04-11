package org.stockws.dao;


import java.util.List;

import org.business.models.applysystem.Task;
import org.business.models.applysystem.Dictionary.Status;
import org.springframework.data.repository.CrudRepository;

public interface TaskDao extends CrudRepository<Task, Long> {

	public Task findByStatus(String status);

	public Task findById(String key);

	public int deleteById(long id) throws Exception;

}
