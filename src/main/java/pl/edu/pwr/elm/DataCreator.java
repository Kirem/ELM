package pl.edu.pwr.elm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import pl.edu.pwr.elm.model.IrisModel;

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

    private DataCreator(String filePath) {
        this.filePath = filePath;
    }

    public static DataCreator from(String filePath) {
        return new DataCreator(filePath);
    }

    public static double[][] createInputs(List<IrisModel> irisModels) {
        double[][] inputs = new double[irisModels.size()][];
        for (int i = 0; i < irisModels.size(); i++) {
            double[] singleInput = {irisModels.get(i).getSepalLength(), irisModels.get(i).getSepalWidth(),
                    irisModels.get(i).getPetalLength(), irisModels.get(i).getPetalWidth()};

            inputs[i] = singleInput;
        }
        return inputs;
    }

    public static double[] createOutputs(List<IrisModel> irisModels) {
        double[] outputs = new double[irisModels.size()];
        for (int i = 0; i < irisModels.size(); i++) {
            outputs[i] = irisModels.get(i).getIrisTypeNumber();
        }
        return outputs;
    }

    public List<Output> getOutputValues() {
        return outputValues;
    }

    public void readData() {
        final Scanner in;
        dataInputRows = new ArrayList<>();
        outputsNamesList = new ArrayList<>();
        outputValues = new ArrayList<>();
        try {
            in = new Scanner(new FileReader(filePath));
            while (in.hasNext()) {
                final String[] columns = in.next().split(",");
                double[] values = new double[columns.length - 1];
                for (int i = 0; i < columns.length - 1; i++) {
                    values[i] = Double.parseDouble(columns[i]);
                }
                dataInputRows.add(values);
                String res = columns[columns.length - 1];
                outputsNamesList.add(res);
                if (!uniqueOutput.contains(res)) {
                    uniqueOutput.add(res);
                }
            }
            replaceResultNamesToNumbers();
            dataReady = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(outputMap);
    }

    public void replaceResultNamesToNumbers() {
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
