package com.yalmon.agent.app.impl.distro;

import java.util.Objects;
import java.util.stream.Stream;

import com.yalmon.agent.app.ext.parser.OutputParser;

/**
 * Finds the linux distribution.
 */
public class DistributionFinder implements OutputParser {

    private static final String KEY_ID = "ID=";

    /**
     * Finds the distribution id from output.
     *
     * @param output release key value entries.
     * @return distribution id.
     * @throws DistributionFindException when the output is null or empty.
     */
    @Override
    public final String parse(final String output) throws DistributionFindException {
        if (isNullOrEmpty(output)) {
            throw new DistributionFindException("parse content cannot be null or empty");
        }
        try {
            return getDistributionName(output);
        } catch (Exception e) {
            throw new DistributionFindException(e.getMessage());
        }
    }

    private boolean isNullOrEmpty(final String output) {
        return Objects.isNull(output) || output.trim().isEmpty();
    }

    private String getDistributionName(final String output) {
        return Stream.of(output.split(System.lineSeparator()))
                     .map(String::trim)
                     .filter(line -> line.startsWith(KEY_ID))
                     .map(line -> line.substring(KEY_ID.length()))
                     .map(this::trim)
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
