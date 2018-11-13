package com.diveplane.interview;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestWithUpdate extends TestCase {

    // simple test to ensure two samples with equal probabilities have close (w/in a tolerance) predicted fractions.
    public void testWithUpdate() {
        Map<Integer, Double> inputData = new HashMap<>();
        inputData.put(1, 0.25d);
        inputData.put(5, 0.25d);
        inputData.put(9, 0.25d);
        inputData.put(7, 0.25d);
        StatsCollector statsCollector = new StatsCollector();
        TestHelper.runTest(inputData, statsCollector);

        TestHelper.assertEqualToTolerance(statsCollector.predFractionFor(1), statsCollector.predFractionFor(5), 0.01);
        TestHelper.assertEqualToTolerance(statsCollector.predFractionFor(5), statsCollector.predFractionFor(9), 0.01);
        TestHelper.assertEqualToTolerance(statsCollector.predFractionFor(9), statsCollector.predFractionFor(7), 0.01);
    }

}
