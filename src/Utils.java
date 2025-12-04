import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

final public class Utils {
    public static ArrayList<String> getStrings(String fileName) {
        ArrayList<String> inputs = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(inputs::add);
        } catch (Exception e) {
            //System.out.println("Error: " + e.getMessage());
        }
        return inputs;
    }

    public static void generateImage(int[][] grid, int number, String path) {
        BufferedImage image = getBufferedImage(grid);

        try {
            if (!new File(path).exists()) {
                new File(path).mkdir();
            }
            File outputFile = new File(path + number + ".png");
            ImageIO.write(image, "png", outputFile);
            //System.out.println("Image saved to " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving the image: " + e.getMessage());
        }
    }

    private static BufferedImage getBufferedImage(int[][] grid) {
        int height = grid.length;
        int width = grid[0].length;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int color = (grid[i][j] == 0) ? 0xFFFFFF : 0x000000;
                image.setRGB(j, i, color);
            }
        }
        return image;
    }
}
