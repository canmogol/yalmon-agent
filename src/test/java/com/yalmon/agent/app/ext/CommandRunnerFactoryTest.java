package com.yalmon.agent.app.ext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import com.yalmon.agent.app.impl.BashCommandRunnerFactory;
import com.yalmon.agent.app.impl.commandrunner.CommandRunnerFactory;
import com.yalmon.agent.app.impl.commandrunner.ShellTypes;
import lombok.val;

/**
 * Command runner factory default/static methods tests.
 */
class CommandRunnerFactoryTest {

    private static final String EMPTY_STRING = "";
    private static final String UNKNOWN_SHELL_NAME = "UNKNOWN_SHELL_NAME";

    @Test
    void testShouldNotThrowExceptionWhenCreateFactoryCalledWithoutAnyParameters() {
        assertDoesNotThrow((ThrowingSupplier<CommandRunnerFactory>)CommandRunnerFactory::createFactory);
    }

    @Test
    void testShouldReturnDefaultBashCommandRunnerFactoryWhenCreateFactoryCalledWithoutAnyParameters() throws Exception {
        val actualFactory = CommandRunnerFactory.createFactory();
        String error = "default factory should be BashCommandRunnerFactory";
        assertEquals(BashCommandRunnerFactory.class, actualFactory.getClass(), error);
    }

    @Test
    void testShouldThrowExceptionWhenNullParameterPassedAsShellName() {
        val exception = assertThrows(Exception.class, () -> CommandRunnerFactory.createFactory(null));
        assertEquals("shell name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenEmptyStringPassedAsShellName() {
        val exception = assertThrows(Exception.class, () -> CommandRunnerFactory.createFactory(EMPTY_STRING));
        assertEquals("shell name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testShouldReturnBashCommandRunnerFactoryWhenCreateFactoryCalledWithParameterBash() throws Exception {
        final CommandRunnerFactory actualFactory = CommandRunnerFactory.createFactory(ShellTypes.BASH.getValue());
        String error = "bash command factory should be returned when called with parameter 'bash'";
        assertEquals(BashCommandRunnerFactory.class, actualFactory.getClass(), error);
    }

    @Test
    void testShouldThrowExceptionWhenUnknownShellNamePassed() {
        val exception = assertThrows(Exception.class, () -> CommandRunnerFactory.createFactory(UNKNOWN_SHELL_NAME));
        final String error = String.format("Unknown shell %s", UNKNOWN_SHELL_NAME);
        assertEquals(error, exception.getMessage());
    }

}
