package org.camunda.bpm.extension.spring.boot.conf;

import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.components.jobexecutor.SpringJobExecutor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;

//@Configuration
//@AutoConfigureBefore(DataSourceCamundaAutoConfiguration.class)
//@AutoConfigureAfter(DataSourceAutoConfiguration.class)
//@ConditionalOnClass(name = "javax.persistence.EntityManagerFactory")
public class CamundaJpaAutoConfiguration {

//  @Configuration
//  @ConditionalOnClass(name= "javax.persistence.EntityManagerFactory")
//  @EnableConfigurationProperties()
//  public static class JpaConfiguration extends AbstractCamundaAutoConfiguration {
//
//    @Bean
//    @ConditionalOnMissingBean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//      return new JpaTransactionManager(emf);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public SpringProcessEngineConfiguration createProcessEngineConfiguration(
//      DataSource dataSource, EntityManagerFactory entityManagerFactory,
//      PlatformTransactionManager transactionManager) throws IOException {
//      SpringProcessEngineConfiguration config = this.createProcessEngineConfiguration(dataSource,
//        transactionManager);
//      config.setJpaEntityManagerFactory(entityManagerFactory);
//      config.setTransactionManager(transactionManager);
//      config.setJpaHandleTransaction(false);
//      config.setJpaCloseEntityManager(false);
//      return config;
//    }
//  }

}
