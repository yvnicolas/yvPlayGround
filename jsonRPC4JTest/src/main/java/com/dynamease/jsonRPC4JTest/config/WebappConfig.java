/*
 * Copyright 2013 the original author or authors.
 *
 */
package com.dynamease.jsonRPC4JTest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.dynamease.jsonrpc.services.DynJsonUserService;
import com.dynamease.jsonrpc.services.DynJsonUserServiceImpl;
import com.googlecode.jsonrpc4j.spring.JsonServiceExporter;

/**
 * Main configuration class for the application.
 * Turns on @Component scanning, loads externalized application.properties
 * @author Yves Nicolas
 */
@Configuration
@ComponentScan(basePackages = "com.dynamease")
@PropertySource("classpath:application.properties")
public class WebappConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(WebappConfig.class);

    @Autowired
    private Environment environment;
    
    @Bean
    public String projectName() {
        return(environment.getProperty("application.name"));
    }
    
    
    @Bean
    public DynJsonUserService dynJsonUserService() {
        logger.info("Creating Json RPC User Service");
        return new DynJsonUserServiceImpl();
    }
    
    @Bean (name = "/jsonRq")
    public JsonServiceExporter jsonServiceExporter() {
        JsonServiceExporter toReturn = new JsonServiceExporter();
        toReturn.setServiceInterface(DynJsonUserService.class);
        toReturn.setService(dynJsonUserService());
        logger.info("Creating Json Exporter");
        return toReturn;
    }
}
