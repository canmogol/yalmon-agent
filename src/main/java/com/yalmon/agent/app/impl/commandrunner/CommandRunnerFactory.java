package com.yalmon.agent.app.impl.commandrunner;

import java.util.Objects;

import com.yalmon.agent.app.ext.commandrunner.CommandRunner;
import com.yalmon.agent.app.impl.BashCommandRunnerFactory;

/**
 * Command Runner abstract factory.
 */
public interface CommandRunnerFactory {

    /**
     * Creates the default command runner factory.
     *
     * @return Command runner factory.
     * @throws Exception when there is no implementation found for the default
     * shell.
     */
    static CommandRunnerFactory createFactory() throws Exception {
        return CommandRunnerFactory.createFactory(ShellTypes.getDefault());
    }

    /**
     * Creates CommandRunnerFactory for current platform.
     *
     * @param shell requested shell implementation, such as 'bash'
     * @return Command runner factory.
     * @throws CommandRunnerException if the given shell is not known.
     */
    static CommandRunnerFactory createFactory(final String shell) throws CommandRunnerException {
        if (Objects.isNull(shell) || shell.trim().isEmpty()) {
            throw new CommandRunnerException("shell name cannot be null or empty");
        } else if (ShellTypes.BASH.getValue().equals(shell)) {
            return new BashCommandRunnerFactory();
        } else {
            final String error = String.format("Unknown shell %s", shell);
            throw new CommandRunnerException(error);
        }
    }

    /**
     * Creates CommandRunner for current platform.
     *
     * @return Command runner.
     */
    CommandRunner createRunner();

}
