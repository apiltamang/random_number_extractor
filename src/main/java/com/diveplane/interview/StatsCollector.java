package com.diveplane.interview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple Statistics collection class to collect the metrics on the fly,
 * and print it out at the end. Valuable for a proof of implementation.
 */
public class StatsCollector {
    List<Long> predictions = new ArrayList<>();
    Map<Long, Integer> freqTable = new HashMap<>();
    Logger log;
    long startTime;
    long elapasedTime;

    public StatsCollector() {
        log = new Logger();
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public void stopTimer() {
        long size = predictions.size();
        elapasedTime = System.currentTimeMillis() - startTime;
        log.info(String.format("Elapsed time for %d experiments: %d milliseconds",size, elapasedTime));
    }

    public void addPrediction(long value) {
        predictions.add(value);
    }

    public void printStatistics() {

        // calculate net frequency by predicted characters
        for (Long predicted: predictions) {
            Integer val = freqTable.getOrDefault(predicted, 0);
            freqTable.put(predicted, val+1);
        }

        // calculate the percentages
        long total = predictions.size();
        for (Long key: freqTable.keySet()) {
            log.info(String.format("Fraction of %d is %f", key, (float)freqTable.get(key)/total));
        }
    }

    public long countFor(long index) {
        return freqTable.get(index);
    }

    public double predFractionFor(long index) {
        return (double) freqTable.get(index)/predictions.size();
    }
}
