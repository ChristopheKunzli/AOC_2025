import java.util.ArrayList;
import java.util.List;

public class Day12 {
    private static class Present {
        int id;
        char[][] shape;

        int squaresOccupied = 0;

        Present(int id, char[][] shape) {
            this.id = id;
            this.shape = shape;

            for (char[] row : shape) for (char c : row) if (c == '#') ++squaresOccupied;
        }
    }

    private static class Region {
        int width, length;
        int[] presentsToFit;

        Region(int width, int length, int[] presentsToFit) {
            this.width = width;
            this.length = length;
            this.presentsToFit = presentsToFit;
        }
    }

    private Present[] parsePresents(List<String> input) {
        Present[] presents = new Present[6];

        int index = 0, resIndex = 0;
        for (int i = 0; i < 6; ++i) {
            ++index;//skip x: line
            char[][] shape = new char[3][3];
            for (int j = 0; j < 3; ++j, ++index) {
                shape[j] = input.get(index).toCharArray();
            }
            presents[resIndex++] = new Present(i, shape);
            ++index;//skip empty line
        }

        return presents;
    }

    private List<Region> parseRegions(List<String> input) {
        List<Region> regions = new ArrayList<>();

        for (int i = 30; i < input.size(); ++i) {
            String[] parts = input.get(i).split(": ");

            String[] dimensions = parts[0].split("x");
            int width = Integer.parseInt(dimensions[0]);
            int length = Integer.parseInt(dimensions[1]);

            String[] presentsToFit = parts[1].split(" ");
            int[] presents = new int[presentsToFit.length];
            for (int j = 0; j < presentsToFit.length; ++j) {
                presents[j] = Integer.parseInt(presentsToFit[j]);
            }

            regions.add(new Region(width, length, presents));
        }

        return regions;
    }

    public void solve(List<String> input) {
        Present[] presents = parsePresents(input);
        List<Region> regions = parseRegions(input);

        System.out.println("Day 12:");
        System.out.println("Part 1: " + part1(presents, regions));
        System.out.println("Part 2: " + part2());
    }

    private boolean canRegionFitPresents(Present[] presents, Region region) {
        int totalSquares = region.width * region.length;
        int occupiedSquares = 0;
        for (int i = 0; i < region.presentsToFit.length; ++i) {
            occupiedSquares += presents[i].squaresOccupied * region.presentsToFit[i];
        }
        return occupiedSquares <= totalSquares;
    }

    private int part1(Present[] presents, List<Region> regions) {
        int count = 0;

        for (Region region : regions) {
            if (canRegionFitPresents(presents, region)) {
                ++count;
            }
        }

        return count;
    }

    private int part2() {
        return 0;
    }
}
