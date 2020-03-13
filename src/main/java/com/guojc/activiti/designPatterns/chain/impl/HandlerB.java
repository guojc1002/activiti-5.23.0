package com.guojc.activiti.designPatterns.chain.impl;

import com.guojc.activiti.designPatterns.chain.Handler;
import com.guojc.activiti.designPatterns.chain.Request;

public class HandlerB extends Handler {

	public void execute(Request request) {
		//处理自己的事，然后交由一下任处理者执行请求
		System.out.println("请求处理者B处理请求");
		next.execute(request);
	}

}
