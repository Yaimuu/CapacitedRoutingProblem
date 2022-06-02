package Model;

import Model.MetaHeuristcs.MetaHeuristic;
import Model.MetaHeuristcs.RecuitSimule;
import Model.MetaHeuristcs.TabouMethod;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static Map<String, MetaHeuristic> metaHeuristicMap = Stream.of(new Object[][] {
            { "Recuit", new RecuitSimule() },
            { "Tabou", new TabouMethod() },
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (MetaHeuristic) data[1]));

    public static MetaHeuristic currentHeuristic = metaHeuristicMap.get(metaHeuristicMap.keySet().toArray()[0]);

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

    public static void updateCurrentHeuristic(String key) {
        currentHeuristic = metaHeuristicMap.get(key);
    }
}
