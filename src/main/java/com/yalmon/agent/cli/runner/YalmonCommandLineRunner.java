package com.yalmon.agent.cli.runner;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Yalmon command line entry class.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class YalmonCommandLineRunner implements CommandLineRunner {

    /**
     * Yalmon command line entry method.
     *
     * @param args command line arguments.
     * @throws Exception for all sorts of IO operations.
     */
    @Override
    public final void run(final String... args) throws Exception {
        if (Objects.isNull(args)) {
            throw new NullPointerException("args cannot be null");
        }
        // run application
    }

}
