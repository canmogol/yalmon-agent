package com.yalmon.agent.app.ext.provider;

/**
 * Command list provider exception, thrown when the underlying implementation cannot
 * find/get the commands for the given distribution.
 */
public class CommandListProviderException extends Exception {

    private static final long serialVersionUID = 7004898433081814753L;

    /**
     * Constructor with message parameter.
     *
     * @param message command list exception.
     */
    public CommandListProviderException(final String message) {
        super(message);
    }

}
