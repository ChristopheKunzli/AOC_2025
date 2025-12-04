import java.util.ArrayList;
import java.util.List;

public class Day1 {

    record Instruction(Direction direction, int steps) {

        public String toString() {
            return direction + " " + steps;
        }
    }

    private final List<Instruction> instructions = new ArrayList<>();
    private static final int N = 100;//amount of numbers from 0 to n - 1

    public void solve(List<String> input) {
        parse(input);
        System.out.println("Day 1");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private void parse(List<String> lines) {
        for (String line : lines) {
            instructions.add(new Instruction(line.charAt(0) == 'L' ? Direction.LEFT : Direction.RIGHT, Integer.parseInt(line.substring(1))));
        }
    }

    private int part1() {
        int dial = 50, sum = 0;

        for (Instruction instruction : instructions) {
            boolean isLeft = instruction.direction == Direction.LEFT;
            int steps = instruction.steps * (isLeft ? -1 : 1) % N;
            dial = (dial + steps + N) % N;
            if (dial == 0) ++sum;
        }

        return sum;
    }

    private int part2() {
        int dial = 50, sum = 0;

        for (Instruction instruction : instructions) {
            boolean atZero = dial == 0;

            if (instruction.direction == Direction.LEFT) {
                dial = dial - instruction.steps;
            } else {
                dial = dial + instruction.steps;
            }

            if (dial >= N) {
                sum += dial / N;
            } else if (dial <= 0) {
                sum += Math.abs(dial / 100) + 1;
                if (atZero) sum -= 1;
            }

            dial = ((dial % N) + N) % N;
        }

        return sum;
    }
}
//6548 too high
//6498