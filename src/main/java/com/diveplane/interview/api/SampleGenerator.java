package com.diveplane.interview.api;

import java.util.Map;

public interface SampleGenerator<T> {
    /**
     * Run an internal experiment that executes random sampling for given iterations
     * @param nAttempts the number of queries to simulate
     */
    void getPredictions(int nAttempts);

    /**
     * Run a query to get one random draw
     * @return
     */
    T getPrediction();

    /**
     * Reset the internal probability state of the application core.
     * @param probStates
     */
    void resetProbabilityStates(Map<T, Double> probStates);
}
