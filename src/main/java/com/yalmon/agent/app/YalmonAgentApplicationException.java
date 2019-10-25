package com.yalmon.agent.app;

/**
 * Yalmon agent application exception.
 */
public class YalmonAgentApplicationException extends Exception {

    private static final long serialVersionUID = -533480635697509710L;

    /**
     * Constructor with message parameter.
     *
     * @param message distribution find exception.
     */
    public YalmonAgentApplicationException(final String message) {
        super(message);
    }

}
