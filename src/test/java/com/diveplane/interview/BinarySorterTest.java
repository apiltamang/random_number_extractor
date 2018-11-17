package com.diveplane.interview;

import com.diveplane.interview.impl.BinarySorter;
import junit.framework.TestCase;

public class BinarySorterTest extends TestCase {
    // indices                       0    1      2     3     4    5      6    7    8     9    10    11   12   13
    Double[] cumProb = new Double[]{0.1, 0.20, 0.25, 0.35, 0.4, 0.45, 0.55, 0.6, 0.7, 0.75, 0.85, 0.9, 0.98, 1.0};
    BinarySorter sorter = new BinarySorter(cumProb);

    public void testSimpleSorter() {
        double providedPsuedoRandom = 0.27;
        assert sorter.evaluateBucket(providedPsuedoRandom) == 3;
    }

    public void testSort2() {
        double providedRandom = 0.97;
        assert sorter.evaluateBucket(providedRandom) == 12;
    }

    public void testSort3() {
        double providedRandom = 0.99;
        assert sorter.evaluateBucket(providedRandom) == 13;
    }

    public void testSort4() {
        double providedRandom = 0.41;
        assert sorter.evaluateBucket(providedRandom) == 5;
    }

    public void testSort5() {
        double providedRandom = 0.09;
        assert sorter.evaluateBucket(providedRandom) == 0;
    }

    public void testSort6() {
        assert sorter.evaluateBucket(0.10) == 1;
    }

    public void testSort7() {
        assert sorter.evaluateBucket(0.249) == 2;
    }

    public void testSort8() {
        assert sorter.evaluateBucket(0.25) == 3;
    }

    public void testSort9() {
        assert sorter.evaluateBucket(0.98) == 13;
    }

    public void testSort10() {
        assert sorter.evaluateBucket(0.55) == 7;
    }

    public void testSort11() {
        assert sorter.evaluateBucket(0.85) == 11;
    }

    public void testSort12() {
        assert sorter.evaluateBucket(1.0) == 13;
    }
}
