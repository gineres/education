package com.labcomu.org.configuration;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
@ComponentScan("com.labcomu.faultinjection")
public class OrgConfiguration {

    //@SneakyThrows
    @Profile("default")
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() {
        try {
            return Server.createTcpServer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}