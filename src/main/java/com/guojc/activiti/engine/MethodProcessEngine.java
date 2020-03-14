package com.guojc.activiti.engine;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.DynamicBpmnService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodProcessEngine {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MethodProcessEngine.class);

	/**
	 * 获取各个业务组件实例
	 */
	@Test
	public void TestGetService() {
		// 读取流程引擎配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("engine/service.xml");
		// 创建流程引擎
		ProcessEngine engine = config.buildProcessEngine();
		// 得到各个业务组件实例

		// 提供一系列管理流程定义和流程部署的API
		RepositoryService repositoryService = engine.getRepositoryService();
		// 在流程运行时对流程实例进行管理与控制
		RuntimeService runtimeService = engine.getRuntimeService();
		// 对流程任务进行管理,例如任务提醒,任务完成和创建任务等
		TaskService taskService = engine.getTaskService();
		// 提供对流程角色数据进行管理的API,这些角色数据包括用户组、用户以及他们之间的关系
		IdentityService identityService = engine.getIdentityService();
		// 提供对流程引擎进行管理和维护的服务
		ManagementService managementService = engine.getManagementService();
		// 对流程的历史数据进行操作,包括查询、删除这些历史数据
		HistoryService historyService = engine.getHistoryService();
		// 使用该服务可以不需要重新部署流程模型就可以实现对流程模型的部分修改
		DynamicBpmnService dynamicBpmnService = engine.getDynamicBpmnService();
		// 输出类名
		System.out.println(repositoryService.getClass().getName());
		System.out.println(runtimeService.getClass().getName());
		System.out.println(taskService.getClass().getName());
		System.out.println(identityService.getClass().getName());
		System.out.println(managementService.getClass().getName());
		System.out.println(historyService.getClass().getName());
		System.out.println(dynamicBpmnService.getClass().getName());
	}

	@Test
	public void TestCloseProcessEngine() throws InterruptedException {

		// 读取配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("engine/close.xml");
		// 创建流程引擎
		ProcessEngine engine = config.buildProcessEngine();
		LOGGER.info("完成流程引擎创建 = {}", engine);
		Thread.sleep(10000);
		// 执行close方法
		engine.close();
	}

	@Test
	public void TestNameProcessEngine() throws InterruptedException {
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("engine/name.xml");
		// 设置流程引擎名称
		config.setProcessEngineName("test");
		ProcessEngine engine = config.buildProcessEngine();

		LOGGER.info("创建的引擎实例的名称： = {}", engine.getName());
		Map<String, ProcessEngine> processEngines = ProcessEngines
				.getProcessEngines();
		
		Iterator<Entry<String, ProcessEngine>> iterMap = processEngines
				.entrySet().iterator();
		
		while (iterMap.hasNext()) {
			Entry<String, ProcessEngine> entry = iterMap.next();
			System.out.println("key为:" + entry.getKey() + ", value为:"
					+ entry.getValue());
		}

		// 根据名称查询流程引擎
		ProcessEngine engineTest = ProcessEngines.getProcessEngine("test");
		
		Map<String, ProcessEngine> processEngines2 = ProcessEngines.getProcessEngines();
		
		LOGGER.info("processEngines2 = {}",processEngines2);
		LOGGER.info("创建的引擎实例： = {}", engine);
		LOGGER.info("查询的引擎实例： = {}", engineTest);
		engineTest = ProcessEngines.getProcessEngine("default");
		LOGGER.info("查询的default引擎实例： = {}", engineTest);
		
	}

}
