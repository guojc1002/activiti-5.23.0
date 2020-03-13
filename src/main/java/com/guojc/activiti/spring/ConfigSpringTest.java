package com.guojc.activiti.spring;

import static org.junit.Assert.assertNotNull;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.guojc.activiti.DemoMain;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:activiti-context.xml" })
public class ConfigSpringTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

	@Autowired
	RuntimeService runtimeService;

	@Autowired
	TaskService taskService;

	@Autowired
	HistoryService historyService;

	@Test
	public void test() {

		ProcessInstance processInstance = runtimeService.startProcessInstanceById("my-process");
		assertNotNull(processInstance);

		Task task = taskService.createTaskQuery().singleResult();
		taskService.complete(task.getId());

		LOGGER.info("执行完毕");
	}

}
