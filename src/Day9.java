import java.util.ArrayList;
import java.util.List;

public class Day9 {

    private record Coordinate(long row, long col) {
        @Override
        public String toString() {
            return "(" + row + "," + col + ")";
        }

        public static long rectangleArea(Coordinate corner1, Coordinate corner2) {
            long length = Math.abs(corner1.row - corner2.row) + 1;
            long width = Math.abs(corner1.col - corner2.col) + 1;
            return length * width;
        }
    }

    private static List<Coordinate> parse(List<String> input) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (String line : input) {
            String[] parts = line.split(",");
            long row = Long.parseLong(parts[1]);
            long col = Long.parseLong(parts[0]);
            coordinates.add(new Coordinate(row, col));
        }
        return coordinates;
    }


    public void solve(List<String> input) {
        List<Coordinate> coordinates = parse(input);

        System.out.println("Day 9");
        System.out.println("Part 1: " + part1(coordinates));
        System.out.println("Part 2: " + part2());
    }

    private long part1(List<Coordinate> coordinates) {
        long maxArea = 0;

        for (int i = 0; i < coordinates.size(); ++i) {
            Coordinate corner1 = coordinates.get(i);
            for (int j = i + 1; j < coordinates.size(); ++j) {
                Coordinate corner2 = coordinates.get(j);
                maxArea = Math.max(Coordinate.rectangleArea(corner1, corner2), maxArea);
            }
        }

        return maxArea;
    }

    private int part2() {
        return 0;
    }
}
