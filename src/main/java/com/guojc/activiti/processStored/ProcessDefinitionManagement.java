package com.guojc.activiti.processStored;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

public class ProcessDefinitionManagement {

	@Test
	public void TestProcessDeploy() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		// 创建DeploymentBuilder实例
		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.addClasspathResource(
				"processStored/process-deploy/bpmn/processDeploy.bpmn")
				.deploy();
	}

	@Test
	public void TestDeployDiagram() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务实例
		RepositoryService repositoryService = engine.getRepositoryService();
		// 部署流程描述文件与流程图
		Deployment dep = repositoryService
				.createDeployment()
				.addClasspathResource(
						"processStored/process-deploy/bpmn/diagram.bpmn")
				.addClasspathResource(
						"processStored/process-deploy/bpmn/diagram.png")
				.deploy();
		// 查询流程定义实体
		ProcessDefinition def = repositoryService
				.createProcessDefinitionQuery().deploymentId(dep.getId())
				.singleResult();
		// 输出结果为 bpmn/diagram.vacationProcess.png
		System.out.println(def.getDiagramResourceName());
	}

	@Test
	public void TestNoGenDiagram() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务实例
		RepositoryService repositoryService = engine.getRepositoryService();
		// 部署流程描述文件与流程图
		Deployment dep = repositoryService
				.createDeployment()
				.addClasspathResource(
						"processStored/process-deploy/bpmn/noGenDiagram.bpmn")
				.deploy();
		
		// 查询流程定义实体
		ProcessDefinition def = repositoryService
				.createProcessDefinitionQuery().deploymentId(dep.getId())
				.singleResult();
		// 输出结果为 bpmn/diagram.vacationProcess.png
		System.out.println("自动生成流程图：" + def.getDiagramResourceName());

		// 读取不生成流程图的配置文件
		ProcessEngineConfiguration config = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("processStored/process-deploy/noGenDiagram.cfg.xml");
		ProcessEngine noGenEngine = config.buildProcessEngine();
		RepositoryService noGenService = noGenEngine.getRepositoryService();
		Deployment noGenDep = noGenService
				.createDeployment()
				.addClasspathResource(
						"processStored/process-deploy/bpmn/noGenDiagram.bpmn")
				.deploy();
		ProcessDefinition noGenDef = noGenService
				.createProcessDefinitionQuery().deploymentId(noGenDep.getId())
				.singleResult();
		// 输出结果为null
		System.out.println("不生成流程图，查询资源为：" + noGenDef.getDiagramResourceName());
	}

}
