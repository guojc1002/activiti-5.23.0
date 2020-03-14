package com.guojc.activiti.processStored;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;

public class AddResource {

	@Test
	public void TestAddInputStream() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务实例
		RepositoryService repositoryService = engine.getRepositoryService();
		// 第一个资源输入流
		InputStream is1 = AddResource.class.getClassLoader()
				.getResourceAsStream("processStored/flow_inputstream1.png");

		// 第二个资源输入流
		InputStream is2 = AddResource.class.getClassLoader()
				.getResourceAsStream("processStored/flow_inputstream2.png");

		// 创建DeploymentBuilder实例
		DeploymentBuilder builder = repositoryService.createDeployment();
		// 为DeploymentBuilder添加资源输入流
		builder.addInputStream("inputA", is1);
		builder.addInputStream("inputB", is2);
		// 执行部署方法
		builder.deploy();
	}

	@Test
	public void TestAddClasspathResource() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		// 创建DeploymentBuilder实例
		DeploymentBuilder builder = repositoryService.createDeployment();
		// 添加classpath下的资源
		builder.addClasspathResource("processStored/classpath.png");
		// 执行部署（写入到数据库中）
		builder.deploy();
	}

	@Test
	public void TestAddString() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		// 创建DeploymentBuilder实例
		DeploymentBuilder builder = repositoryService.createDeployment();
		// 添加String资源
		builder.addString("test", "this is string method");
		// 执行部署（写入到数据库中）
		builder.deploy();
	}

	@Test
	public void TestAddZipInputStream() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		// 创建DeploymentBuilder实例
		DeploymentBuilder builder = repositoryService.createDeployment();
		// 获取zip文件的输入流
		InputStream fis = AddResource.class.getClassLoader()
				.getResourceAsStream("processStored/ZipInputStream.zip");
		// 读取zip文件，创建ZipInputStream对象
		ZipInputStream zi = new ZipInputStream(fis);
		// 添加Zip压缩包资源
		builder.addZipInputStream(zi);
		// 执行部署（写入到数据库中）
		builder.deploy();
	}

	@Test
	public void TestAddBpmnModel() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		// 创建DeploymentBuilder实例
		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.addBpmnModel("MyCodeProcess", createProcessModel())
				.name("MyCodeDeploy").deploy();
	}

	private BpmnModel createProcessModel() {
		// 创建BPMN模型对象
		BpmnModel model = new BpmnModel();
		org.activiti.bpmn.model.Process process = new org.activiti.bpmn.model.Process();
		model.addProcess(process);
		process.setId("myProcess");
		process.setName("My Process");
		// 开始事件
		StartEvent startEvent = new StartEvent();
		startEvent.setId("startEvent");
		process.addFlowElement(startEvent);
		// 用户任务
		UserTask userTask = new UserTask();
		userTask.setName("User Task");
		userTask.setId("userTask");
		process.addFlowElement(userTask);
		// 结束事件
		EndEvent endEvent = new EndEvent();
		endEvent.setId("endEvent");
		process.addFlowElement(endEvent);

		// 添加流程顺序
		process.addFlowElement(new SequenceFlow("startEvent", "userTask"));
		process.addFlowElement(new SequenceFlow("userTask", "endEvent"));
		return model;
	}

	@Test
	public void TestName() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		// 创建DeploymentBuilder实例
		DeploymentBuilder builder = repositoryService.createDeployment();
		// 设置各个属性
		builder.name("crazyit").tenantId("tenanId").key("myKey")
				.category("myCategory");
		// 执行部署（写入到数据库中）
		builder.deploy();
	}

	@Test
	public void TestDuplicateFilter() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务实例
		RepositoryService repositoryService = engine.getRepositoryService();
		// 创建第一个部署对象
		DeploymentBuilder builderA = repositoryService.createDeployment();
		builderA.addClasspathResource("processStored/DuplicateFilter.txt");
		builderA.name("DuplicateFilterA");
		builderA.deploy();

		// 由于资源一样，并且调用了enableDuplicateFiltering方法，因此不会再写入到数据库中
		DeploymentBuilder builderB = repositoryService.createDeployment();
		builderB.addClasspathResource("processStored/DuplicateFilter.txt");
		builderB.name("DuplicateFilterA");
		builderB.enableDuplicateFiltering();
		builderB.deploy();

		// 由于资源发生变化，即使调用了enableDuplicateFiltering方法，也会写到数据库中
		DeploymentBuilder builderC = repositoryService.createDeployment();
		builderC.addClasspathResource("artifact/DuplicateFilterB.txt");
		builderC.name("DuplicateFilterA");
		builderC.enableDuplicateFiltering();
		builderC.deploy();
	}

	@Test
	public void TestDisableValidation() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务实例
		RepositoryService repositoryService = engine.getRepositoryService();
		// 部署一份错误的xml文件，不会报错
		DeploymentBuilder builderA = repositoryService.createDeployment();
		builderA.addClasspathResource("processStored/bpmn/xmlError.bpmn")
				.disableSchemaValidation().deploy();
		// 部署一份不可执行bpmn文件，不会报错
		DeploymentBuilder builderB = repositoryService.createDeployment();
		builderB.addClasspathResource("processStored/bpmn/bpmnError.bpmn")
				.disableBpmnValidation().deploy();
	}
}
