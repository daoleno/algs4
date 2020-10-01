import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] thresholds;
    private int t;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        t = trials;
        thresholds = new double[t + 1];
        for (int i = 1; i <= t; i++) {
            thresholds[i] = threshold(n);
        }
    }

    private double threshold(int n) {
        Percolation perc = new Percolation(n);
        while (!perc.percolates()) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            if (!perc.isOpen(row, col)) {
                perc.open(row, col);
            }
        }
        return (double) perc.numberOfOpenSites() / (n * n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * Math.sqrt(stddev()) / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * Math.sqrt(stddev()) / Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);

        StdOut.printf("%-25s= %f\n", "mean", stats.mean());
        StdOut.printf("%-25s= %f\n", "stddev", stats.stddev());
        StdOut.printf("%-25s= [%f, %f]\n", "95% confidence interval", stats.confidenceLo(), stats.confidenceHi());
    }
}