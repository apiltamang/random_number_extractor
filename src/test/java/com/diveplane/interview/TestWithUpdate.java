package com.diveplane.interview;

import com.diveplane.interview.impl.SampleGeneratorImpl;
import com.diveplane.interview.impl.StatsCollector;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests that executes a probability update. Ensure that there
 * is no internal breakdown, and functionality is intact.
 */
public class TestWithUpdate extends TestCase {

    // simple test to ensure two samples with equal probabilities have close (w/in a tolerance) predicted fractions.
    public void testWithUpdate() {
        Map<Integer, Double> inputData = new HashMap<>();
        inputData.put(1, 0.25d);
        inputData.put(5, 0.25d);
        inputData.put(9, 0.25d);
        inputData.put(7, 0.25d);
        StatsCollector statsCollector = new StatsCollector();
        boolean debug = true;

        System.out.println(" ============ running a new test =============");
        SampleGeneratorImpl processor = new SampleGeneratorImpl(inputData, statsCollector, debug);
        processor.getPredictions(1000000);
        statsCollector.printStatistics();

        TestHelper.assertEqualToTolerance(statsCollector.predFractionFor(1), statsCollector.predFractionFor(5), 0.01);
        TestHelper.assertEqualToTolerance(statsCollector.predFractionFor(5), statsCollector.predFractionFor(9), 0.01);
        TestHelper.assertEqualToTolerance(statsCollector.predFractionFor(9), statsCollector.predFractionFor(7), 0.01);

        inputData.put(5, 0.4d);
        inputData.put(7, 0.1d);

        // an update was called in the internal probability states. Pass this information using the core API
        processor.resetProbabilityStates(inputData);
        processor.getPredictions(1000000);
        statsCollector.printStatistics();

        TestHelper.assertEqualToTolerance(statsCollector.predFractionFor(1), statsCollector.predFractionFor(9), 0.01);
        assertTrue(statsCollector.countFor(1) > statsCollector.countFor(7));
        assertTrue(statsCollector.countFor(5) > statsCollector.countFor(1));
    }

}
