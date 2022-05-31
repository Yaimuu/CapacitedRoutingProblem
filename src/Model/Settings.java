package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Settings {

    public final static int scale = 5;
    public final static boolean DEBUG = true;

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
}
