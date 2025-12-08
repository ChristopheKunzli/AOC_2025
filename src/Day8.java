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
            Box o = (Box) obj;
            return x == o.x && y == o.y && z == o.z;
        }

        @Override
        public String toString() {
            return "Box(" + x + "," + y + "," + z + ")";
        }
    }

    private static class Connection {
        Box a, b;
        long distSq;

        Connection(Box a, Box b) {
            this.a = a;
            this.b = b;
            this.distSq = distanceSq(a, b);
        }

        private static long distanceSq(Box a, Box b) {
            long dx = (long) a.x - b.x;
            long dy = (long) a.y - b.y;
            long dz = (long) a.z - b.z;
            return dx * dx + dy * dy + dz * dz;
        }

        @Override
        public String toString() {
            return a + " <-> " + b + " : " + distSq;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Connection other = (Connection) obj;
            return (a.equals(other.a) && b.equals(other.b)) || (a.equals(other.b) && b.equals(other.a));
        }

        // symmetric hashCode to match unordered equals
        @Override
        public int hashCode() {
            int h1 = a.hashCode();
            int h2 = b.hashCode();
            // XOR is symmetric (a^b == b^a). Alternatively use Objects.hash(min,max).
            return h1 ^ h2;
        }
    }

    private static Box[] parse(List<String> input) {
        List<Box> boxes = new ArrayList<>();
        for (String line : input) {
            if (line == null) continue;
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(",");
            if (parts.length != 3) throw new IllegalArgumentException("Bad input line: " + line);
            boxes.add(new Box(Integer.parseInt(parts[0].trim()),
                Integer.parseInt(parts[1].trim()),
                Integer.parseInt(parts[2].trim())));
        }
        return boxes.toArray(new Box[0]);
    }

    private Connection[] computeAllConnections(Box[] boxes) {
        List<Connection> connections = new ArrayList<>();

        for (int i = 0; i < boxes.length; ++i) {
            for (int j = i + 1; j < boxes.length; ++j) {
                connections.add(new Connection(boxes[i], boxes[j]));
            }
        }

        return connections.toArray(new Connection[0]);
    }

    public void solve(List<String> input) {
        Box[] boxes = parse(input);
        Connection[] allConnections = computeAllConnections(boxes);
        Arrays.sort(allConnections, Comparator.comparingLong(c -> c.distSq));

        System.out.println("Day 8");
        System.out.println("Part 1: " + part1(allConnections, boxes));
        System.out.println("Part 2: " + part2(allConnections, boxes));
    }

    private void attemptConnection(Connection connection, List<Set<Box>> clusters) {
        Box a = connection.a;
        Box b = connection.b;

        Set<Box> setA = null, setB = null;
        for (Set<Box> cluster : clusters) {
            if (setA == null && cluster.contains(a)) setA = cluster;
            if (setB == null && cluster.contains(b)) setB = cluster;
            if (setA != null && setB != null) break;
        }

        if (setA != null && setB != null && setA != setB) {
            setA.addAll(setB);
            clusters.remove(setB);
        }
    }

    private int part1(Connection[] allConnections, Box[] boxes) {
        List<Set<Box>> clusters = initializeClusters(boxes);

        for (int i = 0; i < 1000; ++i) {
            attemptConnection(allConnections[i], clusters);
        }

        clusters.sort((a, b) -> Integer.compare(b.size(), a.size()));

        return clusters.get(0).size() * clusters.get(1).size() * clusters.get(2).size();
    }

    private long part2(Connection[] allConnections, Box[] boxes) {
        List<Set<Box>> clusters = initializeClusters(boxes);

        long result = 0;

        for (Connection connection : allConnections) {
            attemptConnection(connection, clusters);
            if (clusters.size() == 1) {
                result = ((long) connection.a.x) * connection.b.x;
                break;
            }
        }

        for (Set<Box> cluster : clusters) {
            System.out.println(cluster);
        }

        return result;
    }

    private List<Set<Box>> initializeClusters(Box[] boxes) {
        List<Set<Box>> clusters = new ArrayList<>();
        for (Box box : boxes) {
            Set<Box> s = new HashSet<>();
            s.add(box);
            clusters.add(s);
        }
        return clusters;
    }

}
