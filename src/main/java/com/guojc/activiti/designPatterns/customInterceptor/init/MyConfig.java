package com.guojc.activiti.designPatterns.customInterceptor.init;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyConfig {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyConfig.class);

	public static void main(String[] args) {
		// 创建Activiti配置对象
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("customInterceptor/my-config.xml");
		// 初始化流程引擎
		ProcessEngine engine = config.buildProcessEngine();
		// 部署一个最简单的流程
		engine.getRepositoryService()
				.createDeployment()
				.addClasspathResource(
						"customInterceptor/bpmn/config.bpmn20.xml").deploy();
		// 构建流程参数
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("day", 10);
		// 开始流程
		engine.getRuntimeService().startProcessInstanceByKey("vacationProcess",
				vars);

		LOGGER.info("执行完毕");
		System.exit(0);
	}

}
