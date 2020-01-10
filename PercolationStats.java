/* *****************************************************************************
 *  Name: David Yu
 *  Date: Jan 2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] results;
    private int trail;

    public PercolationStats(int n, int trails) {
        if (trails <= 0 || n <= 0) {
            throw new IllegalArgumentException();
        }
        results = new double[trails];
        trail = trails;
        int total_sites = n * n;
        for (int i = 0; i < trails; i++) {
            Percolation test = new Percolation(n);
            int open_count = 0;
            boolean finished = false;
            while (!finished) {
                int rand_row = StdRandom.uniform(n) + 1;
                int rand_col = StdRandom.uniform(n) + 1;
                if (!test.isOpen(rand_row, rand_col)) {
                    test.open(rand_row, rand_col);
                    open_count += 1;
                }
                if (test.percolates()) {
                    finished = true;
                }
            }
            results[i] = ((double) open_count / total_sites);
        }
    }

    public double mean() {
        double result = 0;
        for (double i : results) {
            result += (i / trail);
        }
        return result;
    }

    public double stddev() {
        double mean = mean();
        double std = 0;
        for (double i : results) {
            std += Math.pow((i - mean), 2) / (trail - 1);
        }
        return Math.sqrt(std);
    }

    public double confidenceLo() {
        return (mean()) - ((1.96 * stddev()) / Math.sqrt(trail));
    }

    public double confidenceHi() {
        return (mean()) + ((1.96 * stddev()) / Math.sqrt(trail));
    }

    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(200, 100);
        System.out.println(test.mean());
        System.out.println(test.stddev());
        System.out.println(test.confidenceLo());
        System.out.println(test.confidenceHi());
    }
}
