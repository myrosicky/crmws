package org.stockws.service;

import java.util.List;

import org.business.models.applysystem.Task;
import org.business.models.applysystem.Dictionary.Status;

public interface TaskService {
	
	public List<Task> receiveTasks(Status status, Integer fromIndex,
			Integer toIndex);

	public void saveTask(Task newTask);

	public void removeTask(Task task);
	
}