package com.yalmon.agent.app.impl.shell;

/**
 * Sh command runner.
 */
public class ShCommandRunner extends ShellCommandRunner {

    /**
     * Current shell is Bourne Shell.
     */
    private static final String SH_SHELL = "sh";

    /**
     * Constructor which passes the shell name 'sh'.
     */
    public ShCommandRunner() {
        super(SH_SHELL);
    }
}
