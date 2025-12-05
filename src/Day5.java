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

        static Range merge(Range r1, Range r2) {
            if (!r1.overlaps(r2)) {
                throw new IllegalArgumentException("Ranges do not overlap and cannot be merged.");
            }
            return new Range(Math.min(r1.start, r2.start), Math.max(r1.end, r2.end));
        }
    }

    private Set<Range> ranges = new HashSet<>();
    private final Set<Long> availableIds = new HashSet<>();

    private void parse(List<String> input) {
        int index = 0;

        while (!input.get(index).isEmpty()) {
            String[] parts = input.get(index++).split("-");
            ranges.add(new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
        }

        while (++index < input.size()) {
            availableIds.add(Long.parseLong(input.get(index)));
        }
    }

    private static Set<Range> mergeRanges(Set<Range> ranges) {
        Set<Range> mergedRanges = new HashSet<>();

        while (!ranges.isEmpty()) {
            Range current = ranges.iterator().next();
            ranges.remove(current);

            boolean mergeHappened;
            do {
                mergeHappened = false;
                Set<Range> toRemove = new HashSet<>();
                for (Range range : ranges) {
                    if (current.overlaps(range)) {
                        current = Range.merge(current, range);
                        toRemove.add(range);
                        mergeHappened = true;
                    }
                }
                ranges.removeAll(toRemove);
            } while (mergeHappened);

            mergedRanges.add(current);
        }
        return mergedRanges;
    }

    public void solve(java.util.List<String> input) {
        parse(input);
        ranges = mergeRanges(ranges);

        System.out.println("Day 5");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private int part1() {
        int count = 0;

        for (long id : availableIds) {
            if (ranges.stream().anyMatch(range -> range.isInRange(id))) {
                count++;
            }
        }

        return count;
    }

    private long part2() {
        long count = 0;

        for (Range range : ranges) {
            count += range.lengthInclusive();
        }

        return count;
    }
}
