# DAA Assignment 1 — Divide and Conquer

MergeSort, QuickSort and QuickSelect in Java 17 with metrics and benchmarks.

Author: Nurlan Yussupov, group SE-2526
Report: [REPORT.md](REPORT.md)

## Requirements

JDK 17+, Maven 3.8+

## Build

```bash
mvn clean package
```

## Run tests

```bash
mvn test
```

## Run benchmark

Produces `results.csv` in the project root:

```bash
mvn compile exec:java
```

Custom sizes and number of runs:

```bash
mvn compile exec:java -Dexec.args="results.csv 1000,10000,100000 5"
```

## Plots

`docs/time_vs_n.png`, `docs/depth_vs_n.png`, `docs/ratio_vs_n.png` — generated from `results.csv`.