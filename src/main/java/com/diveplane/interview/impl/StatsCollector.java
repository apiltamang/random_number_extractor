package com.diveplane.interview.impl;

import com.diveplane.interview.impl.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple Statistics collection class to collect the metrics on the fly,
 * and print it out at the end. Valuable for a proof of implementation.
 */
public class StatsCollector<T> {
    List<T> predictions = new ArrayList<>();
    Map<T, Integer> freqTable = new HashMap<>();
    Logger log;
    long startTime;
    long elapasedTime;
    boolean retainData = true;

    public StatsCollector() {
        log = new Logger();
    }

    public StatsCollector(boolean retainData) {
        /*
         * This will be set to false while load-testing for a billion requests,
         * because I ran out of heap space for that test.
         */

        this.retainData = retainData;
        this.log = new Logger();
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public void stopTimer() {
        elapasedTime = System.currentTimeMillis() - startTime;
        log.info(String.format("Elapsed time for experiments: %d milliseconds", elapasedTime));
    }

    public void addPrediction(T value) {
        if (retainData)
            predictions.add(value);
    }

    public void reset() {
        predictions.clear();
        freqTable.clear();
    }

    /**
     * Called at the end of a stress test experiment, this prints various statistics,
     * which could be used as sanity check for the validation of the algorithm.
     */
    public void printStatistics() {

        if (!retainData) {
            log.info("The intermediate data was not retained because of performance reasons.");
            return;
        }

        // calculate net frequency by predicted characters
        for (T predicted: predictions) {
            Integer val = freqTable.getOrDefault(predicted, 0);
            freqTable.put(predicted, val+1);
        }

        // calculate the percentages
        long total = predictions.size();
        for (T key: freqTable.keySet()) {
            log.info(String.format("Fraction of %s is %f", String.valueOf(key), (float)freqTable.get(key)/total));
        }
    }

    /**
     * Returns how many times a certain number was returned in a stress test experiment.
     * @param index
     * @return
     */
    public long countFor(T index) {
        return freqTable.get(index);
    }

    /**
     * Returns the fraction that a certain number was returned in a stress test experiment.
     * @param index
     * @return
     */
    public double predFractionFor(T index) {
        if (predictions.size() == 0)
            return 0;
        return (double) freqTable.get(index)/predictions.size();
    }
}
