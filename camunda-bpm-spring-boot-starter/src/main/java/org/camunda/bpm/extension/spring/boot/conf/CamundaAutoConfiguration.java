package org.camunda.bpm.extension.spring.boot.conf;

import java.io.IOException;

import javax.sql.DataSource;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.jobexecutor.CallerRunsRejectedJobsHandler;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.components.jobexecutor.SpringJobExecutor;
import org.camunda.bpm.extension.spring.boot.CamundaProperties;

import org.glassfish.jersey.server.ResourceConfig;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ConditionalOnMissingBean(ProcessEngineFactoryBean.class)
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@AutoConfigureBefore(JerseyAutoConfiguration.class)
@EnableConfigurationProperties(CamundaProperties.class)
public class CamundaAutoConfiguration {

    @Autowired
    protected CamundaProperties camundaProperties;

    @Autowired
    private DataSource dataSource;

    @Bean
    public ResourceConfig resourceConfig() {
        return new CamundaRestResourceConfig();
    }

    @Bean
    @ConditionalOnMissingBean(PlatformTransactionManager.class)
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfig() throws IOException {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
        config.setDataSource(dataSource);
        config.setProcessEngineName(camundaProperties.getProcessEngineName());

        config.setDatabaseSchemaUpdate(Boolean.TRUE.toString());

        config.setJobExecutorDeploymentAware(camundaProperties.isJobExecutorDeploymentAware());
        config.setJobExecutorActivate(true);
        config.setJobExecutor(springJobExecutor(taskExecutor()));
        config.setCmmnEnabled(camundaProperties.isCmmnEnabled());

        config.setHistory(camundaProperties.getHistoryLevel());

        config.setTransactionManager(transactionManager());
        config.setDeploymentResources(camundaProperties.getDeploymentResources());

        return config;
    }

    @Bean
    public SpringJobExecutor springJobExecutor(final TaskExecutor taskExecutor) {
        SpringJobExecutor springJobExecutor = new SpringJobExecutor();
        springJobExecutor.setTaskExecutor(taskExecutor);
        springJobExecutor.setRejectedJobsHandler(new CallerRunsRejectedJobsHandler());
        return springJobExecutor;
    }

    @Bean
    public ProcessEngineFactoryBean processEngine(final SpringProcessEngineConfiguration configuration)
        throws IOException {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(configuration);
        return processEngineFactoryBean;
    }

    @Bean
    public RepositoryService repositoryService(final ProcessEngine processEngine) throws Exception {
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(final ProcessEngine processEngine) throws Exception {
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(final ProcessEngine processEngine) throws Exception {
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(final ProcessEngine processEngine) throws Exception {
        return processEngine.getHistoryService();
    }

    @Bean
    public ManagementService managementService(final ProcessEngine processEngine) throws Exception {
        return processEngine.getManagementService();
    }

    @Bean
    public CaseService caseService(final ProcessEngine processEngine) throws Exception {
        return processEngine.getCaseService();
    }

    @Bean
    public FilterService filterService(final ProcessEngine processEngine) throws Exception {
        return processEngine.getFilterService();
    }

    @Bean
    public FormService formService(final ProcessEngine processEngine) throws Exception {
        return processEngine.getFormService();
    }

    @Bean
    public IdentityService identityService(final ProcessEngine processEngine) throws Exception {
        return processEngine.getIdentityService();
    }

    @Bean
    @ConditionalOnMissingBean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

}
