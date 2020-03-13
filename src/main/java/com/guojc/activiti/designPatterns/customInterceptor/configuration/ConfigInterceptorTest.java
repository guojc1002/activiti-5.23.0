package com.guojc.activiti.designPatterns.customInterceptor.configuration;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigInterceptorTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigInterceptorTest.class);

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule(
			"customInterceptor/configuration/my-config.xml");// 传入自定义的mdc配置文件

	@Test
	@Deployment(resources = { "customInterceptor/configuration/bpmn/config.bpmn20.xml" })
	// 流程定义文件
	public void test() {
		ProcessInstance processInstance = activitiRule.getRuntimeService()
				.startProcessInstanceByKey("vacationProcess");
		Task task = activitiRule.getTaskService().createTaskQuery()
				.singleResult();
		activitiRule.getTaskService().complete(task.getId());
		LOGGER.info("processInstance = {}",processInstance);
	}
	
	
	
}
