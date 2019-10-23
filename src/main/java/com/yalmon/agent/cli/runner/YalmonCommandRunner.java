package com.yalmon.agent.cli.runner;

import com.yalmon.agent.app.ext.CommandRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Yalmon command line entry class.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class YalmonCommandRunner implements CommandLineRunner {

    private final CommandRunner runner;

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
        // read commands
    }

}
