package pl.edu.pwr.elm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DataCreator {
    double[][] inputs;
    double[] outputs;

    private List<double[]> dataInputRows;
    private List<String> outputsNamesList;
    private boolean dataReady = false;
    private List<Output> outputValues;
    private double[] outputsAsDouble;
    private String filePath;
    private Map<String, Output> outputMap = new HashMap<>();
    private List<String> uniqueOutput = new ArrayList<>();
    private List<Double> maxValues;

    private DataCreator(String filePath) {
        this.filePath = filePath;
    }

    public static DataCreator from(String filePath) {
        return new DataCreator(filePath);
    }

    public List<Output> getOutputValues() {
        return outputValues;
    }

    public void readData() {
        final Scanner in;
        dataInputRows = new ArrayList<>();
        outputsNamesList = new ArrayList<>();
        outputValues = new ArrayList<>();
        maxValues = new ArrayList<>();
        try {
            in = new Scanner(new FileReader(filePath));
            while (in.hasNext()) {
                final String[] columns = in.nextLine().replaceAll(" ", "").split(",");
                double[] values = new double[columns.length - 1];
                try {
                    for (int i = 0; i < columns.length - 1; i++) {
                        values[i] = Double.parseDouble(columns[i]);
                        if (i >= maxValues.size()) {
                            maxValues.add(values[i]);
                        } else if (maxValues.get(i) < values[i]) {
                            maxValues.set(i, values[i]);
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
                dataInputRows.add(values);
                String res = columns[columns.length - 1];
                outputsNamesList.add(res);
                if (!uniqueOutput.contains(res)) {
                    uniqueOutput.add(res);
                }
            }
            replaceResultNamesToNumbers();
//            normalizeArray();
            dataReady = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(outputMap);
    }

    private void normalizeArray() {
        for (double[] dataInputRow : dataInputRows) {
            for (int j = 0; j < dataInputRow.length; j++) {
                double v = dataInputRow[j];
                dataInputRow[j] = v / maxValues.get(j);
            }
        }
    }

    public void replaceResultNamesToNumbers() {
//        System.out.println(outputsNamesList);
        for (String res : outputsNamesList) {
            double[] outputArray = new double[uniqueOutput.size()];
            for (int i = 0; i < uniqueOutput.size(); i++) {
                String var = uniqueOutput.get(i);
                if (var.equals(res)) {
                    outputArray[i] = 1;
                } else {
                    outputArray[i] = 0;
                }
            }
            outputMap.put(res, new Output(outputArray));
        }

        for (String list : outputsNamesList) {
            outputValues.add(outputMap.get(list));
        }
    }

    public double[][] getDataInputRows() {
        checkDataReady();
        double[][] inputs = new double[dataInputRows.size()][];
        dataInputRows.toArray(inputs);
        return inputs;
    }

    private void checkDataReady() {
        if (!dataReady) throw new RuntimeException("You need to call ReadData first");
    }

    public double[] getOutputs() {
        return outputsAsDouble;
    }

    public int getNumberOfInputsClasses() {
        checkDataReady();
        return dataInputRows.get(0).length;
    }

    public int getNumberOfOutputClasses() {
        checkDataReady();
        return outputValues.get(0).getOutput().length;
    }

    public int getRowCount() {
        checkDataReady();
        return dataInputRows.size();
    }
}
