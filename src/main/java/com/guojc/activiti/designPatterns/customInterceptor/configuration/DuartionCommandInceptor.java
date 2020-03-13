package com.guojc.activiti.designPatterns.customInterceptor.configuration;

import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuartionCommandInceptor extends AbstractCommandInterceptor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DuartionCommandInceptor.class);

	public <T> T execute(CommandConfig config, Command<T> command) {
		Long startTime = System.currentTimeMillis();
		try {
			return this.getNext().execute(config, command);
		} finally {
			Long endTime = System.currentTimeMillis();
			LOGGER.info("{}执行时长：{}", command.getClass().getSimpleName(),
					endTime - startTime);
		}
	}
}
