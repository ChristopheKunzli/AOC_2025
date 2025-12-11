import java.util.*;

public class Day11 {

    Map<String, Set<String>> parse(List<String> input) {
        Map<String, Set<String>> devices = new HashMap<>();

        for (String line : input) {
            String[] parts = line.split(" ");
            String device = parts[0].replace(":", "");
            Set<String> connections = new HashSet<>(Arrays.asList(parts).subList(1, parts.length));
            devices.put(device, connections);
        }

        return devices;
    }

    public void solve(List<String> input) {
        Map<String, Set<String>> graph = parse(input);

        System.out.println("Day 11");
        System.out.println("Part 1: " + part1(graph));
        System.out.println("Part 2: " + part2(graph));
    }

    private long dfs(String node, String target, Map<String, Set<String>> graph, Map<String, Long> memo) {
        if (node.equals(target)) {
            return 1L;
        }
        if (memo.containsKey(node)) {
            return memo.get(node);
        }

        long count = 0L;

        for (String neighbor : graph.getOrDefault(node, Collections.emptySet())) {
            count += dfs(neighbor, target, graph, memo);
        }

        memo.put(node, count);
        return count;
    }

    private long part1(Map<String, Set<String>> graph) {
        return dfs("you", "out", graph, new HashMap<>());
    }

    private long part2(Map<String, Set<String>> graph) {
        long dac_out = dfs("dac", "out", graph, new HashMap<>());
        long fft_dac = dfs("fft", "dac", graph, new HashMap<>());
        long svr_fft = dfs("svr", "fft", graph, new HashMap<>());
        long fft_out = dfs("fft", "out", graph, new HashMap<>());
        long dac_fft = dfs("dac", "fft", graph, new HashMap<>());
        long svr_dac = dfs("svr", "dac", graph, new HashMap<>());

        return (svr_dac * dac_fft * fft_out) + (svr_fft * fft_dac * dac_out);
    }
}
