package com.diveplane.interview;

import java.util.Map;

public class TestHelper {

    // simple test helper
    public static void runTest(Map<Integer, Double> inputData, StatsCollector statsCollector) {
        boolean debug = true;
        System.out.println(" ============ running a new test =============");
        CoreProcessor processor = new CoreProcessor(inputData, statsCollector, debug);
        processor.getPredictions(1000000);
        statsCollector.printStatistics();
    }

    public static void assertEqualToTolerance(double d1, double d2, double tolerance) {
        Logger log = new Logger();
        if (Math.abs(d1 - d2) > tolerance)
            throw new RuntimeException(String.format("Assertion failed. Needed d1 and d2 within tolerance. Got %f, %f", d1, d2));
        log.info("passed assertion, d1 within tolerance of d2");
    }
}
