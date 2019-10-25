package com.yalmon.agent.app.impl.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.yalmon.agent.app.ext.commandrunner.CommandRunner;
import lombok.val;

/**
 * Abstract shell command runner.
 */
public abstract class ShellCommandRunner implements CommandRunner {

    private final String shell;

    /**
     * Constructor with shell name.
     *
     * @param shellName name of the shell the commands will be executed.
     */
    protected ShellCommandRunner(final String shellName) {
        this.shell = shellName;
    }

    /**
     * Executes string command and returns command output.
     *
     * @param command shell command.
     * @return output of the executed command.
     * @throws IOException either on process start or on output read.
     */
    @Override
    public final String execute(final String command) throws IOException {
        if (Objects.isNull(command) || command.isEmpty()) {
            return command;
        } else {
            return executeCommand(command);
        }
    }

    private String executeCommand(final String command) throws IOException {
        Process process = createProcessForCommand(command);
        BufferedReader reader = getProcessOutputReader(process);
        return getCommandOutput(reader);
    }

    private Process createProcessForCommand(final String command) throws IOException {
        val processBuilder = new ProcessBuilder();
        processBuilder.command(shell, "-c", command);
        return processBuilder.start();
    }

    private BufferedReader getProcessOutputReader(final Process process) {
        final InputStreamReader in = new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8);
        return new BufferedReader(in);
    }

    private String getCommandOutput(final BufferedReader reader) throws IOException {
        String line;
        val stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

}
