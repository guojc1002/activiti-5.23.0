package com.guojc.activiti.designPatterns.customInterceptor.configuration;

import org.activiti.engine.debug.ExecutionTreeUtil;
import org.activiti.engine.impl.agenda.AbstractOperation;
import org.activiti.engine.impl.interceptor.DebugCommandInvoker;
import org.activiti.engine.logging.LogMDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志拦截器
 */
public class MDCCommandInvoker extends DebugCommandInvoker {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DuartionCommandInceptor.class);

	@Override
	public void executeOperation(Runnable runnable) {
		boolean mdcEnabled = LogMDC.isMDCEnabled();// 取原来的值是否是生效的
		LogMDC.setMDCEnabled(true);
		if (runnable instanceof AbstractOperation) {
			AbstractOperation operation = (AbstractOperation) runnable;
			if (operation.getExecution() != null) {

				LOGGER.info("Execution tree while executing operation {} :",
						operation.getClass());
				LOGGER.info(
						"{}",
						System.lineSeparator()
								+ ExecutionTreeUtil
										.buildExecutionTree(operation
												.getExecution()));

				LogMDC.putMDCExecution(operation.getExecution());// 记录数据
			}
		}

		super.executeOperation(runnable);
		LogMDC.clear();// 【清理MDC信息】为保证环境的清洁
		if (!mdcEnabled) {// 如果原来的值是不生效的
			// 把他的值重新还原一下
			LogMDC.setMDCEnabled(false);

		}
	}

}
