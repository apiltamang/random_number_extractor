package com.diveplane.interview;

import com.diveplane.interview.api.NumberGenerator;
import com.diveplane.interview.impl.NumberGeneratorImpl;
import com.diveplane.interview.impl.StatsCollector;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        // init. data
        Map<Integer, Double> probEntries = new HashMap<>();
        probEntries.put(1, 0.20);
        probEntries.put(9, 0.30);
        probEntries.put(4, 0.10);
        probEntries.put(6, 0.10);
        probEntries.put(2, 0.05);
        probEntries.put(19, 0.02);
        probEntries.put(14, 0.15);
        probEntries.put(10, 0.03);
        probEntries.put(17, 0.01);
        probEntries.put(16, 0.04);

        StatsCollector statsCollector = new StatsCollector();
        NumberGenerator generator = new NumberGeneratorImpl(probEntries, statsCollector, false);
        generator.getPredictions(1000000); // do a simulated million queries run.
        statsCollector.printStatistics();

    }
}
