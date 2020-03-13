package com.guojc.activiti.config;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class O03_History_Config {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(O03_History_Config.class);

	@Test
	public void TestNone() {
		// 读取Activiti配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/historyConfig/history-none.xml");
		// 初始化流程引擎
		ProcessEngine engine = config.buildProcessEngine();
		// 得到流程存储对角
		RepositoryService repositoryService = engine.getRepositoryService();
		// 部署流程文件
		repositoryService
				.createDeployment()
				.addClasspathResource(
						"config/historyConfig/bpmn/history.bpmn20.xml")
				.deploy();
		// 得到运行服务对象
		RuntimeService runtimeService = engine.getRuntimeService();
		// 创建参数
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("day", 10);
		// 开始流程
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("vacationProcess", vars);
		LOGGER.info("processInstance {}", processInstance);
		System.exit(0);
	}

	@Test
	public void TestActivity() {
		// 读取Activiti配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/historyConfig/history-activity.xml");
		// 初始化流程引擎
		ProcessEngine engine = config.buildProcessEngine();
		// 得到流程存储对角
		RepositoryService repositoryService = engine.getRepositoryService();
		// 部署流程文件
		repositoryService
				.createDeployment()
				.addClasspathResource(
						"config/historyConfig/bpmn/history.bpmn20.xml")
				.deploy();
		// 得到运行服务对象
		RuntimeService runtimeService = engine.getRuntimeService();
		// 创建参数
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("day", 10);
		// 开始流程
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("vacationProcess", vars);
		LOGGER.info("processInstance {}", processInstance);
		System.exit(0);
	}

	@Test
	public void TestAudit() {
		// 读取Activiti配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/historyConfig/history-audit.xml");
		// 初始化流程引擎
		ProcessEngine engine = config.buildProcessEngine();
		// 得到流程存储对角
		RepositoryService repositoryService = engine.getRepositoryService();
		// 部署流程文件
		repositoryService
				.createDeployment()
				.addClasspathResource(
						"config/historyConfig/bpmn/history.bpmn20.xml")
				.deploy();
		// 得到运行服务对象
		RuntimeService runtimeService = engine.getRuntimeService();
		// 创建参数
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("day", 10);
		// 开始流程
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("vacationProcess", vars);
		LOGGER.info("processInstance {}", processInstance);
		System.exit(0);
	}

	@Test
	public void TestFull() {
		// 读取Activiti配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/historyConfig/history-full.xml");
		// 初始化流程引擎
		ProcessEngine engine = config.buildProcessEngine();
		// 得到流程存储对角
		RepositoryService repositoryService = engine.getRepositoryService();
		// 部署流程文件
		repositoryService
				.createDeployment()
				.addClasspathResource(
						"config/historyConfig/bpmn/history.bpmn20.xml")
				.deploy();
		// 得到运行服务对象
		RuntimeService runtimeService = engine.getRuntimeService();
		// 创建参数
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("day", 10);
		// 开始流程
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("vacationProcess", vars);
		LOGGER.info("processInstance {}", processInstance);
		System.exit(0);

	}

}
