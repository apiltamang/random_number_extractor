package com.diveplane.interview.impl;

import java.util.Map;

/**
 * The backing class for SampleGeneratorImpl. It utilizes a binary sort strategy for
 * sorting through the provided probability distribution entries, and does so
 * without any recursion. A recursive strategy quickly results in stackoverflow
 * because of creating potentially millions of stacks.
 *
 * Consequently, the algorithm is now O(log n) complex.
 */
public class BinarySorter {

    Double[] cumProb;
    Logger log;
    Object[] fastArr;

    public BinarySorter(Double[] cumProb) {
        this.cumProb = cumProb;
        log = new Logger(true);
    }

    public <T>BinarySorter(Map<Double, T> cumProbBracket) {
        log = new Logger(false);
        int keySize = cumProbBracket.size();
        cumProb = new Double[keySize];
        fastArr = new Object[keySize];
        int i = 0;
        double prev = 0.0;

        for (Double prob: cumProbBracket.keySet()) {
            if (prev >= prob) {
                throw new RuntimeException("Not a linearly increasing set. Error & Indicative of an Algorithm Error.");
            }
            cumProb[i] = prob;
            fastArr[i] = cumProbBracket.get(prob);
            i++;
            prev = prob;
        }
    }

    /**
     * Fetches the value given rand number.
     * @param rand
     * @return
     */
    public Object getFromFastArr(double rand) {
        int predictedIndex = evaluateBucket(rand);
        return fastArr[predictedIndex];
    }

    /**
     * Method evaluates the bucket that should be used
     * given the random number. It is a O(log n) complex
     * binary sort implementation.
     *
     * Furthermore, it utilizes an array to fetch data from
     * cumProb, so that this access is O(1), i.e. it is
     * extremely fast regardless of map entry size.
     * @param rand provided random number.
     * @return
     */
    public int evaluateBucket(double rand) {
        int high = cumProb.length;
        int low = 0;
        int mid = (high + low) / 2;

        if (rand < cumProb[0])
            return 0;

        while (mid <= cumProb.length && mid > 0) {
            if (rand > cumProb[mid]) {
                low = mid;
                mid = (low + high) / 2;
            }
            else if (rand <= cumProb[mid]) {
                high = mid;
                mid = (low + high) / 2;
            } else {
                log.warn("i should not be here...");
            }

            if ((high - low) == 1){
                int foundIndex;
                if (rand == cumProb[high])
                    foundIndex = high + 1; // <-- since the upper bound is exclusive in the partitioning
                else
                    foundIndex = high;
                return (foundIndex > cumProb.length - 1) ? cumProb.length - 1 : foundIndex;
            }
        }
        throw new RuntimeException("shouldnot be here..");
    }

    public void printStates(int low, int high, int mid) {
        String highValue = "";
        if (high == cumProb.length) {
            highValue = String.format("%f",cumProb[high - 1]);
        } else {
            highValue = String.format("%f", cumProb[high]);
        }

        String outpt = String.format("low: (%d, %f), high: (%d, %s), mid: (%d, %f)", low, cumProb[low], high, highValue, mid, cumProb[mid]);
        log.debug(outpt);
    }
}
