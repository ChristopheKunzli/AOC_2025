import java.util.List;

public class Day4 {
    private static final char PAPER_ROLL = '@';
    private static final char EMPTY = '.';

    private static class Pair {
        int row;
        int col;

        Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static char[][] parse(List<String> input) {
        int rows = input.size();
        int cols = input.getFirst().length();
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; ++i) {
            grid[i] = input.get(i).toCharArray();
        }
        return grid;
    }

    private static boolean isInBound(Pair p, char[][] grid) {
        return p.row >= 0 && p.row < grid.length && p.col >= 0 && p.col < grid[0].length;
    }

    private static int countRollsAround(Pair p, char[][] grid) {
        int count = 0;
        for (int drow = -1; drow <= 1; ++drow) {
            for (int dcol = -1; dcol <= 1; ++dcol) {
                if (drow == 0 && dcol == 0) continue;
                Pair neighbor = new Pair(p.row + drow, p.col + dcol);
                if (isInBound(neighbor, grid) && grid[neighbor.row][neighbor.col] == PAPER_ROLL) {
                    ++count;
                }
            }
        }
        return count;
    }

    private int removeRolls(char[][] grid) {
        int count = 0;

        List<Pair> toRemove = new java.util.ArrayList<>();

        for (int row = 0; row < grid.length; ++row) {
            for (int col = 0; col < grid[0].length; ++col) {
                Pair p = new Pair(row, col);
                if (grid[row][col] == PAPER_ROLL && countRollsAround(p, grid) < 4) {
                    ++count;
                    toRemove.add(p);
                }
            }
        }

        for (Pair p : toRemove) {
            grid[p.row][p.col] = EMPTY;
        }

        return count;
    }

    private int part1(char[][] grid) {
        return removeRolls(grid);
    }

    private int part2(char[][] grid) {
        int totalRemoved = 0;

        int removedThisRound;
        do {
            removedThisRound = removeRolls(grid);
            totalRemoved += removedThisRound;
        } while (removedThisRound > 0);

        return totalRemoved;
    }

    public void solve(List<String> input) {
        System.out.println("Day 4");
        System.out.println("Part 1: " + part1(parse(input)));
        System.out.println("Part 2: " + part2(parse(input)));
    }
}
