package com.guojc.activiti.coreApi;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepostoryServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(RepostoryServiceTest.class);

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	
	@Test
	public void testReposetory(){
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
