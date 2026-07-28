package com.example.demoPostGre.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url:#{null}}")
    private String defaultUrl;

    @Value("${spring.datasource.username:#{null}}")
    private String defaultUsername;

    @Value("${spring.datasource.password:#{null}}")
    private String defaultPassword;

    @Bean
    public DataSource dataSource() {
        // 1. Retrieve the database URL from potential environment variables
        String dbUrl = System.getenv("SPRING_DATASOURCE_URL");
        if (dbUrl == null || dbUrl.isEmpty()) {
            dbUrl = System.getenv("DB_URL");
        }
        if (dbUrl == null || dbUrl.isEmpty()) {
            dbUrl = System.getenv("DATABASE_URL");
        }

        // 2. Parse and sanitize the URL if found in the environment variables
        if (dbUrl != null && !dbUrl.isEmpty()) {
            dbUrl = dbUrl.trim();
            // Remove double or single quotes if wrapped
            if (dbUrl.startsWith("\"") && dbUrl.endsWith("\"")) {
                dbUrl = dbUrl.substring(1, dbUrl.length() - 1);
            }
            if (dbUrl.startsWith("'") && dbUrl.endsWith("'")) {
                dbUrl = dbUrl.substring(1, dbUrl.length() - 1);
            }

            // Convert raw postgres/postgresql scheme to jdbc:postgresql
            if (dbUrl.startsWith("postgres://")) {
                dbUrl = "jdbc:postgresql://" + dbUrl.substring("postgres://".length());
            } else if (dbUrl.startsWith("postgresql://")) {
                dbUrl = "jdbc:postgresql://" + dbUrl.substring("postgresql://".length());
            } else if (!dbUrl.startsWith("jdbc:")) {
                dbUrl = "jdbc:" + dbUrl;
            }
        } else {
            // Fallback to the property defined in application.properties
            dbUrl = defaultUrl;
        }

        // Sanitize the defaultUrl in case it is also wrapped in quotes or missing jdbc prefix
        if (dbUrl != null) {
            dbUrl = dbUrl.trim();
            if (dbUrl.startsWith("\"") && dbUrl.endsWith("\"")) {
                dbUrl = dbUrl.substring(1, dbUrl.length() - 1);
            }
            if (dbUrl.startsWith("'") && dbUrl.endsWith("'")) {
                dbUrl = dbUrl.substring(1, dbUrl.length() - 1);
            }
            if (dbUrl.startsWith("postgres://")) {
                dbUrl = "jdbc:postgresql://" + dbUrl.substring("postgres://".length());
            } else if (dbUrl.startsWith("postgresql://")) {
                dbUrl = "jdbc:postgresql://" + dbUrl.substring("postgresql://".length());
            }
        }

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(dbUrl);

        // 3. Extract username and password from environment variables if present, or fallback to properties
        String username = System.getenv("DB_USERNAME");
        if (username == null || username.isEmpty()) {
            username = defaultUsername;
        }
        
        String password = System.getenv("DB_PASSWORD");
        if (password == null || password.isEmpty()) {
            password = defaultPassword;
        }

        if (username != null && !username.isEmpty()) {
            dataSourceBuilder.username(username);
        }
        if (password != null && !password.isEmpty()) {
            dataSourceBuilder.password(password);
        }

        return dataSourceBuilder.build();
    }
}
