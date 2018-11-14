package com.diveplane.interview;

import com.diveplane.interview.impl.Logger;
import com.diveplane.interview.impl.StatsCollector;
import junit.framework.TestCase;
import java.util.HashMap;
import java.util.Map;

/**
 * A suite of simple tests for basic sanity testing.
 */
public class TestSuiteSimple extends TestCase {

    // simple test to ensure two samples with equal probabilities have close (w/in a tolerance) predicted fractions.
    public void test1() {
        Map<Integer, Double> inputData = new HashMap<>();
        inputData.put(1, 0.5d);
        inputData.put(5, 0.5d);
        StatsCollector statsCollector = new StatsCollector();
        TestHelper.runTest(inputData, statsCollector);

        TestHelper.assertEqualToTolerance(statsCollector.predFractionFor(1), statsCollector.predFractionFor(5), 0.01);
    }

    // simple test with 3 entries to show predicted frequency of each index occurs in accordance with specified probabilites.
    public void test2() {
        Map<Integer, Double> inputData = new HashMap<>();
        inputData.put(2, 0.3d);
        inputData.put(5, 0.5d);
        inputData.put(7, 0.2d);
        StatsCollector statsCollector = new StatsCollector();
        TestHelper.runTest(inputData, statsCollector);

        assertTrue(statsCollector.countFor(5) > statsCollector.countFor(2));
        assertTrue(statsCollector.countFor(2) > statsCollector.countFor(7));
    }

    // simple test to ensure that if a probability with value less than or equal to zero is put, the entry is effectively ignored.
    public void test3() {
        Map<Integer, Double> inputData = new HashMap<>();
        inputData.put(1, 0.5d);
        inputData.put(5, 0.5d);
        inputData.put(3, -0.78d);
        StatsCollector statsCollector = new StatsCollector();
        TestHelper.runTest(inputData, statsCollector);

        TestHelper.assertEqualToTolerance(statsCollector.predFractionFor(1), statsCollector.predFractionFor(5), 0.01);
    }

    // simple test to ensure that if probabilities do not sum to 1.0, then the computation halts.
    public void test4() {
        boolean failed = false;
        Map<Integer, Double> inputData = new HashMap<>();
        inputData.put(1, 0.5d);
        inputData.put(2, 0.2d);
        inputData.put(3, 0.1d);
        StatsCollector statsCollector = new StatsCollector();
        try {
            TestHelper.runTest(inputData, statsCollector);
        } catch (RuntimeException e) {
            new Logger().info("Successfully caught an expected assertion");
            failed = true;
        }
        if (failed) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }
}
