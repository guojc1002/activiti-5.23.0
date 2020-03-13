package com.guojc.activiti.designPatterns.command;

public class CommandReceiverImpl implements CommandReceiver {

	@Override
	public void doSomethingA() {
		System.out.println("命令接收人执行命令 A");
	}

	public void doSomethingB() {
		System.out.println("命令接收人执行命令 B");
	}

}
