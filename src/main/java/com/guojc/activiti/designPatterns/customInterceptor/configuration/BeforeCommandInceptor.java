package com.guojc.activiti.designPatterns.customInterceptor.configuration;

import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeforeCommandInceptor extends AbstractCommandInterceptor {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BeforeCommandInceptor.class);

	public <T> T execute(CommandConfig config, Command<T> command) {
		try {
			return this.getNext().execute(config, command);
		} finally {
			LOGGER.info("before...");
		}
	}
}
