package com.yalmon.agent.app.impl.distro;

import com.yalmon.agent.app.ext.parser.OutputParserException;

/**
 * Distribution find exception, thrown when the underlying implementation cannot
 * find the Linux distribution.
 */
public class DistributionFindException extends OutputParserException {

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
