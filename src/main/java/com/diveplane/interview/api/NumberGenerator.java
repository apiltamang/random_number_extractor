package com.diveplane.interview.api;

import java.util.Map;

public interface NumberGenerator {
    void getPredictions(int nAttempts);
    Integer getPrediction();
    void resetProbabilityStates(Map<Integer, Double> probStates);
}
