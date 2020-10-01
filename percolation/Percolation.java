import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF uf;
    private int number;
    private int numberOfOpenSite;
    private int top;
    private int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        number = n;
        top = 0;
        bottom = n * n + 1;

        grid = new int[n + 1][n + 1];

        // 0 is blocked, 1 is opened
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = 0;
            }
        }

        uf = new WeightedQuickUnionUF(n * n + 2);

    }

    // mapping 2D coordinates to 1D coordinates
    private int xyTo1D(int row, int col) {
        return (row - 1) * number + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // check array bounds
        if (row <= 0 || row > number || col <= 0 || col > number) {
            throw new IllegalArgumentException();
        }

        // open site
        if (!isOpen(row, col)) {
            grid[row][col] = 1;
            numberOfOpenSite++;
        }

        // connect to virtual top site
        if (row == 1) {
            uf.union(top, xyTo1D(row, col));
        }

        // connect to virtual bottom site
        if (row == number) {
            uf.union(bottom, xyTo1D(row, col));
        } 
        
        // connect to all of its adjacent open sites
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(xyTo1D(row, col - 1), xyTo1D(row, col));
        }
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(xyTo1D(row - 1, col), xyTo1D(row, col));
        }
        if (row < number && isOpen(row + 1, col)) {
            uf.union(xyTo1D(row + 1, col), xyTo1D(row, col));
        }
        if (col < number && isOpen(row, col + 1)) {
            uf.union(xyTo1D(row, col + 1), xyTo1D(row, col));
        }

        // In order to fix backwash problem.
        // Check whether bottom site is full, if it is, connect to virtual bottom site. But it's too slow.
        // for (int i = 1; i <= number; i++) {
        //     if (isFull(number, i)) {
        //         uf.union(bottom, xyTo1D(number, i));
        //     }
        // }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > number || col <= 0 || col > number) {
            throw new IllegalArgumentException();
        }

        return grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > number || col <= 0 || col > number) {
            throw new IllegalArgumentException();
        }

        return uf.find(0) == uf.find(xyTo1D(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}