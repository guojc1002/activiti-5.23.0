package com.guojc.activiti.designPatterns.command.impl;

import com.guojc.activiti.designPatterns.command.Command;
import com.guojc.activiti.designPatterns.command.CommandReceiver;

/**
 * 命令实现A
 * @author yangenxiong
 *
 */
public class CommandA implements Command {
	public void execute(CommandReceiver receiver) {
		receiver.doSomethingA();
	}
}
