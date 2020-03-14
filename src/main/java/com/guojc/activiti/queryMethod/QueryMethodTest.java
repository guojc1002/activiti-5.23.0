package com.guojc.activiti.queryMethod;

import java.util.List;
import java.util.UUID;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryMethodTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(QueryMethodTest.class);

	// 将用户组数据保存到数据库中
	private void createGroup(IdentityService identityService, String id,
			String name, String type) {
		// 调用newGroup方法创建Group实例
		Group group = identityService.newGroup(id);
		group.setName(name);
		group.setType(type);
		identityService.saveGroup(group);
	}

	@Test
	public void TestListData() {

		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		// 写入5条用户组数据
		createGroup(identityService, "1", "GroupA", "typeA");
		createGroup(identityService, "2", "GroupB", "typeB");
		createGroup(identityService, "3", "GroupC", "typeC");
		createGroup(identityService, "4", "GroupD", "typeD");
		createGroup(identityService, "5", "GroupE", "typeE");

		// 使用list方法查询全部的部署数据
		List<Group> datas = identityService.createGroupQuery().list();
		for (Group data : datas) {
			System.out.println(data.getId() + "---" + data.getName() + " ");
		}
	}

	@Test
	public void TestListPage() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		// 写入5条用户组数据
		createGroup(identityService, "1", "GroupA", "typeA");
		createGroup(identityService, "2", "GroupB", "typeB");
		createGroup(identityService, "3", "GroupC", "typeC");
		createGroup(identityService, "4", "GroupD", "typeD");
		createGroup(identityService, "5", "GroupE", "typeE");
		// 调用listPage方法，从索引为2的记录开始，查询3条记录
		List<Group> datas = identityService.createGroupQuery().listPage(2, 3);
		for (Group data : datas) {
			System.out.println(data.getId() + "---" + data.getName() + " ");
		}
	}

	@Test
	public void TestCount() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		// 写入5条用户组数据
		createGroup(identityService, UUID.randomUUID().toString(), "GroupA",
				"typeA");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupB",
				"typeB");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupC",
				"typeC");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupD",
				"typeD");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupE",
				"typeE");
		// 使用list方法查询全部的部署数据
		long size = identityService.createGroupQuery().count();
		System.out.println("Group 数量：" + size);
	}

	@Test
	public void TestSort() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		// 写入5条用户组数据
		createGroup(identityService, UUID.randomUUID().toString(), "1", "typeA");
		createGroup(identityService, UUID.randomUUID().toString(), "2", "typeB");
		createGroup(identityService, UUID.randomUUID().toString(), "3", "typeC");
		createGroup(identityService, UUID.randomUUID().toString(), "4", "typeD");
		createGroup(identityService, UUID.randomUUID().toString(), "5", "typeE");
		// 调用orderByGroupId和asc方法，结果为按照ID升序排序
		LOGGER.info("asc排序结果：");
		List<Group> datas = identityService.createGroupQuery()
				.orderByGroupName().asc().list();
		for (Group data : datas) {
			System.out.println("    " + data.getId() + "---" + data.getName());
		}

		LOGGER.info("desc排序结果");
		// 调用orderByGroupName和desc方法，结果为名称降序排序
		datas = identityService.createGroupQuery().orderByGroupName().desc()
				.list();
		for (Group data : datas) {
			System.out.println("    " + data.getId() + "---" + data.getName());
		}

	}

	@Test
	public void TestSortMix() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		// 写入5条用户组数据
		createGroup(identityService, "1", "GroupE", "typeB");
		createGroup(identityService, "2", "GroupD", "typeC");
		createGroup(identityService, "3", "GroupC", "typeD");
		createGroup(identityService, "4", "GroupB", "typeE");
		createGroup(identityService, "5", "GroupA", "typeA");
		// 优先按照id降序、名称升序排序
		System.out.println("ID降序排序：");
		List<Group> datas = identityService.createGroupQuery().orderByGroupId()
				.desc().orderByGroupName().asc().list();
		for (Group data : datas) {
			System.out.println("    " + data.getId() + "---" + data.getName()
					+ " ");
		}
		System.out.println("名称降序排序：");
		// 下面结果将按名称排序
		datas = identityService.createGroupQuery().orderByGroupId()
				.orderByGroupName().desc().list();
		for (Group data : datas) {
			System.out.println("    " + data.getId() + "---" + data.getName()
					+ " ");
		}
	}

	/**
	 * 当排序字段是字符串型数字时直接使用会造成错误
	 */
	@Test
	public void TestSortProblem() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		// 写入5条用户组数据
		createGroup(identityService, "1", "GroupA", "typeA");
		createGroup(identityService, "12", "GroupB", "typeB");
		createGroup(identityService, "13", "GroupC", "typeC");
		createGroup(identityService, "2", "GroupD", "typeD");
		createGroup(identityService, "3", "GroupE", "typeE");
		// 根据ID升序排序
		System.out.println("asc排序结果");
		List<Group> datas = identityService.createGroupQuery().orderByGroupId()
				.asc().list();
		for (Group data : datas) {
			System.out.print(data.getId() + " ");
		}

	}

	@Test
	public void TestSingleResult() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		// 写入5条用户组数据
		createGroup(identityService, UUID.randomUUID().toString(), "GroupA",
				"typeA");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupB",
				"typeB");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupC",
				"typeC");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupD",
				"typeD");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupE",
				"typeE");

		// 再写入一条名称为GroupA的数据
		createGroup(identityService, UUID.randomUUID().toString(), "GroupA",
				"typeF");

		// 查询名称为GroupB的记录
		Group groupB = identityService.createGroupQuery().groupName("GroupB")
				.singleResult();

		System.out.println("查询到一条GroupB数据：" + groupB.getId() + "---"
				+ groupB.getName());

		// 查询名称为GroupF的记录
		Group groupF = identityService.createGroupQuery().groupName("GroupF")
				.singleResult();
		System.out.println("没有groupF的数据：" + groupF);

		// 查询名称为GroupA的记录，这里将抛出异常
		Group groupA = identityService.createGroupQuery().groupName("GroupA")
				.singleResult();
		System.out.println("没有groupA的数据：" + groupA);

	}

	@Test
	public void TestGroupQuery() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		// 写入5条用户组数据
		String aId = UUID.randomUUID().toString();
		createGroup(identityService, aId, "GroupA", "typeA");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupB",
				"typeB");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupC",
				"typeC");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupD",
				"typeD");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupE",
				"typeE");

		// groupId方法
		Group groupA = identityService.createGroupQuery().groupId(aId)
				.singleResult();
		System.out.println("groupId method: " + groupA.getId());

		// groupName方法
		Group groupB = identityService.createGroupQuery().groupName("GroupB")
				.singleResult();
		System.out.println("groupName method: " + groupB.getName());

		// groupType方法
		Group groupC = identityService.createGroupQuery().groupType("typeC")
				.singleResult();
		System.out.println("groupType method: " + groupC.getName());

		// groupNameLike方法
		List<Group> groups = identityService.createGroupQuery()
				.groupNameLike("%Group%").list();
		System.out.println("groupNameLike method: " + groups.size());
	}

	@Test
	public void TestNativeQuery() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		// 写入5条用户组数据
		createGroup(identityService, UUID.randomUUID().toString(), "GroupA",
				"typeA");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupB",
				"typeB");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupC",
				"typeC");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupD",
				"typeD");
		createGroup(identityService, UUID.randomUUID().toString(), "GroupE",
				"typeE");
		
		// 使用原生SQL查询全部数据
		List<Group> groups = identityService.createNativeGroupQuery()
				.sql("select * from ACT_ID_GROUP").list();
		System.out.println("查询全部数据：" + groups.size());
		
		// 使用原生SQL按条件查询，并设入参数，只查到一条数据
		groups = identityService.createNativeGroupQuery()
				.sql("select * from ACT_ID_GROUP where NAME_ = 'GroupC'")
				.list();
		System.out.println("按条件查询：" + groups.get(0).getName());
		
		// 使用parameter方法设置查询参数
		groups = identityService.createNativeGroupQuery()
				.sql("select * from ACT_ID_GROUP where NAME_ = #{name}")
				.parameter("name", "GroupD").list();
		
		System.out.println("使用parameter方法按条件查询：" + groups.get(0).getName());
	}

}
