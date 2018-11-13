package com.diveplane.interview;

import com.diveplane.interview.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class contains the core algorithm for extracting the entries from the specified probability set.
 */
public class CoreProcessor {
    Map<Double, Integer> cumProbBucket;
    Random randomGenerator;
    private StatsCollector statsCollector;
    private Logger log;
    final Boolean invertedMapInitialized;

    public CoreProcessor(Map<Integer, Double> inputData, StatsCollector statsCollector, Boolean debug) {
        this.randomGenerator = new Random(1234L);
        this.statsCollector = statsCollector;
        log = new Logger(debug);
        initInverseCumulativeProbBuckets(inputData);
        invertedMapInitialized = true;
        log.debug("Successfully processed input data to create an inverse cumulative probability bracket.");
        log.debug("The inverse cumulative prob. buckets created is: "+cumProbBucket.toString());
    }

    /**
     Create an intermediate bucket map to store cumulative probabilities against index. What it does is
     adds the prob. for each number cumulatively, and stores that as an inverted map. Best exemplified
     as follows:
     if inputData is a map as follows: {1 -> 0.25, 4 -> 0.5, 2 -> 0.10, 7 -> 0.15}, then we get
     an inverted map as follows: {0.25 -> 1, 0.75 -> 4, 0.80 -> 2, 1.0 -> 7}
     */
    protected void initInverseCumulativeProbBuckets(Map<Integer, Double> inputData) {
        /*  Note: The usage of a 'LinkedHashMap', as opposed to a regular 'HashMap' is
         *  critical, because this guarantees a linearly growing probability bucket pools.
         */
        cumProbBucket = new LinkedHashMap<>();
        Double cumRunningProb = 0.0d;

        for (Integer ii: inputData.keySet()) {
            Double prob = inputData.get(ii);
            if (prob != null && prob > 0.0d) {
                cumRunningProb += prob;
                cumProbBucket.put(cumRunningProb, ii);
                log.debug(String.format("Number: %d, Upper cumulative prob. bound evaluated: %f", ii, cumRunningProb));
            } else {
                log.warn(String.format("Numbder: %d needs to have probability greater than zero. Got prob: %f", ii, prob));
            }

        }

        // sanity check ...
        if (!cumRunningProb.equals(1.0d)) {
            throw new RuntimeException("ERROR: probabilities did not sum to 1.0. Aborting operation.");
        }
    }

    public long[] getPredictions(int nAttempts) {
        if(!invertedMapInitialized)
            throw new RuntimeException("Cannot proceed becase the inverted map was not initialized!");

        double nextRandom;
        long[] predicted = new long[nAttempts];
        statsCollector.startTimer();

        for (int i = 0; i<nAttempts; i++) {
            nextRandom = randomGenerator.nextDouble();
            predicted[i] = getBucket(nextRandom);
        }
        statsCollector.stopTimer();
        return predicted;
    }

    public Integer getBucket(double nextRandom) {
        Integer predicted;

        // iterate through the bucket
        for (Double upperBound: cumProbBucket.keySet()) {
            if (nextRandom < upperBound) {
                predicted = cumProbBucket.get(upperBound);
                statsCollector.addPrediction(predicted);
                return predicted;
            }
        }
        throw new RuntimeException("ERROR: the predicted random number should have fallen in a given bucket. Indicative of an algorithm error.");
    }
}
