package com.guojc.activiti.userAndGroup;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserinfoManagement {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserinfoManagement.class);

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
	public void TestAddDeleteUserInfo() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		String id = UUID.randomUUID().toString();
		// 创建用户
		creatUser(identityService, id, "angus", "young", "yangenxiong@163.com",
				"abc");
		// 创建一个用户信息
		identityService.setUserInfo(id, "age", "30");
		// 创建第二个用户信息
		identityService.setUserInfo(id, "weight", "60KG");
		// 删除用户年龄信息
		identityService.deleteUserInfo(id, "age");
	}

	@Test
	public void TestGetUserInfo() {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		String id = UUID.randomUUID().toString();
		// 创建用户
		creatUser(identityService, id, "angus", "young", "yangenxiong@163.com",
				"abc");
		// 创建一个用户信息
		identityService.setUserInfo(id, "age", "30");
		// 创建第二个用户信息
		identityService.setUserInfo(id, "weight", "60KG");
		// 查询用户信息
		String value = identityService.getUserInfo(id, "age");
		LOGGER.info("用户年龄为：= {}", value);
	}

	@Test
	public void TestUserPicture() throws IOException {
		// 创建流程引擎
		ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
		// 得到身份服务组件实例
		IdentityService identityService = engine.getIdentityService();
		String id = UUID.randomUUID().toString();
		// 创建用户
		creatUser(identityService, id, "angus", "young", "yangenxiong@163.com",
				"abc");
		// 读取图片并转换为byte数组
		InputStream resourceAsStream = UserinfoManagement.class
				.getClassLoader().getResourceAsStream(
						"userAndGroup/artifact/picture.png");
		BufferedImage img = ImageIO.read(resourceAsStream);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageIO.write(img, "png", output);
		// 获取图片的byte数组
		byte[] picArray = output.toByteArray();
		// 创建Picture实例
		Picture picture = new Picture(picArray, "angus image");
		// 为用户设置图片
		identityService.setUserPicture(id, picture);
	}

}
