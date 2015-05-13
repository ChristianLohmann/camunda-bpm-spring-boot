package org.camunda.bpm.extension.spring.boot;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.application.impl.metadata.ProcessesXmlImpl;
import org.camunda.bpm.application.impl.metadata.spi.ProcessArchiveXml;
import org.camunda.bpm.container.impl.metadata.BpmPlatformXmlImpl;
import org.camunda.bpm.container.impl.metadata.JobAcquisitionXmlImpl;
import org.camunda.bpm.container.impl.metadata.JobExecutorXmlImpl;
import org.camunda.bpm.container.impl.metadata.ProcessEnginePluginXmlImpl;
import org.camunda.bpm.container.impl.metadata.ProcessEngineXmlImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.history.HistoryLevel;

import org.camunda.connect.plugin.impl.ConnectProcessEnginePlugin;

import org.camunda.spin.plugin.impl.SpinProcessEnginePlugin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import org.springframework.core.io.Resource;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "camunda", ignoreUnknownFields = false)
public class CamundaProperties {

    protected BpmPlatformXmlImpl bpmPlatformXml;
    protected ProcessesXmlImpl processesXml;

    @NestedConfigurationProperty
    protected ProcessArchiveXml processArchiveXml;
    @NestedConfigurationProperty
    protected JobExecutorXmlImpl jobExecutorXml;
    @NestedConfigurationProperty
    protected JobAcquisitionXmlImpl jobAcquisitionXml;
    @NestedConfigurationProperty
    protected ProcessEngineXmlImpl processEngineXml;
    @NestedConfigurationProperty
    protected ProcessEnginePluginXmlImpl processEnginePluginXml;

    private boolean cmmnEnabled;
    private boolean jobExecutorDeploymentAware;
    private boolean jobExecutorActive;
    private String processEngineName = "processEngine";
    private boolean databaseSchemaUpdate = true;
    private Resource[] deploymentResources = new Resource[] {};
    private String historyLevel = HistoryLevel.HISTORY_LEVEL_FULL.getName();

    public void apply(final ProcessEngineConfigurationImpl configuration) {
        configuration.setCmmnEnabled(cmmnEnabled);
        configuration.setHistory(HistoryLevel.HISTORY_LEVEL_FULL.getName());
        configuration.setDatabaseSchema("");
        configuration.setDatabaseTablePrefix("");
        configuration.setJobExecutorDeploymentAware(jobExecutorDeploymentAware);
        configuration.setAutoStoreScriptVariables(false);
        configuration.setDefaultNumberOfRetries(3);
        configuration.setDataSourceJndiName("");
        configuration.setAuthorizationEnabled(false);
        configuration.setCreateIncidentOnFailedJobEnabled(true);
        configuration.setProcessEngineName("default");

        // register Spin and Connect if available on classpath
        List<ProcessEnginePlugin> processEnginePlugins = configuration.getProcessEnginePlugins();
        if (processEnginePlugins == null) {
            processEnginePlugins = new ArrayList<ProcessEnginePlugin>();
        }

        processEnginePlugins.add(new SpinProcessEnginePlugin());
        processEnginePlugins.add(new ConnectProcessEnginePlugin());
    }

}
