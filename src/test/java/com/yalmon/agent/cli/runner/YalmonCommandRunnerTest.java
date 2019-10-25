package com.yalmon.agent.cli.runner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.val;

/**
 * Yalmon agent command runner test.
 */
class YalmonCommandLineRunnerTest {

    private YalmonCommandLineRunner YalmonCommandLineRunner;
    private String[] EMPTY_STRING_ARRAY = new String[] {};

    @BeforeEach
    void setUp() {
        YalmonCommandLineRunner = new YalmonCommandLineRunner();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testShouldThrowExceptionWhenArgsIsNull() {
        String[] args = null;
        val exception = assertThrows(NullPointerException.class, () -> YalmonCommandLineRunner.run(args));
        assertEquals("args cannot be null", exception.getMessage());
    }

    @Test
    void testShouldNotThrowExceptionWhenArgsIsEmpty() {
        assertDoesNotThrow(() -> YalmonCommandLineRunner.run(EMPTY_STRING_ARRAY));
    }

}
