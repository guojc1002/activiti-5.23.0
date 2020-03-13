package com.guojc.activiti.designPatterns.command.impl;

import com.guojc.activiti.designPatterns.command.Command;
import com.guojc.activiti.designPatterns.command.CommandReceiver;

/**
 * 命令实现B
 * @author yangenxiong
 *
 */
public class CommandB implements Command {

	@Override
	public void execute(CommandReceiver receiver) {
		receiver.doSomethingB();
	}

}
