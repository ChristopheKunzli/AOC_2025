import java.util.*;

public class Day7 {

    private static class Coordinate {
        int row;
        int col;

        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "(" + row + "," + col + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return row == that.row && col == that.col;
        }

        @Override
        public int hashCode() {
            return 31 * row + col;
        }
    }

    private int startRow, startCol;
    private char[][] grid;

    private static final char EMPTY = '.';
    private static final char SPLITTER = '^';
    private static final char START = 'S';
    private static final char BEAM = '|';

    public void solve(List<String> input) {
        parse(input);

        System.out.println("Day 7");
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
                int nextRow = current.row + 1;
                int nextCol = current.col;

                if (visited[nextRow][nextCol]) {
                    continue;
                }

                while (nextRow < grid.length && grid[nextRow][nextCol] == EMPTY) {
                    visited[nextRow][nextCol] = true;
                    nextRow++;
                }

                if (nextRow < grid.length && !visited[nextRow][nextCol] && grid[nextRow][nextCol] == SPLITTER) {
                    visited[nextRow][nextCol] = true;
                    ++count;
                    nextBeams.add(new Coordinate(nextRow, nextCol - 1));
                    nextBeams.add(new Coordinate(nextRow, nextCol + 1));
                }
            }

            beams = nextBeams;

        }

        return count;
    }

    private long part2() {
        Map<Coordinate, Long> beams = new HashMap<>();
        beams.put(new Coordinate(startRow, startCol), 1L);

        long paths = 0L;

        while (!beams.isEmpty()) {
            Map<Coordinate, Long> next = new HashMap<>();

            for (Map.Entry<Coordinate, Long> current : beams.entrySet()) {
                Coordinate cur = current.getKey();
                long count = current.getValue();

                int nextRow = cur.row + 1;
                int col = cur.col;

                while (nextRow < grid.length && grid[nextRow][col] == EMPTY) {
                    nextRow++;
                }

                if (nextRow >= grid.length) {
                    paths += count;
                } else if (grid[nextRow][col] == SPLITTER) {
                    int leftCol = col - 1;
                    int rightCol = col + 1;
                    Coordinate left = new Coordinate(nextRow, leftCol);
                    next.merge(left, count, Long::sum);
                    Coordinate right = new Coordinate(nextRow, rightCol);
                    next.merge(right, count, Long::sum);
                }
            }

            beams = next;
        }

        return paths;
    }

    private void parse(List<String> input) {
        grid = new char[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); ++i) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); ++j) {
                grid[i][j] = line.charAt(j);
                if (grid[i][j] == START) {
                    startRow = i;
                    startCol = j;
                }
            }
        }
    }
}
