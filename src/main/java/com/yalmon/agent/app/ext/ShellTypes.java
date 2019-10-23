package com.yalmon.agent.app.ext;

/**
 * Shell types.
 */
public enum ShellTypes {

    BASH("bash"), SH("sh");

    private final String value;

    /**
     * Shell type constructor with shell name.
     *
     * @param shellName name of the shell, such as 'bash' or 'sh'.
     */
    ShellTypes(final String shellName) {
        this.value = shellName;
    }

    /**
     * Returns the value of the enum.
     *
     * @return shell name.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns default shell's name.
     *
     * @return shell name.
     */
    public static String getDefault() {
        return BASH.getValue();
    }

}
