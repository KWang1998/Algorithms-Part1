/* *****************************************************************************
 *  Name:K
 *  Date:
 *  Description:Percolation
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] id;
    private int numberOfOpenSites;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufBackwash;
    private final int n2;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufBackwash = new WeightedQuickUnionUF(n * n + 2);
        id = new boolean[n * n];
        n2 = n;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                id[(i - 1) * n + j - 1] = false;
            }
        }
        numberOfOpenSites = 0;
    }              // create n-by-n grid, with all sites blocked

    public void open(int row, int col) {
        int n = n2;
        if (row > n || row < 1 || col > n || col < 1) {
            throw new IllegalArgumentException();
        }
        if (!id[n * (row - 1) + col - 1]) {
            id[n * (row - 1) + col - 1] = true;
            numberOfOpenSites += 1;
            if (row != 1 && id[n * (row - 2) + col - 1]) {
                uf.union(n * (row - 1) + col - 1, n * (row - 2) + col - 1);
                ufBackwash.union(n * (row - 1) + col - 1, n * (row - 2) + col - 1);
            }
            if (col != 1 && id[n * (row - 1) + col - 2]) {
                uf.union(n * (row - 1) + col - 1, n * (row - 1) + col - 2);
                ufBackwash.union(n * (row - 1) + col - 1, n * (row - 1) + col - 2);
            }
            if (row < n && id[n * row + col - 1]) {
                uf.union(n * (row - 1) + col - 1, n * row + col - 1);
                ufBackwash.union(n * (row - 1) + col - 1, n * row + col - 1);
            }
            if (col < n && id[n * (row - 1) + col]) {
                uf.union(n * (row - 1) + col - 1, n * (row - 1) + col);
                ufBackwash.union(n * (row - 1) + col - 1, n * (row - 1) + col);
            }
            if (row == 1) {
                uf.union(n * (row - 1) + col - 1, n * n + 1);
                ufBackwash.union(n * (row - 1) + col - 1, n * n + 1);
            }
            if (row == n) {
                uf.union(n * (row - 1) + col - 1, n * n);
            }
        }
    }    // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        int n = n2;
        if (row > n || row < 1 || col > n || col < 1) {
            throw new IllegalArgumentException();
        }
        return id[n * (row - 1) + col - 1];
    }  // is site (row, col) open?

    public boolean isFull(int row, int col) {
        int n = n2;
        if (row > n || row < 1 || col > n || col < 1) {
            throw new IllegalArgumentException();
        }
        return (id[n * (row - 1) + col - 1] && ufBackwash
                .connected((n * (row - 1) + col - 1), (n * n + 1)));
    } // is site (row, col) full?

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }     // number of open sites

    public boolean percolates() {
        int n = n2;
        return (uf.find(n * n) == uf.find(n * n + 1));
    }             // does the system percolate?

    public static void main(String[] args) {
        Percolation percolation = new Percolation(4);
        System.out.println(percolation.percolates());
        percolation.open(1, 1);
        percolation.open(2, 1);
        System.out.println(percolation.percolates());
        percolation.open(3, 1);
        percolation.open(3, 2);
        System.out.println(percolation.percolates());
        System.out.println(percolation.isFull(3, 2));
        percolation.open(4, 2);
        System.out.println(percolation.percolates());
    }  // test client (optional)
}


