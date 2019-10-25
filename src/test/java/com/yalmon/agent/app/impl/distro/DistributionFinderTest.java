package com.yalmon.agent.app.impl.distro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.val;

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
        val exception = assertThrows(DistributionFindException.class, () -> finder.parse(null));
        assertEquals("parse content cannot be null or empty", exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenEmptyOutputGiven() {
        val exception = assertThrows(DistributionFindException.class, () -> finder.parse(null));
        assertEquals("parse content cannot be null or empty", exception.getMessage());
    }

    @Test
    void testShouldThrowExceptionWhenNoDistributionFound() {
        val exception = assertThrows(DistributionFindException.class, () -> finder.parse("irrelevant-string\nentry\n"));
        assertEquals("could not find the distribution", exception.getMessage());
    }

    @Test
    void testShouldFindDistroAmazon() throws Throwable {
        val expected = "amzn";
        val releaseCommandContent =
            "NAME=\"Amazon Linux AMI\"\n" + "VERSION=\"2016.09\"\n" + "ID=\"" + expected + "\"\n"
                + "ID_LIKE=\"rhel fedora\"\n" + "VERSION_ID=\"2016.09\"\n"
                + "PRETTY_NAME=\"Amazon Linux AMI 2016.09\"\n" + "ANSI_COLOR=\"0;33\"\n"
                + "CPE_NAME=\"cpe:/o:amazon:linux:2016.09:ga\"\n"
                + "HOME_URL=\"http://aws.amazon.com/amazon-linux-ami/\"";
        val actual = finder.parse(releaseCommandContent);
        assertEquals(expected, actual);
    }

    @Test
    void testShouldFindDistroArch() throws Throwable {
        val expected = "arch";
        val releaseCommandContent =
            "NAME=\"Arch Linux\"\n" + "ID=" + expected + "\n" + "PRETTY_NAME=\"Arch Linux\"\n" + "ANSI_COLOR=\"0;36\"\n"
                + "HOME_URL=\"https://www.archlinux.org/\"\n" + "SUPPORT_URL=\"https://bbs.archlinux.org/\"\n"
                + "BUG_REPORT_URL=\"https://bugs.archlinux.org/\"";
        val actual = finder.parse(releaseCommandContent);
        assertEquals(expected, actual);
    }

    @Test
    void testShouldFindDistroUbuntu() throws DistributionFindException {
        String expected = "ubuntu";
        String releaseCommandContentUbuntu =
            "DISTRIB_ID=Ubuntu\n" + "DISTRIB_RELEASE=16.04\n" + "DISTRIB_CODENAME=xenial\n"
                + "DISTRIB_DESCRIPTION=\"Ubuntu 16.04.6 LTS\"\n" + "NAME=\"Ubuntu\"\n"
                + "VERSION=\"16.04.6 LTS (Xenial Xerus)\"\n" + "ID=ubuntu\n" + "ID_LIKE=debian\n"
                + "PRETTY_NAME=\"Ubuntu 16.04.6 LTS\"\n" + "VERSION_ID=\"16.04\"\n"
                + "HOME_URL=\"http://www.ubuntu.com/\"\n" + "SUPPORT_URL=\"http://help.ubuntu.com/\"\n"
                + "BUG_REPORT_URL=\"http://bugs.launchpad.net/ubuntu/\"\n" + "VERSION_CODENAME=xenial\n"
                + "UBUNTU_CODENAME=xenial";
        String actual = finder.parse(releaseCommandContentUbuntu);
        assertEquals(expected, actual);
    }

    @Test
    void testShouldFindDistroRedhat() throws DistributionFindException {
        String expected = "rhel";
        String releaseCommandContentRedhat =
            "NAME=\"Red Hat Enterprise Linux Server\"\n" + "VERSION=\"7.6 (Maipo)\"\n" + "ID=\"rhel\"\n"
                + "ID_LIKE=\"fedora\"\n" + "VARIANT=\"Server\"\n" + "VARIANT_ID=\"server\"\n" + "VERSION_ID=\"7.6\"\n"
                + "PRETTY_NAME=\"Red Hat Enterprise Linux\"\n" + "ANSI_COLOR=\"0;31\"\n"
                + "CPE_NAME=\"cpe:/o:redhat:enterprise_linux:7.6:GA:server\"\n"
                + "HOME_URL=\"https://www.redhat.com/\"\n" + "BUG_REPORT_URL=\"https://bugzilla.redhat.com/\"";
        String actual = finder.parse(releaseCommandContentRedhat);
        assertEquals(expected, actual);
    }

}
