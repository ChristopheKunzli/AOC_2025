import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java Main <filePath>");
            return;
        }
        List<String> filesPaths = Utils.getStrings(args[0]);

        int i = 0;
        new Day1().solve(Utils.getStrings(filesPaths.get(i++)));
        new Day2().solve(Utils.getStrings(filesPaths.get(i++)));
        new Day3().solve(Utils.getStrings(filesPaths.get(i++)));
        new Day4().solve(Utils.getStrings(filesPaths.get(i++)));
        new Day5().solve(Utils.getStrings(filesPaths.get(i++)));
        new Day6().solve(Utils.getStrings(filesPaths.get(i++)));
        new Day7().solve(Utils.getStrings(filesPaths.get(i++)));
        new Day8().solve(Utils.getStrings(filesPaths.get(i++)));
        new Day9().solve(Utils.getStrings(filesPaths.get(i++)));
        new Day10().solve(Utils.getStrings(filesPaths.get(i++)));
        new Day11().solve(Utils.getStrings(filesPaths.get(i++)));
        new Day12().solve(Utils.getStrings(filesPaths.get(i++)));
    }
}