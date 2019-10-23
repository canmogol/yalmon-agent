package com.yalmon.agent.cli.runner;

import com.yalmon.agent.app.impl.shell.BashCommandRunner;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Yalmon agent command runner test.
 */
class YalmonCommandRunnerTest {

    private YalmonCommandRunner yalmonCommandRunner;
    private String[] EMPTY_STRING_ARRAY = new String[] {};

    @BeforeEach
    void setUp() {
        yalmonCommandRunner = new YalmonCommandRunner(new BashCommandRunner());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testShouldThrowExceptionWhenArgsIsNull() {
        String[] args = null;
        val exception = assertThrows(NullPointerException.class, () -> yalmonCommandRunner.run(args));
        assertEquals("args cannot be null", exception.getMessage());
    }

    @Test
    void testShouldNotThrowExceptionWhenArgsIsEmpty() {
        assertDoesNotThrow(() -> yalmonCommandRunner.run(EMPTY_STRING_ARRAY));
    }

    @Test
    void testShouldRunListCommand() throws Exception {
        yalmonCommandRunner.run("ls");
    }

}