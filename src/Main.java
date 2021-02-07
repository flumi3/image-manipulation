import java.util.ArrayList;

public class Main {
    static String outputPath = "./output/";

    // Exercise 1
    private static void exerciseOne(ImageManipulation man) {
        ArrayList<Pixel> pixels = man.getPixels();
        pixels = man.removeBlueTones(pixels);
        man.createPPM(pixels, outputPath + "aufgabe_1.ppm");
    }

    // Exercise 2
    private static void exerciseTwo(ImageManipulation man) {
        ArrayList<Pixel> pixels = man.getPixels();
        pixels = man.increaseBlue(pixels);
        man.createPPM(pixels, outputPath + "aufgabe_2.ppm");
    }

    // Exercise 3
    private static void exerciseThree(ImageManipulation man) {
        ArrayList<Pixel> pixels = man.getPixels();
        pixels = man.increaseAll(pixels);
        man.createPPM(pixels, outputPath + "aufgabe_3.ppm");
    }

    // Exercise 4
    private static void exerciseFour(ImageManipulation man) {
        ArrayList<Pixel> pixels = man.getPixels();
        man.changeMaxColorValue(pixels,5);
        man.createPPM(pixels, outputPath + "aufgabe_4.ppm");
    }

    // Exercise 5
    private static void exerciseFive(ImageManipulation man) {
        ArrayList<Pixel> pixels = man.getPixels();
        ArrayList<Integer> values = man.convertToPGM(pixels);
        man.createPGM(values, outputPath + "aufgabe_5.pgm");
    }

    // Exercise 6
    private static void exerciseSix(ImageManipulation man) {
        man.createPBM(outputPath + "aufgabe_6.pgm");
    }

    public static void main(String[] args) {
        // create path to ppm file
        String cwd = System.getProperty("user.dir");  // get current working directory
        String path = cwd + "/src/tulip.ppm";

        // create new image manipulator for the given picture
        ImageManipulation man = new ImageManipulation(path);

        // execute the exercises
        exerciseOne(man);
        exerciseTwo(man);
        exerciseThree(man);
        exerciseFour(man);
        exerciseFive(man);
        exerciseSix(man);
    }
}
