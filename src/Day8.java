import java.util.*;

public class Day8 {

    private static class Box {
        int x, y, z;

        Box(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public int hashCode() {
            return x * 31 * 31 + y * 31 + z;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Box other = (Box) obj;
            return x == other.x && y == other.y && z == other.z;
        }

        @Override
        public String toString() {
            return "Box(" + x + "," + y + "," + z + ")";
        }
    }

    private static Box[] parse(List<String> input) {
        Box[] boxes = new Box[input.size()];

        for (int i = 0; i < boxes.length; ++i) {
            String[] parts = input.get(i).split(",");
            boxes[i] = new Box(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        }

        return boxes;
    }

    public void solve(java.util.List<String> input) {
        Box[] boxes = parse(input);

        System.out.println("Day 8");
        System.out.println("Part 1: " + part1(boxes));
        System.out.println("Part 2: " + part2());
    }

    private static double distance(Box a, Box b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2) + Math.pow(a.z - b.z, 2));
    }

    private static Box[] findClosesTwoUnconnectedBoxes(Box[] boxes, List<Set<Box>> circuits) {
        Box[] closest = new Box[2];
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < boxes.length; ++i) {
            for (int j = i + 1; j < boxes.length; ++j) {

                boolean connected = false;
                for (Set<Box> circuit : circuits) {
                    if (circuit.contains(boxes[i]) && circuit.contains(boxes[j])) {
                        connected = true;
                        break;
                    }
                }
                if (connected) {
                    continue;
                }

                double dist = distance(boxes[i], boxes[j]);
                if (dist < minDistance) {
                    minDistance = dist;
                    closest[0] = boxes[i];
                    closest[1] = boxes[j];
                }
            }
        }

        return closest;
    }

    private List<Set<Box>> connectClosesBoxes(int n, Box[] boxes) {
        List<Set<Box>> circuits = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            Box[] closest = findClosesTwoUnconnectedBoxes(boxes, circuits);

            //System.out.println(closest[0] + " <-> " + closest[1] + " : " + distance(closest[0], closest[1]));
            System.out.println(i + 1 + ": " + closest[0] + " <-> " + closest[1]);
            
            Set<Box> c1 = null;
            Set<Box> c2 = null;
            for (Set<Box> circuit : circuits) {
                if (circuit.contains(closest[0])) {
                    c1 = circuit;
                } else if (circuit.contains(closest[1])) {
                    c2 = circuit;
                }
            }

            if (c1 == null && c2 == null) {
                Set<Box> newCircuit = new HashSet<>();
                newCircuit.add(closest[0]);
                newCircuit.add(closest[1]);
                circuits.add(newCircuit);
            } else if (c1 != null && c2 == null) {
                c1.add(closest[1]);
            } else if (c1 == null && c2 != null) {
                c2.add(closest[0]);
            } else if (c1 != c2) {
                c1.addAll(c2);
                circuits.remove(c2);
            }
        }

        return circuits;
    }

    private long part1(Box[] boxes) {
        List<Set<Box>> circuits = connectClosesBoxes(10, boxes);

        long result = 1;
        circuits.sort((a, b) -> b.size() - a.size());
        if (circuits.size() < 3) {
            throw new RuntimeException("Less than 3 circuits found");
        }
        for (int i = 0; i < 3; ++i) {
            System.out.println(circuits.get(i));
            result *= circuits.get(i).size();
        }

        return result;
    }

    private int part2() {
        return 0;
    }
}
