package com.yalmon.agent.app.impl.distro;

/**
 * Distribution find exception, thrown when the underlying implementation cannot
 * find the Linux distribution.
 */
public class DistributionFindException extends Exception {

    private static final long serialVersionUID = 2998624480632020685L;

    /**
     * Constructor with message parameter.
     *
     * @param message distribution find exception.
     */
    public DistributionFindException(final String message) {
        super(message);
    }

}
