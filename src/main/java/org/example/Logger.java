package org.example;

/**
 * The {Logger} class provides functionality for tracking and reporting code coverage
 * using probe-based instrumentation. The coverage data is stored in an array of boolean values,
 * where each index represents a "probe" corresponding to a specific line or block of code.
 *
 * LogProbe(probeId): log coverage for a specific probe
 * Logger.printCoverageReport(): print a report of covered and uncovered probes
 */
public class Logger {
    public static boolean[] coverageProbes = new boolean[60];

    public static void logProbe(int probeId) {
        coverageProbes[probeId] = true;
    }

    public static void printCoverageReport() {
        System.out.println("Coverage Report:");
        for (int i = 0; i < coverageProbes.length; i++) {
            System.out.println("Probe " + i + ": " + (coverageProbes[i] ? "Covered" : "Not Covered"));
        }
    }
}
