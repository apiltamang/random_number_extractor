package com.diveplane.interview.impl;

import com.diveplane.interview.api.NumberGenerator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class contains the core algorithm for extracting the entries from the specified probability set.
 */
public class NumberGeneratorImpl implements NumberGenerator {
    Map<Double, Integer> cumProbBucket;
    Random randomGenerator;
    private StatsCollector statsCollector;
    private Logger log;
    final Boolean invertedMapInitialized;

    public NumberGeneratorImpl(Map<Integer, Double> inputData, StatsCollector statsCollector, Boolean debug) {
        this.randomGenerator = new Random(1234L);
        this.statsCollector = statsCollector;
        log = new Logger(debug);
        initInverseCumulativeProbBuckets(inputData);
        this.invertedMapInitialized = true;
        log.debug("Successfully processed input data to create an inverse cumulative probability bracket.");
        log.debug("The inverse cumulative prob. buckets created is: "+cumProbBucket.toString());
    }

    /**
     * Use this API to perform a prediction extraction experiment that loops
     * through the extraction process for the specified iterations. Statistics
     * are collected on the fly.
     * @param nAttempts The number of predictions to get
     * @return
     */
    @Override
    public void getPredictions(int nAttempts) {
        if(!invertedMapInitialized)
            throw new RuntimeException("Cannot proceed because the inverted map was not initialized!");
        statsCollector.startTimer();
        for (int i = 0; i<nAttempts; i++) {
            getPrediction();
            if ((i%1000000) == 0) {
                log.info(String.format("Passing %d queries on the experiment.", i));
            }
        }
        statsCollector.stopTimer();
    }

    /**
      * Use this API to get prediction for a single number extraction event.
      * The following is O(n) computationally, where 'n' is the number of unique
      * probability entries.
      *
      * @return
     */
    @Override
    public Integer getPrediction() {
        Integer predicted;
        double nextRandom = randomGenerator.nextDouble();
        // iterate through the buckets.
        for (Double upperBound: cumProbBucket.keySet()) {
            if (nextRandom < upperBound) {
                predicted = cumProbBucket.get(upperBound);
                statsCollector.addPrediction(predicted);
                return predicted;
            }
        }
        throw new RuntimeException("ERROR: the predicted random number should have fallen in a given bucket. Indicative of an algorithm error.");
    }

    /**
     * Use this API to reset the probability states.
     * @param probStates
     */
    @Override
    public void resetProbabilityStates(Map<Integer, Double> probStates) {
        this.randomGenerator = new Random(1234L);
        initInverseCumulativeProbBuckets(probStates);
    }

    /**
     * Create an intermediate bucket map to store cumulative probabilities against index. What it does is
     * adds the prob. for each number cumulatively, and stores that as an inverted map. Best exemplified
     * as follows:
     * if inputData is a map as follows: {1 -> 0.25, 4 -> 0.5, 2 -> 0.10, 7 -> 0.15}, then we get
     * an inverted map as follows: {0.25 -> 1, 0.75 -> 4, 0.85 -> 2, 1.0 -> 7}.
     *
     * This inverted data structure effectively allows me to reduce the computation to O(n) order, where
     * n is the number of unique entries in the probability distribution.
     */
    private void initInverseCumulativeProbBuckets(Map<Integer, Double> inputData) {
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
        if (!(Math.abs(cumRunningProb - 1.0d) < 1.e-8)) {
            throw new RuntimeException(String.format("ERROR: probabilities did not sum to 1.0. Aborting operation. Got: %.9f", cumRunningProb));
        }
    }
}
