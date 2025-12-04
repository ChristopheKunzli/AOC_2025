import java.util.Arrays;
import java.util.List;


public class Day2 {

    static class Range {
        long start;
        long end;

        Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return String.format("[%d-%d]", start, end);
        }
    }

    private List<Range> ranges;

    private static List<Range> parseRanges(List<String> input) {
        return input.stream().map(line -> {
            String[] parts = line.split("-");
            return new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
        }).toList();
    }

    public void solve(List<String> input) {
        ranges = parseRanges(Arrays.stream(input.getFirst().split(",")).toList());

        //System.out.println(ranges);

        System.out.println("Day 2");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private boolean isInvalidIdPart1(long id) {
        String idStr = String.valueOf(id);
        if (idStr.length() % 2 == 1) return false;

        String firstHalf = idStr.substring(0, idStr.length() / 2);
        String secondHalf = idStr.substring(idStr.length() / 2);

        return firstHalf.equals(secondHalf);
    }

    private boolean isInvalidIdPart2(long id) {
        String idStr = String.valueOf(id);

        int middle = idStr.length() / 2;
        for (int length = 1; length <= middle; ++length) {
            String pattern = idStr.substring(0, length);
            String repeated = pattern.repeat(idStr.length() / length);

            if (repeated.equals(idStr)) {
                return true;
            }
        }

        return false;
    }

    private long part1() {
        long sum = 0L;

        for (Range range : ranges) {
            for (long current = range.start; current <= range.end; current++) {
                if (isInvalidIdPart1(current)) {
                    sum += current;
                }
            }
        }

        return sum;
    }

    private long part2() {
        long sum = 0L;

        for (Range range : ranges) {
            for (long current = range.start; current <= range.end; current++) {
                if (isInvalidIdPart2(current)) {
                    //System.out.println(range + " adding " + current);
                    sum += current;
                }
            }
        }

        return sum;
    }
}

