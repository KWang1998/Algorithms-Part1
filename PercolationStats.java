/* *****************************************************************************
 *  Name:K
 *  Date:
 *  Description: Algorithms Part1 Week1
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trial;
    private double mean;
    private final double[] xTrials;
    private double stddev;

    public PercolationStats(int n, int trials) {
        trial = trials;
        int col, row;
        if (n <= 0) throw new IllegalArgumentException();
        if (trials <= 0) throw new IllegalArgumentException();
        xTrials = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation temp = new Percolation(n);
            while (!temp.percolates()) {
                col = StdRandom.uniform(n) + 1;
                row = StdRandom.uniform(n) + 1;
                temp.open(row, col);
            }
            xTrials[i] = (double) temp.numberOfOpenSites() / (n * n);
        }
        mean = StdStats.mean(xTrials);
        stddev = StdStats.stddev(xTrials);
    }    // perform trials independent experiments on an n-by-n grid

    public double mean() {
        return mean;
    }                          // sample mean of percolation threshold

    public double stddev() {
        return stddev;
    }                       // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return mean - 1.96 * stddev / Math.sqrt(trial);
    }                 // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean + 1.96 * stddev / Math.sqrt(trial);
    }                 // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(20, 100);
        System.out.println("mean=" + percolationStats.mean());
        System.out.println("stddev=" + percolationStats.stddev());
        System.out.println("95%% confidence Interval=" + percolationStats.confidenceLo() + "  "
                                   + percolationStats.confidenceHi());
    }       // test client (described below)
}
