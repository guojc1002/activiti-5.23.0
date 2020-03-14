package com.guojc.activiti.processStored;

import java.util.Iterator;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.activiti.engine.impl.persistence.deploy.ProcessDefinitionCacheEntry;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessCache {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProcessCache.class);

	@Test
	public void TestDefaultCache() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		ProcessEngineConfigurationImpl config = (ProcessEngineConfigurationImpl) engine
				.getProcessEngineConfiguration();
		// 得到流程存储服务实例
		RepositoryService repositoryService = engine.getRepositoryService();
		// 进行10次部署
		for (int i = 0; i < 10; i++) {
			repositoryService
					.createDeployment()
					.addClasspathResource(
							"processStored/process-cache/bpmn/default-cache.bpmn")
					.name("dep_" + i).key("key_" + i).deploy();
		}
		// 获取缓存
		DefaultDeploymentCache<ProcessDefinitionCacheEntry> cache = (DefaultDeploymentCache<ProcessDefinitionCacheEntry>) config
				.getProcessDefinitionCache();
		LOGGER.info("cache = {}", cache.size());
	}

	@Test
	public void TestUserLimitCache() {
		// 创建流程引擎
		ProcessEngineConfigurationImpl config = (ProcessEngineConfigurationImpl) ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("processStored/process-cache/use-limit.cfg.xml");
		ProcessEngine engine = config.buildProcessEngine();
		// 得到流程存储服务实例
		RepositoryService repositoryService = engine.getRepositoryService();
		// 进行10次部署
		for (int i = 0; i < 10; i++) {
			repositoryService
					.createDeployment()
					.addClasspathResource(
							"processStored/process-cache/bpmn/default-cache.bpmn")
					.name("dep_" + i).key("key_" + i).deploy();
		}
		// 获取缓存
		DefaultDeploymentCache<ProcessDefinitionCacheEntry> cache = (DefaultDeploymentCache<ProcessDefinitionCacheEntry>) config
				.getProcessDefinitionCache();
		LOGGER.info("cache = {}", cache.size());
	}

	@Test
	public void TestCustomCache() {
		// 创建流程引擎
		ProcessEngineConfigurationImpl config = (ProcessEngineConfigurationImpl) ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("processStored/process-cache/my-cache.cfg.xml");
		
		config.setProcessDefinitionCache(new MyCacheBean<ProcessDefinitionCacheEntry>());
		ProcessEngine engine = config.buildProcessEngine();
		// 得到流程存储服务实例
		RepositoryService repositoryService = engine.getRepositoryService();
		// 进行10次部署
		for (int i = 0; i < 10; i++) {
			repositoryService
					.createDeployment()
					.addClasspathResource(
							"processStored/process-cache/bpmn/default-cache.bpmn")
					.name("dep_" + i).key("key_" + i).deploy();
		}
		// 获取缓存
		MyCacheBean<ProcessDefinitionCacheEntry> cache = (MyCacheBean<ProcessDefinitionCacheEntry>) config
				.getProcessDefinitionCache();

		// 遍历缓存，输出Map中的key
		for (Iterator<String> it = cache.cache.keySet().iterator(); it
				.hasNext();) {
			String key = (String) it.next();
			System.out.println(key);
		}
	}

	
}
