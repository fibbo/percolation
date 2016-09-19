import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{
    // Array with all the percolation fractions
    private double[] percThresholdArray;
    // Number of trials
    private int mTrials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        this.mTrials = trials;
        percThresholdArray = new double[mTrials];
        for (int i = 0; i < mTrials; i++)
        {
            percThresholdArray[i] = singleTrial(n);
        }
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(percThresholdArray);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(percThresholdArray);
    }

    // low endpoint of 95% confidence interval    
    public double confidenceLo()
    {

        return mean() - 1.96 * stddev() / Math.sqrt(mTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {

        return mean() + 1.96 * stddev() / Math.sqrt(mTrials);
    }

    /**
     * Running a single trial
     * @param n
     * @return percolation threshold
     */
    private double singleTrial(int n)
    {
        Percolation perc = new Percolation(n);
        int openSites = 0;
        while (!perc.percolates())
        {
            int x = StdRandom.uniform(1, n + 1);
            int y = StdRandom.uniform(1, n + 1);
            boolean isOpen = true;
            while (isOpen)
            {
                x = StdRandom.uniform(1, n + 1);
                y = StdRandom.uniform(1, n + 1);
                isOpen = perc.isOpen(x, y);
            }
            perc.open(x, y);
            openSites++;
        }
        return (double) openSites / (n * n);
    }

    // test client (described below)
    public static void main(String[] args)
    {
        if (Integer.parseInt(args[1]) <= 0 || Integer.parseInt(args[0]) <= 0)
        {
            throw new IllegalArgumentException();
        }
        PercolationStats pstats = new PercolationStats(
                Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("Mean\t\t\t = %.8f\n", pstats.mean());
        StdOut.printf("Stddev\t\t\t = %.8f\n", pstats.stddev());
        StdOut.printf("95%% confidence interval\t = %.8f, %.8f",
                pstats.confidenceLo(), pstats.confidenceHi());
    }
}