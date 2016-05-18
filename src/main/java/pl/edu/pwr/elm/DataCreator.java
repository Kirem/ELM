package pl.edu.pwr.elm;

import pl.edu.pwr.elm.model.IrisModel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataCreator {
    double [][] inputs;
    double [] outputs;

    private static List<double[]> dataInputRows;
    private static List<String> outputsNamesList;
    private static double [] outputsAsDouble;

    public DataCreator() {

    }

    public static void readData(String filePath) {
        final Scanner in;
        dataInputRows = new ArrayList<>();
        outputsNamesList = new ArrayList<>();
//        final List<IrisModel> irisModelsList= new ArrayList<IrisModel>();
        try {
            in = new Scanner(new FileReader(filePath));
            while (in.hasNext()) {
                final String[] columns = in.next().split(",");
                double[] values = new double[columns.length - 1];
                for(int i=0; i < columns.length - 1; i++) {
                    values[i] = Double.parseDouble(columns[i]);
                }
                dataInputRows.add(values);
                outputsNamesList.add(columns[columns.length-1]);

//                final IrisModel irisModel = new IrisModel();
//                irisModel.setSepalLength(Double.valueOf(columns[0]));
//                irisModel.setSepalWidth(Double.valueOf(columns[1]));
//                irisModel.setPetalLength(Double.valueOf(columns[2]));
//                irisModel.setPetalWidth(Double.valueOf(columns[3]));
//                irisModel.setIrisType(columns[4]);
//                irisModelsList.add(irisModel);
//                System.out.println("iris type: " + irisModel.getIrisType());
            }
            replaceResultNamesToNumbers();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        return irisModelsList;
    }

    public static void replaceResultNamesToNumbers() {
        int typeIndex = 0;
        outputsAsDouble = new double[outputsNamesList.size()];
        outputsAsDouble[0] = typeIndex;
        for(int i = 1; i < outputsNamesList.size(); i++) {
            if(!outputsNamesList.get(i-1).equals(outputsNamesList.get(i))) {
                typeIndex++;
            }
            outputsAsDouble[i] = typeIndex;
        }
    }

    public static double[][] getDataInputRows(){
        double[][] inputs = new double[dataInputRows.size()][];
        dataInputRows.toArray(inputs);
        return inputs;
    }

    public static double[] getOutputs() {
        return outputsAsDouble;
    }

    public static int getNumberOfInputsClasses() {
        return dataInputRows.get(0).length;
    }

    public static List<IrisModel> readIrisData() {
        final Scanner in;
        final List<IrisModel> irisModelsList= new ArrayList<IrisModel>();
        try {
            in = new Scanner(new FileReader("iris.txt"));
            int j = 0;
            while (in.hasNext()) {
                final String[] columns = in.next().split(",");
                final IrisModel irisModel = new IrisModel();
                irisModel.setSepalLength(Double.valueOf(columns[0]));
                irisModel.setSepalWidth(Double.valueOf(columns[1]));
                irisModel.setPetalLength(Double.valueOf(columns[2]));
                irisModel.setPetalWidth(Double.valueOf(columns[3]));
                irisModel.setIrisType(columns[4]);
                irisModelsList.add(irisModel);
                System.out.println("iris type: " + irisModel.getIrisType());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return irisModelsList;
    }

    public static double[][] createInputs(List<IrisModel> irisModels) {
        double [][] inputs = new double[irisModels.size()][];
        for (int i = 0; i < irisModels.size(); i++) {
            double[] singleInput = {irisModels.get(i).getSepalLength(), irisModels.get(i).getSepalWidth(),
                    irisModels.get(i).getPetalLength(), irisModels.get(i).getPetalWidth()};

            inputs[i] = singleInput;
        }
        return inputs;
    }

    public static double [] createOutputs(List<IrisModel> irisModels) {
        double[] outputs = new double[irisModels.size()];
        for (int i = 0; i < irisModels.size(); i++) {
            outputs[i] = irisModels.get(i).getIrisTypeNumber();
        }
        return outputs;
    }
}
