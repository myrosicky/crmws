package org.stockws.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.stockws.dao.iface.TaskDao;
import org.stockws.po.Dictionary.Status;
import org.stockws.po.Task;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class TaskDaoImpl implements TaskDao {

	@Override
	public Task getTask(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task findTasksByStatus(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task findTask(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> findTasksByStatus(Status status, Integer fromIndex,
			Integer toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int saveTask(Task task) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteTask(Task task) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

//	private ObjectMapper objectMapper = new ObjectMapper();
//	
//	@Autowired
//	private RedisTemplate<String, String> redisTemplate;
//
//	@Resource(name = "redisTemplate")
//	private HashOperations<String, String, Object> hashOps;
//
//	@Resource(name = "redisTemplate")
//	private SetOperations<String, String> setOps;
//
//	@Resource(name = "redisTemplate")
//	private ZSetOperations<String, String> zsetOps;
//	
//
//	private final String key_onGoingTask = "ogTask",
//			key_completeTask = "compTask", manager = "managerTask%1$x",
//			key_tasks = "tasks", key_taskPattern = "task%1$x";
//	
//	@Override
//	public Task findTask(String id) {
//		return objectMapper.convertValue(hashOps.entries(id), Task.class);
//	}
//	
//	@Override
//	public List<Task> findTasksByStatus(Status status, Integer fromIndex,
//			Integer toIndex) {
//		String statusStr = 
//				status == Status.ONGOING ?  key_onGoingTask : 
//				status == Status.COMPLETED ? key_completeTask : 
//				status == Status.HANDOVER ?  key_completeTask : 
//					null;
//		Set<String> keys =  zsetOps.range(statusStr, fromIndex, toIndex)  ;
//		LinkedList<Task> rtnList = new LinkedList<Task>();
//		if (keys != null) {
//			for (String key : keys) {
//				rtnList.add(findTask(key));
//			}
//		}
//		return rtnList;
//	}
//
//	@Override
//	public Task getTask(String key) {
//		return null;
//	}
//
//	@Override
//	public Task findTasksByStatus(String key) {
//		return null;
//	}
//
//	@Override
//	public int saveTask(Task task) throws Exception {
//		String key_task = "", key_manager = String.format(manager,
//				task.getManagerID());
//		if (task.getId() == 0) {
//			task.setId(setOps.size(key_tasks));
//			key_task = String.format(key_taskPattern, task.getId());
//			setOps.add(key_tasks, key_task);
//			setOps.add(key_manager, key_task);
//			zsetOps.add(key_onGoingTask, key_task,
//					zsetOps.size(key_onGoingTask));
//		} else if (task.getDeadLine().before(new Date())) {
//			key_task = String.format(key_taskPattern, task.getId());
//			zsetOps.remove(key_onGoingTask, key_task);
//			zsetOps.add(key_completeTask, key_task,
//					zsetOps.size(key_onGoingTask));
//		}
//		hashOps.putAll(key_task,
//				objectMapper.convertValue(task, Map.class));
//		return 1;
//	}
//
//	@Override
//	public int deleteTask(Task task) throws Exception {
//		String id = String.format(key_taskPattern, task.getId());
//		setOps.remove(key_tasks, id);
//		zsetOps.remove(key_onGoingTask, id);
//		zsetOps.remove(key_completeTask, id);
//		redisTemplate.delete(id);
//		return 1;
//	}
	
}
