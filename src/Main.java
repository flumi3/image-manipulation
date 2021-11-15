import java.io.File;
import java.util.ArrayList;

public class Main {
    private static final String inputPath = System.getProperty("user.dir") + "/input/";

    private static boolean create_output_folder(String path) {
        File f = new File(path);
        return f.mkdirs();
    }

    public static void main(String[] args) {
        File f = new File(inputPath);
        String[] fileNames = f.list();

        if (fileNames != null) {
            for (String fileName: fileNames) {
                String outputDir = "output/" + fileName.substring(0, fileName.lastIndexOf("."));
                String filePath = inputPath + fileName;

                // Create output folder
                if (!create_output_folder(outputDir)) {
                    System.out.println("Could not create output folder");
                }

                // Create modifications of the ppm picture
                ImageManipulation m = new ImageManipulation(filePath);
                ArrayList<Pixel> pixels = m.getPixels();
                ArrayList<Pixel> withoutBlue = m.removeBlueTones(pixels);
                m.createPPM(withoutBlue, outputDir + "/without_blue_tones.ppm");

                m = new ImageManipulation(filePath);
                pixels = m.getPixels();
                ArrayList<Pixel> increasedBlue = m.increaseBlue(pixels);
                m.createPPM(increasedBlue, outputDir + "/increased_blue_tones.ppm");

                m = new ImageManipulation(filePath);
                pixels = m.getPixels();
                ArrayList<Pixel> increasedAll = m.increaseAll(pixels);
                m.createPPM(increasedAll, outputDir + "/increased_all_tones.ppm");

                m = new ImageManipulation(filePath);
                pixels = m.getPixels();
                ArrayList<Integer> blackWhite = m.convertToPGM(pixels);
                m.createPGM(blackWhite, outputDir + "/black_white.pgm");

                m = new ImageManipulation(filePath);
                pixels = m.getPixels();
                ArrayList<Pixel> decreasedColor = m.changeMaxColorValue(pixels, 5);
                m.createPPM(decreasedColor, outputDir + "/decreased_color_value.ppm");
            }
        }
    }
}
