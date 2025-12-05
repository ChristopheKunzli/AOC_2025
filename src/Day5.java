import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day5 {

    private static class Range {
        long start;
        long end;

        Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        boolean isInRange(long value) {
            return value >= start && value <= end;
        }

        long lengthInclusive() {
            return end - start + 1;
        }

        boolean overlaps(Range other) {
            return this.start <= other.end && other.start <= this.end;
        }

        Range merge(Range other) {
            return new Range(Math.min(this.start, other.start), Math.max(this.end, other.end));
        }
    }

    Set<Range> ranges = new HashSet<>();
    Set<Long> availableIds = new HashSet<>();

    private void parse(List<String> input) {
        int index = 0;

        while (!input.get(index).isEmpty()) {
            String[] parts = input.get(index).split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);
            ranges.add(new Range(start, end));
            ++index;
        }

        ++index;

        while (index < input.size()) {
            availableIds.add(Long.parseLong(input.get(index)));
            ++index;
        }
    }

    public void solve(java.util.List<String> input) {
        parse(input);

        System.out.println("Day 5");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private int part1() {
        int count = 0;

        for (long id : availableIds) {
            for (Range range : ranges) {
                if (range.isInRange(id)) {
                    ++count;
                    break;
                }
            }
        }

        return count;
    }

    private long part2() {
        long count = 0;

        while (!ranges.isEmpty()) {
            Range current = ranges.iterator().next();
            ranges.remove(current);

            boolean merged;
            do {
                merged = false;
                Set<Range> toRemove = new HashSet<>();
                for (Range range : ranges) {
                    if (current.overlaps(range)) {
                        current = current.merge(range);
                        toRemove.add(range);
                        merged = true;
                    }
                }
                ranges.removeAll(toRemove);
            } while (merged);

            count += current.lengthInclusive();
        }
        return count;
    }
}
