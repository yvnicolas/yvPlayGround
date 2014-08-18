/*
 * Copyright 2013 the original author or authors.
 *
 */
package ${package}.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Main configuration class for the application.
 * Turns on @Component scanning, loads externalized application.properties
 * @author Yves Nicolas
 */
@Configuration
@ComponentScan(basePackages = "com.dynamease")
@PropertySource("classpath:application.properties")
public class WebappConfig {

    @Autowired
    private Environment environment;
    
    @Bean
    public String projectName() {
        return(environment.getProperty("application.name"));
    }
    
}
