package com.guojc.activiti.engine;

import java.net.URL;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngineInfo;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用ProcessEngineConfiguration的buildProcessEngine方法
 * 
 */
public class BuildProcessEngine {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BuildProcessEngine.class);

	@Test
	public void TestBuildProcessEngine() {
		// 读取配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("engine/build_engine.xml");
		// 创建ProcessEngine
		ProcessEngine engine = config.buildProcessEngine();
		LOGGER.info("engine {} ", engine);

		ProcessEngines.init();
	}

	@Test
	public void TestInitProcessEngine() {
		// 初始化ProcessEngines的Map,再加载Activiti默认的配置文件（classpath下的activiti.cfg.xml文件）
		// 如果与Spring整合，则读取classpath下的activiti-context.xml文件
		ProcessEngines.init();
		// 得到ProcessEngines的Map
		Map<String, ProcessEngine> engines = ProcessEngines.getProcessEngines();
		System.out.println(engines.size());
		System.out.println(engines.get("default"));
		LOGGER.info("engines.size() {} = ", engines.size());

		LOGGER.info("engines.get('default') {} = ", engines.get("default"));

	}

	@Test
	public void TestUnregisterProcessEngine() {
		// 读取自定义配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("engine/register.xml");
		// 创建ProcessEngine实例
		ProcessEngine engine = config.buildProcessEngine();
		// 获取ProcessEngine的Map
		Map<String, ProcessEngine> engines = ProcessEngines.getProcessEngines();
		System.out.println("注册后引擎数：" + engines.size());
		// 注销ProcessEngine实例
		ProcessEngines.unregister(engine);
		System.out.println("调用unregister后引擎数：" + engines.size());
	}

	@Test
	public void TestRetryProcessEngine() {
		// 得到资源文件的URL实例
		ClassLoader cl = BuildProcessEngine.class.getClassLoader();
		URL url = cl.getResource("engine/retry.xml");
		// 调用retry方法创建ProcessEngine实例
		ProcessEngineInfo info = ProcessEngines.retry(url.toString());
		// 得到流程实例保存对象
		Map<String, ProcessEngine> engines = ProcessEngines.getProcessEngines();
		LOGGER.info("info = {}", info);
		LOGGER.info("调用retry方法后引擎数： {}", engines.size());
	}

	/**
	 * ProcessEngines.destroy():
	 * 对其所维护的所有ProcessEngine实例进行销毁,并且在销毁时会调用所有ProcessEngine实例的close方法,
	 * 实例被销毁的前提是ProcessEngines的初始化状态为true,否则不会被销毁
	 */
	@Test
	public void TestDestroyProcessEngine() {

		// 进行初始化并且返回默认的ProcessEngine实例
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		LOGGER.info("engine = {}",engine);
		LOGGER.info("调用getDefaultProcessEngine方法后引擎数量： = {}", ProcessEngines
				.getProcessEngines().size());
		// 调用销毁方法
		ProcessEngines.destroy();
		// 最终结果为0
		System.out.println("调用destroy方法后引擎数量："
				+ ProcessEngines.getProcessEngines().size());

		// 得到资源文件的URL实例
		ClassLoader cl = BuildProcessEngine.class.getClassLoader();
		URL url = cl.getResource("engine/retry.xml");
		// 调用retry方法创建ProcessEngine实例
		ProcessEngines.retry(url.toString());

		LOGGER.info("只调用 retry方法后引擎数量： = {}", ProcessEngines
				.getProcessEngines().size());

		// 调用销毁方法，没有效果
		ProcessEngines.destroy();
		LOGGER.info("调用destory无效果，引擎数量： = {}", ProcessEngines
				.getProcessEngines().size());

	}

}
