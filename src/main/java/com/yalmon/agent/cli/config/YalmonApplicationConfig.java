package com.yalmon.agent.cli.config;

import com.yalmon.agent.app.ext.CommandRunner;
import com.yalmon.agent.app.ext.CommandRunnerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Yalmon application general configuration.
 */
@Configuration
public class YalmonApplicationConfig {

    /**
     * Command runner.
     *
     * @return command runner.
     * @throws Exception when command runner could not be created.
     */
    @Bean
    public CommandRunner getBashCommandRunner() throws Exception {
        return CommandRunnerFactory.createFactory().createRunner();
    }

}
