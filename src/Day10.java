import java.util.*;

public class Day10 {

    private record Machine(char[] indicatorLightDiagram,
                           List<List<Integer>> buttonWiringSchematics,
                           int[] joltageRequirements) {
        private Machine(char[] indicatorLightDiagram, List<List<Integer>> buttonWiringSchematics, int[] joltageRequirements) {
            this.indicatorLightDiagram = indicatorLightDiagram;
            this.buttonWiringSchematics = buttonWiringSchematics;
            this.joltageRequirements = joltageRequirements;

            //lights = new boolean[indicatorLightDiagram.length];
        }

        public boolean[] lightsFromIndicator() {
            boolean[] lights = new boolean[indicatorLightDiagram.length];
            for (int i = 0; i < indicatorLightDiagram.length; ++i) {
                lights[i] = indicatorLightDiagram[i] == ON;
            }
            return lights;
        }

        @Override
        public String toString() {
            return "Machine{" + "indicatorLightDiagram=[" + String.valueOf(indicatorLightDiagram) + "]" + ", buttonWiringSchematics=" + buttonWiringSchematics + ", joltageRequirements=" + java.util.Arrays.toString(joltageRequirements) + '}';
        }
    }

    private List<Machine> parse(List<String> input) {
        List<Machine> machines = new ArrayList<>();

        for (String line : input) {
            String[] parts = line.split(" ");

            char[] indicatorLightDiagram = parts[0].substring(1, parts[0].length() - 1).toCharArray();
            int[] joltageRequirements = getJoltageRequirements(parts[parts.length - 1]);
            List<List<Integer>> buttonWiringSchematics = getButtonWiringSchematics(parts);

            machines.add(new Machine(indicatorLightDiagram, buttonWiringSchematics, joltageRequirements));
        }

        return machines;
    }

    private static int[] getJoltageRequirements(String part) {
        String temp = part.substring(1, part.length() - 1);
        String[] joltageParts = temp.split(",");
        int[] joltageRequirements = new int[joltageParts.length];
        for (int i = 0; i < joltageParts.length; ++i) {
            joltageRequirements[i] = Integer.parseInt(joltageParts[i].trim());
        }
        return joltageRequirements;
    }

    private static List<List<Integer>> getButtonWiringSchematics(String[] parts) {
        List<List<Integer>> buttonWiringSchematics = new ArrayList<>();
        for (int i = 1; i < parts.length - 1; ++i) {
            String temp = parts[i].substring(1, parts[i].length() - 1);
            String[] wiringParts = temp.split(",");
            List<Integer> wiringList = new ArrayList<>();
            for (String number : wiringParts) {
                wiringList.add(Integer.parseInt(number.trim()));
            }
            buttonWiringSchematics.add(wiringList);
        }
        return buttonWiringSchematics;
    }

    public void solve(List<String> input) {
        List<Machine> machines = parse(input);

        System.out.println("Day 10");
        System.out.println("Part 1: " + part1(machines));
        System.out.println("Part 2: " + part2());
    }

    private static final char ON = '#';
    private static final char OFF = '.';

    private boolean isOn(final char[] indicatorLightDiagram, final boolean[] lights) {
        for (int i = 0; i < lights.length; ++i) {
            if ((indicatorLightDiagram[i] == ON && !lights[i]) || (indicatorLightDiagram[i] == OFF && lights[i])) {
                return false;
            }
        }
        return true;
    }

    private int findShortestPath(Machine machine) {
        Queue<boolean[]> queue = new LinkedList<>();

        boolean[] lights = new boolean[machine.indicatorLightDiagram.length];
        queue.add(lights);

        int count = 0;
        while (!queue.isEmpty()) {
            Queue<boolean[]> next = new LinkedList<>();

            while (!queue.isEmpty()) {
                boolean[] currentLights = queue.poll();

                if (isOn(machine.indicatorLightDiagram, currentLights)) {
                    return count;
                }

                for (List<Integer> buttonWiring : machine.buttonWiringSchematics) {
                    boolean[] newLights = Arrays.copyOf(currentLights, currentLights.length);
                    for (int index : buttonWiring) {
                        newLights[index] = !newLights[index];
                    }
                    next.add(newLights);
                }
            }

            queue = next;
            ++count;
        }

        return -1;
    }

    private int explore(Machine machine, boolean[] lights, int index, int count) {
        if (index >= machine.buttonWiringSchematics.size()) {
            return isOn(machine.indicatorLightDiagram, lights) ? count : Integer.MAX_VALUE;
        }

        int minPresses = explore(machine, lights, index + 1, count);

        for (int lightIndex : machine.buttonWiringSchematics.get(index)) {
            lights[lightIndex] = !lights[lightIndex];
        }

        minPresses = Math.min(minPresses, explore(machine, lights, index + 1, count + 1));

        for (int lightIndex : machine.buttonWiringSchematics.get(index)) {
            lights[lightIndex] = !lights[lightIndex];
        }

        return minPresses;
    }

    private int part1(List<Machine> machines) {
        int sum = 0;

        for (Machine machine : machines) {
            sum += explore(machine, new boolean[machine.indicatorLightDiagram.length], 0, 0);
        }

        return sum;
    }

    private int part2() {
        return 0;
    }
}
