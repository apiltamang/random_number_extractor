## Setup Guide:
This project was build on the skeleton maven archetype template (reference). As such, you will need a maven 3.x and Java 1.8 (or higher) to compile the project. For quick reference:
- `mvn test` runs all the tests in the project.
- `mvn package` packages the source into an executable jar.
- `java -cp target/random_number_extractor-1.0-SNAPSHOT.jar com.diveplane.interview.App` executes a small stress test (million queries) hard-coded in the main method.

# API Guide:
The application is designed primarily around the interface `NumberGenerator`, to which an implementation `NumberGeneratorImpl` is provided that executes the stated goal of the application. It provides three APIs, `getPredictions(int nAttempts)`, `getPrediction()`, and `resetProbabilityStates(Map<Integer, Double> probstates)` which can be used to run a simulated **stress** test, a **single** query test, and a **probability reset** operation on the application, respectively.

A `StatsCollector` type is also provided that is used to log basic statistical measures of the run such as overall time taken, as well as post-mortem analysis of the frequency of samples generated.

A naive `Logger` implementation is provided which simply logs to the console. By default, `debug` statements are hidden, but the logger can be instantiated with `debug=true` in which cases various intermediate computations are also printed.

## Usage Guide:
A sample usage guide is provided in class `com.diveplane.interview.App`, which also happens to contains the `main` method of the application. The usage pattern is as follows:
```
// Use a map to instantiate initial probability entries
Map<Integer, Double> probEntries = new HashMap<>();
probEntries.put(1, 0.20)
probEntries.put(2, 0.80)

StatsCollector statsCollector = new StatsCollector();
boolean debugMode = false;
NumberGenerator generator = new NumberGeneratorImpl(probEntries, statsCollector, debugMode);
generator.getPredictions(1000000); // do a simulated million queries run.
statsCollector.printStatistics();
```
## Core Algorithm Guide:

The core algorithm for solving the problem is a two step process, and is completely implemented in the `NumberGeneratorImpl` class.

#### First Step: Preprocessing
The first step involves pre-processing the provided probability entries to create an *inverted cumulative probability index*. I've called it so, because it is an inverted map of the original form, and the keys to this map is the cumulative probability  of each entry from the source.

Effectively, if the original probability distribution looked as the follows:
```{1 -> 0.25, 4 -> 0.5, 2 -> 0.10, 7 -> 0.15}```
then the result of the pre-processing results in a map that looks as the follows:
```{0.25 -> 1, 0.75 -> 4, 0.85 -> 2, 1.0 -> 7}```

Notice how the keys to the latter are simply the cumulative probabilities as we iterate through the entries of the former! This pre-processing requires a one-time `O(n)` computational cost, where `n` is the number of entries in the original prob. distribution.

#### Second Step: Sampling from a random number.
The second step is actually the process of drawing a sample during query time. When an incoming request is made, a random value (between `0.0` and `1.0`) is sampled from Java's standard `Random` class. A (pseudo) random number implementation is usually available in all programming languages, so I did not provide my own implementation.

Given the random number, I simply find which bucket it falls in the inverted map. This is done by comparing the random number against the inverted map's keys, and getting the first key whose cum. probability is higher than the random value. For example, in the inverted map above, if the random number drawn is 0.56, I draw 4. If the random value is 0.91, I draw 7. Likewise, if the random value drawn is 0.20, I draw 1. Computationally, this is also an `O(n)` cost algorithm, making the entire application `O(n)` complex, where `n` is the total number of entries in the probability distribution.

## Test Suite
A number of tests have been included with this application in the `src/test` folder. In particular,
- `TestSuiteSimple` defines a suite of simple tests intended to test basic sanity and wiring mechanisms.
- `TestSuiteUpdate` provides a simple test to test an update of the internal probability distribution.
- `TestStrenuous` provides a suite of tests where an initial distribution of thousands and millions of entries are created, and run against simulated millions of queries. Some of the more extreme tests have been stubbed out because they do take from 10 minutes to a full hour to complete.

