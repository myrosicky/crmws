package org.stockws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stockws.model.Task;
import org.stockws.model.Dictionary.Status;
import org.stockws.service.iface.TaskService;

@RestController
public class TaskServiceRestController implements TaskService {

	private @Autowired TaskService taskService;
	
	@Override
	@RequestMapping("/receiveTasks.do")
	public List<Task> receiveTasks( Status status, Integer fromIndex,
			Integer toIndex) {
		return taskService.receiveTasks(status, fromIndex, toIndex);
	}

	@Override
	@RequestMapping("/saveTask.do")
	public void saveTask(Task newTask) {
		taskService.saveTask(newTask);
	}

	@Override
	@RequestMapping("/removeTask.do")
	public void removeTask(Task task) {
		taskService.removeTask(task);
	}
}
