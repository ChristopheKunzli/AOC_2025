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

    private static boolean isInBound(int row, int col, char[][] grid) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    private static int countRollsAround(int row, int col, char[][] grid) {
        int count = 0;
        for (int drow = -1; drow <= 1; ++drow) {
            for (int dcol = -1; dcol <= 1; ++dcol) {
                if (drow == 0 && dcol == 0) continue;
                int newRow = row + drow;
                int newCol = col + dcol;
                if (isInBound(newRow, newCol, grid) && grid[newRow][newCol] == PAPER_ROLL) {
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
                if (grid[row][col] == '@' && countRollsAround(row, col, grid) < 4) {
                    ++count;
                    toRemove.add(new Pair(row, col));
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
