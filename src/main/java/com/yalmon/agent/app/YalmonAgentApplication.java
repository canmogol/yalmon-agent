package com.yalmon.agent.app;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.yalmon.agent.app.ext.commandrunner.CommandRunner;
import com.yalmon.agent.app.ext.parser.OutputParser;
import com.yalmon.agent.app.ext.provider.CommandListProvider;
import com.yalmon.agent.app.ext.provider.CommandListProviderException;
import lombok.val;

/**
 * Yalmon agent application.
 */
public class YalmonAgentApplication {

    private final OutputParser finder;
    private final CommandRunner runner;
    private final CommandListProvider provider;
    private final String releaseCommand;

    /**
     * Constructor with arguments.
     *
     * @param commandRunner find-release-command runner.
     * @param distributionFinder output parser for release command.
     * @param commandListProvider provides command list for a distribution.
     * @param command find-release-command.
     */
    public YalmonAgentApplication(final CommandRunner commandRunner, final OutputParser distributionFinder,
                                  final CommandListProvider commandListProvider, final String command) {
        this.finder = distributionFinder;
        this.runner = commandRunner;
        this.provider = commandListProvider;
        this.releaseCommand = command;
    }

    /**
     * Entry point for agent application.
     *
     * @throws YalmonAgentApplicationException on internal errors
     */
    public void start() throws YalmonAgentApplicationException {
        val releaseCommandOutput = this.executeCommand(runner, releaseCommand);
        val distributionName = this.findDistributionName(releaseCommandOutput);
        this.getListOfCommands(distributionName, provider);
        // execute commands
        // prepare output
        // post output
    }

    /**
     * Executes the find-release-command and returns the command output.
     *
     * @param commandRunner command runner.
     * @param command command to be executed.
     * @return command output.
     * @throws YalmonAgentApplicationException when any parameter is null or empty, also when gets an error on
     * command execution.
     */
    final String executeCommand(final CommandRunner commandRunner, final String command)
        throws YalmonAgentApplicationException {
        if (isNullOrEmpty(command)) {
            throw new YalmonAgentApplicationException("release command cannot be null or empty");
        }
        if (isNull(commandRunner)) {
            throw new YalmonAgentApplicationException("command runner cannot be null");
        }
        return execute(commandRunner, command);
    }

    private String execute(final CommandRunner commandRunner, final String command)
        throws YalmonAgentApplicationException {
        try {
            return commandRunner.execute(command);
        } catch (IOException e) {
            String error = String.format("command could not be executed, error: %s", e.getMessage());
            throw new YalmonAgentApplicationException(error);
        }
    }

    private boolean isNullOrEmpty(final String value) {
        return isNull(value) || value.trim().isEmpty();
    }

    private boolean isNull(final Object object) {
        return Objects.isNull(object);
    }

    /**
     * Parses the find-release-command output and returns the distribution name.
     *
     * @param releaseCommandOutput command's output to parse.
     * @return release name.
     * @throws YalmonAgentApplicationException when cannot find the distribution name.
     */
    final String findDistributionName(final String releaseCommandOutput) throws YalmonAgentApplicationException {
        if (isNullOrEmpty(releaseCommandOutput)) {
            throw new YalmonAgentApplicationException("release command output cannot be null or empty");
        }
        try {
            return finder.parse(releaseCommandOutput);
        } catch (Exception e) {
            throw new YalmonAgentApplicationException(e.getMessage());
        }
    }

    /**
     * Returns the list of commands for that distribution.
     *
     * @param distributionName name of the linux distribution, such as 'ubuntu' or 'rhel'
     * @param commandListProvider provides a list of commands for a given distribution name.
     * @return command's label and shell command
     * @throws YalmonAgentApplicationException
     */
    final Map<String, String> getListOfCommands(final String distributionName,
                                                final CommandListProvider commandListProvider)
        throws YalmonAgentApplicationException {
        if (isNullOrEmpty(distributionName)) {
            throw new YalmonAgentApplicationException("distribution name cannot be null or empty");
        }
        if (isNull(commandListProvider)) {
            throw new YalmonAgentApplicationException("command list provider cannot be null");
        }
        return getCommandMap(distributionName, commandListProvider);
    }

    private Map<String, String> getCommandMap(final String distributionName,
                                              final CommandListProvider commandListProvider)
        throws YalmonAgentApplicationException {
        try {
            return commandListProvider.getCommands(distributionName);
        } catch (CommandListProviderException e) {
            val error = String.format("could not get command map for distribution %s", distributionName);
            throw new YalmonAgentApplicationException(error);
        }
    }

    final Map<String, String> executeCommands(final Map<String, String> commands)
        throws YalmonAgentApplicationException {
        if (isNull(commands)) {
            throw new YalmonAgentApplicationException("commands cannot be null");
        }
        if (isMapEmpty(commands)) {
            return Collections.emptyMap();
        }
        return commands.entrySet().stream()
                       .collect(Collectors.toMap(Map.Entry::getKey, e -> getCommandResult(e.getValue())));
    }

    private String getCommandResult(final String command) {
        String commandResult;
        try {
            commandResult = execute(runner, command);
        } catch (YalmonAgentApplicationException ex) {
            commandResult = String.format("command '%s' could not be executed, error: '%s'", command, ex.getMessage());
        }
        return commandResult;
    }

    private boolean isMapEmpty(final Map<String, String> map) {
        return Boolean.TRUE.equals(map.isEmpty());
    }

}
