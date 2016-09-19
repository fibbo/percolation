import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 
 * @author pgl
 *
 *         Percolation class implementing the API specified in the exercise
 *         requirements. To use this class create a Percolation object with int
 *         N as parameter as dimension of the N*N grid.
 */
public class Percolation
{

    // State grid
    private boolean[] grid;
    // Neighboring offsets to join adjacent open cells
    private int[] neighborOffsets =
    { -1, 0, 1, 0, 0, -1, 0, 1 };
    // Dimension of the grid
    private int dimension;
    // Total number of cells in the grid
    private int size;
    // Object identifier for the virtual top
    private int virtualTop;
    // Object identifier for the virtual bottom
    private int virtualBottom;
    // Main WQUUF object
    private WeightedQuickUnionUF qf;
    // Helper WQUUF object for the is full check
    private WeightedQuickUnionUF helper;

    /**
     * Constructor of the percolation class.
     * 
     * @param n
     *            grid length
     * @throws IllegalArgumentException
     *             when n <= 0
     */
    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException();
        }
        dimension = n;
        size = n * n;

        // initialize qf object with 2 virtual sites (index n*n and n*n + 1).
        qf = new WeightedQuickUnionUF(n * n + 2);
        helper = new WeightedQuickUnionUF(n * n + 1);
        virtualTop = n * n;
        virtualBottom = n * n + 1;

        grid = new boolean[size];
        for (int i = 0; i < size; i++)
        {
            grid[i] = false;
        }
    }

    /**
     * Convert i, j into a contiguous index
     * 
     * @param i
     * @param j
     * @return contiguous index
     */
    private int convertToIndex(int i, int j)
    {
        return (i - 1) * dimension + (j - 1);
    }

    /**
     * Opens the site i,j and joins it with open adjacent neighbors If the site
     * is in the top row perform union with virtual top for both the main and
     * helper QWUUF. For the bottom only join the main QWUUF with the the
     * virtual bottom.
     * 
     * @param index
     *            i, row of the site
     * @param index
     *            j, column of the site
     * 
     * 
     */
    public void open(int i, int j)
    {
        
        if (!validIndices(i, j))
        {
            throw new IndexOutOfBoundsException();
        }
        int index = convertToIndex(i, j);
        if (grid[index])
        {
            return;
        }
        grid[index] = true;
        // if i == 0 || i == n it's in the top/bottom row
        // so connect it with virtual top/bottom
        if (i == 1)
        {
            qf.union(index, virtualTop);
            helper.union(index, virtualTop);
        } 
        if (i == dimension)
        {
            qf.union(index, virtualBottom);
        }
        for (int k = 0; k < 4; k++)
        {
            int ni = i + neighborOffsets[2 * k];
            int nj = j + neighborOffsets[2 * k + 1];

            int p = convertToIndex(i, j);
            int q = convertToIndex(ni, nj);
            if ((p % dimension == 0 && p - q == 1)
                    || ((p % dimension == dimension - 1) && q - p == 1) || q < 0
                    || q >= size)
            {
                continue;
            }

            if (isOpen(ni, nj))
            {
                qf.union(p, q);
                helper.union(p, q);
            }
        }
    }

    /**
     * Check if the contiguous index is within the bounds
     * 
     * @param index
     * @return true if index is valid for the grid array, false otherwise
     */

    private boolean validIndices(int i, int j)
    {
        return (i > 0 && i <= dimension && j > 0 && j <= dimension);
    }
    /**
     * Check if site is open
     * 
     * @param i
     * @param j
     * @throws IndexOutOfBoundsException
     *             when index is not valid
     * @return
     */
    public boolean isOpen(int i, int j)
    {
        if (!validIndices(i, j))
        {
            throw new IndexOutOfBoundsException();
        }
        int index = convertToIndex(i, j);
        return grid[index];
    }

    /**
     * check if site is full
     * 
     * @param i
     * @param j
     * @throws IndexOutOfBoundsException
     *             when index is not valid
     * @return
     */
    public boolean isFull(int i, int j)
    {
        
        if (!validIndices(i, j))
        {
            throw new IndexOutOfBoundsException();
        }
        int index = convertToIndex(i, j);
        return qf.connected(index, virtualTop)
                && helper.connected(index, virtualTop);
    }

    /**
     * Is the grid percolating or not
     * 
     * @return true when grid percolates
     */
    public boolean percolates()
    {
        return qf.connected(size, size + 1);
    }

    public static void main(String[] args)
    {
        Percolation perc = new Percolation(Integer.parseInt(args[0]));

        perc.open(1, 2);
        StdOut.println(perc.percolates());
        perc.open(2, 2);
        StdOut.println(perc.percolates());
        perc.open(3, 2);
        StdOut.println(perc.percolates());
        perc.open(4, 2);
        StdOut.println(perc.percolates());
        perc.open(5, 2);
        StdOut.println(perc.percolates());
    }
}