package com.guojc.activiti.processStored;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

public class QueryDeploy {
	/**
	 * 查询部署资源
	 * 
	 * @throws IOException
	 */
	@Test
	public void TestGetResource() throws IOException {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		// 部署一份txt文件
		Deployment dep = repositoryService
				.createDeployment()
				.addClasspathResource(
						"processStored/query-deploy/artifact/GetResource.txt")
				.deploy();

		// 查询资源文件
		InputStream is = repositoryService.getResourceAsStream(dep.getId(),
				"processStored/query-deploy/artifact/GetResource.txt");
		// 读取输入流
		int count = is.available();
		byte[] contents = new byte[count];
		is.read(contents);
		String result = new String(contents);
		// 输入结果
		System.out.println(result);
	}

	@Test
	public void TestGetProcessModel() throws IOException {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		// 部署一份txt文件
		Deployment dep = repositoryService
				.createDeployment()
				.addClasspathResource(
						"processStored/query-deploy/bpmn/getProcessModel.bpmn")
				.deploy();
		// 查询流程定义
		// 查询流程定义实体
		ProcessDefinition def = repositoryService
				.createProcessDefinitionQuery().deploymentId(dep.getId())
				.singleResult();

		// 查询资源文件
		InputStream is = repositoryService.getProcessModel(def.getId());
		// 读取输入流
		int count = is.available();
		byte[] contents = new byte[count];
		is.read(contents);
		String result = new String(contents);
		// 输入结果
		System.out.println(result);
	}

	/**
	 * 查询流程图
	 * 
	 * @throws IOException
	 */
	@Test
	public void TestGetProcessDiagram() throws IOException {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		// 部署一份流程文件与相应的流程图文件
		Deployment dep = repositoryService
				.createDeployment()
				.addClasspathResource(
						"processStored/query-deploy/bpmn/getProcessDiagram.bpmn")
				.addClasspathResource(
						"processStored/query-deploy/bpmn/getProcessDiagram.png")
				.deploy();

		// 查询流程定义
		ProcessDefinition def = repositoryService
				.createProcessDefinitionQuery().deploymentId(dep.getId())
				.singleResult();

		// 查询资源文件
		InputStream is = repositoryService.getProcessDiagram(def.getId());
		// 将输入流转换为图片对象
		BufferedImage image = ImageIO.read(is);
		// 保存为图片文件
		File file = new File("processStored/query-deploy/artifact/result.png");
		if (!file.exists())
			file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		ImageIO.write(image, "png", fos);
		fos.close();
		is.close();
	}

	/**
	 * 查询部署资源名称
	 */
	@Test
	public void TestGetResourceNames() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		// 部署一份流程文件与相应的流程图文件
		Deployment dep = repositoryService
				.createDeployment()
				.addClasspathResource(
						"processStored/query-deploy/bpmn/GetResourceNames.bpmn")
				.addClasspathResource(
						"processStored/query-deploy/bpmn/GetResourceNames.png")
				.deploy();
		// 查询资源文件名称集合
		List<String> names = repositoryService.getDeploymentResourceNames(dep
				.getId());
		for (String name : names) {
			System.out.println(name);
		}
	}

	@Test
	public void TestDeleteDeployment() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务对象
		RepositoryService repositoryService = engine.getRepositoryService();
		// 部署一份流程文件与相应的流程图文件
		Deployment dep = repositoryService
				.createDeployment()
				.addClasspathResource(
						"processStored/query-deploy/bpmn/deleteDeployment.bpmn")
				.deploy();
		// 查询流程定义
		ProcessDefinition def = repositoryService
				.createProcessDefinitionQuery().deploymentId(dep.getId())
				.singleResult();
		// 开始流程
		engine.getRuntimeService().startProcessInstanceById(def.getId());
		try {
			// 删除部署数据失败，此时将会抛出异常，由于cascade为false
			repositoryService.deleteDeployment(dep.getId());
		} catch (Exception e) {
			System.out.println("删除失败，流程开始，没有设置cascade为true");
		}

		System.out.println("============分隔线");
		// 成功删除部署数据
		repositoryService.deleteDeployment(dep.getId(), true);
	}

	@Test
	public void TestDeploymentQuery() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到流程存储服务实例
		RepositoryService repositoryService = engine.getRepositoryService();
		// 写入5条Deployment数据
		Deployment depA = repositoryService.createDeployment()
				.addString("a1", "a1").addString("a2", "a2")
				.addString("a3", "a3").name("a").deploy();
		Deployment depB = repositoryService.createDeployment()
				.addString("b1", "b1").addString("b2", "b2")
				.addString("b3", "b3").name("b").deploy();
		Deployment depC = repositoryService.createDeployment()
				.addString("c1", "c1").addString("c2", "c2")
				.addString("c3", "c3").name("c").deploy();
		Deployment depD = repositoryService.createDeployment()
				.addString("d1", "d1").addString("d2", "d2")
				.addString("d3", "d3").name("da").deploy();
		Deployment depE = repositoryService.createDeployment()
				.addString("e1", "e1").addString("e2", "e2")
				.addString("e3", "e3").name("eb").deploy();
		
		
		// deploymentId方法
		Deployment depAQuery = repositoryService.createDeploymentQuery()
				.deploymentId(depA.getId()).singleResult();
		System.out.println("根据id查询：" + depAQuery.getName());
		
		// deploymentName方法
		Deployment depBQuery = repositoryService.createDeploymentQuery()
				.deploymentName("b").singleResult();
		System.out.println("查询名称为b：" + depBQuery.getName());
		
		// deploymentNameLike, 模糊查询，结果集为2
		List<Deployment> depCQuery = repositoryService.createDeploymentQuery()
				.deploymentNameLike("%b%").list();
		System.out.println("模糊查询b，结果数量：" + depCQuery.size());
	}
}
