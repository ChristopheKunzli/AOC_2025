import java.util.ArrayList;
import java.util.List;

public class Day9 {

    private record Coordinate(long row, long col) {}

    private record Rectangle(Coordinate c1, Coordinate c2) {
        boolean hasOverlapWithLine(Rectangle line) {
            return c1.col < line.c2.col && c2.col > line.c1.col && c1.row < line.c2.row && c2.row > line.c1.row;
        }

        long area() {
            long length = Math.abs(c1.row - c2.row) + 1;
            long width = Math.abs(c1.col - c2.col) + 1;
            return length * width;
        }

        static Rectangle fromCorners(Coordinate corner1, Coordinate corner2) {
            long minRow = Math.min(corner1.row, corner2.row);
            long maxRow = Math.max(corner1.row, corner2.row);
            long minCol = Math.min(corner1.col, corner2.col);
            long maxCol = Math.max(corner1.col, corner2.col);
            return new Rectangle(new Coordinate(minRow, minCol), new Coordinate(maxRow, maxCol));
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
        List<Coordinate> redTiles = parse(input);

        System.out.println("Day 9");
        System.out.println("Part 1: " + part1(redTiles));
        System.out.println("Part 2: " + part2(redTiles));
    }

    private long part1(List<Coordinate> redTiles) {
        long maxArea = 0;

        for (int i = 0; i < redTiles.size(); ++i) {
            Coordinate corner1 = redTiles.get(i);
            for (int j = i + 1; j < redTiles.size(); ++j) {
                Coordinate corner2 = redTiles.get(j);
                maxArea = Math.max(new Rectangle(corner1, corner2).area(), maxArea);
            }
        }

        return maxArea;
    }

    private long part2(List<Coordinate> redTiles) {
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < redTiles.size() - 1; ++i) {
            for (int j = i + 1; j < redTiles.size(); ++j) {
                rectangles.add(Rectangle.fromCorners(redTiles.get(i), redTiles.get(j)));
            }
        }

        List<Rectangle> lines = new ArrayList<>();

        for (int i = 0; i < redTiles.size() - 1; ++i) {
            Coordinate start = redTiles.get(i);
            Coordinate end = redTiles.get((i + 1));
            lines.add(Rectangle.fromCorners(start, end));
        }
        lines.add(Rectangle.fromCorners(redTiles.getLast(), redTiles.getFirst()));

        long maxArea = 0;

        for (Rectangle r : rectangles) {
            boolean overlap = false;

            for (Rectangle line : lines) {
                if (r.hasOverlapWithLine(line)) {
                    overlap = true;
                    break;
                }
            }

            if (!overlap) {
                maxArea = Math.max(maxArea, r.area());
            }
        }

        return maxArea;
    }
}
