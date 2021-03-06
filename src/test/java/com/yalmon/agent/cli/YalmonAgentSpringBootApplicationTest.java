package com.yalmon.agent.cli;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * Spring boot application entry point test.
 */
class YalmonAgentSpringBootApplicationTest {

    private static final String[] EMPTY_ARGS = {};

    /**
     * Spring boot entry method.
     */
    @Test
    void testShouldExecuteTheMainMethod() {
        assertDoesNotThrow(() -> YalmonAgentSpringBootApplication.main(EMPTY_ARGS));
    }
}
