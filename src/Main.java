import java.io.File;
import java.util.ArrayList;

public class Main {
    private static final String inputPath = System.getProperty("user.dir") + "/input/";

    private static boolean create_output_folder(String path) {
        File f = new File(path);
        return f.mkdir();
    }

    public static void main(String[] args) {
        File f = new File(inputPath);
        String[] fileNames = f.list();

        if (fileNames != null) {
            for (String fileName: fileNames) {
                String outputPath = "./output/" + fileName;
                String filePath = inputPath + fileName;
                ImageManipulation m = new ImageManipulation(filePath);
                ArrayList<Pixel> pixels = m.getPixels();

                // Create modifications of the ppm picture
                ArrayList<Pixel> withoutBlue = m.removeBlueTones(pixels);
                ArrayList<Pixel> increasedBlue = m.increaseBlue(pixels);
                ArrayList<Pixel> increasedAll = m.increaseAll(pixels);
                ArrayList<Pixel> decreasedColor = m.changeMaxColorValue(pixels, 5);
                ArrayList<Integer> blackWhite = m.convertToPGM(pixels);

                // Create output folder
                if (!create_output_folder(outputPath)) {
                    System.out.println("Could not create output folder");
                }

                // Create output files
                m.createPPM(withoutBlue, outputPath + "/without_blue_tones.ppm");
                m.createPPM(increasedBlue, outputPath + "/increased_blue_tones.ppm");
                m.createPPM(increasedAll, outputPath + "/increased_all_tones.ppm");
                m.createPPM(decreasedColor, outputPath + "/decreased_color_value.ppm");
                m.createPGM(blackWhite, outputPath + "/black_white.pgm");
            }
        }
    }
}
