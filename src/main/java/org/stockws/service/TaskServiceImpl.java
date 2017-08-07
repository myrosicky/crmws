package org.stockws.service;

import java.util.List;







import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.stockws.dao.iface.TaskDao;
import org.stockws.po.DictionaryItem;
import org.stockws.po.Task;
import org.stockws.po.Dictionary.Status;
import org.stockws.service.iface.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TaskDao taskDao;
	private final int maxNumPerReq = 20;

	@Override
	public List<Task> receiveTasks(Status status, Integer fromIndex,
			Integer toIndex) {
		Assert.isTrue(fromIndex >= 0 && toIndex >= 0, "index over ramge");
		Assert.isTrue(toIndex - fromIndex + 1 <= maxNumPerReq, "请求记录总数超出限定数！");
		List<Task> rtnList = taskDao.findTasksByStatus(status,fromIndex,toIndex);
		return rtnList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void saveTask(Task newTask) {
		logger.info("newTask.getId():" + newTask.getId());
		logger.info("newTask.getDeadLine():" + newTask.getDeadLine());
		try {
			taskDao.saveTask(newTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeTask(Task task) {
		 try {
			taskDao.deleteTask(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
