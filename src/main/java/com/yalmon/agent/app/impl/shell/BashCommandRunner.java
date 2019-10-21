package com.yalmon.agent.app.impl.shell;

/**
 * Bash command runner.
 */
public class BashCommandRunner extends ShellCommandRunner {

    /**
     * Current shell is Bourne Again Shell.
     */
    private static final String BASH_SHELL = "bash";

    /**
     * Constructor which passes the shell name 'bash'.
     */
    public BashCommandRunner() {
        super(BASH_SHELL);
    }
}
