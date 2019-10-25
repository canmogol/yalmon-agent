package com.yalmon.agent.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.yalmon.agent.app.ext.commandrunner.CommandRunner;
import com.yalmon.agent.app.ext.parser.OutputParser;
import com.yalmon.agent.app.ext.parser.OutputParserException;
import com.yalmon.agent.app.ext.provider.CommandListProvider;
import com.yalmon.agent.app.ext.provider.CommandListProviderException;
import lombok.val;

/**
 * Yalmon agent application test.
 */
class YalmonAgentApplicationTest {

    private static final String RELEASE_COMMAND = "cat /etc/*release";
    @Mock
    CommandRunner runner;
    @Mock
    OutputParser parser;
    @Mock
    CommandListProvider provider;
    private YalmonAgentApplication yalmonAgentApplication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        yalmonAgentApplication = new YalmonAgentApplication(runner, parser, provider, RELEASE_COMMAND);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testShouldThrowExceptionWhenNullContentProvided() {
        String nullContent = null;
        val exception = assertThrows(YalmonAgentApplicationException.class,
            () -> yalmonAgentApplication.findDistributionName(nullContent, parser));
        assertEquals("release command output cannot be null or empty", exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenEmptyContentProvided() {
        String emptyContent = "";
        val exception = assertThrows(YalmonAgentApplicationException.class,
            () -> yalmonAgentApplication.findDistributionName(emptyContent, parser));
        assertEquals("release command output cannot be null or empty", exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenParseErrorOccurs() throws OutputParserException {
        val nonNullContent = "this-is-non-null-content";
        val expectedError = "expected-error-message";
        Mockito.when(parser.parse(nonNullContent)).thenThrow(new OutputParserException(expectedError));
        val exception = assertThrows(YalmonAgentApplicationException.class,
            () -> yalmonAgentApplication.findDistributionName(nonNullContent, parser));
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenNullCommandProvided() {
        String nullReleaseCommand = null;
        YalmonAgentApplicationException exception = assertThrows(YalmonAgentApplicationException.class,
            () -> yalmonAgentApplication.executeCommand(runner, nullReleaseCommand));
        assertEquals("release command cannot be null or empty", exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenEmptyCommandProvided() {
        String emptyReleaseCommand = "";
        YalmonAgentApplicationException exception = assertThrows(YalmonAgentApplicationException.class,
            () -> yalmonAgentApplication.executeCommand(runner, emptyReleaseCommand));
        assertEquals("release command cannot be null or empty", exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenNullCommandRunnerProvided() {
        val nullReleaseCommand = "non-null-release-command";
        final CommandRunner nullRunner = null;
        YalmonAgentApplicationException exception = assertThrows(YalmonAgentApplicationException.class,
            () -> yalmonAgentApplication.executeCommand(nullRunner, nullReleaseCommand));
        assertEquals("command runner cannot be null", exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenCommandCouldNotBeExecuted() throws IOException {
        String failingCommand = "command-should-fail";
        Mockito.when(runner.execute(failingCommand)).thenThrow(new IOException(failingCommand));
        String error = String.format("command could not be executed, error: %s", failingCommand);

        YalmonAgentApplicationException exception = assertThrows(YalmonAgentApplicationException.class,
            () -> yalmonAgentApplication.executeCommand(runner, failingCommand));

        assertEquals(error, exception.getMessage());
    }

    @Test
    void testShouldReturnCommandOutputWhenReleaseCommandProvidedForUbuntu()
        throws YalmonAgentApplicationException, IOException {
        String releaseCommandContentUbuntu = //
            "DISTRIB_ID=Ubuntu\n" //
                + "DISTRIB_RELEASE=16.04\n" //
                + "DISTRIB_CODENAME=xenial\n" //
                + "DISTRIB_DESCRIPTION=\"Ubuntu 16.04.6 LTS\"\n" //
                + "NAME=\"Ubuntu\"\n" //
                + "VERSION=\"16.04.6 LTS (Xenial Xerus)\"\n" //
                + "ID=ubuntu\n" //
                + "ID_LIKE=debian\n" //
                + "PRETTY_NAME=\"Ubuntu 16.04.6 LTS\"\n" //
                + "VERSION_ID=\"16.04\"\n" //
                + "HOME_URL=\"http://www.ubuntu.com/\"\n" //
                + "SUPPORT_URL=\"http://help.ubuntu.com/\"\n" //
                + "BUG_REPORT_URL=\"http://bugs.launchpad.net/ubuntu/\"\n" //
                + "VERSION_CODENAME=xenial\n" //
                + "UBUNTU_CODENAME=xenial";
        Mockito.when(runner.execute(RELEASE_COMMAND)).thenReturn(releaseCommandContentUbuntu);

        String actual = yalmonAgentApplication.executeCommand(runner, RELEASE_COMMAND);

        assertEquals(releaseCommandContentUbuntu, actual);
    }

    @Test
    void testShouldReturnCommandOutputWhenReleaseCommandProvidedForRedhat()
        throws YalmonAgentApplicationException, IOException {
        String releaseCommandContentRedhat = //
            "NAME=\"Red Hat Enterprise Linux Server\"\n" //
                + "VERSION=\"7.6 (Maipo)\"\n" //
                + "ID=\"rhel\"\n" //
                + "ID_LIKE=\"fedora\"\n" //
                + "VARIANT=\"Server\"\n" //
                + "VARIANT_ID=\"server\"\n" //
                + "VERSION_ID=\"7.6\"\n" //
                + "PRETTY_NAME=\"Red Hat Enterprise Linux\"\n" //
                + "ANSI_COLOR=\"0;31\"\n" //
                + "CPE_NAME=\"cpe:/o:redhat:enterprise_linux:7.6:GA:server\"\n" //
                + "HOME_URL=\"https://www.redhat.com/\"\n"//
                + "BUG_REPORT_URL=\"https://bugzilla.redhat.com/\"";
        Mockito.when(runner.execute(RELEASE_COMMAND)).thenReturn(releaseCommandContentRedhat);

        String actual = yalmonAgentApplication.executeCommand(runner, RELEASE_COMMAND);

        assertEquals(releaseCommandContentRedhat, actual);
    }

    @Test
    void testShouldThrowExceptionWhenNullDistributionNameProvided() {
        String nullDistributionName = null;
        YalmonAgentApplicationException exception = assertThrows(YalmonAgentApplicationException.class,
            () -> yalmonAgentApplication.getListOfCommands(nullDistributionName, provider));
        assertEquals("distribution name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenEmptyDistributionNameProvided() {
        String emptyDistributionName = "";
        YalmonAgentApplicationException exception = assertThrows(YalmonAgentApplicationException.class,
            () -> yalmonAgentApplication.getListOfCommands(emptyDistributionName, provider));
        assertEquals("distribution name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenNullProviderProvided() {
        String distributionName = "NON-EMPTY-DISTRO-NAME";
        CommandListProvider nullProvider = null;
        YalmonAgentApplicationException exception = assertThrows(YalmonAgentApplicationException.class,
            () -> yalmonAgentApplication.getListOfCommands(distributionName, nullProvider));
        assertEquals("command list provider cannot be null", exception.getMessage());
    }

    @Test
    void testShouldReturnListOfCommandsWhenDistributionNameProvided()
        throws YalmonAgentApplicationException, CommandListProviderException {
        Map<String, String> expected = new LinkedHashMap<>();
        val cpuLabel = "cpu";
        val memLabel = "mem";
        val expectedLabels = String.format("%s,%s", cpuLabel, memLabel);
        val cpuCommand = "ps - Ao pcpu --sort = -pcpu | head - 1";
        val memCommand = "ps -Ao pcpu --sort=-pmem | head -1";
        val expectedCommands = String.format("%s,%s", cpuCommand, memCommand);
        expected.put(cpuLabel, cpuCommand);
        expected.put(memLabel, memCommand);
        val distributionName = "ubuntu";
        Mockito.when(provider.getCommands(distributionName)).thenReturn(expected);

        val actual = yalmonAgentApplication.getListOfCommands(distributionName, provider);

        assertEquals(expected.size(), actual.size(), "number of commands should be the same.");
        val actualLabels = String.join(",", actual.keySet());
        assertEquals(expectedLabels, actualLabels, "expected labels should be the same.");
        val actualCommands = String.join(",", actual.values());
        assertEquals(expectedCommands, actualCommands, "expected commands should be the same.");
    }

    @Test
    void testShouldThrowExceptionWhenCommandListCouldNotBeRetrieved() throws CommandListProviderException {
        val distributionName = "some-distribution";
        val expectedError = String.format("could not get command map for distribution %s", distributionName);

        Mockito.when(provider.getCommands(distributionName)).thenThrow(new CommandListProviderException("error"));

        YalmonAgentApplicationException exception = assertThrows(YalmonAgentApplicationException.class,
            () -> yalmonAgentApplication.getListOfCommands(distributionName, provider));

        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenNullCommandMapProvided() {
        Map<String, String> nullCommands = null;
        YalmonAgentApplicationException exception = assertThrows(YalmonAgentApplicationException.class,
            () -> yalmonAgentApplication.executeCommands(nullCommands));
        assertEquals("commands cannot be null", exception.getMessage());
    }

    @Test
    void testShouldNotReturnNullResultWhenAnCommandMapProvided() throws YalmonAgentApplicationException {
        Map<String, String> commands = new HashMap<>();
        Map<String, String> actual = yalmonAgentApplication.executeCommands(commands);
        assertNotNull(actual, "execute commands cannot return null result");
    }

    @Test
    void testShouldReturnAnEmptyResultMapWhenAnEmptyCommandMapProvided() throws YalmonAgentApplicationException {
        Map<String, String> emptyMap = new HashMap<>();
        Map<String, String> actual = yalmonAgentApplication.executeCommands(emptyMap);
        assertEquals(emptyMap.size(), actual.size(),
            "The size of the empty map and the size of the result map should be equal.");
    }

    @Test
    void testShouldReturnResultMapWhenACommandMapProvided() throws YalmonAgentApplicationException, IOException {
        val label = "ls";
        val command = "ls /dev/null";
        val commandResult = "/dev/null";
        final Map<String, String> commandMap = new HashMap<>();
        commandMap.put(label, command);
        Mockito.when(runner.execute(command)).thenReturn(commandResult);

        Map<String, String> actual = yalmonAgentApplication.executeCommands(commandMap);

        assertEquals(commandMap.size(), actual.size(),
            "The size of the command map and the size of the result map should be equal.");
        final Map.Entry<String, String> entry = actual.entrySet().iterator().next();
        assertEquals(label, entry.getKey(), "The key should be same.");
        assertNotNull(entry.getValue(), "The command result cannot be null");
        assertEquals(commandResult, entry.getValue(), "The command result should be the value returned by the runner.");
    }

    @Test
    void testShouldReturnTheErrorMessageWhenACommandCouldNotBeExecuted()
        throws YalmonAgentApplicationException, IOException {
        val label = "ls";
        val command = "ls /dev/null";
        final Map<String, String> commandMap = new HashMap<>();
        commandMap.put(label, command);
        val ioError = "expected-IO-error";
        Mockito.when(runner.execute(command)).thenThrow(new IOException(ioError));

        Map<String, String> actual = yalmonAgentApplication.executeCommands(commandMap);

        final Map.Entry<String, String> entry = actual.entrySet().iterator().next();
        final String actualCommandResult = entry.getValue();
        final String expectedErrorMessage =
            String.format("command '%s' could not be executed, error: 'command could not be executed, error: %s'",
                command, ioError);

        assertEquals(expectedErrorMessage, actualCommandResult,
            "The command result should be the value returned by the runner.");
    }
}
