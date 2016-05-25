package pl.edu.pwr.elm;

import java.io.*;
import java.util.*;

public class ConfigReader {
    private static final String SOURCE_PATH = "datasets.txt";
    Map<String, String> enableDatasets;

    public ConfigReader() {
        final Scanner in;
        enableDatasets = new HashMap<>();
        int pointer = 1;
        try {
            in = new Scanner(new FileReader(SOURCE_PATH));
            while (in.hasNext()) {
                String singlePath = in.next();
                String name = pointer + ". " + singlePath.substring(0, singlePath.length() -4);
                enableDatasets.put(name, singlePath);
                pointer++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addPathToDataset(String newPath) {
        try {
            File sourceFile = new File(SOURCE_PATH);
            PrintWriter out = new PrintWriter(new FileWriter(sourceFile, true));
            out.append(newPath +  "\n");
            out.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file " + SOURCE_PATH);
        }
    }

    public List<String> getEnableDatasetsNames() {
        List<String> enablePaths = new ArrayList<>();
        enablePaths.addAll(enableDatasets.keySet());
        return enablePaths;
    }

    public String getFilePath(String key) {
        return enableDatasets.get(key);
    }
}
