package com.yalmon.agent.app.impl.shell;

import com.yalmon.agent.app.ext.CommandRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Bash Command Runner Test.
 */
class BashCommandRunnerTest extends ShellCommandRunnerTest {

    private CommandRunner commandRunner;

    @BeforeEach
    void setUp() {
        commandRunner = new BashCommandRunner();
    }

    @AfterEach
    void tearDown() {
    }

    @Override
    CommandRunner getCommandRunner() {
        return commandRunner;
    }

}
