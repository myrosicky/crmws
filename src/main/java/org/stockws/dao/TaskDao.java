package org.stockws.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.stockws.model.Task;
import org.stockws.model.Dictionary.Status;

public interface TaskDao extends CrudRepository<Task, Long> {

	public Task findTasksByStatus(String key);

	public Task findTaskById(String key);

	public List<Task> findTasksByStatus(Status status, Integer fromIndex,
			Integer toIndex);
	
	public int deleteTaskById(long id) throws Exception;

}
