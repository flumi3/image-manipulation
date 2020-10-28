package project_1;

import java.util.ArrayList;

public class Main {

    // Exercise 1
    private static void exerciseOne(ImageManipulation man) {
        ArrayList<Pixel> pixels = man.getPixels();
        pixels = man.removeBlueTones(pixels);
        man.createPPM(pixels, "./src/project_1/aufgabe_1.ppm");
    }

    // Exercise 2
    private static void exerciseTwo(ImageManipulation man) {
        ArrayList<Pixel> pixels = man.getPixels();
        pixels = man.increaseBlue(pixels);
        man.createPPM(pixels, "./src/project_1/aufgabe_2.ppm");
    }

    // Exercise 3
    private static void exerciseThree(ImageManipulation man) {
        ArrayList<Pixel> pixels = man.getPixels();
        pixels = man.increaseAll(pixels);
        man.createPPM(pixels, "./src/project_1/aufgabe_3.ppm");
    }

    // Exercise 4
    private static void exerciseFour(ImageManipulation man) {
        ArrayList<Pixel> pixels = man.getPixels();
        man.changeMaxColorValue(pixels,5);
        man.createPPM(pixels, "./src/project_1/aufgabe_4.ppm");
    }

    // Exercise 5
    private static void exerciseFive(ImageManipulation man) {
        ArrayList<Pixel> pixels = man.getPixels();
        ArrayList<Integer> values = man.convertToPGM(pixels);
        man.createPGM(values, "./src/project_1/aufgabe_5.pgm");
    }

    // Exercise 6
    private static void exerciseSix(ImageManipulation man) {
        man.createPBM("./src/project_1/aufgabe_6.pgm");
    }

    public static void main(String[] args) {
        // create path to ppm file
        String cwd = System.getProperty("user.dir");  // get current working directory
        String path = cwd + "/src/project_1/tulip.ppm";

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
