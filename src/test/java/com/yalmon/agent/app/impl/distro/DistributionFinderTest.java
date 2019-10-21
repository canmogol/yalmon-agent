package com.yalmon.agent.app.impl.distro;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Distribution finder tests.
 */
class DistributionFinderTest {

    private DistributionFinder finder;

    @BeforeEach
    void setUp() {
        finder = new DistributionFinder();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testShouldThrowExceptionWhenNullOutputGiven() {
        val exception = assertThrows(Exception.class,
            () -> finder.find(null));
        assertEquals("output cannot be null or empty", exception.getMessage());
    }
    @Test
    void testShouldThrowExceptionWhenEmptyOutputGiven() {
        val exception = assertThrows(Exception.class,
            () -> finder.find(null));
        assertEquals("output cannot be null or empty", exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenNoDistributionFound() {
        val exception = assertThrows(RuntimeException.class,
            () -> finder.find("irrelevant-string\nentry\n"));
        assertEquals("could not find the distribution", exception.getMessage());
    }

    @Test
    void testShouldFindDistroAmazon() throws Throwable {
        val expected = "amzn";
        val catReleaseOutput = "NAME=\"Amazon Linux AMI\"\n" +
            "VERSION=\"2016.09\"\n" +
            "ID=\"" + expected + "\"\n" +
            "ID_LIKE=\"rhel fedora\"\n" +
            "VERSION_ID=\"2016.09\"\n" +
            "PRETTY_NAME=\"Amazon Linux AMI 2016.09\"\n" +
            "ANSI_COLOR=\"0;33\"\n" +
            "CPE_NAME=\"cpe:/o:amazon:linux:2016.09:ga\"\n" +
            "HOME_URL=\"http://aws.amazon.com/amazon-linux-ami/\"";
        val actual = finder.find(catReleaseOutput);
        assertEquals(expected, actual);
    }

    @Test
    void testShouldFindDistroArch() throws Throwable {
        val expected = "arch";
        val catReleaseOutput = "NAME=\"Arch Linux\"\n" +
            "ID=" + expected + "\n" +
            "PRETTY_NAME=\"Arch Linux\"\n" +
            "ANSI_COLOR=\"0;36\"\n" +
            "HOME_URL=\"https://www.archlinux.org/\"\n" +
            "SUPPORT_URL=\"https://bbs.archlinux.org/\"\n" +
            "BUG_REPORT_URL=\"https://bugs.archlinux.org/\"";
        val actual = finder.find(catReleaseOutput);
        assertEquals(expected, actual);
    }

}