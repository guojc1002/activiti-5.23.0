package com.guojc.activiti.userAndGroup;

import java.util.UUID;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupManagement {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GroupManagement.class);

	@Test
	public void TestAddAndUpdateGroup() {
		// 创建默认的流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		// 生成UUID
		String genId = UUID.randomUUID().toString();

		// 调用newGroup方法创建Group实例
		Group group = identityService.newGroup(genId);
		group.setName("经理组");
		group.setType("manager");
		// 保存Group到数据库
		identityService.saveGroup(group);
		// 查询用户组
		Group data = identityService.createGroupQuery().groupId(genId)
				.singleResult();
		// 保存后查询Group
		LOGGER.info("Group ID = {}, Name = {}", data.getId(), data.getName());

		// // 设置名称
		data.setName("经理2组");
		identityService.saveGroup(data);
		// 再次查询
		data = identityService.createGroupQuery().groupId(genId).singleResult();
		LOGGER.info("Group ID = {}, Name = {}", data.getId(), data.getName());
	}

	@Test
	public void TestDeleteGroup() {
		// 创建默认的流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		String genId = UUID.randomUUID().toString();
		
		// 调用newGroup方法创建Group实例
		Group group = identityService.newGroup(genId);
		group.setName("经理组");
		group.setType("manager");
		// 保存Group到数据库
		identityService.saveGroup(group);
		// 查询用户组
			LOGGER.info("保存后用户组数量：  = {}", identityService.createGroupQuery().count());
		
		// 根据ID删除用户组
		identityService.deleteGroup(genId);
		
		LOGGER.info("删除后用户组数量： = {}", identityService.createGroupQuery().count());
	}

}
