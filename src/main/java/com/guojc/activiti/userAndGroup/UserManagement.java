package com.guojc.activiti.userAndGroup;

import java.util.List;
import java.util.UUID;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.identity.Authentication;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserManagement {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserManagement.class);

	// 创建用户方法
	private void creatUser(IdentityService identityService, String id,
			String first, String last, String email, String passwd) {
		// 使用newUser方法创建User实例
		User user = identityService.newUser(id);
		// 设置用户的各个属性
		user.setFirstName(first);
		user.setLastName(last);
		user.setEmail(email);
		user.setPassword(passwd);
		// 使用saveUser方法保存用户
		identityService.saveUser(user);
	}

	@Test
	public void TestAddUser() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		
		String id = UUID.randomUUID().toString();
		// 使用newUser方法创建User实例
		User user = identityService.newUser(id);
		// 设置用户的各个属性
		user.setFirstName("Angus");
		user.setLastName("Young");
		user.setEmail("yangenxiong@163.com");
		user.setPassword("abc");
		// 使用saveUser方法保存用户
		identityService.saveUser(user);
		// 根据 id 查询
		user = identityService.createUserQuery().userId(id).singleResult();
		LOGGER.info(user.getEmail());
	}

	@Test
	public void TestUpdateUser() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		String id = UUID.randomUUID().toString();
		// 创建用户
		creatUser(identityService, id, "angus", "young", "yangenxiong@163.com",
				"abc");
		// 查询用户
		User user = identityService.createUserQuery().userId(id).singleResult();
		user.setEmail("abc@163.com");
		// 执行保存
		identityService.saveUser(user);
		// 查询用户
		user = identityService.createUserQuery().userId(id).singleResult();
		LOGGER.info(user.getEmail());
	}

	@Test
	public void TestDeleteUser() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		String id = UUID.randomUUID().toString();
		// 创建用户
		creatUser(identityService, id, "angus", "young", "yangenxiong@163.com",
				"abc");
		LOGGER.info("删除前数量："
				+ identityService.createUserQuery().userId(id).count());
		
		// 删除用户
		identityService.deleteUser(id);
		LOGGER.info("删除后数量："
				+ identityService.createUserQuery().userId(id).count());
	}

	@Test
	public void TestCheckPasswd() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		String id = UUID.randomUUID().toString();
		// 创建用户
		creatUser(identityService, id, "angus", "young", "yangenxiong@163.com",
				"abc");
		// 验证用户密码
		System.out
				.println("验证密码结果：" + identityService.checkPassword(id, "abc"));
		LOGGER.info("验证密码结果：" + identityService.checkPassword(id, "c"));
	}

	@Test
	public void TestUserQuery() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		String id1 = UUID.randomUUID().toString();
		String id2 = UUID.randomUUID().toString();
		// 创建两个用户
		creatUser(identityService, id1, "angus", "young",
				"yangenxiong@163.com", "abc");
		creatUser(identityService, id2, "angus2", "young2", "abc@163.com",
				"123");
		// 调用UserQuery的各个查询方法
		// userId
		User user = identityService.createUserQuery().userId(id1)
				.singleResult();
		LOGGER.info("userId: = {}" ,user.getFirstName());
		// userFirstName
		user = identityService.createUserQuery().userFirstName("angus")
				.singleResult();
		LOGGER.info("userFirstName: = {}" , user.getFirstName());
		// userFirstNameLike
		List<User> datas = identityService.createUserQuery()
				.userFirstNameLike("angus%").list();
		LOGGER.info("createUserQuery: = {}",  datas.size());
		// userLastName
		user = identityService.createUserQuery().userLastName("young")
				.singleResult();
		LOGGER.info("userLastName: = {}", user.getFirstName());
		// userLastNameLike
		datas = identityService.createUserQuery().userLastNameLike("young%")
				.list();
		LOGGER.info("userLastNameLike: = {}" + datas.size());
		
		// userEmail
		user = identityService.createUserQuery().userEmail("abc@163.com")
				.singleResult();
		LOGGER.info("userEmail: = {}" + user.getFirstName());
		// userEmailLike
		datas = identityService.createUserQuery().userEmailLike("%163.com")
				.list();
		LOGGER.info("userEmailLike: = {}" + datas.size());
		// 使用NativeQuery
		datas = identityService.createNativeUserQuery()
				.sql("select * from ACT_ID_USER where EMAIL_ = #{email}")
				.parameter("email", "yangenxiong@163.com").list();
		LOGGER.info("native query: = {}" + datas.get(0).getEmail());
	}

	@Test
	public void TestAuthenticatedUserId() throws InterruptedException {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		final IdentityService identityService = engine.getIdentityService();
		// 设置当前线程的userId为3
		identityService.setAuthenticatedUserId("3");
		LOGGER.info("当前线程usreId："
				+ Authentication.getAuthenticatedUserId());
		// 启动两条线程
		new Thread() {
			public void run() {
				try {
					identityService.setAuthenticatedUserId("1");
					Thread.sleep(5000);
					LOGGER.info("线程1的userId："
							+ Authentication.getAuthenticatedUserId());
				} catch (Exception e) {

				}
			}
		}.start();

		new Thread() {
			public void run() {
				identityService.setAuthenticatedUserId("2");
				LOGGER.info("线程2的usrId："
						+ Authentication.getAuthenticatedUserId());
			}
		}.start();
		
		Thread.sleep(10000);
	}

	
}
