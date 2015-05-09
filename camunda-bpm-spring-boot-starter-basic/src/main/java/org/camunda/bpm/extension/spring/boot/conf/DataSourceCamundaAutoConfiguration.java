package org.camunda.bpm.extension.spring.boot.conf;

import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.components.jobexecutor.SpringJobExecutor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class DataSourceCamundaAutoConfiguration {

  @Configuration
  @ConditionalOnMissingClass(name = "javax.persistence.EntityManagerFactory")
  public static class DataSourceProcessEngineConfiguration extends AbstractCamundaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
      return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
      DataSource dataSource,
      PlatformTransactionManager transactionManager, SpringJobExecutor springJobExecutor) throws IOException {
      return this.createProcessEngineConfiguration(dataSource, transactionManager, springJobExecutor);
    }

  }

}
