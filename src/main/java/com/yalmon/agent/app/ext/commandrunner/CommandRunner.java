package com.yalmon.agent.app.ext.commandrunner;

import java.io.IOException;

/**
 * Command Runner Interface.
 */
public interface CommandRunner {

    /**
     * Executes the given command and returns the output.
     *
     * @param command executable command.
     * @return output of the command.
     * @throws IOException when command execution fails.
     */
    String execute(String command) throws IOException;

}
