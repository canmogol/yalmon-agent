package com.yalmon.agent.app.impl.provider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import com.yalmon.agent.app.ext.provider.CommandListProvider;
import com.yalmon.agent.app.ext.provider.CommandListProviderException;
import lombok.val;

/**
 * Provides a list of commands for a given distribution from YAML file.
 */
public class YamlFileCommandListProvider implements CommandListProvider {

    /**
     * Returns the commands and their labels for a given distribution from a YAML file.
     *
     * @param distribution distro name, such as 'ubuntu' or 'rhel'
     */
    @Override
    public Map<String, String> getCommands(final String distribution) throws CommandListProviderException {
        if (isNullOrEmpty(distribution)) {
            throw new CommandListProviderException("distribution cannot be null or empty");
        }
        val propertiesFileName = String.format("%s.commands.properties", distribution);
        return getCommandsFromFile(propertiesFileName);
    }

    private Map<String, String> getCommandsFromFile(final String propertiesFileName)
        throws CommandListProviderException {
        val resource = this.getClass().getClassLoader().getResource(propertiesFileName);
        if (isNull(resource)) {
            val error = String.format("file not found in the classpath's root, file: %s", propertiesFileName);
            throw new CommandListProviderException(error);
        }
        return getCommandsFromResource(propertiesFileName, resource);
    }

    private Map<String, String> getCommandsFromResource(final String propertiesFileName, final URL resource)
        throws CommandListProviderException {
        try {
            return readCommandsFromResource(resource);
        } catch (IllegalArgumentException | IOException | URISyntaxException e) {
            val error =
                String.format("could not read commands from file: %s, error: %s", propertiesFileName, e.getMessage());
            throw new CommandListProviderException(error);
        }
    }

    private Map<String, String> readCommandsFromResource(final URL resource) throws URISyntaxException, IOException {
        Properties commands = new Properties();
        val propertiesFile = resource.toURI();
        commands.load(Files.newInputStream(Paths.get(propertiesFile), StandardOpenOption.READ));
        return commands.entrySet()
                       .stream()
                       .map(entry -> new HashMap.SimpleEntry<>(String.valueOf(entry.getKey()),
                           String.valueOf(entry.getValue())))
                       .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean isNull(final Object o) {
        return Objects.isNull(o);
    }

    private boolean isNullOrEmpty(final String output) {
        return Objects.isNull(output) || output.trim().isEmpty();
    }

}
