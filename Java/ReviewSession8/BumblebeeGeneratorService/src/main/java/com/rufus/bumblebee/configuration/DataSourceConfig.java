package com.rufus.bumblebee.configuration;

import com.rufus.bumblebee.repository.ContainerRepository;
import com.rufus.bumblebee.repository.CustomContainerRepository;
import com.rufus.bumblebee.repository.TestDataRepository;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = {
        ContainerRepository.class,
        CustomContainerRepository.class,
        TestDataRepository.class
})
public class DataSourceConfig {

    @Autowired
    private Environment env;

    private static final String HIBERNATE_DDL = "hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String HIBERNATE_BATCH_SIZE = "hibernate.jdbc.batch_size";
    private static final String HIBERNATE_ORDER_INSERTS = "hibernate.order_inserts";
    private static final String HIBERNATE_STATISTICS = "hibernate.generate_statistics";
    private static final String HIBERNATE_METADATA = "hibernate.temp.use_jdbc_metadata_defaults";

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.rufus.bumblebee.repository");
        em.setPersistenceUnitName("dataGenerator");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Autowired LocalContainerEntityManagerFactoryBean factoryBean) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factoryBean.getObject());
        return transactionManager;
    }

    @Bean
    public SpringLiquibase liquibase(@Autowired DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/db.changelog.yaml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

    private Properties additionalProperties() {
        final Properties properties = new Properties();
        properties.setProperty(HIBERNATE_DDL, env.getProperty(HIBERNATE_DDL));
        properties.setProperty(HIBERNATE_DIALECT, env.getProperty(HIBERNATE_DIALECT));
        properties.setProperty(HIBERNATE_SHOW_SQL, env.getProperty(HIBERNATE_SHOW_SQL));
        properties.setProperty(HIBERNATE_FORMAT_SQL, env.getProperty(HIBERNATE_FORMAT_SQL));
        properties.setProperty(HIBERNATE_BATCH_SIZE, env.getProperty(HIBERNATE_BATCH_SIZE));
        properties.setProperty(HIBERNATE_ORDER_INSERTS, env.getProperty(HIBERNATE_ORDER_INSERTS));
        properties.setProperty(HIBERNATE_STATISTICS, env.getProperty(HIBERNATE_STATISTICS));
        properties.setProperty(HIBERNATE_METADATA, env.getProperty(HIBERNATE_METADATA));
        return properties;
    }
}
