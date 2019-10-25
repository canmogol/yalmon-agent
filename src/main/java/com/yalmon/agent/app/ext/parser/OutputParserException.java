package com.yalmon.agent.app.ext.parser;

/**
 * Output parser exception, thrown when the underlying implementation cannot
 * parse the output.
 */
public class OutputParserException extends Exception {

    private static final long serialVersionUID = 8619106776403392546L;

    /**
     * Constructor with message parameter.
     *
     * @param message parse exception.
     */
    public OutputParserException(final String message) {
        super(message);
    }

}
