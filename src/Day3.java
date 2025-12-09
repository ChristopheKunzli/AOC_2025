import java.util.List;

public class Day3 {

    private List<String> batteries;

    public void solve(List<String> input) {
        batteries = input;
        System.out.println("Day 3");
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    private int findJBestJoltagePart1(String battery) {
        int max = 0;

        for (int i = 0; i < battery.length(); ++i) {
            for (int j = i + 1; j < battery.length(); ++j) {
                int joltage = Integer.parseInt(battery.charAt(i) + "" + battery.charAt(j));
                max = Math.max(joltage, max);
            }
        }

        return max;
    }

    private long findJBestJoltagePart2(String battery) {
        int n = battery.length();

        StringBuilder sb = new StringBuilder(12);
        int start = 0;
        int remaining = 12;

        for (int pick = 0; pick < 12; ++pick) {
            int end = n - remaining;
            char best = '0';
            int bestIdx = start;
            for (int i = start; i <= end; ++i) {
                char c = battery.charAt(i);
                if (c > best) {
                    best = c;
                    bestIdx = i;
                    if (best == '9') break;
                }
            }
            sb.append(best);
            start = bestIdx + 1;
            --remaining;
        }

        return Long.parseLong(sb.toString());
    }

    private int part1() {
        int sum = 0;
        for (String battery : batteries) {
            sum += findJBestJoltagePart1(battery);
        }
        return sum;
    }

    private long part2() {
        long sum = 0;
        for (String battery : batteries) {
            sum += findJBestJoltagePart2(battery);
        }
        return sum;
    }
}