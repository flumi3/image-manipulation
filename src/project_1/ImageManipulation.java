package project_1;

import java.io.*;
import java.util.ArrayList;

public class ImageManipulation {
    protected String path;
    protected String ppmFormat;
    protected int numOfColumns;
    protected int numOfLines;
    protected int maxColorValue;

    public ImageManipulation(String path) {
        this.path = path;

        // get format lines (P3, 1024 768, 255)
        String[] formatLines = this.getFormatLines();
        this.ppmFormat = formatLines[0];
        this.maxColorValue = Integer.parseInt(formatLines[2]);

        String[] s = formatLines[1].split(" ");
        this.numOfColumns = Integer.parseInt(s[0]);
        this.numOfLines = Integer.parseInt(s[1]);
    }

    protected String[] getFormatLines() {
        String[] lines = new String[3];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.path));
            String line = reader.readLine();
            int counter = 0;
            while (line != null && counter < 3) {
                lines[counter] = line;
                line = reader.readLine();
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    protected ArrayList<Pixel> getPixels() {
        ArrayList<Pixel> pixels = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.path));
            String line = reader.readLine();
            int lineCounter = 0;  // counts number of read lines

            while (line != null) {
                // ignore the first 3 lines because they are not part of the color codes
                if (lineCounter > 2) {
                    String[] values = line.split(" ");  // Split the line into its values

                    int r = -1;
                    int g = -1;
                    int valueCounter = 0;  // counts to 3 in order to add the rgb values to each pixel

                    // loop through all values of this line
                    for (String value : values) {
                        if (valueCounter == 0) {
                            r = Integer.parseInt(value);
                            valueCounter++;
                        } else if (valueCounter == 1) {
                            g = Integer.parseInt(value);
                            valueCounter++;
                        } else if (valueCounter == 2) {
                            int b = Integer.parseInt(value);
                            pixels.add(new Pixel(r, g, b));
                            valueCounter = 0;
                        }
                    }
                }
                lineCounter++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pixels;
    }

    protected ArrayList<Pixel> removeBlueTones(ArrayList<Pixel> pixels) {
        for (Pixel pixel : pixels) {
            pixel.b = 0;
        }
        return pixels;
    }

    protected ArrayList<Pixel> increaseBlue(ArrayList<Pixel> pixels) {
        for (Pixel pixel : pixels) {
            if (pixel.b > pixel.r && pixel.b > pixel.g) {
                pixel.r = 0;
                pixel.g = 0;
                pixel.b = 255;
            }
        }
        return pixels;
    }

    protected ArrayList<Pixel> increaseAll(ArrayList<Pixel> pixels) {
        for (Pixel pixel : pixels) {
            if (pixel.r >= pixel.g && pixel.r >= pixel.b)
                pixel.r = 255;
            if (pixel.g >= pixel.r && pixel.g >= pixel.b)
                pixel.g = 255;
            if (pixel.b >= pixel.r && pixel.b >= pixel.g)
                pixel.b = 255;
        }
        return pixels;
    }

    protected ArrayList<Pixel> changeMaxColorValue(ArrayList<Pixel> pixels, int value) {
        this.maxColorValue = value;

        // Calculate how many percent the new value represents of 255
        double percentage = (100.0 / 255.0) * value;

        // Scale down the actual values of the color components
        for (Pixel pixel : pixels) {
            pixel.r = (int) ((pixel.r / 100.0) * percentage);
            pixel.g = (int) ((pixel.g / 100.0) * percentage);
            pixel.b = (int) ((pixel.b / 100.0) * percentage);
        }
        return pixels;
    }

    protected ArrayList<Integer> convertToPGM(ArrayList<Pixel> pixels) {
        ArrayList<Integer> values = new ArrayList<>();
        for (Pixel pixel : pixels) {
            int y = (int) (0.299 * pixel.r + 0.587 * pixel.g + 0.114 * pixel.b);
            values.add(y);
        }
        return values;
    }

    protected void createPGM(ArrayList<Integer> values, String path) {
        String pgmFormat = "P2\n" + this.numOfColumns + " " + this.numOfLines + "\n" + "255\n";

        try {
            File pgmFile = new File(path);
            if (pgmFile.exists()) {
                pgmFile.delete();
                pgmFile.createNewFile();
            }

            // create buffered writer in appending mode
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.append(pgmFormat);

            StringBuilder line = new StringBuilder();
            int valuesPlacedInLine = 0;
            for (int value : values) {
                if (valuesPlacedInLine == this.numOfColumns - 1) {
                    // add last value to the line and append to the pgm file
                    line.append(value).append("\n");
                    writer.append(line);

                    // reset
                    line = new StringBuilder();
                    valuesPlacedInLine = 0;
                } else {
                    line.append(value).append(" ");
                    valuesPlacedInLine++;
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void createPPM(ArrayList<Pixel> pixels, String path) {
        StringBuilder ppm = new StringBuilder(this.ppmFormat + "\n" + this.numOfColumns + " " + this.numOfLines + "\n"
                + this.maxColorValue + "\n");

        int pixelsPlacedInLine = 0;
        for (Pixel pixel : pixels) {
            if (pixelsPlacedInLine == this.numOfColumns-1) {
                ppm.append(pixel.r).append(" ");
                ppm.append(pixel.g).append(" ");
                ppm.append(pixel.b).append("\n");
                pixelsPlacedInLine = 0;
            } else {
                ppm.append(pixel.r).append(" ");
                ppm.append(pixel.g).append(" ");
                ppm.append(pixel.b).append(" ");
                pixelsPlacedInLine++;
            }
        }

        // write string to file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(ppm.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void createPBM(String path) {
        String pbmFormat = "P1\n" + "100 100\n";

        // create the white line
        StringBuilder whiteLine = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            if (i == 99)
                whiteLine.append(0).append("\n");
            else
                whiteLine.append(0).append(" ");
        }

        // create the line with 40 black pixels in the middle
        StringBuilder mixedLine = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            if (i > 29 && i < 70) {
                mixedLine.append(1).append(" ");
            } else if (i == 99) {
                mixedLine.append(0).append("\n");
            } else {
                mixedLine.append(0).append(" ");
            }
        }

        try {
            File pbmFile = new File(path);
            if (pbmFile.exists()) {
                pbmFile.delete();
                pbmFile.createNewFile();
            }

            // create buffered writer in appending mode
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.append(pbmFormat);

            for (int i = 0; i < 100; i++) {
                if (i > 29 && i < 70) {
                    writer.append(mixedLine);
                } else {
                    writer.append(whiteLine);
                }
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
