package com.diveplane.interview;

import com.diveplane.interview.impl.StatsCollector;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * These tests ensure that the application scales, i.e. probability entries with tens of
 * thousands of unique entries, followed by tens of millions of queries still run in
 * acceptable time, and without any internal breakdown.
 */
public class TestStrenuous extends TestCase {

    public void testStrenuousWithThousandEntriesAndMillionQueries() {
        System.out.println("Running a strenuous experiment with a 1000 probability entries and million simulated queries");

        // initialize a distribution with 1000 entries, with equi-partitioned probabilities
        Map<Integer, Double> equiPartionedEntries = new HashMap<>();

        int nEntries = 1000;
        int nAttempts = nEntries * 1000;
        double prob = 1.0d/nEntries;

        // populate the equi-partitioned prob. entries
        for(int i = 1; i<= nEntries; i++) {
            equiPartionedEntries.put(i, prob);
        }

        // Now using the TestHelper interface, execute load testing
        TestHelper helper = new TestHelper().withProbEntries(equiPartionedEntries).withStatsCollector(new StatsCollector());
        helper.loadTest(nAttempts);
    }

    public void testStrenuousWithThousandEntriesAndTenMillionQueries() {
        System.out.println("Running a strenuous experiment with a 1000 probability entries and ten million simulated queries");

        Map<Integer, Double> equiPartionedEntries = new HashMap<>();
        int nEntries = 1000;
        int nAttempts = nEntries * 10000;
        double prob = 1.0d/nEntries;

        // populate the equi-partitioned prob. entries
        for(int i = 1; i<= nEntries; i++) {
            equiPartionedEntries.put(i, prob);
        }

        // Now using the TestHelper interface, execute load testing
        TestHelper helper = new TestHelper().withProbEntries(equiPartionedEntries).withStatsCollector(new StatsCollector(false));
        helper.loadTest(nAttempts);
    }

    public void testStrenuousWithThousandEntriesAndHundredMillionQueries() {
        System.out.println("Running a strenuous experiment with a 1000 probability entries and ten million simulated queries");

        Map<Integer, Double> equiPartionedEntries = new HashMap<>();
        int nEntries = 1000;
        int nAttempts = nEntries * 100000;
        double prob = 1.0d/nEntries;

        // populate the equi-partitioned prob. entries
        for(int i = 1; i<= nEntries; i++) {
            equiPartionedEntries.put(i, prob);
        }

        // Now using the TestHelper interface, execute load testing
        TestHelper helper = new TestHelper().withProbEntries(equiPartionedEntries).withStatsCollector(new StatsCollector(false));
        helper.loadTest(nAttempts);
    }

    public void testStrenuousWithTenThousandEntriesAndHundredMillionQueries() {
        System.out.println("Running a strenuous experiment with a 1000 probability entries and ten million simulated queries");

        Map<Integer, Double> equiPartionedEntries = new HashMap<>();
        int nEntries = 10000;
        int nAttempts = nEntries * 10000;
        double prob = 1.0d/nEntries;

        // populate the equi-partitioned prob. entries
        for(int i = 1; i<= nEntries; i++) {
            equiPartionedEntries.put(i, prob);
        }

        // Now using the TestHelper interface, execute load testing
        TestHelper helper = new TestHelper().withProbEntries(equiPartionedEntries).withStatsCollector(new StatsCollector(false));
        helper.loadTest(nAttempts);
    }

    public void testStrenuousWithHundredThousandEntriesAndHundredMillionQueries() {
        System.out.println("Running a strenuous experiment with a 1000 probability entries and ten million simulated queries");

        Map<Integer, Double> equiPartionedEntries = new HashMap<>();
        int nEntries = 100000;
        int nAttempts = nEntries * 1000;
        double prob = 1.0d/nEntries;

        // populate the equi-partitioned prob. entries
        for(int i = 1; i<= nEntries; i++) {
            equiPartionedEntries.put(i, prob);
        }

        // Now using the TestHelper interface, execute load testing
        TestHelper helper = new TestHelper().withProbEntries(equiPartionedEntries).withStatsCollector(new StatsCollector(false));
        helper.loadTest(nAttempts);
    }

    // skipped this test because it takes about 5 minutes to do this.
    public void _testStrenuousWithTenThousandEntriesAndBillionQueries() {
        System.out.println("Running a strenuous experiment with a 10000 probability entries and ten million simulated queries");

        Map<Integer, Double> equiPartionedEntries = new HashMap<>();
        int nEntries = 10000;
        int nAttempts = nEntries * 100000;
        double prob = 1.0d/nEntries;

        // populate the equi-partitioned prob. entries
        for(int i = 1; i<= nEntries; i++) {
            equiPartionedEntries.put(i, prob);
        }

        // Now using the TestHelper interface, execute load testing
        TestHelper helper = new TestHelper().withProbEntries(equiPartionedEntries).withStatsCollector(new StatsCollector(false));
        helper.loadTest(nAttempts);
    }

}
