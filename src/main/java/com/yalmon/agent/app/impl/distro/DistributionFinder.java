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
     * @throws Exception when the output is null or empty.
     */
    public final String find(final String output) throws Exception {
        if (Objects.isNull(output) || output.trim().isEmpty()) {
            throw new Exception("output cannot be null or empty");
        }
        String distribution = getDistributionName(output);
        return trimDistribution(distribution);
    }

    private String getDistributionName(final String output) {
        return Stream.of(output.split(System.lineSeparator()))
            .map(String::trim)
            .filter(line -> line.startsWith(KEY_ID))
            .map(line -> line.substring(KEY_ID.length()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("could not find the distribution"));
    }

    private String trimDistribution(final String distribution) {
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
