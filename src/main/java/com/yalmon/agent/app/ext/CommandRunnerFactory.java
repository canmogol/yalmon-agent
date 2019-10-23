package com.yalmon.agent.app.ext;

import com.yalmon.agent.app.impl.BashCommandRunnerFactory;

import java.util.Objects;

/**
 * Command Runner abstract factory.
 */
public interface CommandRunnerFactory {

    /**
     * Bourne again shell.
     */
    String BASH = "bash";

    /**
     * Creates the default command runner factory.
     *
     * @return Command runner factory.
     * @throws Exception when there is no implementation found for the default shell.
     */
    static CommandRunnerFactory createFactory() throws Exception {
        return CommandRunnerFactory.createFactory(BASH);
    }

    /**
     * Creates CommandRunnerFactory for current platform.
     *
     * @param shell requested shell implementation, such as 'bash'
     * @return Command runner factory.
     * @throws CommandRunnerCreationException if the given shell is not known.
     */
    static CommandRunnerFactory createFactory(final String shell) throws CommandRunnerCreationException {
        if (Objects.isNull(shell) || shell.trim().isEmpty()) {
            throw new CommandRunnerCreationException("shell name cannot be null or empty");
        } else if (BASH.equals(shell)) {
            return new BashCommandRunnerFactory();
        } else {
            final String error = String.format("Unknown shell %s", shell);
            throw new CommandRunnerCreationException(error);
        }
    }

    /**
     * Creates CommandRunner for current platform.
     *
     * @return Command runner.
     */
    CommandRunner createRunner();

}
