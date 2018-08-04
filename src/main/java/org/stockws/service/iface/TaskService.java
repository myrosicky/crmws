package org.stockws.service.iface;

import java.util.List;




import org.stockws.model.Task;
import org.stockws.model.Dictionary.Status;

public interface TaskService {
	
	public List<Task> receiveTasks(Status status, Integer fromIndex,
			Integer toIndex);

	public void saveTask(Task newTask);

	public void removeTask(Task task);
	
}