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

    private int dfs(String node, Map<String, Set<String>> graph) {
        if (node.equals("out")) {
            return 1;
        }

        int count = 0;

        for (String neighbor : graph.getOrDefault(node, Collections.emptySet())) {
            count += dfs(neighbor, graph);
        }

        return count;
    }

    private int part1(Map<String, Set<String>> graph) {
        return dfs("you", graph);
    }

    private int part2(Map<String, Set<String>> graph) {
        return 0;
    }
}
