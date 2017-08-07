package org.stockws.service.iface;

import java.util.List;



import org.stockws.po.Task;
import org.stockws.po.Dictionary.Status;

public interface TaskService {
	
	public List<Task> receiveTasks(Status status, Integer fromIndex,
			Integer toIndex);

	public void saveTask(Task newTask);

	public void removeTask(Task task);
	
}