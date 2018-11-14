package com.diveplane.interview;

import com.diveplane.interview.api.SampleGenerator;
import com.diveplane.interview.impl.SampleGeneratorImpl;
import com.diveplane.interview.impl.StatsCollector;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertTrue;

public class TestOtherTypeDistribution extends TestCase {

    public void testKeyString() {
        Map<String, Double> inputData = new HashMap<>();
        inputData.put("Dog", 0.3d);
        inputData.put("Cat", 0.5d);
        inputData.put("CatDog!", 0.2d);

        StatsCollector statsCollector = new StatsCollector<String>();
        SampleGenerator generator = new SampleGeneratorImpl<String>(inputData, statsCollector, true);
        generator.getPredictions(1000000); // do a simulated million queries run.
        statsCollector.printStatistics();

        assertTrue(statsCollector.countFor("Cat") > statsCollector.countFor("Dog"));
        assertTrue(statsCollector.countFor("Dog") > statsCollector.countFor("CatDog!"));
    }

    public void testKeyCharacters() {
        Map<Character, Double> inputData = new HashMap<>();
        inputData.put('K', 0.2d);
        inputData.put('L', 0.4d);
        inputData.put('Z', 0.2d);
        inputData.put('?', 0.1d);
        inputData.put('@', 0.1d);

        StatsCollector statsCollector = new StatsCollector<Character>();
        SampleGenerator generator = new SampleGeneratorImpl<Character>(inputData, statsCollector, true);
        generator.getPredictions(1000000); // do a simulated million queries run.
        statsCollector.printStatistics();

        assertTrue(statsCollector.countFor('L') > statsCollector.countFor('K'));
        assertTrue(statsCollector.countFor('K') > statsCollector.countFor('@'));
        TestHelper.assertEqualToTolerance(statsCollector.predFractionFor('?'), statsCollector.predFractionFor('@'), 0.01);
        TestHelper.assertEqualToTolerance(statsCollector.predFractionFor('K'), statsCollector.predFractionFor('Z'), 0.01);
    }

}
