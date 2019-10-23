package com.yalmon.agent.app.ext;

/**
 * Command runner creation exception.
 */
public class CommandRunnerCreationException extends Exception {

    private static final long serialVersionUID = 2998624480632020685L;

    /**
     * Constructor with message parameter.
     *
     * @param message Command runner creation exception.
     */
    public CommandRunnerCreationException(final String message) {
        super(message);
    }

}
