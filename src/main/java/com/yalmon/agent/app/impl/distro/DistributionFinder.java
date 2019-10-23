package com.yalmon.agent.app.impl.distro;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Finds the linux distribution.
 */
public class DistributionFinder {

    public static final String KEY_ID = "ID=";

    /**
     * Finds the distribution id from output.
     *
     * @param output release key value entries.
     * @return distribution id.
     * @throws DistributionFindException when the output is null or empty.
     */
    public final String find(final String output) throws DistributionFindException {
        if (Objects.isNull(output) || output.trim().isEmpty()) {
            throw new DistributionFindException(
                    "could not parse and find the distribution, parse content cannot be null or empty");
        }
        return getDistributionName(output);
    }

    private String getDistributionName(final String output) {
        return Stream.of(output.split(System.lineSeparator()))
            .map(String::trim)
            .filter(line -> line.startsWith(KEY_ID))
            .map(line -> line.substring(KEY_ID.length()))
            .map(distributionName -> trim(distributionName))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("could not find the distribution"));
    }

    private String trim(final String distribution) {
        String dist = distribution;
        if (dist.startsWith("\"")) {
            dist = dist.substring(1);
        }
        if (dist.endsWith("\"")) {
            dist = dist.substring(0, dist.length() - 1);
        }
        return dist;
    }

}
