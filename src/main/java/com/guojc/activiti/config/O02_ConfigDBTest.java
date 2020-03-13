package com.guojc.activiti.config;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class O02_ConfigDBTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(O02_ConfigDBTest.class);

	@Test
	public void testDatabaseType() {
		// 读取Activiti配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/db/database-type.xml");
		// 启动Activiti
		ProcessEngine engine = config.buildProcessEngine();

		LOGGER.info("ProcessEngine {} ", engine);
		// 关闭流程引擎
		engine.close();
	}

	@Test
	public void testDBCPConfig() throws SQLException {
		// 读取 dbcp-config.xml配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/db/dbcp-config.xml");
		// 能正常输出，即完成配置
		DataSource ds = config.getDataSource();
		// 查询数据库元信息，如果能查询则表示连接成功
		ds.getConnection().getMetaData();
		// 结果为 org.apache.commons.dbcp.BasicDataSource
		System.out.println(ds.getClass().getName());
	}

	@Test
	public void testDBCPCoding() throws SQLException {
		// 创建DBCP数据源
		BasicDataSource ds = new BasicDataSource();
		// 设置JDBC连接的各个属性
		ds.setUsername("root");
		ds.setPassword("mysql");
		ds.setUrl("jdbc:mysql://192.168.153.3:3306/activiti");
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		// 验证是否连接成功
		ds.getConnection().getMetaData();
		// 读取Activiti配置文件
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/db/dbcp-coding.xml");
		// 为ProcessEngineConfiguration设置dataSource属性
		config.setDataSource(ds);
		System.out.println(config.getDataSource());
	}

	@Test
	public void testC3P0Config() throws SQLException {
		// 读取c3p0-config.xml配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/db/c3p0-config.xml");
		// 能正常输出，即完成配置
		DataSource ds = config.getDataSource();
		// 查询数据库元信息，如果能查询则表示连接成功
		ds.getConnection().getMetaData();
		// 结果为 com.mchange.v2.c3p0.ComboPooledDataSource
		System.out.println(config.getDataSource().getClass().getName());
	}

	@Test
	public void testC3P0Coding() throws SQLException, PropertyVetoException {
		// 创建C3P0数据源
		ComboPooledDataSource ds = new ComboPooledDataSource();
		// 设置JDBC连接的各个属性
		ds.setUser("root");
		ds.setPassword("mysql");
		ds.setJdbcUrl("jdbc:mysql://192.168.153.3:3306/activiti");
		ds.setDriverClass("com.mysql.jdbc.Driver");
		// 验证是否连接成功
		ds.getConnection().getMetaData();

		// 读取Activiti配置文件
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/db/c3p0-coding.xml");
		// 为ProcessEngineConfiguration设置dataSource属性
		config.setDataSource(ds);
		System.out.println(config.getDataSource());
	}

	@Test
	public void testDBPolicyConfiguration() {
		// 读取Activiti配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/db/schemaUpdate-create-drop.xml");

		// 读取Activiti配置
		config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/db/schemaUpdate-false.xml"); // 读取Activiti配置
		
		config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("config/db/schemaUpdate-true.xml");

		// 启动Activiti
		ProcessEngine engine = config.buildProcessEngine();
		// 关闭流程引擎
		engine.close();
	}

}
