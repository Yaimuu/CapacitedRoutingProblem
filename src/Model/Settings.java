package Model;

import Model.MetaHeuristcs.MetaHeuristic;
import Model.MetaHeuristcs.MetaheuristicName;
import Model.MetaHeuristcs.RecuitSimule;
import Model.MetaHeuristcs.TabouMethod;
import com.opencsv.CSVWriter;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Settings {

    public final static boolean DEBUG = false;

    public final static String STATS_DIRECTORY = "./Ressources/";
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

    public static int scale = 5;
    private static List<Color> paletteTruck;

    public static Course currentCourse = Course.getInstance();

    public static Map<String, MetaHeuristic> metaHeuristicMap = Stream.of(new Object[][] {
            { "Recuit", new RecuitSimule() },
            { "Tabou", new TabouMethod() },
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (MetaHeuristic) data[1]));

    public static MetaHeuristic currentHeuristic = metaHeuristicMap.get(metaHeuristicMap.keySet().toArray()[0]);

    private static Map<String, List<Float>> recuitSettings = Stream.of(new Object[][] {
            { "T0", Arrays.asList(new Float[]{10000f, 0f, 10000f})},
            { "N1", Arrays.asList(new Float[]{100f, 0f, 10000f})},
            { "N2", Arrays.asList(new Float[]{100f, 0f, 10000f})},
            { "Mu", Arrays.asList(new Float[]{0.99f, 0f, 1f})},
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (List<Float>) data[1]));

    private static Map<String, List<Float>> TabouSettings = Stream.of(new Object[][] {
            { "MaxIter", Arrays.asList(new Float[]{10000f, 0f, 10000f}) }
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (List<Float>) data[1]));

    public static Map<String, List<Float>> currentHeuristicSettings = TabouSettings;

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

    public static void updateCourse(Course course) {
        try {
            currentCourse = (Course) course.clone();
            currentHeuristic.setSolution((Course) currentCourse.clone());
        }catch (Exception e) {}

        switch (currentHeuristic.getName()) {
            case TABOU:
                currentHeuristicSettings = TabouSettings;
                break;
            case RECUIT:
                currentHeuristicSettings = recuitSettings;
                break;
        }
    }

    public static Map<String, List<Float>> getRecuitSettings() {
        return recuitSettings;
    }

    public static Map<String, List<Float>> getTabouSettings() {
        return TabouSettings;
    }

    public static void exportStatsResults(List<String[]> data, String name) throws IOException
    {
        try (CSVWriter writer = new CSVWriter(new FileWriter(Settings.STATS_DIRECTORY + name + ".csv"), ';', '"', '"', "\n" ) ) {
            writer.writeAll(data);
            System.out.println("File " + Settings.STATS_DIRECTORY + name + ".csv" + " successfully created !");
        }
    }

//    public String convertToCSV(String[] data) {
//        return Stream.of(data)
//                .map(this::escapeSpecialCharacters)
//                .collect(Collectors.joining(","));
//    }
}
