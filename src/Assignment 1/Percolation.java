import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private WeightedQuickUnionUF uf1;
    private WeightedQuickUnionUF uf2;
    private boolean[] flatGrid;
    private int isOpened;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        isOpened = 0;
        flatGrid = new boolean[n * n + 2];
        uf1 = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n * n + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) { //row, col out of bound
            throw new IllegalArgumentException();
        }
        int index = flatten(row, col);
        if(!isOpen(row, col)) {
            flatGrid[index] = true;
            isOpened++;
            if (row == 1) {
                uf1.union(index, 0);
                uf2.union(index, 0);
            }
            if (row == n) {
                uf1.union(index, n * n + 1);
            }
            if (row > 1 && isOpen(row-1, col)) {
                uf1.union(flatten(row-1, col), index);
                uf2.union(flatten(row-1, col), index);
            }
            if (col > 1 && isOpen(row, col - 1)) {
                uf1.union(flatten(row, col - 1), index);
                uf2.union(flatten(row, col - 1), index);
            }
            if (row < n && isOpen(row+1, col)) {
                uf1.union(flatten(row+1,col),index);
                uf2.union(flatten(row+1,col),index);
            }
            if (col < n && isOpen(row, col+1)) {
                uf1.union(flatten(row, col+1), index);
                uf2.union(flatten(row, col+1), index);
            }
        }
    }

    private int flatten(int r, int c) {
        return ((r - 1) * n + c);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) { //row, col out of bound
            throw new IllegalArgumentException();
        }
        return flatGrid[flatten(row,col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) { //row, col out of bound
            throw new IllegalArgumentException();
        }
        return uf2.find(flatten(row,col)) == uf2.find(flatten(1,0));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return isOpened;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf1.find(flatten(1, 0)) == uf1.find(n * n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}