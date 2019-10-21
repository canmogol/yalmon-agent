package com.yalmon.agent.app.impl.shell;

import com.yalmon.agent.app.ext.CommandRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Sh Command Runner Test.
 */
class ShCommandRunnerTest extends ShellCommandRunnerTest {

    private CommandRunner commandRunner;

    @BeforeEach
    void setUp() {
        commandRunner = new ShCommandRunner();
    }

    @AfterEach
    void tearDown() {
    }

    @Override
    CommandRunner getCommandRunner() {
        return commandRunner;
    }

}
