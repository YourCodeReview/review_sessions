package com.rufus.bumblebee.repository;

import com.rufus.bumblebee.configuration.DataSourceConfig;
import com.rufus.bumblebee.controllers.requests.ReportType;
import com.rufus.bumblebee.repository.tables.Container;
import com.rufus.bumblebee.services.dto.ContainerStatus;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BaseRepository.ConfigurationRepository.class, DataSourceConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Ignore
public class BaseRepository {

    protected Container getTestContainer() {
        Container container = new Container();
        container.setName(UUID.randomUUID().toString());
        container.setHistoryOn(false);
        container.setDate(LocalDateTime.now());
        container.setStatus(ContainerStatus.NEW);
        container.setType(ReportType.EXCEL_TYPE);
        container.setCuid(UUID.randomUUID());
        return container;
    }

    @Configuration
    public static class ConfigurationRepository {

        @Bean
        public DataSource dataSource() {
            PostgreSQLContainer sqlContainer = new PostgreSQLContainer("postgres:11.1");
            sqlContainer.start();
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(sqlContainer.getJdbcUrl());
            ds.setUsername(sqlContainer.getUsername());
            ds.setPassword(sqlContainer.getPassword());
            return ds;
        }

    }
}
