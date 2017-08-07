package org.stockws.dao.iface;


import java.util.List;

import org.stockws.po.Task;
import org.stockws.po.Dictionary.Status;

public interface TaskDao {

	public Task getTask(String key);

	public Task findTasksByStatus(String key);

	public Task findTask(String key);

	public List<Task> findTasksByStatus(Status status, Integer fromIndex,
			Integer toIndex);
	
	public int saveTask(Task task) throws Exception;
	public int deleteTask(Task task) throws Exception;
	

}
