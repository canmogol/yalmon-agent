package com.yalmon.agent.app.ext.parser;

/**
 * Parses a command's output.
 */
public interface OutputParser {

    /**
     * Parses the output and returns parsed content.
     *
     * @param output command's output.
     * @return parsed content.
     * @throws Exception on parse error.
     */
    String parse(String output) throws Exception;

}
