
public class PercolationStats {

	double[] percThresholdArray;
	int m_trials;
	
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	this.m_trials = trials;
    	percThresholdArray = new double[m_trials];
    	for (int i = 0; i<m_trials; i++)
    	{
    		percThresholdArray[i] = singleTrial(n);
    	}
    }

    // sample mean of percolation threshold
    public double mean() {
    	double sum = 0;
        for (int i = 0; i < m_trials; i++)
        {
        	sum += percThresholdArray[i];
        }
        return sum/m_trials;
    } 

    
    // sample standard deviation of percolation threshold
    public double stddev() {
        double mean = mean();
        double sum = 0;
        for (int i = 0; i<m_trials; i++)
        {
        	sum += Math.sqrt((percThresholdArray[i]-mean));
        }
        
        return sum/(m_trials-1);
    } 

    public double confidenceLo() {
        // low endpoint of 95% confidence interval
        return 1.0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

        return 1.0;
    }
    
    private double singleTrial(int n)
    {
    	Percolation perc = new Percolation(n);
    	int openSites = 0;
    	while (!perc.percolates())
    	{
    		//open site
    	}
    	return openSites/(n*n);
    }

    // test client (described below)
    public static void main(String[] args) {
 
    }
}