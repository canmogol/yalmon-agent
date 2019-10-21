package com.yalmon.agent.app.impl.shell;

import com.yalmon.agent.app.ext.CommandRunner;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Abstract shell command runner test.
 */
public abstract class ShellCommandRunnerTest {

    @Test
    void testShouldReturnNullWhenExecuteCalledWithNullParameter() throws IOException {
        final String actual = getCommandRunner().execute(null);
        assertNull(actual, "execution of null as a command should return null");
    }

    @Test
    void testShouldReturnEmptyWhenExecuteCalledWithEmptyParameter() throws IOException {
        final String expected = "";
        final String actual = getCommandRunner().execute("");
        assertEquals(expected, actual, "execution of empty as a command should return empty");
    }

    @Test
    void testShouldReturnOutputWhenExecuteCalledWithLsDevNullCommand() throws IOException {
        val expected = String.format("/dev/null%s", System.lineSeparator());
        val lsDevNullCommand = "ls /dev/null";
        val actual = getCommandRunner().execute(lsDevNullCommand);
        val errorMessage = String.format("execution of %s as a command should return %s", lsDevNullCommand, expected);
        assertEquals(expected, actual, errorMessage);
    }

    abstract CommandRunner getCommandRunner();

}
