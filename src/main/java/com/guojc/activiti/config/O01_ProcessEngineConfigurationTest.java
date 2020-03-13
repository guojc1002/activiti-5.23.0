package com.guojc.activiti.config;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class O01_ProcessEngineConfigurationTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(O01_ProcessEngineConfigurationTest.class);

	@Test
	public void testConfig1() {
		// 使用Activiti默认的方式创建ProcessEngineConfiguration
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResourceDefault();
		LOGGER.info("configuration = {} ", configuration);
	}

	@Test
	public void testConfig2() {
		// 指定配置文件创建ProcessEngineConfiguration
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/engine/my-activiti1.xml");
		LOGGER.info("configuration = {} ", configuration);
	}

	@Test
	public void testConfig3() {
		// 指定配置文件创建ProcessEngineConfiguration
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource(
						"config/engine/my-activiti2.xml", "test");
		LOGGER.info("configuration = {} ", configuration);
	}

	@Test
	public void testConfig4() throws FileNotFoundException {
		// 得到文件输入流
		InputStream inputStream = O01_ProcessEngineConfigurationTest.class
				.getClassLoader()
				.getResourceAsStream("config/engine/input-stream.xml");
		// 使用createProcessEngineConfigurationFromInputStream方法创建ProcessEngineConfiguration
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromInputStream(inputStream);
		LOGGER.info("configuration = {} ", configuration);
	}

	@Test
	public void testConfig5() throws FileNotFoundException {
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();
		// 默认值为 false
		LOGGER.info("databaseSchemaUpdate = {} ",
				configuration.getDatabaseSchemaUpdate());
		LOGGER.info("JdbcUrl = {} ", configuration.getJdbcUrl());
	}

	@Test
	public void testConfig6() throws FileNotFoundException {
		ProcessEngineConfiguration configuration = ProcessEngineConfiguration
				.createStandaloneInMemProcessEngineConfiguration();
		// 默认值为 false
		LOGGER.info("databaseSchemaUpdate = {} ",
				configuration.getDatabaseSchemaUpdate());
		LOGGER.info("JdbcUrl = {} ", configuration.getJdbcUrl());
	}

}
