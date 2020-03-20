package com.guojc.activiti.taskManagement;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class TaskControl {

	// 保存一个task
	private void saveTask(TaskService taskService, String id) {
		Task task1 = taskService.newTask(id);
		taskService.saveTask(task1);
	}

	@Test
	public void TestNewTask() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 获取任务服务组件
		TaskService taskService = engine.getTaskService();
		// 保存第一个Task，不设置ID
		Task task1 = taskService.newTask();
		taskService.saveTask(task1);
		// 保存第二个Task，设置ID
		Task task2 = taskService.newTask("审核任务");
		taskService.saveTask(task2);
	}

	@Test
	public void TestDeleteTask() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 获取任务服务组件
		TaskService taskService = engine.getTaskService();
		// 保存若干个Task
		for (int i = 1; i < 10; i++) {
			saveTask(taskService, String.valueOf(i));
		}
		// 删除task（不包括历史数据和子任务）
		taskService.deleteTask("1");
		
		// 删除task（包括历史数据和子任务）
		taskService.deleteTask("2", true);
		
		// 删除多个task（不包括历史数据和子任务）
		List<String> ids = new ArrayList<String>();
		ids.add("3");
		ids.add("4");
		taskService.deleteTasks(ids);
		
		// 删除多个task（包括历史数据和子任务）
		ids = new ArrayList<String>();
		ids.add("5");
		ids.add("6");
		taskService.deleteTasks(ids, true);
		
		// 再删除ID为3的task，此时3的历史数据也会被删除（如果有的话）
		taskService.deleteTask("3", true);
	}
}
