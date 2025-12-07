import java.util.*;

public class Day7 {

    private static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }

    private int startRow, startCol;
    private char[][] grid;
    private char[][] copyGrid;

    private static final char EMPTY = '.';
    private static final char SPLITTER = '^';
    private static final char START = 'S';
    private static final char BEAM = '|';

    public void solve(List<String> input) {
        parse(input);

        System.out.println("Day 6");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private int part1() {
        Queue<Coordinate> beams = new LinkedList<>();
        boolean[][] visited = new boolean[grid.length][grid[0].length];

        beams.add(new Coordinate(startRow, startCol));
        visited[startRow][startCol] = true;

        int count = 0;

        while (!beams.isEmpty()) {
            Queue<Coordinate> nextBeams = new LinkedList<>();

            for (Coordinate current : beams) {
                int nextRow = current.x + 1;
                int nextCol = current.y;

                if (visited[nextRow][nextCol]) {
                    continue;
                }

                while (nextRow < grid.length && grid[nextRow][nextCol] == EMPTY) {
                    visited[nextRow][nextCol] = true;
                    copyGrid[nextRow][nextCol] = BEAM;
                    nextRow++;
                }

                if (nextRow < grid.length && !visited[nextRow][nextCol] && grid[nextRow][nextCol] == SPLITTER) {
                    visited[nextRow][nextCol] = true;
                    copyGrid[nextRow][nextCol + 1] = BEAM;
                    copyGrid[nextRow][nextCol - 1] = BEAM;
                    ++count;
                    nextBeams.add(new Coordinate(nextRow, nextCol - 1));
                    nextBeams.add(new Coordinate(nextRow, nextCol + 1));
                }
            }

            beams = nextBeams;

        }

        return count;
    }

    private int part2() {
        for (char[] row : copyGrid) {
            for (char col : row) {
                System.out.print(col);
            }
            System.out.println();
        }

        return 0;
    }

    private void parse(List<String> input) {
        grid = new char[input.size()][input.get(0).length()];
        copyGrid = new char[grid.length][grid[0].length];

        for (int i = 0; i < input.size(); ++i) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); ++j) {
                grid[i][j] = line.charAt(j);
                copyGrid[i][j] = line.charAt(j);
                if (grid[i][j] == START) {
                    startRow = i;
                    startCol = j;
                }
            }
        }
    }


}
