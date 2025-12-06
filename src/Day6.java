import java.util.ArrayList;
import java.util.List;

public class Day6 {

    private final List<List<Integer>> numbers = new ArrayList<>();
    private final List<Character> operators = new ArrayList<>();


    private void parse(List<String> input) {
        for (int i = 0; i < input.size() - 1; ++i) {
            String[] parts = input.get(i).split(" ");
            List<Integer> num = new ArrayList<>();
            for (String part : parts) {
                if (part.isEmpty()) continue;
                num.add(Integer.parseInt(part));
            }
            numbers.add(num);
        }

        for (char c : input.getLast().toCharArray()) {
            if (c != ' ') operators.add(c);
        }
    }

    public void solve(List<String> input) {
        parse(input);

        System.out.println("Day 6");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2(input));
    }

    private long part1() {
        long sum = 0;
        for (int i = 0; i < operators.size(); ++i) {
            char operator = operators.get(i);
            long result = operator == '+' ? 0 : 1;

            for (List<Integer> number : numbers) {
                int val = number.get(i);
                if (operator == '+') {
                    result += val;
                } else if (operator == '*') {
                    result *= val;
                } else {
                    throw new RuntimeException("unknown operator");
                }
            }

            sum += result;
        }

        return sum;
    }

    private long part2(List<String> input) {
        long sum = 0;

        int rowLength = input.getFirst().length();
        int colLength = input.size();

        List<Integer> numbers = new ArrayList<>();

        for (int i = rowLength - 1; i >= 0; --i) {
            StringBuilder number = new StringBuilder();
            for (int j = 0; j < colLength - 1; ++j) {
                char c = input.get(j).charAt(i);
                if (c != ' ') number.append(c);
            }
            numbers.add(Integer.parseInt(number.toString()));

            char maybeOperator = input.get(colLength - 1).charAt(i);
            if (maybeOperator == '+') {
                long result = 0;
                for (int num : numbers) {
                    result += num;
                }
                sum += result;
                numbers.clear();
                --i;
            } else if (maybeOperator == '*') {
                long result = 1;
                for (int num : numbers) {
                    result *= num;
                }
                sum += result;
                numbers.clear();
                --i;
            }
        }

        return sum;
    }
}
