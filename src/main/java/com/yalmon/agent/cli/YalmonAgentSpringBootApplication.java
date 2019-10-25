package com.yalmon.agent.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Yalmon agent entry class.
 */
@SpringBootApplication(proxyBeanMethods = false)
public class YalmonAgentSpringBootApplication {

    /**
     * Entry point method.
     *
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(YalmonAgentSpringBootApplication.class, args);
    }

}
