package com.diveplane.interview;

import com.diveplane.interview.impl.NumberGeneratorImpl;
import com.diveplane.interview.impl.Logger;
import com.diveplane.interview.impl.StatsCollector;

import java.util.Map;

public class TestHelper {

    int nAttempts;
    StatsCollector statsCollector;
    Map<Integer, Double> probEntries;
    boolean debug;

    public TestHelper() {

    }

    public TestHelper withStatsCollector(StatsCollector statsCollector) {
        this.statsCollector = statsCollector;
        return this;
    }

    public TestHelper withProbEntries(Map<Integer, Double> probEntries) {
        this.probEntries = probEntries;
        return this;
    }

    public TestHelper withDebugMode(Boolean debug) {
        this.debug = debug;
        return this;
    }

    public void loadTest(int nAttempts) {
        System.out.println(" ============ running a new test =============");
        NumberGeneratorImpl processor = new NumberGeneratorImpl(probEntries, statsCollector, debug);
        processor.getPredictions(nAttempts);
        statsCollector.printStatistics();
    }

    public static void runTest(Map<Integer, Double> inputData, StatsCollector statsCollector) {
        new TestHelper().withProbEntries(inputData).
                withStatsCollector(statsCollector).withDebugMode(true).loadTest(100000);
    }

    public static void assertEqualToTolerance(double d1, double d2, double tolerance) {
        Logger log = new Logger();
        if (Math.abs(d1 - d2) > tolerance)
            throw new RuntimeException(String.format("Assertion failed. Needed d1 and d2 within tolerance. Got %f, %f", d1, d2));
        log.info("passed assertion, d1 within tolerance of d2");
    }
}
