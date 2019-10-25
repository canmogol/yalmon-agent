package com.yalmon.agent.app.impl.commandrunner;

/**
 * Command runner exception.
 */
public class CommandRunnerException extends Exception {

    private static final long serialVersionUID = -7654964107216544783L;

    /**
     * Constructor with message parameter.
     *
     * @param message Command runner creation exception.
     */
    public CommandRunnerException(final String message) {
        super(message);
    }

}
