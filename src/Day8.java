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
            return Objects.hash(x, y, z);
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

    private static class Connection {
        Box a, b;
        double distance;

        Connection(Box a, Box b, double distance) {
            this.a = a;
            this.b = b;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return a + " <-> " + b + " : " + distance;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Connection other = (Connection) obj;
            return (a.equals(other.a) && b.equals(other.b)) || (a.equals(other.b) && b.equals(other.a));
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

    private Connection[] computeAllConnections(Box[] boxes) {
        List<Connection> connections = new ArrayList<>();

        for (int i = 0; i < boxes.length; ++i) {
            for (int j = i + 1; j < boxes.length; ++j) {
                double dist = distance(boxes[i], boxes[j]);
                connections.add(new Connection(boxes[i], boxes[j], dist));
            }
        }

        return connections.toArray(new Connection[0]);
    }

    public void solve(List<String> input) {
        Box[] boxes = parse(input);
        Connection[] allConnections = computeAllConnections(boxes);
        Arrays.sort(allConnections, Comparator.comparingDouble(c -> c.distance));

        System.out.println("Day 8");
        System.out.println("Part 1: " + part1(allConnections, boxes, 1000));
        System.out.println("Part 2: " + part2());
    }

    private static double distance(Box a, Box b) {
        return Math.ceil(Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2) + Math.pow(a.z - b.z, 2)) * 1000.0) / 1000.0;
    }

    private boolean attemptConnection(Connection connection, List<Set<Box>> clusters) {
        Box a = connection.a;
        Box b = connection.b;

        Set<Box> setA = null, setB = null;
        for (Set<Box> cluster : clusters) {
            if (cluster.contains(a)) setA = cluster;
            if (cluster.contains(b)) setB = cluster;
        }
        
        if (setA != null && setB != null && setA != setB) {
            setA.addAll(setB);
            clusters.remove(setB);
            return true;
        }

        return false;
    }

    private int part1(Connection[] allConnections, Box[] boxes, int n) {
        int connectionsMade = 0, index = 0;
        List<Set<Box>> clusters = new ArrayList<>();
        for (Box box : boxes) {
            Set<Box> cluster = new HashSet<>();
            cluster.add(box);
            clusters.add(cluster);
        }

        while (index < allConnections.length && connectionsMade < n) {
            if (attemptConnection(allConnections[index++], clusters)) {
                ++connectionsMade;
            }
        }

        clusters.sort((a, b) -> Integer.compare(b.size(), a.size()));

        for (Set<Box> cluster : clusters) {
            System.out.println(cluster);
        }

        if (clusters.size() < 3) {
            throw new IllegalArgumentException("There must be at least 3 circuits");
        }

        return clusters.get(0).size() * clusters.get(1).size() * clusters.get(2).size();
    }

    private int part2() {
        return 0;
    }
}
