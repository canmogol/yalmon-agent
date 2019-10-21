package com.yalmon.agent.app.impl;

import com.yalmon.agent.app.ext.CommandRunner;
import com.yalmon.agent.app.ext.CommandRunnerFactory;
import com.yalmon.agent.app.impl.shell.BashCommandRunner;

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
