package com.yalmon.agent.app.impl.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.yalmon.agent.app.ext.provider.CommandListProviderException;
import lombok.val;

class YamlFileCommandListProviderTest {

    private YamlFileCommandListProvider yamlFileCommandListProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        yamlFileCommandListProvider = new YamlFileCommandListProvider();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testShouldThrowExceptionWhenNullDistributionNameProvided() {
        String nullDistributionName = null;
        val exception = assertThrows(CommandListProviderException.class,
            () -> yamlFileCommandListProvider.getCommands(nullDistributionName));
        assertEquals("distribution cannot be null or empty", exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenEmptyDistributionNameProvided() {
        val emptyDistributionName = "";
        val exception = assertThrows(CommandListProviderException.class,
            () -> yamlFileCommandListProvider.getCommands(emptyDistributionName));
        assertEquals("distribution cannot be null or empty", exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenUnknownDistributionNameProvided() throws CommandListProviderException {
        val nonEmptyDistributionName = "unknown-distribution-name";
        val expectedError = String.format("file not found in the classpath's root, file: %s.commands.properties",
            nonEmptyDistributionName);

        val exception = assertThrows(CommandListProviderException.class,
            () -> yamlFileCommandListProvider.getCommands(nonEmptyDistributionName));
        assertEquals(expectedError, exception.getMessage());
    }

    @Test
    void testShouldReturnCommandMapWhenUbuntuAsDistributionNameProvided()
        throws CommandListProviderException, IOException, URISyntaxException {
        val distributionName = "ubuntu";
        val expected = getPropertiesForDistroFromFile(distributionName);

        val actual = yamlFileCommandListProvider.getCommands(distributionName);

        assertEquals(expected.size(), actual.size(),
            "number of commands read from file should be equal to the number" + " of commands returned.");
        assertTrue(expected.values().containsAll(actual.values()), "all the labels from file should be returned.");
        assertTrue(expected.values().containsAll(actual.values()), "all the commands from file should be returned.");
    }

    @Test
    void testShouldReturnCommandMapWhenRhelAsDistributionNameProvided()
        throws CommandListProviderException, IOException, URISyntaxException {
        val distributionName = "rhel";
        val expected = getPropertiesForDistroFromFile(distributionName);

        val actual = yamlFileCommandListProvider.getCommands(distributionName);

        assertEquals(expected.size(), actual.size(),
            "number of commands read from file should be equal to the number" + " of commands returned.");
        assertTrue(expected.values().containsAll(actual.values()), "all the labels from file should be returned.");
        assertTrue(expected.values().containsAll(actual.values()), "all the commands from file should be returned.");
    }

    @Test
    void testShouldThrowExceptionWhenMalformattedCommandFileProvided() {
        val nonExistingDistributionName = "malformatted-command-file";
        val propertiesFileName = String.format("%s.commands.properties", nonExistingDistributionName);
        final String expectedErrorMessage =
            String.format("could not read commands from file: %s, error:", propertiesFileName);

        val exception = assertThrows(CommandListProviderException.class,
            () -> yamlFileCommandListProvider.getCommands(nonExistingDistributionName));

        assertTrue(exception.getMessage().startsWith(expectedErrorMessage));
    }

    private Properties getPropertiesForDistroFromFile(String distributionName) throws IOException, URISyntaxException {
        Properties ubuntuCommands = new Properties();
        val propertiesFileName = String.format("%s.commands.properties", distributionName);
        final URL resource = this.getClass().getClassLoader().getResource(propertiesFileName);
        val propertiesFile = Objects.requireNonNull(resource).toURI();
        ubuntuCommands.load(Files.newInputStream(Paths.get(propertiesFile), StandardOpenOption.READ));
        return ubuntuCommands;
    }

}
