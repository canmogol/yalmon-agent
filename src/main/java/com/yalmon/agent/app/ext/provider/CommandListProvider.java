package com.yalmon.agent.app.ext.provider;

import java.util.Map;

/**
 * Provides a list of commands for a given distribution.
 */
public interface CommandListProvider {

    /**
     * Returns the commands and their labels for a given distribution.
     *
     * @param distribution distro name, such as 'ubuntu' or 'rhel'
     * @throws CommandListProviderException on read/find/get commands errors.
     * @return label and command map.
     */
    Map<String, String> getCommands(String distribution) throws CommandListProviderException;

}
