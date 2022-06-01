package Model;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Settings {

    public final static int scale = 5;
    public final static boolean DEBUG = false;

    public final static String MAP_DIRECTORY = "./Ressources/Tests/";
    public static String curentFile = getAllMapFiles().get(0);

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static List<Color> paletteTruck;

    public static void generatePaletteFromFile(String filename)
    {

    }

    public static void generateRandomPalette(int n)
    {
        Random rand = new Random();
        paletteTruck = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            paletteTruck.add(new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256)));
        }
    }

    public static List<Color> getPalette() {
        return paletteTruck;
    }

    public static List<String> getAllMapFiles()
    {
        File f = new File(MAP_DIRECTORY);
        return Arrays.asList(f.list());
    }
}
