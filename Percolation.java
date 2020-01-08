/* *****************************************************************************
 *  Name: David
 *  Date: 2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    public int size;
    public WeightedQuickUnionUF states;
    public int[] grid;
    public int[][] neighbors = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        size = n;
        states = new WeightedQuickUnionUF(n * n + 2);
        grid = new int[n * n];
        for (int i = 1; i <= n; i++) {
            states.union(0, i);
            states.union(n * n + 1, (n * n + 1 - i));
        }
        for (int i = 0; i < n * n; i++) {
            grid[i] = 0;
        }
    }

    private boolean is_valid_coor(int row, int col) {
        return (row > 0 && row <= size && col > 0 && col <= size);
    }

    private int to_index(int r, int c) {
        return (r - 1) * size + c;
    }

    private int get_neighbors_x(int row, int count) {
        return row + neighbors[count][0];
    }

    private int get_neighbors_y(int col, int count) {
        return col + neighbors[count][1];
    }

    private boolean is_open(int index) {
        return grid[index - 1] == 1;
    }

    public void open(int row, int col) {
        if (!is_valid_coor(row, col)) {
            throw new IllegalArgumentException();
        }
        int ind = to_index(row, col);
        if (!is_open(ind)) {
            grid[ind - 1] = 1;
            for (int i = 0; i < 4; i++) {
                int neighbor_x = get_neighbors_x(row, i);
                int neighbor_y = get_neighbors_y(col, i);
                if (is_valid_coor(neighbor_x, neighbor_y)) {
                    int neighbor_ind = to_index(neighbor_x, neighbor_y);
                    if (is_open(neighbor_ind)) {
                        states.union(neighbor_ind, ind);
                    }

                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (is_valid_coor(row, col)) {
            return is_open(to_index(row, col));
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public boolean isFull(int row, int col) {
        if (!is_valid_coor(row, col)) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            if (row == 1) {
                return true;
            }
            else {
                return states.connected(0, to_index(row, col));
            }
        }
        else {
            return false;
        }
    }

    public int numberOfOpenSite() {
        int size_ind = size * size;
        int count = 0;
        for (int i = 1; i <= size_ind; i++) {
            if (is_open(i)) {
                count += 1;
            }
        }
        return count;
    }

    public boolean percolates() {
        if (size == 1) {
            return is_open(0);
        }
        else {
            return states.connected(0, size * size + 1);
        }
    }

    public static void main(String[] args) {
        Percolation a = new Percolation(3);
        System.out.println(a.size);
        System.out.println(a.numberOfOpenSite());
        System.out.println(a.percolates());
        a.open(1, 1);
        System.out.println(a.numberOfOpenSite());
        System.out.println(a.percolates());
        a.open(2, 1);
        System.out.println(a.numberOfOpenSite());
        System.out.println(a.percolates());
        a.open(3, 1);
        System.out.println(a.numberOfOpenSite());
        System.out.println(a.percolates());
    }
}
