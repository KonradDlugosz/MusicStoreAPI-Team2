package com.sparta.musicstoreapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class SpringJdbcConfig
{
    public DataSource dataSource() throws IOException {
        Properties properties;
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        properties = loadProperties();
        setProperties(properties, dataSource);

        return dataSource;
    }

    private Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        File file = new File("src/main/resources/application.properties");
        properties.load(new FileReader(file));
        return properties;
    }

    private void setProperties(Properties properties, DriverManagerDataSource dataSource)
    {
        String url = properties.getProperty("spring.datasource.url");
        String user = properties.getProperty("spring.datasource.username");
        String password = properties.getProperty("spring.datasource.password");
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }

}
