package com.yalmon.agent.app.impl.shell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.yalmon.agent.app.ext.commandrunner.CommandRunner;

/**
 * Bash command runner factory test.
 */
class BashCommandRunnerFactoryTest {

    private BashCommandRunnerFactory bashCommandRunnerFactory;

    @BeforeEach
    void setUp() {
        bashCommandRunnerFactory = new BashCommandRunnerFactory();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testShouldReturnBashCommandRunnerWhenCreateRunnerCalled() {
        final CommandRunner actualCommandRunner = bashCommandRunnerFactory.createRunner();
        final Class<BashCommandRunner> expected = BashCommandRunner.class;
        final String error = "BashCommandRunnerFactory should return an instance of BashCommandRunner";
        assertEquals(expected, actualCommandRunner.getClass(), error);
    }

}
