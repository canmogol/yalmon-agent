package com.yalmon.agent.app.impl.shell;

import com.yalmon.agent.app.ext.commandrunner.CommandRunner;
import com.yalmon.agent.app.impl.commandrunner.CommandRunnerFactory;

/**
 * Bash Command Runner Factory.
 */
public class BashCommandRunnerFactory implements CommandRunnerFactory {

    /**
     * Creates a bash command runner.
     *
     * @return BashCommandRunner.
     */
    @Override
    public final CommandRunner createRunner() {
        return new BashCommandRunner();
    }

}
